
package backend;

import sorting.MergeSort;
import utils.AlgorithmMetrics;
import java.util.Arrays;

public class MergeSortTest {

    public static void main(String[] args) {

        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("BEFORE SORTING:");
        System.out.println(Arrays.toString(numbers));

        MergeSort mergeSort = new MergeSort();
        AlgorithmMetrics metrics = new AlgorithmMetrics();

        mergeSort.sort(numbers, metrics);

        System.out.println("\nAFTER MERGE SORT:");
        System.out.println(Arrays.toString(numbers));

        System.out.println("\nPERFORMANCE METRICS:");
        metrics.displayMetrics();
    }
}
