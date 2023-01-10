package uber.kautilya;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterDemoWithLockInterface {
  public static void main(String[] args) {

    CounterWithLockInterface counter = new CounterWithLockInterface();
    new Thread(counter, "One").start();
    new Thread(counter, "Two").start();
    new Thread(counter, "Three").start();
    new Thread(counter, "Four").start();

    new Thread(new CounterWithLockInterface(), "Counter2 Thread").start();
  }
}

class CounterWithLockInterface implements Runnable {
  private int value = 0;
  //A ReentrantLock is a direct equivalent of a synchronized monitor lock
  private Lock reentrantLock = new ReentrantLock();

  @Override
  public void run() {
    //Acquire a lock on the reentrantLock object in the instance
    reentrantLock.lock();
    try {
      this.increment();
      System.out.println(Thread.currentThread().getName() + " increments: " + this.getValue());
      this.decrement();
      System.out.println(Thread.currentThread().getName() + " decrements: " + this.getValue());
    } finally {
      //Release the lock in a 'finally' block to handle the potential for an exception
      reentrantLock.unlock();
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
