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
	
	#pragma omp parallel sections 
	{
		#pragma omp section 
		{
			yourturn(NULL);
		}

		#pragma omp section 
		{
			myturn();
		}
	}
	return 0;
}
