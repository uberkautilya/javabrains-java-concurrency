package uber.kautilya.semaphores;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
  public static void main(String[] args) throws InterruptedException {
    /*
     * Only 3 permits are assigned to the semaphore - implies only 3 threads can simultaneously acquire it
     * The second argument fair=true ensures first-in-first-out for waiting threads requesting a semaphore
    */
    Semaphore semaphore = new Semaphore(3, true);

    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      Runnable r = () -> {
        try {
          //Tries to acquire one permit (analogous to a monitor lock, but multiple)
          //A blocking operation if semaphore is currently exhausted
          semaphore.acquire(1);
          //semaphore.acquireUninterruptibly() will acquire a semaphore only if the thread hasn't been interrupted
          System.out.print("\nSemaphore acquired. Calculating n the prime number");
          Integer number = PrimeNumberUtil.calculatePrime(n);
          System.out.format("\nThe %d th prime number is: %d", n, number);
        } catch (InterruptedException e) {
          System.out.print("\nInterrupted Exception");
        } finally {
          semaphore.release();
        }
      };
      Thread t = new Thread(r);
      t.start();
      Thread.sleep(1000);
    }
  }


}
