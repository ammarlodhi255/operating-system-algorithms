#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h> /* pthread library was missing */

pthread_t tid[2];
int counter = 0;
pthread_mutex_t lock; 

void* trythis(void* arg) {
	pthread_mutex_lock(&lock);
	unsigned long i = 0;
	counter += 1;
	
	printf("\n Job %d has started\n", counter);
	
	for (i = 0; i < (0xFFFFFFFF); i++);

	printf("\n Job %d has finished\n", counter);
	pthread_mutex_unlock(&lock);
	return NULL;
}

int main(void) {
	int error;
	if (pthread_mutex_init(&lock, NULL) != 0) {
		printf("\n mutex init has failed\n");
		return 1;
	}
	
	int i = 0; /* i was undeclared. */
	
	while (i < 2) {
		pthread_create(&(tid[i]),NULL, &trythis, NULL);
		i++;
	}
	
	pthread_join(tid[0], NULL);
	pthread_join(tid[1], NULL);
	pthread_mutex_destroy(&lock);
	return 0;
}
