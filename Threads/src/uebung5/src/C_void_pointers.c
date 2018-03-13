/*
 ============================================================================
 Name        : C_void_pointers.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

void* maximum(int len, void* array, size_t size, int (*cmp) (const void*, const void*));

void* maximum(int len, void* array, size_t size, int (*cmp) (const void*, const void*)) {
	void* max = array;
	int length = len;

	while(length-- > 1) {
		array += size;
		if((cmp)(max, array)) max = array;
	}

	return max;
}

int implemented_compare_method_for_int(int *a, int *b) {
	if (*b > *a) return 1;
	return 0;
}

int main(void) {
	int array[5] = {1,2,3,6,-13};
	size_t size = sizeof(int);
	int len = 5;
	int (*cmp) (const void*, const void*);
	cmp = &implemented_compare_method_for_int;
	int result = *(int*)maximum(len, array, size, cmp);

	printf("the maximum is %d", result);
}


