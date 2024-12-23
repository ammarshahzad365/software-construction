// Shared resource class
class Counter {
    private int count = 0;

    // Synchronized method to increment counter
    public synchronized void increment() {
        count++;
    }

    // Method to retrieve the final count
    public int getCount() {
        return count;
    }
}

// Runnable class for threads
class CounterIncrementer implements Runnable {
    private Counter counter;

    // Constructor to share the same Counter object
    public CounterIncrementer(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment(); // Increment the shared counter
            try {
                Thread.sleep(10); // Simulate processing time
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

// Main class to execute threads
public class Task2 {
    public static void main(String[] args) {
        Counter sharedCounter = new Counter(); // Shared resource

        // Create and start three threads
        Thread thread1 = new Thread(new CounterIncrementer(sharedCounter));
        Thread thread2 = new Thread(new CounterIncrementer(sharedCounter));
        Thread thread3 = new Thread(new CounterIncrementer(sharedCounter));

        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Print the final value of the counter
        System.out.println("Final Counter Value: " + sharedCounter.getCount());
    }
}
