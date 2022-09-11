public class Task01_Queue <T> {
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

    public String toString() {
        String start = "[";
        Node traversor = head;
        while(traversor != null) {
            String str = traversor.data.toString();
            if(traversor.next != null)
                start += str + ", ";
            else 
                start += str + "]";
            traversor = traversor.next;
        }
        return start;
    }

    public static void main(String[] args) {
        Task01_Queue queue = new Task01_Queue<Integer>();
        queue.enqueue(32);
        queue.enqueue(34);
        queue.enqueue(35);
        queue.enqueue(37);
        queue.enqueue(39);

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }
}