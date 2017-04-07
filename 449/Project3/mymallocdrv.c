//Atta Ebrahimi

#include <stdio.h>
#include "mymalloc.h"

int main()
{
		int *pointer_one;
		pointer_one = (int *)my_nextfit_malloc(sizeof(int));
		*pointer_one = 10;
		printf("%d\n", *pointer_one);


		int *pointer_two;
		pointer_two = (int *)my_nextfit_malloc(sizeof(int)*2);
		*pointer_two = 20;
		printf("%d\n", *pointer_two);

		int *pointer_three;
		pointer_three = (int *)my_nextfit_malloc(sizeof(int)*4);
		*pointer_three = 30;
		printf("%d\n", *pointer_three);

		int *pointer_four;
		pointer_four = (int *)my_nextfit_malloc(sizeof(int));
		*pointer_four = 40;
		printf("%d\n", *pointer_four);

		my_free(pointer_one);
		my_free(pointer_two);
		my_free(pointer_three);
		my_free(pointer_four);

		printf("\n%d\n", *pointer_one);
		printf("%d\n",   *pointer_two);
		printf("%d\n",   *pointer_three);
		printf("%d\n",   *pointer_four);

		return 0;
}