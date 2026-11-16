
package backend;

import sorting.QuickSort;
import utils.AlgorithmMetrics;
import java.util.Arrays;

public class QuickSortTest {

    public static void main(String[] args) {

        int[] original = {64, 34, 25, 12, 22, 11, 90};

        // Create separate copies for both tests
        int[] firstPivotNumbers = Arrays.copyOf(
                original, original.length);

        int[] randomizedNumbers = Arrays.copyOf(
                original, original.length);

        QuickSort quickSort = new QuickSort();

        AlgorithmMetrics firstMetrics = new AlgorithmMetrics();
        AlgorithmMetrics randomMetrics = new AlgorithmMetrics();

        // Test 1: First-element pivot
        System.out.println("FIRST-ELEMENT PIVOT QUICK SORT");

        System.out.println("Before sorting:");
        System.out.println(Arrays.toString(firstPivotNumbers));

        quickSort.sort(firstPivotNumbers, firstMetrics);

        System.out.println("\nAfter sorting:");
        System.out.println(Arrays.toString(firstPivotNumbers));

        System.out.println("\nPerformance metrics:");
        firstMetrics.displayMetrics();

        // Test 2: Randomized pivot
        System.out.println("\n================================");

        System.out.println("RANDOMIZED-PIVOT QUICK SORT");

        System.out.println("Before sorting:");
        System.out.println(Arrays.toString(randomizedNumbers));

        quickSort.sortRandomizedPivot(
                randomizedNumbers, randomMetrics);

        System.out.println("\nAfter sorting:");
        System.out.println(Arrays.toString(randomizedNumbers));

        System.out.println("\nPerformance metrics:");
        randomMetrics.displayMetrics();
    }
}
