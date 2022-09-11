#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <semaphore.h> // semaphore library was not included before

void *thread_function(void *arg);

#define WORK_SIZE 10 // WORK_SIZE was not defined before

sem_t bin_sem;
char work_area[WORK_SIZE];

int main() {
	int res;
	pthread_t a_thread;
	void *thread_result;
	
	sem_init(&bin_sem, 0, 0);
	pthread_create(&a_thread, NULL, thread_function, NULL);
	printf("Input some text. Enter 'end' to finish\n");
	
	while(strncmp("end", work_area, 3) != 0) {
		fgets(work_area, WORK_SIZE, stdin);
		sem_post(&bin_sem);
	}
	
	printf("\nWaiting for thread to finish...\n");
	pthread_join(a_thread, &thread_result);
	printf("Thread joined\n");
	sem_destroy(&bin_sem);
	exit(EXIT_SUCCESS);
}

void *thread_function(void *arg) {
	sem_wait(&bin_sem);
	while(strncmp("end", work_area, 3) != 0) {
		printf("You input %ld characters\n", strlen(work_area) -1);
		sem_wait(&bin_sem);
	}
	pthread_exit(NULL);
}
