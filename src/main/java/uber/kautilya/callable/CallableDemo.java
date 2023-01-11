package uber.kautilya.callable;

import java.util.concurrent.*;

public class CallableDemo {
  public static void main(String[] args) {
    /*
    Callable needs an implementation for method call().
    A callable can be submitted to the executor service to create a separate thread
    But cannot pass Callable to a thread
    */
    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        System.out.println("Print from within the callable");
        Thread.sleep(2000);
        return "String from Callable";
      }
    };

    ExecutorService execService = Executors.newFixedThreadPool(1);
    Future<String> resultFuture = execService.submit(callable);
    /* ..The main thread can continue working on other tasks while callable is handled in another thread */
    System.out.println("Main thread activities");
    printResult(resultFuture);
    execService.shutdown();
  }

  private static void printResult(Future<String> resultFuture) {
    try {
      /* Blocks: resultFuture.get blocks the execution till it resolves into a value */
      System.out.println("Result: " + resultFuture.get());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      System.out.println("ExecutionException e: "+e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /* Runnable does not return value */
  Runnable r = ()->{
    System.out.println("Print from runnable");
  };
  /* Need an ExecutorService or a Thread to run this runnable in a separate thread */



}
