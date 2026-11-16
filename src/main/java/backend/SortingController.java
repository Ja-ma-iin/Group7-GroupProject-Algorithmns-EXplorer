package backend;

import frontend.SortingPanel;
import sorting.*;
import utils.AlgorithmMetrics;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingController implements SortingPanel.Listener {

    private static class Step {
        private final int[] array;
        private final int indexA;
        private final int indexB;
        private final String description;

        private Step(int[] array, int indexA, int indexB, String description) {
            this.array = array.clone();
            this.indexA = indexA;
            this.indexB = indexB;
            this.description = description;
        }
    }

    private final SortingPanel panel;
    private final List<Step> steps = new ArrayList<>();
    private final Timer autoPlayTimer;

    private int currentStep = -1;
    private AlgorithmMetrics lastMetrics = new AlgorithmMetrics();

    public SortingController(SortingPanel panel) {
        if (panel == null) throw new IllegalArgumentException("SortingPanel cannot be null.");
        this.panel = panel;
        this.panel.setListener(this);

        autoPlayTimer = new Timer(700, event -> requestNextStep());
    }

    @Override
    public void startSorting(int[] values, String algorithm, String pivotStrategy) {
        stopAutoPlay();
        steps.clear();
        currentStep = -1;

        if (values == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }

        int[] workingArray = values.clone();
        lastMetrics = new AlgorithmMetrics();

        steps.add(new Step(workingArray, -1, -1, "Initial array."));

        SortStepListener recorder = (array, a, b, description) ->
                steps.add(new Step(array, a, b, description));

        try {
            if ("Bubble Sort".equals(algorithm)) {
                new BubbleSort().sort(workingArray, lastMetrics, recorder);
            } else if ("Selection Sort".equals(algorithm)) {
                new SelectionSort().sort(workingArray, lastMetrics, recorder);
            } else if ("Insertion Sort".equals(algorithm)) {
                new InsertionSort().sort(workingArray, lastMetrics, recorder);
            } else if ("Merge Sort".equals(algorithm)) {
                new MergeSort().sort(workingArray, lastMetrics, recorder);
            } else if ("Quick Sort".equals(algorithm)) {
                QuickSort quickSort = new QuickSort();
                if ("Randomised Pivot".equals(pivotStrategy)
                        || "Randomized Pivot".equals(pivotStrategy)) {
                    quickSort.sortRandomizedPivot(workingArray, lastMetrics, recorder);
                } else {
                    quickSort.sort(workingArray, lastMetrics, recorder);
                }
            } else {
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
            }

            if (steps.isEmpty()
                    || !Arrays.equals(steps.get(steps.size() - 1).array, workingArray)) {
                steps.add(new Step(workingArray, -1, -1, "Sorting completed."));
            }

            currentStep = 0;
            displayCurrentStep();

            if (steps.size() == 1) {
                finishSorting();
            }
        } catch (RuntimeException ex) {
            stopAutoPlay();
            panel.showSortingComplete("Sorting failed: " + ex.getMessage(),
                    "0", "0", "Not measured");
            throw ex;
        }
    }

    @Override
    public void requestNextStep() {
        if (steps.isEmpty() || currentStep >= steps.size() - 1) {
            finishSorting();
            return;
        }

        currentStep++;
        displayCurrentStep();

        if (currentStep == steps.size() - 1) {
            finishSorting();
        }
    }

    private void displayCurrentStep() {
        Step step = steps.get(currentStep);
        panel.updateArray(
                step.array,
                step.indexA,
                step.indexB,
                step.description,
                currentStep + 1,
                steps.size()
        );
    }

    private void finishSorting() {
        stopAutoPlay();
        panel.showSortingComplete(
                "Sorting completed successfully.",
                String.valueOf(lastMetrics.getComparisons()),
                String.valueOf(lastMetrics.getSwapsOrMoves()),
                String.format("%.4f ms", lastMetrics.getExecutionTimeMilliseconds())
        );
    }

    @Override
    public void setAutoPlay(boolean enabled, int delayMillis) {
        if (enabled && !steps.isEmpty() && currentStep < steps.size() - 1) {
            autoPlayTimer.setDelay(Math.max(50, delayMillis));
            autoPlayTimer.setInitialDelay(Math.max(50, delayMillis));
            autoPlayTimer.start();
        } else {
            stopAutoPlay();
        }
    }

    private void stopAutoPlay() {
        if (autoPlayTimer != null) autoPlayTimer.stop();
    }

    @Override
    public void compareAll(int[] values) {
        if (values == null) throw new IllegalArgumentException("Input array cannot be null.");

        String[][] results = new String[5][4];
        runComparison(results, 0, "Bubble Sort", values, 0);
        runComparison(results, 1, "Selection Sort", values, 1);
        runComparison(results, 2, "Insertion Sort", values, 2);
        runComparison(results, 3, "Merge Sort", values, 3);
        runComparison(results, 4, "Quick Sort", values, 4);

        panel.updateComparisonResults(results);
    }

    private void runComparison(String[][] results, int row, String name,
                               int[] original, int algorithmIndex) {
        int[] copy = original.clone();
        AlgorithmMetrics metrics = new AlgorithmMetrics();

        switch (algorithmIndex) {
            case 0: new BubbleSort().sort(copy, metrics); break;
            case 1: new SelectionSort().sort(copy, metrics); break;
            case 2: new InsertionSort().sort(copy, metrics); break;
            case 3: new MergeSort().sort(copy, metrics); break;
            case 4: new QuickSort().sort(copy, metrics); break;
            default: throw new IllegalArgumentException("Unsupported algorithm.");
        }

        results[row][0] = name;
        results[row][1] = String.valueOf(metrics.getComparisons());
        results[row][2] = String.valueOf(metrics.getSwapsOrMoves());
        results[row][3] = String.format("%.4f ms",
                metrics.getExecutionTimeMilliseconds());
    }
}
