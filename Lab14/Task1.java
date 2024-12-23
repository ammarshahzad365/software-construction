// Thread class to print numbers from 1 to 10
class NumberThread extends Thread {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Number: " + i);
            try {
                Thread.sleep(100); // Simulate processing time
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

// Runnable class to print squares of numbers from 1 to 10
class SquareRunnable implements Runnable {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Square: " + (i * i));
            try {
                Thread.sleep(100); // Simulate processing time
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

// Main class to execute threads
public class Task1 {
    public static void main(String[] args) {
        // Create and start NumberThread
        NumberThread numberThread = new NumberThread();
        numberThread.start();

        // Create and start SquareRunnable thread
        Thread squareThread = new Thread(new SquareRunnable());
        squareThread.start();

        // Main thread message
        System.out.println("Main thread is running...");
    }
}
