
package backend;

import algorithms.Factorial;
import algorithms.Fibonacci;
import algorithms.TowerOfHanoi;
import algorithms.TowerOfHanoi.Move;
import frontend.RecursionPanel;
import frontend.RecursionPanel.FactorialMethod;
import frontend.RecursionPanel.FibonacciMethod;
import utils.AlgorithmMetrics;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

public class RecursionController implements RecursionPanel.Listener {

    private final RecursionPanel panel;
    private final AlgorithmMetrics metrics = new AlgorithmMetrics();

    private Timer hanoiTimer;
    private List<Move> hanoiMoves = new ArrayList<>();

    private List<List<Integer>> pegs = new ArrayList<>();

    private int currentMove;
    private int totalMoves;
    private int numberOfDisks;

    private char sourceRod = 'A';
    private char destinationRod = 'C';
    private char auxiliaryRod = 'B';

    public RecursionController(RecursionPanel panel) {
        this.panel = panel;
        initialisePegs();
    }

    // =====================================================
    // FIBONACCI
    // =====================================================

    @Override
    public void calculateFibonacci(int n, FibonacciMethod method) {

        if (n < 0 || n > 92) {
            panel.showFibonacciResult(
                    "Invalid input",
                    "Enter a number between 0 and 92.",
                    "-"
            );
            return;
        }

        if (method == FibonacciMethod.NAIVE_RECURSION && n > 30) {
            panel.showFibonacciResult(
                    "Input too large",
                    "Naive recursion is limited to n <= 30.",
                    "-"
            );
            return;
        }

        metrics.reset();
        metrics.startTimer();

        long result;

        try {
            switch (method) {
                case NAIVE_RECURSION:
                    result = Fibonacci.naiveFibonacci(n, metrics);
                    break;

                case MEMOIZED_RECURSION:
                    result = Fibonacci.memoizedFibonacci(n, metrics);
                    break;

                case BOTTOM_UP_DP:
                    result = Fibonacci.bottomUpFibonacci(n, metrics);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown Fibonacci method."
                    );
            }

            metrics.stopTimer();

            String operations =
                    "Recursive calls: " + metrics.getRecursiveCalls()
                    + "\nIterations: " + metrics.getIterations();

            String time = String.format(
                    "%.6f ms",
                    metrics.getExecutionTimeMilliseconds()
            );

            panel.showFibonacciResult(
                    String.valueOf(result),
                    operations,
                    time
            );

        } catch (Exception ex) {
            metrics.stopTimer();
            panel.showFibonacciResult(
                    "Calculation failed",
                    ex.getMessage(),
                    "-"
            );
        }
    }

    // =====================================================
    // FACTORIAL
    // =====================================================

    @Override
    public void calculateFactorial(int n, FactorialMethod method) {

        if (n < 0 || n > 20) {
            panel.showFactorialResult(
                    "Invalid input",
                    "Enter a number between 0 and 20.",
                    "-"
            );
            return;
        }

        metrics.reset();
        metrics.startTimer();

        try {
            long result;

            if (method == FactorialMethod.RECURSIVE) {
                result = Factorial.recursiveFactorial(n, metrics);
            } else {
                result = Factorial.iterativeFactorial(n, metrics);
            }

            metrics.stopTimer();

            String operations =
                    "Recursive calls: " + metrics.getRecursiveCalls()
                    + "\nIterations: " + metrics.getIterations();

            String time = String.format(
                    "%.6f ms",
                    metrics.getExecutionTimeMilliseconds()
            );

            panel.showFactorialResult(
                    String.valueOf(result),
                    operations,
                    time
            );

        } catch (Exception ex) {
            metrics.stopTimer();
            panel.showFactorialResult(
                    "Calculation failed",
                    ex.getMessage(),
                    "-"
            );
        }
    }

    // =====================================================
    // TOWER OF HANOI
    // =====================================================

    @Override
    public void startHanoi(
            int disks,
            String source,
            String destination,
            String auxiliary,
            int delayMillis
    ) {
        if (disks < 1 || disks > 8) {
            return;
        }

        if (source.equals(destination)
                || source.equals(auxiliary)
                || destination.equals(auxiliary)) {
            return;
        }

        stopTimer();

        numberOfDisks = disks;
        sourceRod = source.charAt(0);
        destinationRod = destination.charAt(0);
        auxiliaryRod = auxiliary.charAt(0);

        metrics.reset();
        metrics.startTimer();

        hanoiMoves = new ArrayList<>();

        generateMoves(
                disks,
                sourceRod,
                destinationRod,
                auxiliaryRod
        );

        metrics.stopTimer();

        totalMoves = hanoiMoves.size();
        currentMove = 0;

        initialisePegs();
        panel.updateHanoiState(pegs, currentMove, totalMoves);

        if (hanoiTimer == null) {
            hanoiTimer = new Timer(delayMillis, e -> performNextMove());
        } else {
            hanoiTimer.setDelay(delayMillis);
        }

        hanoiTimer.start();
    }

    private void generateMoves(
            int disks,
            char source,
            char destination,
            char auxiliary
    ) {
        if (disks == 1) {
            hanoiMoves.add(new Move(1, source, destination));
            return;
        }

        generateMoves(
                disks - 1,
                source,
                auxiliary,
                destination
        );

        hanoiMoves.add(
                new Move(disks, source, destination)
        );

        generateMoves(
                disks - 1,
                auxiliary,
                destination,
                source
        );
    }

    private void performNextMove() {

        if (currentMove >= totalMoves) {
            stopTimer();
            return;
        }

        Move move = hanoiMoves.get(currentMove);

        List<Integer> fromPeg = getPeg(move.getFromRod());
        List<Integer> toPeg = getPeg(move.getToRod());

        if (fromPeg != null && toPeg != null && !fromPeg.isEmpty()) {
            int disk = fromPeg.remove(fromPeg.size() - 1);
            toPeg.add(disk);
        }

        currentMove++;

        panel.updateHanoiState(
                copyPegs(),
                currentMove,
                totalMoves
        );

        if (currentMove >= totalMoves) {
            stopTimer();
        }
    }

    @Override
    public void pauseHanoi() {
        if (hanoiTimer != null) {
            hanoiTimer.stop();
        }
    }

    @Override
    public void stepHanoi() {
        if (hanoiTimer != null && hanoiTimer.isRunning()) {
            hanoiTimer.stop();
        }

        performNextMove();
    }

    @Override
    public void resetHanoi(int disks, String source) {

        stopTimer();

        if (disks < 1 || disks > 8) {
            return;
        }

        numberOfDisks = disks;
        sourceRod = source.charAt(0);

        currentMove = 0;
        totalMoves = (int) Math.pow(2, disks) - 1;

        hanoiMoves.clear();
        initialisePegs();

        panel.updateHanoiState(
                copyPegs(),
                currentMove,
                totalMoves
        );
    }

    private void initialisePegs() {

        pegs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            pegs.add(new ArrayList<>());
        }

        if (numberOfDisks > 0) {
            List<Integer> source = getPeg(sourceRod);

            if (source != null) {
                for (int disk = numberOfDisks; disk >= 1; disk--) {
                    source.add(disk);
                }
            }
        }
    }

    private List<Integer> getPeg(char rod) {

        if (rod == 'A') {
            return pegs.get(0);
        }

        if (rod == 'B') {
            return pegs.get(1);
        }

        if (rod == 'C') {
            return pegs.get(2);
        }

        return null;
    }

    private List<List<Integer>> copyPegs() {

        List<List<Integer>> copy = new ArrayList<>();

        for (List<Integer> peg : pegs) {
            copy.add(new ArrayList<>(peg));
        }

        return copy;
    }

    private void stopTimer() {
        if (hanoiTimer != null) {
            hanoiTimer.stop();
        }
    }
}
