
package backend;

import sorting.InsertionSort;
import utils.AlgorithmMetrics;
import java.util.Arrays;

public class InsertionSortTest {

    public static void main(String[] args) {

        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("BEFORE SORTING:");
        System.out.println(Arrays.toString(numbers));

        InsertionSort insertionSort = new InsertionSort();
        AlgorithmMetrics metrics = new AlgorithmMetrics();

        insertionSort.sort(numbers, metrics);

        System.out.println("\nAFTER INSERTION SORT:");
        System.out.println(Arrays.toString(numbers));

        System.out.println("\nPERFORMANCE METRICS:");
        metrics.displayMetrics();
    }
}
