#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int main() {
	printf("Before sleep\n");
	sleep(5);
	printf("After sleeping\n");

	// printing numbers 1-10 with a second delay in between:
	
	for(int i = 1; i <= 10; i++) {
		sleep(1);
		printf("%d\n", i);
	}
	return 0;
}
