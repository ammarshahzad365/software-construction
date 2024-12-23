import java.util.concurrent.CopyOnWriteArrayList;

public class Task3 {
    public static void main(String[] args) {
        // Thread-safe list
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        // Writer thread
        Runnable writer = () -> {
            for (int i = 1; i <= 10; i++) {
                sharedList.add(i);
                System.out.println(Thread.currentThread().getName() + " added: " + i);
                try {
                    Thread.sleep(50); // Simulate write delay
                } catch (InterruptedException e) {
                    System.out.println("Writer interrupted: " + e.getMessage());
                }
            }
        };

        // Reader thread
        Runnable reader = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " read: " + sharedList);
                try {
                    Thread.sleep(100); // Simulate read delay
                } catch (InterruptedException e) {
                    System.out.println("Reader interrupted: " + e.getMessage());
                }
            }
        };

        // Create threads
        Thread writerThread = new Thread(writer, "WriterThread");
        Thread readerThread = new Thread(reader, "ReaderThread");

        // Start threads
        writerThread.start();
        readerThread.start();

        // Wait for threads to finish
        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Final state of the list
        System.out.println("Final List: " + sharedList);
    }
}
