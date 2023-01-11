package uber.kautilya.forkjoin;

import uber.kautilya.forkjoin.forkjointask.CalculatePrimeSumTask;

import java.util.concurrent.ForkJoinPool;

/*
Accept an array of numbers
Calculates nth prime number - n being the value of i in an array arr[i]
Add all elements of the so calculated array of prime numbers
*/
public class ForkJoinDemo {
  public static void main(String[] args) {
    new ForkJoinDemo().runForkJoinExample();
  }

  public void runForkJoinExample() {
    int[] input = {2, 3, 4, 5, 6, 7, 8, 9, 10};
    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    CalculatePrimeSumTask calculatePrimeSumTask = new CalculatePrimeSumTask(input, 0, input.length - 1);

    Integer sumOfPrimes = forkJoinPool.invoke(calculatePrimeSumTask);
    System.out.println("Sum of primes calculated: " + sumOfPrimes);
  }
}
