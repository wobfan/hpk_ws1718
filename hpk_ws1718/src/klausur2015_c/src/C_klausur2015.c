/*
 ============================================================================
 Name        : C_klausur2015.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

typedef void* Y;
typedef void* X;

void map(unsigned int n, 		// tuple length
		X x,					// X tuple pointer
		size_t sizeof_X,		// sizeof(x[0])
		Y y,					// Y tuple pointer
		size_t sizeof_Y,		// sizeof(y[0])
		void (*f) (X, Y));		// f: X -> Y fct-pointer

void map(unsigned int n, X x, size_t sizeof_X, Y y, size_t sizeof_Y, void (*f) (X, Y)) {
	while(n != 0) {
		f(x,y);
		x += sizeof_X;
		y += sizeof_Y;
		n--;
	}
}
