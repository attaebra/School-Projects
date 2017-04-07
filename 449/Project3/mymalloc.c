//Atta Ebrahimi

#include "mymalloc.h"
#include <unistd.h>
#include <stdio.h>

//Keeps track of beginning and end
void *base = NULL;
void *curr_pos = NULL;

//Represents doubly linked list used to keep track of memory
typedef struct metadata_node *block;

struct metadata_node {
	int chunk_size;	//stores size of mallocd region in bytes
	int free_flag;	//space free if 0, otherwise not free
	block next;		//store next node
	block prev;		//store previous node
	char data_ptr[1];	//stores pointer to block that stores metadata
	void *ptr;
};

//Finds block
block find_block ( int size )
{
	block b = curr_pos;
	//loop to find free area
	while ( b && !(b->free_flag) && (b->chunk_size <= size) )
	{
		/* 
		 * (1) If pointer is not null at the end keep going
		 * (2) If end is reached go back to where we started
		 * (3) NULL if no space found.
		 */
		if (b != NULL) {
			b = b->next;
		}else {
			b = base;

		if(b == curr_pos)
			return NULL;
		}
	}
	curr_pos = sbrk(0);

	//return that address if appropriate space was found for requested size
	return b;
}

//Expands heap if no empty space
block extend_heap ( block last, int s )
{
	block b;
	b = sbrk(0);

	if( sbrk( sizeof(struct metadata_node) +s ) == (void*)-1 )
		return (NULL);

	b->chunk_size = s;
	b->next = NULL;

	if( last != NULL )
		last->next = b;
	
	b->free_flag = 0;
	return (b);
}

void *my_nextfit_malloc( int size )
{
	block b;
	block last;

	if( base != NULL )
	{
		last = curr_pos;
		b = find_block(size);

		if(b != NULL )
		{
			//Set to free if b != NULL
			b->free_flag = 0;

		}else{
			b = extend_heap (last ,size);

			if ( b == NULL )
				return(NULL);
		}
	}
	else
	{
		b = extend_heap(NULL, size);
		if( b == NULL )
			return NULL;
		//keep track of current position and base
		base = b;
		curr_pos = b;
	}

	return (b->data_ptr);
}

block free_block( void *p )
{
	char *tmp;
	tmp = p;
	return (p = tmp -= sizeof(struct metadata_node));
}

//Checks if pointer is on the heap to validate
int valid( void *p )
{
	if (base)
		if(p>base && p<sbrk(0))
			return (p == (free_block(p)) -> ptr);

	return (0);
}

//Coalesce to eliminate fragmentation
block coalesce( block b)
{
	if (b->next && b->next->free_flag){
		b->chunk_size += sizeof(struct metadata_node) +b->next->chunk_size;
		b->next = b->next->next;
		if(b->next)
			b->next->prev = b;
	}
	return (b);
}

void my_free( void *ptr )
{
	block b;
	if ( valid(ptr) )
	{
		b = free_block(ptr);
		b->free_flag = 1;

		if(b->prev && b->prev->free_flag)
			b = coalesce(b->prev);

		if(b->next)
			coalesce(b);
		else
		{
			if(b->prev)
				b->prev->next = NULL;
			else
				base = NULL;
			brk(b);
		}
	}
}