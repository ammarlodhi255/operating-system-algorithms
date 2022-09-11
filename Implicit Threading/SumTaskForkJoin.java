import java.util.concurrent.*;

public class SumTaskForkJoin extends RecursiveTask<Integer> {
	static final int THRESHOLD = 1000;
	private int begin;
	private int end;
	private int[] array;

	public SumTaskForkJoin(int begin, int end, int[] array) {
		this.begin = begin;
		this.end = end;
		this.array = array;
	}

	protected Integer compute() {
		if (end - begin < THRESHOLD) {
			int sum = 0;
			for (int i = begin; i <= end; i++)
				sum += array[i];
			return sum;
		} else {
			int mid = (begin + end) / 2;
			SumTaskForkJoin leftTask = new SumTaskForkJoin(begin, mid, array);
			SumTaskForkJoin rightTask = new SumTaskForkJoin(mid + 1, end, array);
			leftTask.fork();
			rightTask.fork();
			return rightTask.join() + leftTask.join();
		}
	}
}