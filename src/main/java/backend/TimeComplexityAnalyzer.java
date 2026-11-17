package backend;

import sorting.*;
import utils.AlgorithmMetrics;

import java.util.Arrays;
import java.util.Random;

public class TimeComplexityAnalyzer {

    private static final int REPEATS = 3;
    private static final int WARMUP_RUNS = 2;
    private static final int[] SIZES = {
        100, 500, 1000, 2000, 5000, 10000
    };

    private static final String[] DATA_TYPES = {
        "Random", "Sorted", "Reverse"
    };

    public static void main(String[] args) {

        StringBuilder csvContent = new StringBuilder();

        // CSV column headings
        csvContent.append(
            "Algorithm,Input Size,Dataset Type," +
            "Average Execution Time (ns)," +
            "Average Comparisons," +
            "Average Swaps or Moves\n"
        );

        System.out.println("==============================================");
        System.out.println("       MODULE D: TIME COMPLEXITY ANALYSIS");
        System.out.println("==============================================");

        for (String dataType : DATA_TYPES) {

            System.out.println("\n\nDATA TYPE: " + dataType);
            System.out.println("==============================================");

            for (int size : SIZES) {

                int[] original = generateArray(size, dataType);

                System.out.println("\nInput Size: " + size);

                runBenchmark("Bubble Sort", original, 1,
                        dataType, csvContent);

                runBenchmark("Selection Sort", original, 2,
                        dataType, csvContent);

                runBenchmark("Insertion Sort", original, 3,
                        dataType, csvContent);

                runBenchmark("Merge Sort", original, 4,
                        dataType, csvContent);

                runBenchmark("Quick Sort (First Pivot)", original, 5,
                        dataType, csvContent);

                runBenchmark("Quick Sort (Random Pivot)", original, 6,
                        dataType, csvContent);
            }
        }

        // Larger datasets for O(n log n) algorithms
        System.out.println("\n\nLARGE DATASET ANALYSIS");
        System.out.println("==============================================");

        for (int size : new int[]{50000, 100000}) {

            int[] original = generateArray(size, "Random");

            System.out.println("\nInput Size: " + size);

            runBenchmark("Merge Sort", original, 4,
                    "Random (Large)", csvContent);

            runBenchmark("Quick Sort (Random Pivot)", original, 6,
                    "Random (Large)", csvContent);
        }

        // Export all benchmark results
        BenchmarkCSVExporter.exportResults(
                "benchmark_results.csv",
                csvContent.toString()
        );

        System.out.println("\n==============================================");
        System.out.println("TIME COMPLEXITY ANALYSIS COMPLETED");
        System.out.println("==============================================");
    }

    // Generates random, sorted or reverse-sorted arrays.
    private static int[] generateArray(int size, String type) {

        int[] array = new int[size];

        Random random = new Random(42);

        if (type.equals("Sorted")) {

            for (int i = 0; i < size; i++) {
                array[i] = i;
            }

        } else if (type.equals("Reverse")) {

            for (int i = 0; i < size; i++) {
                array[i] = size - i;
            }

        } else {

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(100000);
            }
        }

        return array;
    }

    private static void executeAlgorithm(
            int[] data,
            int algorithm,
            AlgorithmMetrics metrics) {

        switch (algorithm) {

            case 1:
                new BubbleSort().sort(data, metrics);
                break;

            case 2:
                new SelectionSort().sort(data, metrics);
                break;

            case 3:
                new InsertionSort().sort(data, metrics);
                break;

            case 4:
                new MergeSort().sort(data, metrics);
                break;

            case 5:
                new QuickSort().sort(data, metrics);
                break;

            case 6:
                new QuickSort().sortRandomizedPivot(data, metrics);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown sorting algorithm.");
        }
    }

    // Runs one algorithm three times and calculates average metrics.
    private static void runBenchmark(
            String name,
            int[] original,
            int algorithm,
            String dataType,
            StringBuilder csvContent) {

        long totalTime = 0;
        long totalComparisons = 0;
        long totalMoves = 0;

        // ==========================================
        // JVM WARM-UP RUNS
        // ==========================================

        for (int run = 0; run < WARMUP_RUNS; run++) {

            int[] warmupData = Arrays.copyOf(
                    original, original.length);

            AlgorithmMetrics warmupMetrics =
                    new AlgorithmMetrics();

            executeAlgorithm(
                    warmupData,
                    algorithm,
                    warmupMetrics
            );

            if (!isSorted(warmupData)) {
                throw new IllegalStateException(
                        name + " failed during warm-up.");
            }
        }

        // ==========================================
        // ACTUAL BENCHMARK RUNS
        // ==========================================

        for (int run = 0; run < REPEATS; run++) {

            int[] data = Arrays.copyOf(
                    original, original.length);

            AlgorithmMetrics metrics =
                    new AlgorithmMetrics();

            executeAlgorithm(data, algorithm, metrics);

            totalTime += metrics.getExecutionTime();
            totalComparisons += metrics.getComparisons();
            totalMoves += metrics.getSwapsOrMoves();

            if (!isSorted(data)) {
                throw new IllegalStateException(
                        name + " failed to sort the array.");
            }
        }

        // Calculate averages.
        double averageTime = (double) totalTime / REPEATS;

        double averageComparisons =
                (double) totalComparisons / REPEATS;

        double averageMoves =
                (double) totalMoves / REPEATS;

        // Display results.
        System.out.println("----------------------------------------------");
        System.out.println("Algorithm: " + name);
        System.out.println("Average Time: " + averageTime + " ns");
        System.out.println("Average Time: "
                + (averageTime / 1_000_000.0) + " ms");
        System.out.println("Average Comparisons: " + averageComparisons);
        System.out.println("Average Swaps/Moves: " + averageMoves);

        // Export results to CSV.
        csvContent.append(escapeCSV(name)).append(",");
        csvContent.append(original.length).append(",");
        csvContent.append(escapeCSV(dataType)).append(",");
        csvContent.append(averageTime).append(",");
        csvContent.append(averageComparisons).append(",");
        csvContent.append(averageMoves).append("\n");
    }

    // Escapes CSV values safely.
    private static String escapeCSV(String value) {

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Verifies that the array is in ascending order.
    private static boolean isSorted(int[] array) {

        for (int i = 1; i < array.length; i++) {

            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        return true;
    }
}