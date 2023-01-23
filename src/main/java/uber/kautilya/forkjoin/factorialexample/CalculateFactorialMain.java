package uber.kautilya.forkjoin.factorialexample;

import java.util.concurrent.ForkJoinPool;

public class CalculateFactorialMain {
  public static void main(String[] args) {
    Integer number = 6;
    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    CalculateFactorialTask calculateFactorialTask = new CalculateFactorialTask(1, number);
    Integer result = forkJoinPool.invoke(calculateFactorialTask);
    System.out.format("Factorial of %d: %d", number, result);
  }
}
