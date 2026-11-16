
package backend;

import algorithms.Fibonacci;
import utils.AlgorithmMetrics;

public class FibonacciTest {

    public static void main(String[] args) {

        int n = 31;

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        System.out.println("================================");
        System.out.println("       FIBONACCI TEST");
        System.out.println("================================");

        // Test 1: Naive recursion
        metrics.reset();
        metrics.startTimer();

        long result1 = Fibonacci.naiveFibonacci(n, metrics);

        metrics.stopTimer();

        System.out.println("\n1. NAIVE RECURSION");
        System.out.println("Result: " + result1);
        metrics.displayMetrics();


        // Test 2: Memoized recursion
        metrics.reset();
        metrics.startTimer();

        long result2 = Fibonacci.memoizedFibonacci(n, metrics);

        metrics.stopTimer();

        System.out.println("\n2. MEMOIZED RECURSION");
        System.out.println("Result: " + result2);
        metrics.displayMetrics();


        // Test 3: Bottom-up dynamic programming
        metrics.reset();
        metrics.startTimer();

        long result3 = Fibonacci.bottomUpFibonacci(n, metrics);

        metrics.stopTimer();

        System.out.println("\n3. BOTTOM-UP DYNAMIC PROGRAMMING");
        System.out.println("Result: " + result3);
        metrics.displayMetrics();


        // Confirm that all algorithms agree.
        System.out.println("\n================================");

        if (result1 == result2 && result2 == result3) {
            System.out.println("SUCCESS: All results match!");
        } else {
            System.out.println("ERROR: Results do not match!");
        }

        System.out.println("================================");
    }
}
