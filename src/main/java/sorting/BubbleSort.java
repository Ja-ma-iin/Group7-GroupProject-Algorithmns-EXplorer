package sorting;

import utils.AlgorithmMetrics;

public class BubbleSort {

    public void sort(int[] array, AlgorithmMetrics metrics) {
        sort(array, metrics, null);
    }

    public void sort(int[] array, AlgorithmMetrics metrics,
                     SortStepListener listener) {
        validate(array, metrics);
        metrics.reset();
        metrics.startTimer();

        notifyStep(array, listener, -1, -1, "Starting Bubble Sort.");

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                metrics.addComparison();
                notifyStep(array, listener, j, j + 1,
                        "Comparing " + array[j] + " and " + array[j + 1] + ".");

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    metrics.addSwapOrMove();
                    swapped = true;

                    notifyStep(array, listener, j, j + 1,
                            "Swapped " + array[j] + " and " + array[j + 1] + ".");
                }
            }

            notifyStep(array, listener, -1, -1,
                    "Completed Bubble Sort pass " + (i + 1) + ".");
            if (!swapped) {
                notifyStep(array, listener, -1, -1,
                        "No swaps occurred. The array is sorted.");
                break;
            }
        }

        metrics.stopTimer();
        notifyStep(array, listener, -1, -1, "Bubble Sort completed.");
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
