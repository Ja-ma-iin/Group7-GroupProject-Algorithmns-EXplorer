package sorting;

import utils.AlgorithmMetrics;
import java.util.Random;

public class QuickSort {

    private final Random random = new Random();

    public void sort(int[] array, AlgorithmMetrics metrics) {
        sort(array, metrics, null);
    }

    public void sort(int[] array, AlgorithmMetrics metrics,
                     SortStepListener listener) {
        runSort(array, metrics, listener, false);
    }

    public void sortRandomizedPivot(int[] array, AlgorithmMetrics metrics) {
        sortRandomizedPivot(array, metrics, null);
    }

    public void sortRandomizedPivot(int[] array, AlgorithmMetrics metrics,
                                    SortStepListener listener) {
        runSort(array, metrics, listener, true);
    }

    private void runSort(int[] array, AlgorithmMetrics metrics,
                         SortStepListener listener, boolean randomized) {
        validate(array, metrics);
        metrics.reset();
        metrics.startTimer();

        notifyStep(array, listener, -1, -1,
                randomized ? "Starting Quick Sort with a randomized pivot."
                          : "Starting Quick Sort with the first-element pivot.");

        quickSort(array, 0, array.length - 1, metrics, randomized, listener);

        metrics.stopTimer();
        notifyStep(array, listener, -1, -1, "Quick Sort completed.");
    }

    private void quickSort(int[] array, int low, int high,
                           AlgorithmMetrics metrics, boolean randomized,
                           SortStepListener listener) {
        while (low < high) {
            metrics.addRecursiveCall();

            if (randomized) {
                int randomIndex = low + random.nextInt(high - low + 1);
                swap(array, low, randomIndex, metrics, listener);
                notifyStep(array, listener, low, randomIndex,
                        "Selected a random pivot and moved it to the start.");
            }

            int pivotIndex = partition(array, low, high, metrics, listener);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(array, low, pivotIndex - 1,
                        metrics, randomized, listener);
                low = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, high,
                        metrics, randomized, listener);
                high = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] array, int low, int high,
                          AlgorithmMetrics metrics, SortStepListener listener) {
        int pivot = array[low];
        int yellow = low;

        notifyStep(array, listener, low, high,
                "Partitioning around pivot " + pivot
                        + ". Yellow region: values <= pivot; green region: values > pivot.");

        for (int green = low + 1; green <= high; green++) {
            metrics.addComparison();
            notifyStep(array, listener, low, green,
                    "Comparing " + array[green] + " with pivot " + pivot + ".");

            if (array[green] <= pivot) {
                yellow++;
                if (yellow != green) {
                    swap(array, yellow, green, metrics, listener);
                    notifyStep(array, listener, yellow, green,
                            "Moved " + array[yellow] + " into the yellow region.");
                }
            }
        }

        if (yellow != low) {
            swap(array, low, yellow, metrics, listener);
        }

        notifyStep(array, listener, yellow, -1,
                "Pivot " + array[yellow] + " is now in its final position.");
        return yellow;
    }

    private void swap(int[] array, int first, int second,
                      AlgorithmMetrics metrics, SortStepListener listener) {
        if (first == second) return;
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
        metrics.addSwapOrMove();
        notifyStep(array, listener, first, second,
                "Swapped positions " + first + " and " + second + ".");
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
