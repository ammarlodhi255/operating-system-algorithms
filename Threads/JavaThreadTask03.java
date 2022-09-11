public class JavaThreadTask03 extends Thread {
    static int val;

    public static void main(String[] args) {
        val = Integer.valueOf(args[0]);
        JavaThreadTask03 newThread = new JavaThreadTask03();
        newThread.start();
    }

    public void run() {
        System.out.println("Passed argument is an integer: " + val);
    }
}