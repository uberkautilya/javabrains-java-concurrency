package uber.kautilya;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    List<Thread> calculatePrimeThreadList = new ArrayList<>();
    Thread statusThread = new Thread(() -> {
      printThreadStatus(calculatePrimeThreadList);
    }, "statusThread");
    //Daemon: This thread ends when the main thread is ended
    //Useful in monitoring, logging functions etc., which have meaning while the main application is alive
    statusThread.setDaemon(true);
    statusThread.start();

    //The while loop is not blocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) {
        exitAsNumberIsZero(calculatePrimeThreadList, statusThread);
        break;
      }

      Runnable r = new Runnable() {
        @Override
        public void run() {
          Integer number = PrimeNumberUtil.calculatePrime(n);
          System.out.format("\nThe %d th prime number is: %d", n, number);
        }
      };
      Thread t = new Thread(r);
      t.setDaemon(true);
      calculatePrimeThreadList.add(t);
      t.start();
    }

  }

  private static void exitAsNumberIsZero(List<Thread> calculatePrimeThreadList, Thread statusThread) {
    //Since the statusThread is a daemon, it will end when the parent thread ends.However,
    // the alternate is interrupt it (can be any - daemon or user thread)
    statusThread.interrupt();
    System.out.println("Waiting on calculatePrimeThreads to complete their execution");
    waitForJoinThreads(calculatePrimeThreadList);
    System.out.println(calculatePrimeThreadList.size() + " prime numbers calculated");
  }

  private static void printThreadStatus(List<Thread> threadList) {
    try {
      while (true) {
        //Pause for 5 seconds - static method sleep() on current thread - Timed Waiting Lifecycle state
        Thread.sleep(5000);
        System.out.println("\nThread Status: ");
        threadList.forEach(t -> {
          System.out.println("Name: " + t.getName());
          System.out.println("Status: " + t.getState());
        });
      }
    } catch (InterruptedException e) {
      System.out.println("Status Thread interrupted. Ending status updates");
    }
  }

  /**
   * For threads in the argument thread list, waits for all threads to complete execution
   * @param threadList
   */
  private static void waitForJoinThreads(List<Thread> threadList) {
    try {
      for (Thread thread : threadList) {
        if (thread.getState() != Thread.State.TERMINATED) {
          thread.join();
        }
      }
    } catch (InterruptedException e) {
      System.out.println("Exception: " + e.getMessage());
    }
  }


}
