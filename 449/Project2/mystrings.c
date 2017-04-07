#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main( int argc, char *argv[])  {
	FILE *f;
	f = fopen( argv[1], "rt");
	if( f == NULL)  {
		printf("File wasn't opened. Something is wrong\n");  }
	int count = 0;
	int max_count = 0;
	char current;
	int tester = 1;
	while( !feof(f) )  {
		tester = 1;
		while( tester && !feof(f)){
			fread( &current, sizeof(char), 1, f);
			if( current <127 && current > 32){
				count++;  
			}
			else{ tester = 0;}
		}
		if( count > max_count)  {
			max_count = count;  }
		count = 0;
		
	}
	printf("%d\n", max_count);
	fclose(f);
	FILE *file;
	file  = fopen( argv[1], "rt");
	char *a;
	max_count++;
	a = malloc( max_count * sizeof( char));
	count = 0;
	int i = 0;
	strncpy( a, "", max_count);
	while( !feof(file) ) {
		tester = 1;
		while( tester && !feof(f) ) {
			fread( &current, sizeof(char), 1,f);
			if( current < 127 && current > 32 )  {
				a[i++] = current;  }
			else { tester = 0 ; }
		}
		if( i >= 4 )  {
			printf("%s\n", a);  }
		i = 0;
		strncpy( a , "", max_count);
	}
	free(a);
	return 0;
}
