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

typedef struct colour {
	int r, g, b;
} Color;

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

Color implemented_compare_method_for_color(Color *a, Color *b) {
	int aSum = a->r + a->b + a->g;
	int bSum = b->r + b->b + b->g;
	if (bSum > aSum) return *b;
	return *a;
}


void maxIntFunction() {
	int array[5] = {1,2,3,6,-13};
	size_t size = sizeof(int);
	int len = 5;
	int (*cmp) (const void*, const void*);
	cmp = &implemented_compare_method_for_int;
	int result = *(int*)maximum(len, array, size, cmp);

	printf("the maximum is %d\n", result);
}

void maxColorFunction() {
	Color *a = calloc(sizeof(Color), 1);
	Color *b = calloc(sizeof(Color), 1);
	a->r = 100; a->g = 200; a->b = 255;
	b->r = 200; b->g = 101; b->b = 255;
	Color array[2] = {*a,*b};

	size_t size = sizeof(Color);
	int len = 2;
	int (*cmp) (const void*, const void*);
	cmp = &implemented_compare_method_for_color;
	Color result = *(Color*)maximum(len, array, size, cmp);

	printf("the brightest color was r=%d, g=%d, b=%d\n", result.r, result.g, result.b);
}

int main(void) {
	maxIntFunction();
	fflush(stdout);
	maxColorFunction();
	fflush(stdout);
}
