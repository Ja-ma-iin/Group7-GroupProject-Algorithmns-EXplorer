package sorting;

import utils.AlgorithmMetrics;

public class MergeSort {

    public void sort(int[] array, AlgorithmMetrics metrics) {
        sort(array, metrics, null);
    }

    public void sort(int[] array, AlgorithmMetrics metrics,
                     SortStepListener listener) {
        validate(array, metrics);
        metrics.reset();
        metrics.startTimer();

        notifyStep(array, listener, -1, -1, "Starting Merge Sort.");
        int[] temp = new int[array.length];
        mergeSort(array, temp, 0, array.length - 1, metrics, listener);

        metrics.stopTimer();
        notifyStep(array, listener, -1, -1, "Merge Sort completed.");
    }

    private void mergeSort(int[] array, int[] temp, int left, int right,
                           AlgorithmMetrics metrics, SortStepListener listener) {
        metrics.addRecursiveCall();
        if (left < right) {
            int middle = left + (right - left) / 2;
            notifyStep(array, listener, left, right,
                    "Dividing section " + left + " to " + right + ".");

            mergeSort(array, temp, left, middle, metrics, listener);
            mergeSort(array, temp, middle + 1, right, metrics, listener);
            merge(array, temp, left, middle, right, metrics, listener);
        }
    }

    private void merge(int[] array, int[] temp, int left, int middle, int right,
                       AlgorithmMetrics metrics, SortStepListener listener) {
        int i = left;
        int j = middle + 1;
        int k = left;

        notifyStep(array, listener, left, right,
                "Merging sorted sections " + left + "-" + middle
                        + " and " + (middle + 1) + "-" + right + ".");

        while (i <= middle && j <= right) {
            metrics.addComparison();
            notifyStep(array, listener, i, j,
                    "Comparing " + array[i] + " and " + array[j] + ".");
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
            metrics.addSwapOrMove();
        }

        while (i <= middle) {
            temp[k++] = array[i++];
            metrics.addSwapOrMove();
        }
        while (j <= right) {
            temp[k++] = array[j++];
            metrics.addSwapOrMove();
        }

        for (int x = left; x <= right; x++) {
            array[x] = temp[x];
            metrics.addSwapOrMove();
        }

        notifyStep(array, listener, left, right,
                "Merged section " + left + " to " + right + ".");
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
