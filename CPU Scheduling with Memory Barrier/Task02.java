import java.util.concurrent.*;
// TASK 02 IS IMPLEMENTED USING ROUND ROBIN SCHEDULING POLICY

class Job {
    long waitTime;
    double cpuBurst;
    long startTime;
    long endTime;
    String jobName;

    Job(String jobName, double cpuBurst) {
        this.jobName = jobName;
        this.startTime = System.nanoTime();
        this.cpuBurst = cpuBurst;
    }
}

class SingleThread implements Runnable {
    RoundRobinQueue jobBatch;
    String batchName;

    SingleThread(String batchName, RoundRobinQueue jobBatch) {
        this.batchName = batchName;
        this.jobBatch = jobBatch;
    }

    public void run() {
        System.out.println(batchName + ": started execution on " + Thread.currentThread().getName());
        jobBatch.enqueueJobs();
        jobBatch.allocateCPU();
        System.out.println(batchName + ": execution completed.\n");

        // calculating the average waiting time for the batch of jobs:
        long avgWaitTime = 0;
        long avgTurnaroundTime = 0;
        for(int x = 0; x < 10; x++) {
            avgWaitTime += (jobBatch.jobs[x].waitTime);
            avgTurnaroundTime += (jobBatch.jobs[x].endTime);
        }

        avgWaitTime /= 10;
        avgTurnaroundTime /= 10;

        System.out.println("Average Waiting Time for " + batchName + ":     " + String.format("%.4f", (avgWaitTime * 1e-9)) + "ms");
        System.out.println("Average Turnaround Time for " + batchName + ":  " + String.format("%.4f", (avgTurnaroundTime * 1e-9)) + "ms\n");
    }
}

public class Task02 {
    public static void main(String[] args) {
        Job[][] job_batches = new Job[10][4];
        RoundRobinQueue[] queueArray = new RoundRobinQueue[4];
        long systemThroughPut = System.nanoTime();

        System.out.println("Please wait... Jobs are being inserted");

        for (int i = 0; i < 4; i++) {
            MemoryBarrier memory_barrier = new MemoryBarrier();

            for(int j = 0; j < 10; j++) {
                double cpuBurst = (0.2 + (double)(Math.random())) % 0.9; // Range of cpu burst between 0.2 - 0.9
                cpuBurst *= 1200;
                Job job = new Job("Job[" + (i+1) + "][" + (j+1) + ']', cpuBurst);
                try {
                    Thread.sleep(100); // Arrival of each job to memory barrier after every 100ms
                } catch(InterruptedException ex) {}
                memory_barrier.insertJob(job);
            }

            Job[] jobs = memory_barrier.extractJobs();
            //Once the memory barrier is full that batch of job should be shifted to the FIFO queue.
            job_batches[i] = jobs;
            queueArray[i] = new RoundRobinQueue();
            queueArray[i].jobs = job_batches[i];
        }   

        // Allocating each batch 4 threads and running them in parallel
        
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new SingleThread("Batch 1", queueArray[0]));
        executorService.execute(new SingleThread("Batch 2", queueArray[1]));
        executorService.execute(new SingleThread("Batch 3", queueArray[2]));
        executorService.execute(new SingleThread("Batch 4", queueArray[3]));
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {}

        systemThroughPut = (System.nanoTime() - systemThroughPut) / (queueArray.length * queueArray[0].jobs.length);
        System.out.println("\nSYSTEM THROUGHPUT: " + String.format("%.4f", (systemThroughPut * 1e-9)) + " jobs completed per ms");
    }   
} 

class RoundRobinQueue {
    Job[] jobs;
    final long TIME_QUANTUM = 200; // quantum of time is set to 200ms

    private class Node {
        Node next;
        Job job;

        Node(Job job) {
            this.job = job;
            next = null;
        }
    }

    Node head = null;
    Node tail = null;

    public void enqueueJobs() {
        for(int i = 0; i < 10; i++) {
            Node newNode = new Node(jobs[i]);
            if(isEmpty()) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                tail = tail.next;
            }
        }
    }

    public void allocateCPU() {
        while(!isEmpty()) {
            Job currentJob = dequeueJob();
            currentJob.waitTime = (System.nanoTime()) - currentJob.startTime;
            long currentJobsBurst = (long) currentJob.cpuBurst;
            System.out.println("Current Job: " + currentJob.jobName);

            try {
                for(long time = 0; time < TIME_QUANTUM; time++) {
                    Thread.sleep(1);
                    currentJob.cpuBurst--;
                    /*  If the current Job's CPU Burst is greater than the time quantum then 
                        the job is put at the tail of the queue when the quantum 
                        of time is over */
                    if(time == TIME_QUANTUM - 1 && currentJob.cpuBurst > 0) { 
                        Node nodeToInsert = new Node(currentJob);
                        tail.next = nodeToInsert;
                        tail = nodeToInsert;
                    }
                }
            } catch(InterruptedException ex) {}
            if(currentJob.cpuBurst < 0)
                currentJob.endTime = (System.nanoTime()) - currentJob.startTime;
        }
    }

    public Job dequeueJob() {
        if(isEmpty()) return null;
        Job job = head.job;
        head = head.next;
        return job;
    }

    public boolean isEmpty() {
        return head == null;
    }
}

class MemoryBarrier {
    final int NUM_OF_JOBS = 10;
    Job[] jobs = new Job[NUM_OF_JOBS];
    int in = -1;

    void insertJob(Job job) {
        if (isFull()) 
            System.out.println("ERROR: Memory barrier is full");
        else
            jobs[++in] = job;
    }

    Job[] extractJobs() {
        if(isEmpty())
            throw new RuntimeException("ERROR: Memory barrier is empty");
        else if(isFull()) // Extract jobs array only if the memory barrier is full
            return jobs;
        else
            throw new RuntimeException("ERROR: Memory barrier is not full yet");
    }

    boolean isFull() {
        return (in+1) == NUM_OF_JOBS;
    }

    boolean isEmpty() {
        return in == -1;
    }
}   

