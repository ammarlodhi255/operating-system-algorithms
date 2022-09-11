public class Task02 {
    public static void main(String[] args) {
        Queue queue = new Queue<String>();
        queue.enqueue("Item1");
        queue.enqueue("Item2");
        queue.enqueue("Item3");
        queue.enqueue("Item4");
        queue.enqueue("Item5");

        try {
            Thread.sleep(5000);
            System.out.println(queue.dequeue());
            Thread.sleep(5000);
            System.out.println(queue.dequeue());
            Thread.sleep(5000);
            System.out.println(queue.dequeue());
            Thread.sleep(5000);
            System.out.println(queue.dequeue());
            Thread.sleep(5000);
            System.out.println(queue.dequeue());
        } catch(InterruptedException e) {
            System.out.println("InterruptedException: " + e);
        }
    }
}

class Queue <T> {
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