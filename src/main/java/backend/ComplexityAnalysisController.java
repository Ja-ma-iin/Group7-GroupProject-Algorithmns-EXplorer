
package backend;

import frontend.ComplexityAnalysisPanel;
import sorting.*;
import utils.AlgorithmMetrics;

import javax.swing.SwingWorker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ComplexityAnalysisController
        implements ComplexityAnalysisPanel.Listener {

    private final ComplexityAnalysisPanel panel;

    private static final int MAX_QUADRATIC_SIZE = 10000;

    private static final String[] ALGORITHMS = {
            "Bubble Sort",
            "Selection Sort",
            "Insertion Sort",
            "Merge Sort",
            "Quick Sort (First Pivot)",
            "Quick Sort (Random Pivot)"
    };

    private static final String[] COMPLEXITIES = {
            "O(n²)",
            "O(n²)",
            "O(n²)",
            "O(n log n)",
            "O(n log n) avg; O(n²) worst",
            "O(n log n) avg; O(n²) worst"
    };

    public ComplexityAnalysisController(
            ComplexityAnalysisPanel panel) {

        if (panel == null) {
            throw new IllegalArgumentException(
                    "Complexity Analysis Panel cannot be null."
            );
        }

        this.panel = panel;
        this.panel.setListener(this);
    }

    // =====================================================
    // RUN BENCHMARK
    // =====================================================

    @Override
    public void runBenchmark(
            int[] inputSizes,
            String inputCondition,
            int repetitions,
            boolean includeLargeTests,
            int[] customValues) {

        if (inputSizes == null || inputSizes.length == 0) {
            panel.showBenchmarkComplete(
                    "Benchmark failed: No input sizes supplied."
            );
            return;
        }

        if (repetitions < 1) {
            panel.showBenchmarkComplete(
                    "Benchmark failed: Repetitions must be positive."
            );
            return;
        }

        if (customValues != null && customValues.length == 0) {
            panel.showBenchmarkComplete(
                    "Benchmark failed: Custom array is empty."
            );
            return;
        }

        int[] sizes = Arrays.copyOf(
                inputSizes,
                inputSizes.length
        );

        int[] values = customValues == null
                ? null
                : Arrays.copyOf(
                        customValues,
                        customValues.length
                );

        boolean allowLargeTests =
                includeLargeTests || values != null;

        new SwingWorker<BenchmarkResult, Void>() {

            @Override
            protected BenchmarkResult doInBackground() {

                return performBenchmark(
                        sizes,
                        inputCondition,
                        repetitions,
                        allowLargeTests,
                        values
                );
            }

            @Override
            protected void done() {

                try {

                    BenchmarkResult result = get();

                    panel.updateBenchmarkResults(
                            result.table
                    );

                    panel.updateCharts(
                            result.inputSizes,
                            result.quadraticNames,
                            result.quadraticTimes,
                            result.nLogNNames,
                            result.nLogNTimes
                    );

                    panel.updateSummary(
                            result.averageTime,
                            result.fastestAlgorithm
                    );

                    panel.showBenchmarkComplete(
                            result.message
                    );

                } catch (Exception ex) {

                    Throwable cause = ex.getCause();

                    String errorMessage = cause != null
                            ? cause.getMessage()
                            : ex.getMessage();

                    panel.showBenchmarkComplete(
                            "Benchmark failed: " + errorMessage
                    );

                    ex.printStackTrace();
                }
            }

        }.execute();
    }

    // =====================================================
    // PERFORM BENCHMARK
    // =====================================================

    private BenchmarkResult performBenchmark(
            int[] requestedSizes,
            String condition,
            int repetitions,
            boolean includeLargeTests,
            int[] customValues) {

        List<Integer> validSizes = new ArrayList<>();

        for (int size : requestedSizes) {

            if (size <= 0) {
                continue;
            }

            if (!includeLargeTests
                    && size > MAX_QUADRATIC_SIZE) {
                continue;
            }

            validSizes.add(size);
        }

        if (validSizes.isEmpty()) {

            throw new IllegalArgumentException(
                    "No valid input sizes were supplied."
            );
        }

        // Custom array mode uses exactly one input size.
        if (customValues != null) {

            validSizes.clear();
            validSizes.add(customValues.length);
        }

        int[] sizes = validSizes.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        List<String[]> rows = new ArrayList<>();

        double[][] allTimes =
                new double[ALGORITHMS.length][sizes.length];

        for (double[] series : allTimes) {
            Arrays.fill(series, Double.NaN);
        }

        int largestCommonSize = 0;

        double fastestAtLargestSize = Double.MAX_VALUE;

        String fastestAlgorithm = "Unavailable";

        double totalTimeAtLargestSize = 0;

        int algorithmsAtLargestSize = 0;

        for (int s = 0; s < sizes.length; s++) {

            int size = sizes[s];

            System.out.println(
                    "\nBenchmarking input size: " + size
                            + " | Condition: " + condition
            );

            int[] original;

            if (customValues != null) {

                // Use the exact values entered by the user.
                original = Arrays.copyOf(
                        customValues,
                        customValues.length
                );

            } else {

                original = generateArray(
                        size,
                        condition
                );
            }

            for (int a = 0; a < ALGORITHMS.length; a++) {

                String algorithm = ALGORITHMS[a];

                // Skip O(n²) algorithms for large datasets.
                if (size > MAX_QUADRATIC_SIZE && a < 3) {

                    rows.add(new String[]{
                            algorithm,
                            String.valueOf(size),
                            condition,
                            "Skipped",
                            "N/A",
                            "N/A",
                            COMPLEXITIES[a]
                    });

                    continue;
                }

                long totalExecutionTime = 0;

                long totalComparisons = 0;

                long totalMoves = 0;

                // Warm-up execution.
                int[] warmup = Arrays.copyOf(
                        original,
                        original.length
                );

                executeAlgorithm(
                        warmup,
                        algorithm,
                        new AlgorithmMetrics()
                );

                // Actual benchmark repetitions.
                for (int run = 0; run < repetitions; run++) {

                    int[] data = Arrays.copyOf(
                            original,
                            original.length
                    );

                    AlgorithmMetrics metrics =
                            new AlgorithmMetrics();

                    executeAlgorithm(
                            data,
                            algorithm,
                            metrics
                    );

                    if (!isSorted(data)) {

                        throw new IllegalStateException(
                                algorithm
                                        + " produced an unsorted array."
                        );
                    }

                    totalExecutionTime +=
                            metrics.getExecutionTime();

                    totalComparisons +=
                            metrics.getComparisons();

                    totalMoves +=
                            metrics.getSwapsOrMoves();
                }

                double averageTimeNs =
                        (double) totalExecutionTime / repetitions;

                double averageTimeMs =
                        averageTimeNs / 1_000_000.0;

                double averageComparisons =
                        (double) totalComparisons / repetitions;

                double averageMoves =
                        (double) totalMoves / repetitions;

                allTimes[a][s] = averageTimeMs;

                rows.add(new String[]{
                        algorithm,
                        String.valueOf(size),
                        condition,
                        String.format(
                                Locale.US,
                                "%.4f",
                                averageTimeMs
                        ),
                        String.format(
                                Locale.US,
                                "%.0f",
                                averageComparisons
                        ),
                        String.format(
                                Locale.US,
                                "%.0f",
                                averageMoves
                        ),
                        COMPLEXITIES[a]
                });

                // Compare algorithms at the largest size
                // where all six algorithms can run.
                if (size <= MAX_QUADRATIC_SIZE) {

                    if (size > largestCommonSize) {

                        largestCommonSize = size;

                        totalTimeAtLargestSize = 0;

                        algorithmsAtLargestSize = 0;

                        fastestAtLargestSize = Double.MAX_VALUE;

                        fastestAlgorithm = "Unavailable";
                    }

                    if (size == largestCommonSize) {

                        totalTimeAtLargestSize += averageTimeMs;

                        algorithmsAtLargestSize++;

                        if (averageTimeMs < fastestAtLargestSize) {

                            fastestAtLargestSize = averageTimeMs;

                            fastestAlgorithm = algorithm;
                        }
                    }
                }

                System.out.println(
                        algorithm
                                + " | Size: " + size
                                + " | Time: "
                                + String.format(
                                        Locale.US,
                                        "%.4f",
                                        averageTimeMs
                                )
                                + " ms | Comparisons: "
                                + String.format(
                                        Locale.US,
                                        "%.0f",
                                        averageComparisons
                                )
                                + " | Moves: "
                                + String.format(
                                        Locale.US,
                                        "%.0f",
                                        averageMoves
                                )
                );
            }
        }

        // Separate algorithms into chart groups.
        String[] quadraticNames = {
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort"
        };

        double[][] quadraticTimes = {
                allTimes[0],
                allTimes[1],
                allTimes[2]
        };

        String[] nLogNNames = {
                "Merge Sort",
                "Quick Sort (First Pivot)",
                "Quick Sort (Random Pivot)"
        };

        double[][] nLogNTimes = {
                allTimes[3],
                allTimes[4],
                allTimes[5]
        };

        String averageTime;

        if (algorithmsAtLargestSize > 0) {

            averageTime = String.format(
                    Locale.US,
                    "Average at n = %d: %.4f ms",
                    largestCommonSize,
                    totalTimeAtLargestSize
                            / algorithmsAtLargestSize
            );

        } else {

            averageTime = "Average execution time: unavailable";
        }

        String[][] table = rows.toArray(
                new String[0][]
        );

        return new BenchmarkResult(
                table,
                sizes,
                quadraticNames,
                quadraticTimes,
                nLogNNames,
                nLogNTimes,
                averageTime,
                fastestAlgorithm,
                "Benchmark completed successfully for "
                        + condition + " input."
        );
    }

    // =====================================================
    // GENERATE INPUT ARRAYS
    // =====================================================

    private int[] generateArray(
            int size,
            String condition) {

        int[] array = new int[size];

        Random random = new Random(42L + size);

        for (int i = 0; i < size; i++) {

            if ("Already Sorted".equals(condition)
                    || "Sorted".equals(condition)) {

                array[i] = i;

            } else if ("Reverse Sorted".equals(condition)
                    || "Reverse".equals(condition)) {

                array[i] = size - i;

            } else {

                array[i] = random.nextInt(100000);
            }
        }

        return array;
    }

    // =====================================================
    // EXECUTE SORTING ALGORITHMS
    // =====================================================

    private void executeAlgorithm(
            int[] data,
            String algorithm,
            AlgorithmMetrics metrics) {

        switch (algorithm) {

            case "Bubble Sort":

                new BubbleSort().sort(data, metrics);

                break;

            case "Selection Sort":

                new SelectionSort().sort(data, metrics);

                break;

            case "Insertion Sort":

                new InsertionSort().sort(data, metrics);

                break;

            case "Merge Sort":

                new MergeSort().sort(data, metrics);

                break;

            case "Quick Sort (First Pivot)":

                new QuickSort().sort(data, metrics);

                break;

            case "Quick Sort (Random Pivot)":

                new QuickSort().sortRandomizedPivot(
                        data,
                        metrics
                );

                break;

            default:

                throw new IllegalArgumentException(
                        "Unknown algorithm: " + algorithm
                );
        }
    }

    // =====================================================
    // CHECK SORTED ARRAY
    // =====================================================

    private boolean isSorted(int[] array) {

        for (int i = 1; i < array.length; i++) {

            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        return true;
    }

    // =====================================================
    // BENCHMARK RESULT
    // =====================================================

    private static class BenchmarkResult {

        final String[][] table;

        final int[] inputSizes;

        final String[] quadraticNames;

        final double[][] quadraticTimes;

        final String[] nLogNNames;

        final double[][] nLogNTimes;

        final String averageTime;

        final String fastestAlgorithm;

        final String message;

        BenchmarkResult(
                String[][] table,
                int[] inputSizes,
                String[] quadraticNames,
                double[][] quadraticTimes,
                String[] nLogNNames,
                double[][] nLogNTimes,
                String averageTime,
                String fastestAlgorithm,
                String message) {

            this.table = table;

            this.inputSizes = inputSizes;

            this.quadraticNames = quadraticNames;

            this.quadraticTimes = quadraticTimes;

            this.nLogNNames = nLogNNames;

            this.nLogNTimes = nLogNTimes;

            this.averageTime = averageTime;

            this.fastestAlgorithm = fastestAlgorithm;

            this.message = message;
        }
    }
}
