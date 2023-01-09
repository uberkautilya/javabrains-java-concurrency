package uber.kautilya;

import uber.kautilya.prime.PrimeNumberUtil;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    //The while loop is not brocked as each calculation is done in a separate thread
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.print("\n0 to end. Calculate nth prime number. Enter n: ");
      int n = sc.nextInt();
      if (n == 0) break;

      Runnable r = new Runnable() {
        @Override
        public void run() {
          Integer number = PrimeNumberUtil.calculatePrime(n);
          System.out.format("\nThe %d th prime number is: %d", n, number);
        }
      };
      Thread t = new Thread(r);
      //When the main method thread ends, daemon threads are killed
      //Useful in monitoring, logging functions etc., which have meaning while the main application is alive
      t.setDaemon(true);
      t.start();
    }

  }

}
