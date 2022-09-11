#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int main() {
	pid_t pid;
	pid_t cpid;
	pid = fork();
	cpid = wait(NULL);

	if(pid == 0) {
		printf("I'm Child process having pid: %d\n", getpid());
		sleep(5);
		printf("Statement after sleeping 5 seconds");
		exit(0);
	} else {
		printf("I'm parent process having pid: %d\n", getpid());
		for(int i = 0; i < 5; i++) printf("%d\n", i);
	}
	return 0;
}
