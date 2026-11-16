
package datastructures;

public class BracketChecker {

    public boolean isOpeningBracket(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    public boolean isClosingBracket(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    public boolean matches(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '[' && closing == ']') ||
               (opening == '{' && closing == '}');
    }

    public boolean checkExpression(String expression) {

        ArrayStack stack = new ArrayStack(expression.length());

        System.out.println("\nChecking expression: " + expression);
        System.out.println("--------------------------------");

        for (int i = 0; i < expression.length(); i++) {

            char current = expression.charAt(i);

            if (isOpeningBracket(current)) {

                stack.push((int) current);

                System.out.println(
                    current + " -> Opening bracket pushed. Stack: "
                    + stack.getContentsAsBrackets()
                );

            } else if (isClosingBracket(current)) {

                if (stack.isEmpty()) {
                    System.out.println(
                        current + " -> ERROR: No matching opening bracket."
                    );
                    return false;
                }

                char opening = (char) stack.pop();

                if (!matches(opening, current)) {
                    System.out.println(
                        current + " -> ERROR: Mismatched brackets. "
                        + "Expected closing bracket for " + opening
                    );
                    return false;
                }

                System.out.println(
                    current + " -> Matching bracket found. Stack: "
                    + stack.getContentsAsBrackets()
                );

            } else {
                System.out.println(
                    current + " -> Not a bracket. Stack: "
                    + stack.getContentsAsBrackets()
                );
            }
        }

        if (stack.isEmpty()) {
            System.out.println("--------------------------------");
            System.out.println("RESULT: Expression is BALANCED.");
            return true;
        }

        System.out.println("--------------------------------");
        System.out.println("RESULT: Expression is NOT BALANCED.");
        System.out.println("Unmatched opening brackets remain: "
                + stack.getContentsAsBrackets());

        return false;
    }
}
