
package utils;

public class AlgorithmMetrics {

    private long comparisons;
    private long swapsOrMoves;
    private long recursiveCalls;
    private long iterations;

    private long startTime;
    private long executionTime;

    // Reset all counters before a new algorithm run.
    public void reset() {
        comparisons = 0;
        swapsOrMoves = 0;
        recursiveCalls = 0;
        iterations = 0;
        startTime = 0;
        executionTime = 0;
    }

    // Start measuring execution time.
    public void startTimer() {
        startTime = System.nanoTime();
    }

    // Stop measuring execution time.
    public void stopTimer() {
        executionTime = System.nanoTime() - startTime;
    }

    // Record a comparison.
    public void addComparison() {
        comparisons++;
    }

    // Record a swap or movement.
    public void addSwapOrMove() {
        swapsOrMoves++;
    }

    // Record a recursive function call.
    public void addRecursiveCall() {
        recursiveCalls++;
    }

    // Record a loop iteration.
    public void addIteration() {
        iterations++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwapsOrMoves() {
        return swapsOrMoves;
    }

    public long getRecursiveCalls() {
        return recursiveCalls;
    }

    public long getIterations() {
        return iterations;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public double getExecutionTimeMilliseconds() {
        return executionTime / 1_000_000.0;
    }

    // Display the performance results.
    public void displayMetrics() {
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps/Moves: " + swapsOrMoves);
        System.out.println("Recursive Calls: " + recursiveCalls);
        System.out.println("Iterations: " + iterations);
        System.out.println(
            "Execution Time: " +
            getExecutionTimeMilliseconds() + " ms"
        );
    }
}
