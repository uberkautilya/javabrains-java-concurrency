package uber.kautilya.forkjoin.factorialexample;

import java.util.concurrent.RecursiveTask;

public class CalculateFactorialTask extends RecursiveTask<Integer> {
  Integer start;
  Integer end;

  public CalculateFactorialTask(Integer start, Integer end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    if (start == end) {
      return start;
    } else if (end - start == 1) {
      return start * end;
    } else {
      Integer mid = (start + end) / 2;
      CalculateFactorialTask calculateFactorialTask1 = new CalculateFactorialTask(start, mid);
      CalculateFactorialTask calculateFactorialTask2 = new CalculateFactorialTask(mid + 1, end);
      invokeAll(calculateFactorialTask1, calculateFactorialTask2);
      return calculateFactorialTask1.join() * calculateFactorialTask2.join();
    }
  }
}
