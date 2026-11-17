package sorting;

/**
 * Receives snapshots from sorting algorithms for visualisation.
 */
public interface SortStepListener {
    void onStep(int[] array, int indexA, int indexB, String description);
}
