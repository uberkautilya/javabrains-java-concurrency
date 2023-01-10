package uber.kautilya.executorservice;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class AppWithExecutorService {
  //Prime numbers with a thread pool
  public static void main(String[] args) {

    ExecutorService fixedThreadPoolExecutorService = Executors.newFixedThreadPool(3);
    ExecutorService singleThreadExecutorService = Executors.newSingleThreadExecutor();
    ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
    ThreadPoolExecutor fixedThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    //For reporting. Starting thread pool size is provided. Will scale up when needed
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    Runnable reportRunnable = () -> {
      System.out.print("\nRunning report: " +
        fixedThreadPoolExecutor.getActiveCount() + " active threads, " +
        fixedThreadPoolExecutor.getCompletedTaskCount() + " completed tasks");
    };
    scheduledExecutorService.scheduleAtFixedRate(reportRunnable, 5, 10, TimeUnit.SECONDS);

    //The while loop is not blocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\nOnly 3 threads if fixed thread pool executor service." +
        " 0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      Runnable r = () -> {
        Integer number = PrimeNumberUtil.calculatePrime(n);
        System.out.format("\nThe %d th prime number is: %d", n, number);
      };
      //Needn't create threads manually
      fixedThreadPoolExecutor.execute(r);
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
   *
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
