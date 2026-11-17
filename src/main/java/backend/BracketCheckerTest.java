
package backend;

import datastructures.BracketChecker;

public class BracketCheckerTest {

    public static void main(String[] args) {

        BracketChecker checker = new BracketChecker();

        System.out.println("TEST 1: Balanced expression");
        checker.checkExpression("{[()]}");

        System.out.println("\nTEST 2: Unbalanced expression");
        checker.checkExpression("{[(])}");

        System.out.println("\nTEST 3: Missing closing bracket");
        checker.checkExpression("{[(");

        System.out.println("\nTEST 4: Expression with normal characters");
        checker.checkExpression("a+(b*c)-{d/e}");
    }
}
