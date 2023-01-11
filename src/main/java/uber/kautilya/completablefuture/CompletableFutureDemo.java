package uber.kautilya.completablefuture;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

public class CompletableFutureDemo {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    //The while loop is not blocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      /*
      CompletableFuture does an action once a future is resolved
      CompletableFuture supplyAsync will run the argument lambda in a separate thread,
      Once completed thenAccept receives the result as its argument for its lambda
      */
      CompletableFuture.supplyAsync(() -> PrimeNumberUtil.calculatePrime(n), executorService)
        .thenAccept((Integer prime) -> System.out.print("\n" + n + " th Prime number: " + prime));
      //If the executorService object is not supplied to the supplyAsync, the lambda is run in a shared thread pool:
      //in a Fork Join Thread Pool
    }
    executorService.shutdown();
  }
}
