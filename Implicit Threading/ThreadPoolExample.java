import java.util.concurrent.*;

public class ThreadPoolExample {
	public static void main(String[] args) {
		int numTasks = Integer.parseInt(args[0].trim());
		/* Create the thread pool */
		ExecutorService pool = Executors.newCachedThreadPool();
		/* Run each task using a thread in the pool */
		for (int i = 0; i < numTasks; i++)
			pool.execute(new Task());
		/* Shut down the pool once all threads have completed */
		pool.shutdown();
	}
}