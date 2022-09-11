#define BUFFER_SIZE 10

typedef struct {
	. . .
} item;

int main() {
	item buffer[BUFFER_SIZE];
	int in = 0;
	int out = 0;

	/* PRODUCER */

	item next_produced;
	while (true) {
		/* produce an item in next produced */
		while (((in + 1) % BUFFER_SIZE) == out) // is it full?
		; /* do nothing */
		buffer[in] = next_produced;
		in = (in + 1) % BUFFER_SIZE;
	}

	/* CONSUMER */

	item next_consumed;
	while (true) {
		while (in == out) // is it empty?
		; /* do nothing */
		next_consumed = buffer[out];
		out = (out + 1) % BUFFER_SIZE;
		/* consume the item in next consumed */
	}
}

