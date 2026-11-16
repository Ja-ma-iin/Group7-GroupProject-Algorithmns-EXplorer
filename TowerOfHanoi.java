
package algorithms;

import java.util.ArrayList;
import java.util.List;
import utils.AlgorithmMetrics;

public class TowerOfHanoi {

    // Represents one disk movement.
    public static class Move {
        private final int disk;
        private final char fromRod;
        private final char toRod;

        public Move(int disk, char fromRod, char toRod) {
            this.disk = disk;
            this.fromRod = fromRod;
            this.toRod = toRod;
        }

        public int getDisk() {
            return disk;
        }

        public char getFromRod() {
            return fromRod;
        }

        public char getToRod() {
            return toRod;
        }

        @Override
        public String toString() {
            return "Move disk " + disk + " from rod "
                    + fromRod + " to rod " + toRod;
        }
    }

    // Default solution using rods A, B and C.
    // Preserves compatibility with existing code.
    public static List<Move> solve(
            int numberOfDisks,
            AlgorithmMetrics metrics) {

        return solve(numberOfDisks, 'A', 'C', 'B', metrics);
    }

    // Generates a solution using user-selected rods.
    // Time Complexity: O(2^n)
    // Space Complexity: O(2^n) for the move list.
    public static List<Move> solve(
            int numberOfDisks,
            char source,
            char destination,
            char auxiliary,
            AlgorithmMetrics metrics) {

        if (numberOfDisks < 1 || numberOfDisks > 15) {
            throw new IllegalArgumentException(
                "Enter a number of disks between 1 and 15."
            );
        }

        validateRods(source, destination, auxiliary);

        if (metrics == null) {
            throw new IllegalArgumentException(
                "AlgorithmMetrics cannot be null."
            );
        }

        List<Move> moves = new ArrayList<>();

        solveRecursive(
            numberOfDisks,
            source,
            destination,
            auxiliary,
            moves,
            metrics
        );

        return moves;
    }

    // Ensures that all three rods are valid and distinct.
    private static void validateRods(
            char source,
            char destination,
            char auxiliary) {

        if (!isValidRod(source)
                || !isValidRod(destination)
                || !isValidRod(auxiliary)) {

            throw new IllegalArgumentException(
                "Rods must be A, B or C."
            );
        }

        if (source == destination
                || source == auxiliary
                || destination == auxiliary) {

            throw new IllegalArgumentException(
                "Source, destination and auxiliary rods must be different."
            );
        }
    }

    private static boolean isValidRod(char rod) {
        return rod == 'A' || rod == 'B' || rod == 'C';
    }

    // Recursive Tower of Hanoi implementation.
    private static void solveRecursive(
            int disks,
            char source,
            char destination,
            char auxiliary,
            List<Move> moves,
            AlgorithmMetrics metrics) {

        metrics.addRecursiveCall();

        // Base case: move one disk directly.
        if (disks == 1) {
            moves.add(new Move(disks, source, destination));
            metrics.addSwapOrMove();
            return;
        }

        // Move the smaller disks to the auxiliary rod.
        solveRecursive(
            disks - 1,
            source,
            auxiliary,
            destination,
            moves,
            metrics
        );

        // Move the largest remaining disk to the destination.
        moves.add(new Move(disks, source, destination));
        metrics.addSwapOrMove();

        // Move the smaller disks onto the largest disk.
        solveRecursive(
            disks - 1,
            auxiliary,
            destination,
            source,
            moves,
            metrics
        );
    }
}
