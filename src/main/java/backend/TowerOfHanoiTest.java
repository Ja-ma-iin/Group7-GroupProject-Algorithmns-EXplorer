
package backend;

import algorithms.TowerOfHanoi;
import algorithms.TowerOfHanoi.Move;
import java.util.List;
import utils.AlgorithmMetrics;

public class TowerOfHanoiTest {

    public static void main(String[] args) {

        int numberOfDisks = 3;

        AlgorithmMetrics metrics = new AlgorithmMetrics();

        System.out.println("================================");
        System.out.println("      TOWER OF HANOI TEST");
        System.out.println("================================");

        metrics.reset();
        metrics.startTimer();

        List<Move> moves =
                TowerOfHanoi.solve(numberOfDisks, metrics);

        metrics.stopTimer();

        System.out.println("\nNumber of disks: " + numberOfDisks);
        System.out.println("\nMOVEMENT STEPS:");

        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i));
        }

        System.out.println("\nTotal moves: " + moves.size());

        System.out.println("\nPERFORMANCE METRICS:");
        metrics.displayMetrics();

        System.out.println("\n================================");
        System.out.println("EXPECTED NUMBER OF MOVES: "
                + ((1 << numberOfDisks) - 1));
        System.out.println("================================");
    }
}
