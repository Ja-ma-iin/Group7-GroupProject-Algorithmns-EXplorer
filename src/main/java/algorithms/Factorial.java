
package algorithms;

import utils.AlgorithmMetrics;

public class Factorial {

    // Validates input.
    // Java long can safely store factorials from 0! to 20!.
    private static void validateInput(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException(
                "Enter a number between 0 and 20."
            );
        }
    }

    // 1. RECURSIVE FACTORIAL
    // Time Complexity: O(n)
    // Space Complexity: O(n), due to the recursive call stack.
    public static long recursiveFactorial(int n, AlgorithmMetrics metrics) {
        validateInput(n);

        metrics.addRecursiveCall();

        // Base case: 0! = 1 and 1! = 1
        if (n == 0 || n == 1) {
            return 1;
        }

        return n * recursiveFactorial(n - 1, metrics);
    }

    // 2. ITERATIVE FACTORIAL
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static long iterativeFactorial(int n, AlgorithmMetrics metrics) {
        validateInput(n);

        long result = 1;

        for (int i = 2; i <= n; i++) {
            metrics.addIteration();
            result *= i;
        }

        return result;
    }
}
