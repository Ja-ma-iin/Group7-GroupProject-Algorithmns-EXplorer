
package backend;

import sorting.BubbleSort;
import utils.AlgorithmMetrics;
import java.util.Arrays;

public class BubbleSortTest {

    public static void main(String[] args) {

        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("BEFORE SORTING:");
        System.out.println(Arrays.toString(numbers));

        BubbleSort bubbleSort = new BubbleSort();
        AlgorithmMetrics metrics = new AlgorithmMetrics();

        bubbleSort.sort(numbers, metrics);

        System.out.println("\nAFTER BUBBLE SORT:");
        System.out.println(Arrays.toString(numbers));

        System.out.println("\nPERFORMANCE METRICS:");
        metrics.displayMetrics();
    }
}
