
package algorithms;

import utils.AlgorithmMetrics;

public class Fibonacci {

    // Validate that n is within the supported range.
    private static void validateInput(int n) {
        if (n < 0 || n > 92) {
            throw new IllegalArgumentException(
                "Enter a number between 0 and 92."
            );
        }
    }

    // 1. NAIVE RECURSION
    // Time Complexity: O(2^n)
    // Space Complexity: O(n)

    public static long naiveFibonacci(
            int n, AlgorithmMetrics metrics) {

        validateInput(n);

        if (n > 30) {
            throw new IllegalArgumentException(
                "Naive Fibonacci is limited to n <= 30."
            );
        }

        metrics.addRecursiveCall();

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        long first = naiveFibonacci(n - 1, metrics);
        long second = naiveFibonacci(n - 2, metrics);

        return first + second;
    }


    // 2. MEMOIZED RECURSION
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    public static long memoizedFibonacci(
            int n, AlgorithmMetrics metrics) {

        validateInput(n);

        long[] memo = new long[n + 1];

        for (int i = 0; i <= n; i++) {
            memo[i] = -1;
        }

        return memoHelper(n, memo, metrics);
    }

    private static long memoHelper(
            int n, long[] memo, AlgorithmMetrics metrics) {

        metrics.addRecursiveCall();

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (memo[n] != -1) {
            return memo[n];
        }

        memo[n] = memoHelper(n - 1, memo, metrics)
                + memoHelper(n - 2, memo, metrics);

        return memo[n];
    }


    // 3. BOTTOM-UP DYNAMIC PROGRAMMING
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    public static long bottomUpFibonacci(
            int n, AlgorithmMetrics metrics) {

        validateInput(n);

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        long[] dp = new long[n + 1];

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {

            metrics.addIteration();

            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
