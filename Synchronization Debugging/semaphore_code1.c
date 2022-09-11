#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
sem_t mutex;

void* thread(void* arg) {
	sem_wait(&mutex); //wait state
	printf("\nEntered into the Critical Section..\n");
	sleep(3); //critical section
	printf("\nCompleted...\n"); //comming out from Critical section
	sem_post(&mutex);
}

int main() {
	// th1 and th2 were not declared before
	pthread_t th1;
	pthread_t th2;

	sem_init(&mutex, 0, 1);
	pthread_create(&th1, NULL, thread, NULL);
	sleep(2);
	pthread_create(&th2, NULL, thread, NULL);
	//Join threads with the main thread
	pthread_join(th1, NULL);
	pthread_join(th2, NULL);
	return 0;
}
