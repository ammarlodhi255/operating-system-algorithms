#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <semaphore.h> /* semaphore library included unnecessary */

void *thread_function(void *arg);
pthread_mutex_t work_mutex;
#define WORK_SIZE 1024
char work_area[WORK_SIZE];
int time_to_exit = 0;

int main() {
	pthread_t a_thread;
	void *thread_result;
	pthread_mutex_init(&work_mutex, NULL);
	pthread_create(&a_thread, NULL, thread_function, NULL);
	pthread_mutex_lock(&work_mutex);
	printf("Input some text. Enter 'end' to finish\n");
	
	while(!time_to_exit) {
		fgets(work_area, WORK_SIZE, stdin);
		pthread_mutex_unlock(&work_mutex);
		while(1) {
			pthread_mutex_lock(&work_mutex);
			if (work_area[0] != '\0') {
				pthread_mutex_unlock(&work_mutex);
				sleep(1);
			}
			else {
				break;
			}
		}
	} /* missing closing bracket */
	pthread_mutex_unlock(&work_mutex);
	printf("\nWaiting for thread to finish...\n");
	pthread_join(a_thread, &thread_result);
	printf("Thread joined\n");
	pthread_mutex_destroy(&work_mutex);
	exit(EXIT_SUCCESS);
}

void *thread_function(void *arg) {
	sleep(1);
	pthread_mutex_lock(&work_mutex);
	
	while(strncmp("end", work_area, 3) != 0) {
		printf("You input %ld characters\n", strlen(work_area) -1);
		work_area[0] = '\0';
		pthread_mutex_unlock(&work_mutex);
		sleep(1);
		pthread_mutex_lock(&work_mutex);
		
		while (work_area[0] == '\0' ) {
			pthread_mutex_unlock(&work_mutex);
			sleep(1);
			pthread_mutex_lock(&work_mutex);
		}
	}
	
	time_to_exit = 1;
	work_area[0] = '\0';
	pthread_mutex_unlock(&work_mutex);
	pthread_exit(0);
}
