#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int main() {
	pid_t pid;
	pid = fork();

	if(pid == 0) {
		printf("I'm a child process having id: %d\n", getpid());
		printf("Having parent id: %d\n", getppid());
	} else {
		printf("I'm a parent process having id: %d\n", getpid());
		printf("Having child id: %d\n", pid);	
	}
	return 0;
}
