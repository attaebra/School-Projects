//Atta Ebrahimi
//ID3 Tag Editor
//When using this assume all arguments in quotations
//i.e.  ./id3tageditor with.ogg -title "Sail"
//2/9/2016

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct song_tag{
	char tag_tag[3];
	char title[30];
	char artist[30];
	char album[30];
	char year[4]; 
	char comment[28]; 
	char zero_separator[1];
	char track_num[1];
	char genre[1];
};

const char* program_name;

//Reads tag to a struct
int read_tag(FILE* mp3_file, struct song_tag* tag)
{
	fseek(mp3_file, -128, SEEK_END);
	fread(tag ,1, 128, mp3_file);

	//Has a tag
	if(strncmp(tag, "TAG", 3))
	{
		printf("This file doesn\'t have a tag.\n");
		return 0;
	}
	return 1;
}

//Prints the tag
void print_tag(struct song_tag* tag)
{
	char buffer[31] = {0};//temp array for printing non-null terminated strings
	strncpy(buffer, tag->title, 30);
	buffer[30]=0x00;
	printf("Title: %s\n",buffer);
	strncpy(buffer, tag->artist, 30);
	buffer[30] = 0x00;
	printf("Artist: %s\n",buffer);
	strncpy(buffer, tag->album, 30);
	buffer[30] = 0x00;
	printf("Album: %s\n",buffer);
	strncpy(buffer, tag->year, 4);
	buffer[4] = 0x00;
	printf("Year: %s\n", buffer);
	strncpy(buffer, tag->comment, 28);
	buffer[28] = 0x00;
	printf("Comments: %s\n", buffer);
	printf("Track number is %d\n", *tag->track_num);
	printf("Genre is %X\n", *tag->genre);
}

//Appends a tag to the end
void append_tag(FILE* mp3_file, struct song_tag* tag)
{
	fwrite(tag, 128, 1, mp3_file);
	fclose(mp3_file);
	return;
}

//Modifies the current tag
void modify_tag(FILE* mp3_file, int offset, char* content, int size)
{
	fseek(mp3_file,offset,SEEK_END);
	fwrite(content,1,size,mp3_file);	
}

//Prints usage instructions
void print_usage(FILE* stream, int exit_code)
{
	fprintf(stream, "Usage: %s options [ inputfile ... ]\n", program_name);
	fprintf(stream,
	" \"-help\" Display this usage information.\n"
	" \"-title\" [arg] Modify title of the mp3 file.\n"
	" \"-artist\" [arg] Modify the artist.\n"
	" \"-album\" [arg] Modify the album.\n"
	" \"-comment\" [arg] Modify the comment.\n"
	" \"-track\" [arg] Modify the track.\n");
	exit (exit_code);
}

int main(int argb, char *arga[])
{
	struct song_tag tag; FILE *mp3_file;
	int has_tag = 0;
	int arg_index;
	program_name=arga[0];

	//If there are no options
	if(argb == 1)
	{
		print_usage(stderr,1);
		return -1;
	}

	mp3_file = fopen(arga[1], "r");//read only

	//Checks if file is valid
	if(mp3_file == NULL)
	{
		printf("The file \"%s\" does not exist.\n",arga[1]);
		return -1;
	}
	
	
	has_tag = read_tag(mp3_file,&tag);

	fclose(mp3_file);
	
	//Prints tag_tag if there is only one argument
	if(argb == 2)
	{
		if(has_tag)
		{
			print_tag(&tag);return 0;
		}
		else
			{
				printf("There is no tag to print.\n");return -1;
			}
	}

	if(argb % 2 != 0)
	{
		printf("Arguements are missing.\n");
		print_usage(stderr,1);
	}
	
	struct song_tag newtag;
	newtag.tag_tag[0] = 'T';
	newtag.tag_tag[1] = 'A';
	newtag.tag_tag[2] = 'G';
	memset(newtag.title, 0 ,30);
	memset(newtag.year, 0, 4);
	memset(newtag.artist, 0, 30);
	memset(newtag.album, 0, 30);
	memset(newtag.comment, 0, 28);
	newtag.zero_separator[0] = 0;
	newtag.track_num[0] = 0;
	newtag.genre[0] = 0;
	
	//If the file doesn't have a tag, reopen and append a new tag
	if(!has_tag)
	{
		mp3_file = fopen(arga[1],"ab");
	}

	//Modify a tag
	else
	{
		mp3_file = fopen(arga[1],"r+b");
	}
	
	for(arg_index = 2; arg_index < argb; arg_index++)
	{	
		//Do title things
		if(!strncmp("-title", arga[arg_index], 6))
		{
				printf("Title has been modified\n");
				strncpy(newtag.title, arga[++arg_index], 30);
				if(has_tag)
				{
	 				modify_tag(mp3_file, -128+3, newtag.title, 30);
				}				
		}	

		//Do artist things
		else if(!strncmp("-artist", arga[arg_index], 7))
		{
				printf("Artist has been modified\n");
				strncpy(newtag.artist, arga[++arg_index] ,30);
				if(has_tag)
				{
					modify_tag(mp3_file, -128+33, newtag.artist, 30);
				}
		}

		//Do album things
		else if(!strncmp("-album", arga[arg_index],6))
		{
				printf("Album has been modified\n");
				strncpy(newtag.album,arga[++arg_index],30);
				if(has_tag)
				{	
					modify_tag(mp3_file, -128+63, newtag.album, 30);
				}
		}

		//Do year things
		else if(!strncmp("-year", arga[arg_index], 5))
		{
				printf("Year has been modified\n");
				strncpy(newtag.year, arga[++arg_index], 4);
				if(has_tag)
				{
					modify_tag(mp3_file, -128+93, newtag.year, 4);
				}
		}

		//Do comment things
		else if(!strncmp("-comment", arga[arg_index], 8))
		{
				printf("Comment has been modified\n");
				strncpy(newtag.comment, arga[++arg_index], 28);
				if(has_tag)
				{
					modify_tag(mp3_file, -128+97, newtag.comment, 28);
				}	
		}

		//Do track things
		else if(!strncmp("-track", arga[arg_index], 6))
		{
				printf("Track number has been modified\n");
				newtag.track_num[0] = (char)atoi(arga[++arg_index]);

				if(has_tag)
				{
					modify_tag(mp3_file,-128+126,newtag.track_num,1);
				}
		}

		//Nothing is recognized
		else
		{
			print_usage(stderr,1);
		}
	}

	if(!has_tag)
	{
		append_tag(mp3_file,&newtag);
		printf("New tag has been created!\n");
	}

	else
	{
		printf("The tag has been modified!\n");
		fclose(mp3_file);
	}
}
	