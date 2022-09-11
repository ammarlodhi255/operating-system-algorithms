class Philosopher extends Thread {
    String state;
    int i;
    EatingTable table;

    Philosopher(EatingTable table, int i) {
        this.table = table;
        this.i = i;
    }

    void eat() {
        try {
            table.philosophers[i].state = "Eating";
            System.out.println("Philosopher[" + i + "]: " + table.philosophers[i].state);

            // The Philosopher is allowed to eat for a random period of time.
            long eatingTime = (long) (((0.2 + Math.random()) % 0.8) * 2000);
            Thread.sleep(eatingTime);
        } catch(InterruptedException ex) { ex.printStackTrace(); }   
    }

    void think() {
        try {
            long thinkingTime = (long) (((0.2 + Math.random()) % 0.8) * 1000);
            Thread.sleep(thinkingTime);
        } catch(InterruptedException ex) { ex.printStackTrace(); }
    }

    public void run() {
        int rand_num = new java.util.Random().nextInt(10) + 1;
        while(rand_num != 0) {
            table.takeForks(i);
            rand_num--;
        }
    }
}

class EatingTable {
    int[] chopsticks = {1, 1, 1, 1, 1}; // Semaphores
    Philosopher[] philosophers;
    FIFOQueue<Philosopher> waitingPhilosophers = new<Philosopher> FIFOQueue();

    void Wait(Philosopher p, int fork) {
        while(chopsticks[fork] <= 0) {
            waitingPhilosophers.enqueue(p); /* wait */
            System.out.print(""); 
        }
        chopsticks[fork]--;
    }

    void signal(int fork) {
        chopsticks[fork]++;
    }

     /*
        The right and left chopsticks are locked so that no philosopher sitting at either 
        right or left of the current eating philosopher is allowed to eat.

        If philosopher[0] is eating then philosopher[1] and philosopher[4] can't eat
        If philosopher[1] is eating then philosopher[0] and philosopher[2] can't eat
        and so on.
    */
    
    void takeForks(int i) {
        int rightFork = (i+1) % 5;
        int leftFork = i;
        Wait(philosophers[i], rightFork); 
        Wait(philosophers[i], leftFork);
        philosophers[i].eat();
        putDownForks(i); // Unlock the right and left chopsticks
        philosophers[i].think();
    }

    void putDownForks(int i) {
        int rightFork = (i + 1) % 5;
        int leftFork = i;
        philosophers[i].state = "Thinking";
        System.out.println("Philosopher[" + i + "]: " + philosophers[i].state);

        waitingPhilosophers.dequeue();
        signal(rightFork);
        signal(leftFork);
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        EatingTable sharedTable = new EatingTable();  

        for(int i = 0; i < 5; i++) 
            philosophers[i] = new Philosopher(sharedTable, i);

        sharedTable.philosophers = philosophers;
        
        philosophers[0].start();
        philosophers[1].start();
        philosophers[2].start();
        philosophers[3].start();
        philosophers[4].start();
        try {
            philosophers[0].join();
            philosophers[1].join();
            philosophers[2].join();
            philosophers[3].join();
            philosophers[4].join();
        } catch(InterruptedException ex) { ex.printStackTrace(); }
    }
}

class FIFOQueue <T> {
    Node head = null;
    Node tail = null;

    private class Node <T> {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    public void enqueue(T data) {
        Node newNode = new Node(data);
        if(head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public T dequeue() {
        if(head == null) throw new RuntimeException("Error: Queue is empty!");
        T data = (T) head.data;
        head = head.next;
        return data;
    }
}