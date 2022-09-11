#include <stdio.h>
#include <omp.h>

int main(int argc, int *argv[]) {
	int sum = 0;
	int N;

	printf("Enter an integer N: ");
	scanf("%d", &N);

	#pragma omp for
	for(int i = 1; i <= N; i++) 
			sum += i;

	printf("Sum = %d\n", sum);
	return 0;
}
