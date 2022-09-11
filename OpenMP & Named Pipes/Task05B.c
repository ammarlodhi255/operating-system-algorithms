#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, int *argv[]) {
	// TASK05-B: Reading from the named pipe
	int num;
	int fd1 = open("myfifo", O_RDONLY);

	if(fd1 == -1) {
		fprintf(stderr, "Error: couldn't open the file\n");
		return -1;
	}

	read(fd1, &num, sizeof(num));
        printf("New value of num after reading from named pipe: %d\n", num);
	close(fd1);	

	return 0;
}
