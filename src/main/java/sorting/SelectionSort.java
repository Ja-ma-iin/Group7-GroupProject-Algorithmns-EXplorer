package sorting;

import utils.AlgorithmMetrics;

public class SelectionSort {

    public void sort(int[] array, AlgorithmMetrics metrics) {
        sort(array, metrics, null);
    }

    public void sort(int[] array, AlgorithmMetrics metrics,
                     SortStepListener listener) {
        validate(array, metrics);
        metrics.reset();
        metrics.startTimer();

        notifyStep(array, listener, -1, -1, "Starting Selection Sort.");
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            notifyStep(array, listener, i, minIndex,
                    "Finding the smallest value in the unsorted section.");

            for (int j = i + 1; j < n; j++) {
                metrics.addComparison();
                notifyStep(array, listener, minIndex, j,
                        "Comparing current minimum " + array[minIndex]
                                + " with " + array[j] + ".");
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                    notifyStep(array, listener, i, minIndex,
                            "New minimum found: " + array[minIndex] + ".");
                }
            }

            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                metrics.addSwapOrMove();
                notifyStep(array, listener, i, minIndex,
                        "Placed the minimum value " + array[i]
                                + " into position " + i + ".");
            } else {
                notifyStep(array, listener, i, minIndex,
                        array[i] + " is already in its correct position.");
            }
        }

        metrics.stopTimer();
        notifyStep(array, listener, -1, -1, "Selection Sort completed.");
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
