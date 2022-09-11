#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, int *argv[]) {
	// TASK05-A: Writing the integer to the named pipe
	int res;
	res = mkfifo("myfifo", 0777);
	if(res == -1) {
		printf("Error: couldn't create the named pipe");
		return -1;
	}
	printf("Named pipe created\n");
	
	int fd;
	fd = open("myfifo", O_WRONLY); 

	if(fd == -1) {
		printf("Error: couldn't open the file\n");
		return -1;
	}
	
	int num = 3;
	printf("Current value of num: %d\n", num);
	num *= 4;
	write(fd, &num, sizeof(num));
	close(fd);
	return 0;	
}
