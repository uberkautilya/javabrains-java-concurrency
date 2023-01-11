package uber.kautilya.forkjoin.forkjointask;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.concurrent.RecursiveTask;

/*
compute() either does the job or breaks it down further
 */
public class CalculatePrimeSumTask extends RecursiveTask<Integer> {
  int[] array;
  int start;
  int end;

  public CalculatePrimeSumTask(int[] array, int start, int end) {
    this.array = array;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    if (start == end) {
      Integer primeStart = PrimeNumberUtil.calculatePrime(array[start]);
      System.out.println(array[start] + ":" + primeStart);
      return primeStart;
    }
    if (end - start == 1) {
      Integer primeStart = PrimeNumberUtil.calculatePrime(array[start]);
      Integer primeEnd = PrimeNumberUtil.calculatePrime(array[end]);
      System.out.println(array[start] + ":" + primeStart);
      System.out.println(array[end] + ":" + primeEnd);
      //End point criteria to do the work.
      //Here, Don't want to split task further if the task is just 2 prime number calculations
      return primeStart + primeEnd;
    }
    //Otherwise split the task further into sub tasks
    int mid = (start + end) / 2;
    CalculatePrimeSumTask subTask1 = new CalculatePrimeSumTask(array, start, mid);
    CalculatePrimeSumTask subTask2 = new CalculatePrimeSumTask(array, mid + 1, end);

    invokeAll(subTask1, subTask2);
    return subTask1.join() + subTask2.join();
  }
}
