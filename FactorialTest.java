
package backend;

import algorithms.Factorial;
import utils.AlgorithmMetrics;

public class FactorialTest {

    public static void main(String[] args) {

        int n = 5;

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        System.out.println("================================");
        System.out.println("       FACTORIAL TEST");
        System.out.println("================================");

        // Test recursive factorial
        metrics.reset();
        metrics.startTimer();

        long recursiveResult =
                Factorial.recursiveFactorial(n, metrics);

        metrics.stopTimer();

        System.out.println("\n1. RECURSIVE FACTORIAL");
        System.out.println("Input: " + n);
        System.out.println("Result: " + recursiveResult);
        metrics.displayMetrics();

        // Test iterative factorial
        metrics.reset();
        metrics.startTimer();

        long iterativeResult =
                Factorial.iterativeFactorial(n, metrics);

        metrics.stopTimer();

        System.out.println("\n2. ITERATIVE FACTORIAL");
        System.out.println("Input: " + n);
        System.out.println("Result: " + iterativeResult);
        metrics.displayMetrics();

        // Compare results
        System.out.println("\n================================");

        if (recursiveResult == iterativeResult) {
            System.out.println("SUCCESS: Both results match!");
        } else {
            System.out.println("ERROR: Results do not match!");
        }

        System.out.println("================================");
    }
}
