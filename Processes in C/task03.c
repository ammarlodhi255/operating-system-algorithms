#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int main() {
	pid_t pid;
	pid = fork();
	pid_t cpid = wait(NULL); // Parent will wait until child finishes execution

	if(pid == 0) {
		printf("I'm Child process\n");
		for(int i = 0; i < 5; i++) {
			printf("%s: %d\n", "ChildProcess", getpid() + i);
		}
		exit(0);
	} else {
		printf("This is parent process after execution of child has finished\n");
	}
	return 0;
}
