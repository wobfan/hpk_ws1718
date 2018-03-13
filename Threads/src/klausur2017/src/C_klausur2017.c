/*
 ============================================================================
 Name        : C_klausur2017.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

typedef void* X;
typedef void* Y;

void map(unsigned int n, 		// tuple length
		X x,					// X tuple pointer
		size_t sizeof_X,		// sizeof(x[0])
		Y y,					// Y tuple pointer
		size_t sizeof_Y,		// sizeof(y[0])
		void (*f) (X, Y));		// f: X -> Y fct-pointer

void map(unsigned int n, X x, size_t sizeof_X, Y y, size_t sizeof_Y, void (*f) (X, Y)) {
	while(n-- > 0) {
		f(x,y);
		x += sizeof_X;
		y += sizeof_Y;
	}
}

void map_example(int *a, int *b) {
	*b = *a + 2;
}

int main(void) {
	int a[10] = {1,2,3,4,5,6,7,8,9,0};
	int b[10];
	int n = 10;
	size_t sizeof_X = sizeof(int);
	size_t sizeof_Y = sizeof(int);

	void (*mapex) (void*, void*);
	mapex = &map_example;

	map(n, a, sizeof_X, b, sizeof_Y, (*mapex));

	for(int i = 0; i < 10; i++) {
		printf("%d ", b[i]);
	}
}
