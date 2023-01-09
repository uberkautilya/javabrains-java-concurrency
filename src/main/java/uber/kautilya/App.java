package uber.kautilya;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    List<Thread> threadList = new ArrayList<>();
    Thread statusThread = new Thread(() -> {
      try {
        //Pause for 5 seconds - static method sleep() on current thread - Timed Waiting Lifecycle state
        Thread.sleep(5000);
        printThreadStatus(threadList);
      } catch (InterruptedException e) {
        System.out.println("Exception: " + e.getMessage());
      }
    }, "statusThread");
    //Daemon: This thread ends when the main thread is ended
    statusThread.setDaemon(true);
    statusThread.start();

    //The while loop is not brocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) break;

      Runnable r = new Runnable() {
        @Override
        public void run() {
          Integer number = PrimeNumberUtil.calculatePrime(n);
          System.out.format("\nThe %d th prime number is: %d", n, number);
        }
      };
      Thread t = new Thread(r);
      //When the main method thread ends, daemon threads are killed
      //Useful in monitoring, logging functions etc., which have meaning while the main application is alive
      t.setDaemon(true);
      threadList.add(t);
      t.start();
    }

  }

  private static void printThreadStatus(List<Thread> threadList) throws InterruptedException {
    while (true) {
      System.out.println("\nThread Status: ");
      threadList.forEach(t -> {
        System.out.println("Name: " + t.getName());
        System.out.println("Status: " + t.getState());
      });
    }
  }

}
