#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

void *run(void *param);

int main(int argc, int *argv[]) {
	pthread_t thread_id;
	pthread_attr_t attr;
	
	long passed_arg = atoi((char *) argv[1]);
	if(passed_arg <= 0) {
		fprintf(stderr, "Error: Argument must be > 0\n");
		return -1;
	}

	pthread_attr_init(&attr);
	pthread_create(&thread_id, &attr, run, (void *) passed_arg);
	pthread_join(thread_id, NULL);

	return 0;
}

void *run(void *param) {
	long val = (long) param;
	printf("%ld\n", val);
}
