public class Task03 {
    Node head = null;
    Node tail = null;
    static long STRTTIME;

    private class Item {
        String objectName;
        long startTime;
        long endTime;
        double cpuBurst;
        long waitTime = 0;

        Item(String objectName, long startTime, long endTime, double cpuBurst) {
            this.objectName = objectName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.cpuBurst = cpuBurst;
        }
    }

    private class Node {
        Item item;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    public void enqueue(String objectName) {
        double cpuBurst = (0.2 + (double)(Math.random())) % 0.8; // Range of cpu burst 0.2 - 0.8
        long stime = System.nanoTime(); // get start time for the current job
        Item newItem = new Item(objectName, stime, 0, cpuBurst);
        
        Node newNode = new Node(newItem);
        if(head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public Item dequeue() {
        if(head == null) throw new RuntimeException("Error: Queue is empty!");
        Item item = head.item;
        head = head.next;
        item.waitTime = System.nanoTime() - STRTTIME;
        return item;
    }

    public static void main(String[] args) {
        Task03 queue = new Task03();
        double avgWaitingTime = 0;
        double avgServeTime = 0;
        double throughput = 0;
        double mainTime = System.nanoTime() * (1e-9);
        Item items[] = new Item[20];

        System.out.println("Enqueueing jobs.... Please wait");
        for(int i = 0; i < 20; i++) {
            if(i == 0) 
                STRTTIME = System.nanoTime();
            queue.enqueue("Process " + (1+i));
            try {
                Thread.sleep(100); // enqueue another item after 0.1 seconds
            } catch(InterruptedException e) { e.printStackTrace(); } 
        }

        System.out.println("\t\tEnd Time\tWait Time");
        Item item;

        for(int i = 0; i < 20; i++) {
            item = queue.dequeue();
            try {
                // allowing the process to release the cpu only after the length of its cpu burst has elapsed.
                Thread.sleep(Math.round(item.cpuBurst*100)/100); 
            } catch(InterruptedException e) {}

            item.endTime = System.nanoTime() - item.startTime; // calculate the end time for the job.
            
            System.out.println(item.objectName + ":\t" + String.format("%.4f", (item.endTime * 1e-9)) + "s" + "\t\t" + String.format("%.4f", (item.waitTime * 1e-9)) + "s");
            items[i] = item;
        }
        
        // Calculating the average waiting time
        for(int i = 0; i < 20; i++) 
            avgWaitingTime += items[i].waitTime;  

        avgWaitingTime /= 20;
        System.out.println("\nAverage Waiting time: " + String.format("%.4f", (avgWaitingTime * 1e-9)) + "s");

        // Calculating average serve time
        for(int i = 0; i < 20; i++)
            avgServeTime += items[i].cpuBurst;

        avgServeTime /= 20;
        System.out.println("Average Serve time: " + String.format("%.15f", (avgServeTime * 1e-9)) + "s");
      
        // Calculating the throughput
        mainTime = (System.nanoTime() * (1e-9)) - mainTime;
        throughput = (20/mainTime);
        System.out.println("Throughput: " + throughput + " jobs completed per 1s");
    }
}
