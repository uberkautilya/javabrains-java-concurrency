package uber.kautilya.prime;

public class PrimeNumberUtil {
  /**
   * Inefficient way to calculate prime number that burns CPU cycles to test multi threading
   * @param n
   * @return
   */
  public static Integer calculatePrime(int n) {
    Integer number;
    int numberOfPrimesFound;
    int i;
    number = 1;
    numberOfPrimesFound = 0;
    while (numberOfPrimesFound < n) {
      number++;
      for (i = 2; i <= number && number % i != 0; i++) {
      }
      if (i == number) {
        numberOfPrimesFound++;
      }
    }
    return number;
  }
}
