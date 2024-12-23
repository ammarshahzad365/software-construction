import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class AtomicBankAccount {
    private AtomicInteger balance;

    // Constructor to initialize balance
    public AtomicBankAccount(int initialBalance) {
        this.balance = new AtomicInteger(initialBalance);
    }

    // Method for deposit
    public void deposit(int amount) {
        balance.addAndGet(amount);
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount + ", New Balance: " + balance.get());
    }

    // Method for withdrawal
    public void withdraw(int amount) {
        while (true) {
            int currentBalance = balance.get();
            if (currentBalance >= amount) {
                if (balance.compareAndSet(currentBalance, currentBalance - amount)) {
                    System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + ", New Balance: " + balance.get());
                    break;
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + ", Insufficient funds!");
                break;
            }
        }
    }

    // Method to get the final balance
    public int getBalance() {
        return balance.get();
    }
}

public class Task4 {
    public static void main(String[] args) {
        // Create a shared bank account with an initial balance of 1000
        AtomicBankAccount account = new AtomicBankAccount(1000);

        // Create a random number generator
        Random random = new Random();

        // Runnable for client transactions
        Runnable clientTask = () -> {
            for (int i = 0; i < 10; i++) { // Each client performs 10 transactions
                int amount = random.nextInt(200) + 1; // Random amount between 1 and 200
                if (random.nextBoolean()) {
                    account.deposit(amount); // Perform deposit
                } else {
                    account.withdraw(amount); // Perform withdrawal
                }
                try {
                    Thread.sleep(50); // Simulate delay
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
                }
            }
        };

        // Create multiple client threads
        Thread client1 = new Thread(clientTask, "Client1");
        Thread client2 = new Thread(clientTask, "Client2");
        Thread client3 = new Thread(clientTask, "Client3");

        // Start client threads
        client1.start();
        client2.start();
        client3.start();

        // Wait for threads to finish
        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Print the final balance
        System.out.println("Final Account Balance: " + account.getBalance());
    }
}
