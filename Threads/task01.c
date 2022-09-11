#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

void *yourturn() {
	for(int i = 0; i < 3; i++) {
		sleep(1);
		printf("yourturn()\n");
	}
	return NULL;
}

void myturn() {
	for(int i = 0; i < 10; i++) {
		sleep(1);
		printf("myturn()\n");
	}
	return;
}

int main() {
	pthread_t tid;

	pthread_create(&tid, NULL, yourturn, NULL);
	myturn();
	return 0;
}
