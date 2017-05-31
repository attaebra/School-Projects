/*
	FUSE: Filesystem in Userspace
	Copyright (C) 2001-2007  Miklos Szeredi <miklos@szeredi.hu>

	This program can be distributed under the terms of the GNU GPL.
	See the file COPYING.
*/

/*
	Had some issues with the old functions that I didn't anticipate.
	Ended up revamping them for the most part.
	Had a LOT of issues with MKNOD where I would get a directory doesn't
	exist error on creating a file. It should work right now.
*/
#define	FUSE_USE_VERSION 26

#include <stdlib.h>
#include <fuse.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>

//size of a disk block
#define	BLOCK_SIZE 512

//we'll use 8.3 filenames
#define	MAX_FILENAME 8
#define	MAX_EXTENSION 3

//How many files can there be in one directory?
#define MAX_FILES_IN_DIR (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + (MAX_EXTENSION + 1) + sizeof(size_t) + sizeof(long))

//The attribute packed means to not align these things
struct cs1550_directory_entry
{
	int nFiles;	//How many files are in this directory.
				//Needs to be less than MAX_FILES_IN_DIR

	struct cs1550_file_directory
	{
		char fname[MAX_FILENAME + 1];	//filename (plus space for nul)
		char fext[MAX_EXTENSION + 1];	//extension (plus space for nul)
		size_t fsize;					//file size
		long nStartBlock;				//where the first block is on disk
	} __attribute__((packed)) files[MAX_FILES_IN_DIR];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_FILES_IN_DIR * sizeof(struct cs1550_file_directory) - sizeof(int)];
} ;

typedef struct cs1550_root_directory cs1550_root_directory;

#define MAX_DIRS_IN_ROOT (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + sizeof(long))

struct cs1550_root_directory
{
	int nDirectories;	//How many subdirectories are in the root
						//Needs to be less than MAX_DIRS_IN_ROOT
	struct cs1550_directory
	{
		char dname[MAX_FILENAME + 1];	//directory name (plus space for nul)
		long nStartBlock;				//where the directory block is on disk
	} __attribute__((packed)) directories[MAX_DIRS_IN_ROOT];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_DIRS_IN_ROOT * sizeof(struct cs1550_directory) - sizeof(int)];
} ;

typedef struct cs1550_directory_entry cs1550_directory_entry;

//How much data can one block hold?
#define	MAX_DATA_IN_BLOCK (BLOCK_SIZE)

struct cs1550_disk_block
{
	//The next disk block, if needed. This is the next pointer in the linked 
	//allocation list
	long nNextBlock;

	//All of the space in the block can be used for actual data
	//storage.
	char data[MAX_DATA_IN_BLOCK];
};

typedef struct cs1550_disk_block cs1550_disk_block;

#define MAX_FAT_ENTRIES (BLOCK_SIZE/sizeof(short))

struct cs1550_file_alloc_table_block {
	short table[MAX_FAT_ENTRIES];
};

typedef struct cs1550_file_alloc_table_block cs1550_fat_block;

/*
 * Called whenever the system wants to know the file attributes, including
 * simply whether the file exists or not. 
 *
 * man -s 2 stat will show the fields of a stat structure
 */
