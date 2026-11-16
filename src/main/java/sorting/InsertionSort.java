package sorting;

import utils.AlgorithmMetrics;

public class InsertionSort {

    public void sort(int[] array, AlgorithmMetrics metrics) {
        sort(array, metrics, null);
    }

    public void sort(int[] array, AlgorithmMetrics metrics,
                     SortStepListener listener) {
        validate(array, metrics);
        metrics.reset();
        metrics.startTimer();

        notifyStep(array, listener, -1, -1, "Starting Insertion Sort.");

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            notifyStep(array, listener, i, -1,
                    "Taking " + key + " as the value to insert.");

            while (j >= 0) {
                metrics.addComparison();
                notifyStep(array, listener, j, j + 1,
                        "Comparing " + array[j] + " with key " + key + ".");

                if (array[j] > key) {
                    array[j + 1] = array[j];
                    metrics.addSwapOrMove();
                    notifyStep(array, listener, j, j + 1,
                            "Moving " + array[j] + " one position to the right.");
                    j--;
                } else {
                    break;
                }
            }

            array[j + 1] = key;
            metrics.addSwapOrMove();
            notifyStep(array, listener, j + 1, i,
                    "Inserted " + key + " at position " + (j + 1) + ".");
        }

        metrics.stopTimer();
        notifyStep(array, listener, -1, -1, "Insertion Sort completed.");
    }

    private void validate(int[] array, AlgorithmMetrics metrics) {
        if (array == null) throw new IllegalArgumentException("Array cannot be null.");
        if (metrics == null) throw new IllegalArgumentException("Metrics cannot be null.");
    }

    private void notifyStep(int[] array, SortStepListener listener,
                            int a, int b, String description) {
        if (listener != null) listener.onStep(array.clone(), a, b, description);
    }
}
