#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

pthread_t tid[2];
sem_t S;

void *tfunc() {
	sem_wait(&S);
	for(int i = 0; i < 10; i++) {
		sleep(1);
		printf("i = %d\n", i); 
	}
	sem_post(&S);
}

int main() {
	sem_init(&S, 0, 1);
	pthread_create(&tid[0], NULL, tfunc, NULL);
	pthread_create(&tid[1], NULL, tfunc, NULL);
	pthread_join(tid[0], NULL);
	pthread_join(tid[1], NULL);
	return 0;
}
