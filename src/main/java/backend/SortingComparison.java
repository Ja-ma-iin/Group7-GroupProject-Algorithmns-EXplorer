
package backend;

import sorting.BubbleSort;
import sorting.SelectionSort;
import sorting.InsertionSort;
import sorting.MergeSort;
import sorting.QuickSort;
import utils.AlgorithmMetrics;

import java.util.Arrays;

public class SortingComparison {

    public static void main(String[] args) {

        int[] original = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("====================================");
        System.out.println("       SORTING ALGORITHM COMPARISON");
        System.out.println("====================================");

        System.out.println("\nOriginal Array:");
        System.out.println(Arrays.toString(original));

        // Run Bubble Sort
        runSort("Bubble Sort",
                new BubbleSort(), original);

        // Run Selection Sort
        runSort("Selection Sort",
                new SelectionSort(), original);

        // Run Insertion Sort
        runSort("Insertion Sort",
                new InsertionSort(), original);

        // Run Merge Sort
        runSort("Merge Sort",
                new MergeSort(), original);

        // Run First-Pivot Quick Sort
        runQuickSort("Quick Sort (First Pivot)",
                original, false);

        // Run Randomized-Pivot Quick Sort
        runQuickSort("Quick Sort (Random Pivot)",
                original, true);
    }

    private static void runSort(
            String name,
            Object algorithm,
            int[] original) {

        int[] numbers = Arrays.copyOf(
                original, original.length);

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        if (algorithm instanceof BubbleSort) {
            ((BubbleSort) algorithm).sort(numbers, metrics);

        } else if (algorithm instanceof SelectionSort) {
            ((SelectionSort) algorithm).sort(numbers, metrics);

        } else if (algorithm instanceof InsertionSort) {
            ((InsertionSort) algorithm).sort(numbers, metrics);

        } else if (algorithm instanceof MergeSort) {
            ((MergeSort) algorithm).sort(numbers, metrics);
        }

        displayResult(name, numbers, metrics);
    }

    private static void runQuickSort(
            String name,
            int[] original,
            boolean randomized) {

        int[] numbers = Arrays.copyOf(
                original, original.length);

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        QuickSort quickSort = new QuickSort();

        if (randomized) {
            quickSort.sortRandomizedPivot(numbers, metrics);
        } else {
            quickSort.sort(numbers, metrics);
        }

        displayResult(name, numbers, metrics);
    }

    private static void displayResult(
            String name,
            int[] numbers,
            AlgorithmMetrics metrics) {

        System.out.println("\n------------------------------------");
        System.out.println(name);
        System.out.println("------------------------------------");

        System.out.println("Sorted Array: "
                + Arrays.toString(numbers));

        System.out.println("Comparisons: "
                + metrics.getComparisons());

        System.out.println("Swaps/Moves: "
                + metrics.getSwapsOrMoves());

        System.out.println("Execution Time: "
                + metrics.getExecutionTime() + " ns");
    }
}