static int cs1550_getattr(const char *path, struct stat *stbuf)
{
	struct cs1550_root_directory root;
	int res = 0;
	memset(stbuf, 0, sizeof(struct stat));

	FILE *f = fopen(".disk", "rb+");

	fread(&root, sizeof(struct cs1550_root_directory), 1, f);

	//Is root
	if (strcmp(path, "/") == 0) {
		stbuf->st_mode = S_IFDIR | 0755;
		stbuf->st_nlink = 2;
	} 
	else{
		char name[MAX_FILENAME+1];
		char filename[MAX_FILENAME+1];
		char ext[MAX_EXTENSION+1];

		//Check if sub-directory
		if(sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext) == 1){
			int i;
			for(i = 0; i < root.nDirectories; ++i){
				if(strcmp(root.directories[i].dname, name) == 0){
					stbuf->st_mode = S_IFDIR | 0755;
					stbuf->st_nlink = 2;
					res = 0;
					fclose(f);
					return res;
				}
			}
			res = -ENOENT;
		}
		else if(sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext) == 3){

			int d_block_num = 0;
			int i;
			for(i = 0; i < root.nDirectories; ++i){
				if(strcmp(root.directories[i].dname, name) == 0){
					d_block_num = root.directories[i].nStartBlock;
					break;
				}else if(i == (root.nDirectories - 1)){
					printf("Directory not found.\n");
				}
			}

			struct cs1550_directory_entry current_dir;

			fseek(f, BLOCK_SIZE * d_block_num, SEEK_SET);
			fread(&current_dir, sizeof(current_dir), 1, f);

			for(i = 0; i < current_dir.nFiles; ++i){
				if(strcmp(current_dir.files[i].fname, filename) == 0 && strcmp(current_dir.files[i].fext, ext) == 0){
					stbuf->st_size = current_dir.files[i].fsize;
					stbuf->st_mode = S_IFREG | 0666;
					stbuf->st_nlink = 1;
					fclose(f);
					return 0;
				}
			}
			res = -ENOENT;
		}else{
			res = -ENOENT;
		}
	}
	fclose(f);
	return res;
}

/* 
 * Called whenever the contents of a directory are desired. Could be from an 'ls'
 * or could even be when a user hits TAB to do autocompletion
 */
static int cs1550_readdir(const char *path, void *buf, fuse_fill_dir_t filler,
			 off_t offset, struct fuse_file_info *fi)
{
	//Since we're building with -Wall (all warnings reported) we need
	//to "use" every parameter, so let's just cast them to void to
	//satisfy the compiler
	(void) offset;
	(void) fi;

	//This line assumes we have no subdirectories, need to change
	char name[MAX_FILENAME+1];
	char filename[MAX_FILENAME+1];
	char ext[MAX_EXTENSION+1];
	sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext);

	FILE *f = fopen(".disk", "rb+");

	struct cs1550_root_directory root;
	struct cs1550_directory_entry dir;
	fread(&root, sizeof(cs1550_root_directory), 1, f);

	//the filler function allows us to add entries to the listing
	//read the fuse.h file for a description (in the ../include dir)
	filler(buf, ".", NULL, 0);
	filler(buf, "..", NULL, 0);

	//Determine if root or directory is being accessed
	if(strcmp(path, "/") == 0){
		int i;
		for(i = 0; i < root.nDirectories; ++i){
			filler(buf, root.directories[i].dname, NULL, 0);
		}
	}
	else{
		int i;
		for(i = 0; i < root.nDirectories; ++i){
			if(strcmp(root.directories[i].dname, name) == 0){
				fseek(f, BLOCK_SIZE * root.directories[i].nStartBlock, SEEK_SET);
				fread(&dir, sizeof(dir), 1, f);
				char s[15];
				int t;
				for(t = 0; t < dir.nFiles; ++t){
					strcpy(s, dir.files[t].fname);
					strcat(s, ".");
					strcat(s, dir.files[t].fext);
					filler(buf, s, NULL, 0);
				}
			}
		}
	}

	fclose(f);
	return 0;
}

/* 
 * Creates a directory. We can ignore mode since we're not dealing with
 * permissions, as long as getattr returns appropriate ones for us.
 */
static int cs1550_mkdir(const char *path, mode_t mode)
{
	(void) path;
	(void) mode;

	FILE *f = fopen(".disk", "rb+");

	if(strcmp(path, "/") == 0){return -EPERM;}

	char name[MAX_FILENAME+1];
	char filename[MAX_FILENAME+1];
	char ext[MAX_EXTENSION+1];

	sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext);

	//Check if name length is valid
	if(strlen(name) > MAX_FILENAME+1){return -ENAMETOOLONG;}

	struct cs1550_root_directory root;
	fread(&root, sizeof(struct cs1550_root_directory), 1, f);

	//Check if directory exists.
	int i;
	for(i = 0; i < root.nDirectories; ++i){
		if(strcmp(root.directories[i].dname, name) == 0){
			printf("The directory already exists.\n");
			fclose(f);
			return -EEXIST;
		}
	}

	//Updates start block
	strcpy(root.directories[root.nDirectories].dname, name);
	root.directories[root.nDirectories].nStartBlock = (root.nDirectories + 1);
	++root.nDirectories;

	fseek(f, 0, SEEK_SET);
	fwrite(&root, sizeof(struct cs1550_root_directory), 1, f);
	fclose(f);

	return 0;
}

