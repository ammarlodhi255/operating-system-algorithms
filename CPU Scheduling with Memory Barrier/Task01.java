// TASK 01 IS IMPLEMENTED USING FCFS POLICY

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

class FIFOQueue {
    Job[] jobs;

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
        for(int i = 0; i < jobs.length; i++) {
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
        for(int i = 0; i < jobs.length; i++) {
            Job currentJob = dequeueJob();
            currentJob.waitTime = (System.nanoTime()) - currentJob.startTime;
            long currentJobsBurst = (long) currentJob.cpuBurst;
            System.out.println("Current Job: " + currentJob.jobName);
            try {
                // Each job will release the CPU after its CPU Burst has elapsed.
                Thread.sleep(currentJobsBurst);
            } catch(InterruptedException ex) {}
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

public class Task01 {
    public static void main(String[] args) {
        Job[][] job_batches = new Job[10][4];
        FIFOQueue[] queueArray = new FIFOQueue[4];
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
            queueArray[i] = new FIFOQueue();
            queueArray[i].jobs = job_batches[i];
        }   

        for(int i = 0; i < 4; i++) {
            System.out.println("Batch: " + (i+1));
            queueArray[i].enqueueJobs();
            queueArray[i].allocateCPU();
            System.out.println("Batch " + (i+1) + ": execution completed.\n");
            
            // calculating the average waiting time for batch i:
            long avgWaitTime = 0;
            long avgTurnaroundTime = 0;
            for(int x = 0; x < 10; x++) {
                avgWaitTime += (queueArray[i].jobs[x].waitTime);
                avgTurnaroundTime += (queueArray[i].jobs[x].endTime);
            }

            avgWaitTime /= 10;
            avgTurnaroundTime /= 10;

            System.out.println("Average Waiting Time for Batch " + (i+1) + ":     " + String.format("%.4f", (avgWaitTime * 1e-9)) + "ms");
            System.out.println("Average Turnaround Time for Batch " + (i+1) + ":  " + String.format("%.4f", (avgTurnaroundTime * 1e-9)) + "ms\n");
        }

        systemThroughPut = (System.nanoTime() - systemThroughPut) / (queueArray.length * queueArray[0].jobs.length);
        System.out.println("\nSYSTEM THROUGHPUT: " + String.format("%.4f", (systemThroughPut * 1e-9)) + " jobs completed per ms");
    }   
} 