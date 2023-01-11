package uber.kautilya.callable;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.*;
import java.util.concurrent.*;

public class FuturesAndCallableDemoPrime {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Map<Integer, Future<Integer>> futures = new LinkedHashMap<>();

    //The while loop is not blocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      Callable<Integer> callable = () -> PrimeNumberUtil.calculatePrime(n);
      Future<Integer> future = executorService.submit(callable);
      //future has a cancel() method which will attempt to cancel the execution of the task
      if (!futures.containsKey(n)) {
        futures.put(n, future);
      }

      //For each will throw a concurrent modification exception
      Iterator<Map.Entry<Integer, Future<Integer>>> iterator = futures.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<Integer, Future<Integer>> next = iterator.next();
        if (next.getValue().isDone()) {
          System.out.println(next.getKey() + " th Prime number: " + next.getValue().get());
          iterator.remove();
        }
      }
    }
    executorService.shutdown();
  }


}
