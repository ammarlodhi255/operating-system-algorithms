#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

pthread_t tid[2];
pthread_mutex_t lock;

void *tfunction() {
	pthread_mutex_lock(&lock);
	for(int i = 0; i < 10; i++) {
		sleep(1);
		printf("i = %d\n", i);
	}
	pthread_mutex_unlock(&lock);
}

int main() {
	pthread_mutex_init(&lock, NULL);
	pthread_create(&tid[0], NULL, tfunction, NULL);
	pthread_create(&tid[1], NULL, tfunction, NULL);
	pthread_join(tid[0], NULL);
	pthread_join(tid[1], NULL);
	return 0;
}