/* 
 * Removes a directory.
 */
static int cs1550_rmdir(const char *path)
{
	(void) path;
    return 0;
}

/* 
 * Does the actual creation of a file. Mode and dev can be ignored.
 * I think I have problems with this still.
 */
static int cs1550_mknod(const char *path, mode_t mode, dev_t dev)
{
	(void) mode;
	(void) dev;

	char name[MAX_FILENAME+1];
	char filename[MAX_FILENAME+1];
	char ext[MAX_EXTENSION+1];

	//Make sure not root directory
	if(sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext) < 3){
		return -EPERM;
	}

	//Check file length
	if(strlen(filename) > 8 || strlen(ext) > 3){
		return -ENAMETOOLONG;
	}

	//Does file exist
	FILE* disk = fopen(".disk", "rb+");
	struct cs1550_root_directory root;
	int d_block = 0;

	fread(&root, sizeof(root), 1, disk);

	//Find directory index
	int i;
	for(i = 0; i < root.nDirectories; ++i){
		if(strcmp(root.directories[i].dname, name) == 0){
			d_block = root.directories[i].nStartBlock;
			break;
		}
	}

	//Determine if file already exists
	struct cs1550_directory_entry entry;
	fseek(disk, BLOCK_SIZE * d_block, SEEK_SET);
	fread(&entry, sizeof(entry), 1, disk);

	for(i = 0; i < entry.nFiles; ++i){
		if(strcmp(entry.files[i].fname, filename) == 0){
			printf("This file already exists.\n");
			fclose(disk);
			return -EEXIST;
		}
	}

	//Copy information
	strcpy(entry.files[entry.nFiles].fname, filename);
	strcpy(entry.files[entry.nFiles].fext, ext);

	//FAT
	struct cs1550_file_alloc_table_block FAT;
	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fread(&FAT, sizeof(FAT), 1, disk);

	//Determine start of file
	for(i = 0; i < MAX_FAT_ENTRIES; ++i){
		if(FAT.table[i] != -1){
			entry.files[entry.nFiles].nStartBlock = i + MAX_DIRS_IN_ROOT + 1;
			FAT.table[i] = -1;
			break;
		}
		else if(i == (MAX_FAT_ENTRIES - 1)){
			printf("Error: No space on disk.\n");
			fclose(disk);
			return -1;
		}
	}

	++entry.nFiles;
	fseek(disk, BLOCK_SIZE * d_block, SEEK_SET);
	fwrite(&entry, sizeof(entry), 1, disk);

	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fwrite(&FAT, sizeof(FAT), 1, disk);

	fclose(disk);
	return 0;
}

/*
 * Deletes a file
 */
static int cs1550_unlink(const char *path)
{
    (void) path;

    return 0;
}

/* 
 * Read size bytes from file into buf starting from offset
 *
 */
static int cs1550_read(const char *path, char *buf, size_t size, off_t offset,
			  struct fuse_file_info *fi)
{
	(void) buf;
	(void) offset;
	(void) fi;
	(void) path;

	//check to make sure path exists
	//extract file path details
	char name[MAX_FILENAME+1];
	char filename[MAX_FILENAME+1];
	char ext[MAX_EXTENSION+1];

	sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext);
	if(strlen(filename) == 0 || *filename < 65 || *filename == 96){return -EISDIR;}
	

	//Open disk and retrieve directory information
	FILE *f = fopen(".disk", "rb+");
	struct cs1550_root_directory root;
	int d_block = 0;

	fread(&root, sizeof(root), 1, f);

	//Find index of directory
	int i;
	for(i = 0; i < root.nDirectories; ++i){
		if(strcmp(root.directories[i].dname, name) == 0){
			d_block = root.directories[i].nStartBlock;
			break;
		}
	}

	//Find file information by reading directory block
	struct cs1550_directory_entry entry;
	fseek(f, BLOCK_SIZE * d_block, SEEK_SET);
	fread(&entry, BLOCK_SIZE, 1, f);
	int f_block = 0;

	for(i = 0; i < entry.nFiles; ++i){
		if(strcmp(entry.files[i].fname, filename) == 0){
			f_block = entry.files[i].nStartBlock;
			if(entry.files[i].fsize < offset){
				fclose(f);
				return -EFBIG;
			}
			break;
		}
	}

	//Check that file size is > 0 
	if((int)size < 0 || offset > entry.files[i].fsize){
		fclose(f);
		return -1;
	}


	//FAT
	struct cs1550_file_alloc_table_block FAT;
	fseek(f, -BLOCK_SIZE, SEEK_END);
	fread(&FAT, sizeof(FAT), 1, f);

	//Read in data
	fseek(f, (BLOCK_SIZE * f_block) + offset, SEEK_SET);
	int fat_location = f_block - MAX_DIRS_IN_ROOT - 1;
	int rs = entry.files[i].fsize - offset;

	if(rs < (BLOCK_SIZE - offset)){
		fread(&buf[0], rs, 1, f);
	}
	else{
		int buf_location = 0;
		fread(&buf[0], (BLOCK_SIZE - offset), 1, f);
		rs -= BLOCK_SIZE - offset;
		buf_location += (BLOCK_SIZE - offset);
		fat_location = FAT.table[fat_location];

		while(FAT.table[fat_location] != -1){
			fseek(f, BLOCK_SIZE * (fat_location + MAX_DIRS_IN_ROOT + 1), SEEK_SET);
			fread(&buf[buf_location], BLOCK_SIZE, 1, f);
			buf_location += BLOCK_SIZE;
			rs -= BLOCK_SIZE;
			fat_location = FAT.table[fat_location];
		}

		fseek(f, BLOCK_SIZE * (fat_location + MAX_DIRS_IN_ROOT + 1), SEEK_SET);
		fread(&buf[buf_location], rs, 1, f);
	}
	fclose(f);
	size = (entry.files[i].fsize - offset);

	return size;
}

/* 
 * Write size bytes from buf into file starting from offset
 *
 */
