public class JavaThreadTask02 extends Thread {    
    public void run() {
        for(int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ie) {}
            System.out.println("yourturn()");
        }
    }

    public static void myturn() {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ie) {}
            System.out.println("myturn()");
        }
    }

    public static void main(String[] args) {
        JavaThreadTask02 newThread = new JavaThreadTask02();
        newThread.start();
        try {
            newThread.join();
        } catch(InterruptedException ie) {}

        myturn();
    }
}