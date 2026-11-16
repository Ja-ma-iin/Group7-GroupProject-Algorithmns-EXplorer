
package backend;

import sorting.SelectionSort;
import utils.AlgorithmMetrics;
import java.util.Arrays;

public class SelectionSortTest {

    public static void main(String[] args) {

        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("BEFORE SORTING:");
        System.out.println(Arrays.toString(numbers));

        SelectionSort selectionSort = new SelectionSort();
        AlgorithmMetrics metrics = new AlgorithmMetrics();

        selectionSort.sort(numbers, metrics);

        System.out.println("\nAFTER SELECTION SORT:");
        System.out.println(Arrays.toString(numbers));

        System.out.println("\nPERFORMANCE METRICS:");
        metrics.displayMetrics();
    }
}