static int cs1550_write(const char *path, const char *buf, size_t size,
			  off_t offset, struct fuse_file_info *fi)
{
	(void) buf;
	(void) offset;
	(void) fi;
	(void) path;

	//check that size is > 0
	if((int) size < 0){
		printf("Error: Size must be > 0\n");
		return -1;
	}

	//Ensure path exists
	char name[MAX_FILENAME+1];
	char filename[MAX_FILENAME+1];
	char ext[MAX_EXTENSION+1];
	sscanf(path, "/%[^/]/%[^.].%s", name, filename, ext);

	//Open disk for reading and writing
	FILE *f = fopen(".disk", "rb+");
	struct cs1550_root_directory root;
	int d_block = 0;

	//Read root information
	fread(&root, sizeof(root), 1, f);

	//Find index of the directory
	int i;
	for(i = 0; i < root.nDirectories; ++i){
		if(strcmp(root.directories[i].dname, name) == 0){
			d_block = root.directories[i].nStartBlock;
			break;
		}
	}

	struct cs1550_directory_entry entry;
	fseek(f, BLOCK_SIZE * d_block, SEEK_SET);
	fread(&entry, sizeof(entry), 1, f);

	int f_block = 0;

	//Find file index
	for(i = 0; i < entry.nFiles; ++i){
		if(strcmp(entry.files[i].fname, filename) == 0){
			f_block = entry.files[i].nStartBlock;
			if(entry.files[i].fsize < offset){
				fclose(f);
				return -EFBIG;
			}
			break;
		}
	}

	//Read FAT
	struct cs1550_file_alloc_table_block FAT;
	fseek(f, -BLOCK_SIZE, SEEK_END);
	fread(&FAT, sizeof(FAT), 1, f);

	//Write
	fseek(f, (BLOCK_SIZE * f_block) + offset, SEEK_SET);
	int write_size = size;
	int fat_location = f_block - MAX_DIRS_IN_ROOT - 1;
	int buf_location = 0;

	if(size < BLOCK_SIZE){fwrite(&buf[0], size, 1, f);}	

	//Multiple blocks
	else{
		fwrite(&buf[0], (BLOCK_SIZE - offset), 1, f);
		write_size -= (BLOCK_SIZE - offset);
		buf_location += (BLOCK_SIZE - offset);

		int x;
		while(write_size > BLOCK_SIZE){
			for(x = 0; x < MAX_FAT_ENTRIES; ++x){
				if(FAT.table[x] == 0){
					FAT.table[fat_location] = x;
					FAT.table[x] = -1;
					fat_location = x;
					break;
				}else if(x == (MAX_FAT_ENTRIES) - 1){
					printf("Error: Disk size has been exceeded on write.\n");
					fclose(f);
					return -1;
				}
			}
			//Write block to disk
			fseek(f, BLOCK_SIZE * (x + MAX_DIRS_IN_ROOT + 1), SEEK_SET);
			fwrite(&buf[buf_location], BLOCK_SIZE, 1, f);
			write_size -= BLOCK_SIZE;
			buf_location += BLOCK_SIZE;
		}

		//Write to last block
		for(x = 0; x < MAX_FAT_ENTRIES; ++x){
			if(FAT.table[x] == 0){
				FAT.table[fat_location] = x;
				FAT.table[x] = -1;
				fat_location = x;
				break;
			}else if(x == (MAX_FAT_ENTRIES) - 1){
				printf("Error: Disk size has been exceeded on write.\n");
				fclose(f);
				return -1;
			}
		}
		fseek(f, BLOCK_SIZE * (x + MAX_DIRS_IN_ROOT + 1), SEEK_SET);
		fwrite(&buf[buf_location], write_size, 1, f);
		buf_location += write_size;

	}

	//Write FAT
	fseek(f, -BLOCK_SIZE, SEEK_END);
	fwrite(&FAT, sizeof(FAT), 1, f);

	//Ensure set size is the same
	entry.files[i].fsize = offset + size;

	//Write file information
	fseek(f, BLOCK_SIZE * d_block, SEEK_SET);
	fwrite(&entry, sizeof(f), 1, f);

	fclose(f);
	size = entry.files[i].fsize;

	return size;
}

/******************************************************************************
 *
 *  DO NOT MODIFY ANYTHING BELOW THIS LINE
 *
 *****************************************************************************/

/*
 * truncate is called when a new file is created (with a 0 size) or when an
 * existing file is made shorter. We're not handling deleting files or 
 * truncating existing ones, so all we need to do here is to initialize
 * the appropriate directory entry.
 *
 */
static int cs1550_truncate(const char *path, off_t size)
{
	(void) path;
	(void) size;

    return 0;
}


/* 
 * Called when we open a file
 *
 */
static int cs1550_open(const char *path, struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;
    /*
        //if we can't find the desired file, return an error
        return -ENOENT;
    */

    //It's not really necessary for this project to anything in open

    /* We're not going to worry about permissions for this project, but 
	   if we were and we don't have them to the file we should return an error

        return -EACCES;
    */

    return 0; //success!
}

/*
 * Called when close is called on a file descriptor, but because it might
 * have been dup'ed, this isn't a guarantee we won't ever need the file 
 * again. For us, return success simply to avoid the unimplemented error
 * in the debug log.
 */
static int cs1550_flush (const char *path , struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;

	return 0; //success!
}


//register our new functions as the implementations of the syscalls
static struct fuse_operations hello_oper = {
    .getattr	= cs1550_getattr,
    .readdir	= cs1550_readdir,
    .mkdir	= cs1550_mkdir,
	.rmdir = cs1550_rmdir,
    .read	= cs1550_read,
    .write	= cs1550_write,
	.mknod	= cs1550_mknod,
	.unlink = cs1550_unlink,
	.truncate = cs1550_truncate,
	.flush = cs1550_flush,
	.open	= cs1550_open,
};

//Don't change this.
int main(int argc, char *argv[])
{
	return fuse_main(argc, argv, &hello_oper, NULL);
}
