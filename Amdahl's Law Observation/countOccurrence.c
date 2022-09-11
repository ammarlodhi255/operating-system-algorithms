#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>

/*
 * This program calculates execution time WITHOUT multithreading.
 */
int findWord(char *filename, char *word);

int main(int argc, int *argv[]) {
	struct timeval stop, start;
	gettimeofday(&start, NULL);
	char *filename = "data.txt";
	// words to search for in the text
	char *word1 = "paragraph";
	char *word2 = "is";
	char *word3 = "line";
	char *word4 = "English";

	int n1 = findWord(filename, word1);
	printf("The word '%s' appeared %d times\n", word1, n1);

	int n2 = findWord(filename, word2);							
	printf("The word '%s' appeared %d times\n", word2, n2);
	
	int n3 = findWord(filename, word3);
	printf("The word '%s' appeared %d times\n", word3, n3);

	int n4 = findWord(filename, word4);
	printf("The word '%s' appeared %d times\n", word4, n4);

	gettimeofday(&stop, NULL);
	printf("\nThe execution time took: %f ms\n", ((stop.tv_sec - start.tv_sec) * 1000000 + stop.tv_usec - start.tv_usec)*0.001);
	return 0;
}

int findWord(char *filename, char *word) {
	int occurrenceCount = 0;
	char str[1024];
	FILE *fpointer = fopen(filename, "r");
    char *indexPos;
    int idx;

	while ((fgets(str, sizeof(str), fpointer)) != NULL) {
		idx = 0;
		while ((indexPos = strstr(str + idx, word)) != NULL) {
		    idx = (indexPos - str) + 1;
		    occurrenceCount++;
		}
	}

	fclose(fpointer);
	return occurrenceCount;
}
