package uber.kautilya;

public class CounterDemo {
  public static void main(String[] args) {
    //All threads operating on the same object with no synchronization, value change is non-deterministic
    //Each thread copies to core registers and does execution with no synchronization
    Counter counter = new Counter();
    new Thread(counter, "One").start();
    new Thread(counter, "Two").start();
    new Thread(counter, "Three").start();
    new Thread(counter, "Four").start();
  }
}

class Counter implements Runnable {
  private int value = 0;

  @Override
  public void run() {
    this.increment();
    System.out.println(Thread.currentThread().getName() + " increments: " + this.getValue());
    this.decrement();
    System.out.println(Thread.currentThread().getName() + " decrements: " + this.getValue());
  }

  private void decrement() {
    value--;
  }
  private void increment() {
    value++;
  }
  private int getValue() {
    return value;
  }
}
