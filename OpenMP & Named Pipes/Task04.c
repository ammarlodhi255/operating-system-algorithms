#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>

int main(int argc, int *argv[]) {
 	int fd[2];
	int num = 3; // parent declares a number
	pipe(fd);
	pid_t pid;
	pid = fork();

	if(pid == 0) {
		close(fd[0]);
		printf("Current value of num: %d\n", num);
		num *= 4;
		write(fd[1], &num, sizeof(num));
		close(fd[1]);
		exit(0);
	} else {
		close(fd[1]);
		read(fd[0], &num, sizeof(num));
		close(fd[0]);
		printf("Value of num after passing to child process: %d\n", num);
	}
	return 0;
}
