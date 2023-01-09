package uber.kautilya;

public class CounterDemo {
  public static void main(String[] args) {
    //All threads operating on the same object with no synchronization, value change is non-deterministic
    //Each thread copies to core registers and does execution with no synchronization
    //Now synchronized, needing a lock on the current instance
    Counter counter = new Counter();
    new Thread(counter, "One").start();
    new Thread(counter, "Two").start();
    new Thread(counter, "Three").start();
    new Thread(counter, "Four").start();

    //This thread can get a lock on the new object, without waiting for a lock on 'counter' object
    new Thread(new Counter(), "Counter2 Thread").start();
  }
}

class Counter implements Runnable {
  private int value = 0;
//  private Integer iVar = 0;

  @Override
  public void run() {
    //A lock on 'refObject' - any object is required to be acquired before this block of code can be executed
    //A thread that calls the method on a different instance shouldn't be blocked. To this end, pass 'this' reference to the synchronized block
    //Another thread working with a different object will be able to get a lock on it
    synchronized (this) {
      this.increment();
      System.out.println(Thread.currentThread().getName() + " increments: " + this.getValue());
      this.decrement();
      System.out.println(Thread.currentThread().getName() + " decrements: " + this.getValue());
    }
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
