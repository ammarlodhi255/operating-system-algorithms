#include <stdio.h>
#include <omp.h>

void *yourturn(void* param) {
	for(int i = 0; i < 10; i++) {
		sleep(2);	
		printf("yourturn()\n");
	}
	return NULL;
}

void myturn() {
	for(int i = 0; i < 5; i++) {
	       	sleep(1);	
		printf("myturn()\n");
	}
	return;
}

int main(int argc, int *argv[]) {
	int tid1, tid2;

	#pragma omp parallel sections 
	{
		#pragma omp section 
		{
			tid1 = omp_get_thread_num();
			printf("ID of current thread executing yourturn() is: %d\n", tid1);
			yourturn(NULL);
		}

		#pragma omp section 
		{
			tid2 = omp_get_thread_num();
			printf("ID of current thread executing myturn() is: %d\n", tid2);
			myturn();
		}
	}
	return 0;
}
