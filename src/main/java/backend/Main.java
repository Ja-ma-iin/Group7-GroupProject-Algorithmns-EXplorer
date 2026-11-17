
package backend;

import utils.AlgorithmMetrics;

public class Main {

    public static void main(String[] args) {

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        System.out.println("================================");
        System.out.println("     ALGORITHM EXPLORER");
        System.out.println("================================");

        metrics.startTimer();

        // Simulate five algorithm operations
        for (int i = 0; i < 5; i++) {
            metrics.addIteration();
        }

        metrics.stopTimer();

        System.out.println("\nBackend is working!");
        System.out.println("\nPerformance Metrics:");

        metrics.displayMetrics();
    }
}
