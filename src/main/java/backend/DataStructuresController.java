
package backend;

import datastructures.ArrayStack;
import datastructures.LinearQueue;
import datastructures.CircularQueue;
import datastructures.SinglyLinkedList;
import datastructures.DoublyLinkedList;

import frontend.DataStructuresPanel;

import java.util.ArrayList;
import java.util.List;

public class DataStructuresController
        implements DataStructuresPanel.Listener {

    private final DataStructuresPanel panel;

    private ArrayStack stack;
    private LinearQueue linearQueue;
    private CircularQueue circularQueue;

    private SinglyLinkedList singlyList;
    private DoublyLinkedList doublyList;

    private int stackCapacity = -1;
    private int linearCapacity = -1;
    private int circularCapacity = -1;

    // Visualisation copies of the data structure contents.
    private final List<Integer> stackValues = new ArrayList<>();
    private final List<Integer> linearValues = new ArrayList<>();
    private final List<Integer> circularValues = new ArrayList<>();
    private final List<Integer> singlyValues = new ArrayList<>();
    private final List<Integer> doublyValues = new ArrayList<>();

    public DataStructuresController(DataStructuresPanel panel) {
        this.panel = panel;
    }

    // =====================================================
    // MAIN OPERATION HANDLER
    // =====================================================

    @Override
    public void performOperation(
            String structure,
            String operation,
            String value,
            String target,
            int capacity
    ) {

        try {
            int number = value.isEmpty()
                    ? 0 : Integer.parseInt(value);

            int targetNumber = target.isEmpty()
                    ? 0 : Integer.parseInt(target);

            switch (structure) {

                case "STACK":
                    handleStack(operation, number, capacity);
                    break;

                case "LINEAR_QUEUE":
                    handleLinearQueue(operation, number, capacity);
                    break;

                case "CIRCULAR_QUEUE":
                    handleCircularQueue(operation, number, capacity);
                    break;

                case "SINGLY_LINKED_LIST":
                    handleSinglyList(operation, number, targetNumber);
                    break;

                case "DOUBLY_LINKED_LIST":
                    handleDoublyList(operation, number, targetNumber);
                    break;

                default:
                    showResult(
                            structure,
                            "",
                            "Unknown data structure.",
                            ""
                    );
            }

        } catch (NumberFormatException ex) {

            showResult(
                    structure,
                    getVisualisation(structure),
                    "Please enter valid whole numbers.",
                    ""
            );

        } catch (IllegalStateException | IllegalArgumentException ex) {

            showResult(
                    structure,
                    getVisualisation(structure),
                    ex.getMessage(),
                    ""
            );

        } catch (Exception ex) {

            showResult(
                    structure,
                    getVisualisation(structure),
                    "Operation failed: " + ex.getMessage(),
                    ""
            );
        }
    }

    // =====================================================
    // STACK OPERATIONS
    // =====================================================

    private void handleStack(
            String operation,
            int value,
            int capacity
    ) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Stack capacity must be greater than zero."
            );
        }

        if (stack == null || stackCapacity != capacity) {

            stack = new ArrayStack(capacity);
            stackCapacity = capacity;
            stackValues.clear();
        }

        String message;

        switch (operation) {

            case "Push":

                stack.push(value);
                stackValues.add(value);

                message = "Pushed " + value + " onto the stack.";
                break;

            case "Pop":

                int popped = stack.pop();

                stackValues.remove(stackValues.size() - 1);

                message = "Popped " + popped + " from the stack.";
                break;

            case "Peek":

                message = "Top element: " + stack.peek();
                break;

            case "Size":

                message = "Stack size: " + stack.size();
                break;

            case "Is Empty":

                message = "Is empty: " + stack.isEmpty();
                break;

            case "Is Full":

                message = "Is full: " + stack.isFull();
                break;

            case "Display":

                message = "Displaying all stack elements.";
                break;

            default:

                message = "Unknown stack operation.";
        }

        StringBuilder visual = new StringBuilder();

        visual.append("ARRAY-BASED STACK\n");
        visual.append("=================\n\n");

        if (stackValues.isEmpty()) {

            visual.append("[ EMPTY ]\n");

        } else {

            visual.append("TOP\n");

            for (int i = stackValues.size() - 1; i >= 0; i--) {

                visual.append("  | ")
                        .append(stackValues.get(i))
                        .append(" |\n");
            }

            visual.append("BOTTOM\n");
        }

        visual.append("\nSize: ").append(stack.size());
        visual.append("\nCapacity: ").append(capacity);
        visual.append("\nIs Full: ").append(stack.isFull());

        showResult(
                "STACK",
                visual.toString(),
                message,
                ""
        );
    }

    // =====================================================
    // LINEAR QUEUE OPERATIONS
    // =====================================================

    private void handleLinearQueue(
            String operation,
            int value,
            int capacity
    ) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Queue capacity must be greater than zero."
            );
        }

        if (linearQueue == null || linearCapacity != capacity) {

            linearQueue = new LinearQueue(capacity);
            linearCapacity = capacity;
            linearValues.clear();
        }

        String message;

        switch (operation) {

            case "Enqueue":

                linearQueue.enqueue(value);
                linearValues.add(value);

                message = "Enqueued " + value + ".";
                break;

            case "Dequeue":

                int removed = linearQueue.dequeue();

                linearValues.remove(0);

                message = "Dequeued " + removed + ".";
                break;

            case "Front":

                message = "Front element: " + linearQueue.peek();
                break;

            case "Size":

                message = "Queue size: " + linearQueue.size();
                break;

            case "Is Empty":

                message = "Is empty: " + linearQueue.isEmpty();
                break;

            case "Is Full":

                message = "Is full: " + linearQueue.isFull();
                break;

            case "Display":

                message = "Displaying linear queue.";
                break;

            default:

                message = "Unknown queue operation.";
        }

        StringBuilder visual = new StringBuilder();

        visual.append("LINEAR QUEUE\n");
        visual.append("============\n\n");

        if (linearValues.isEmpty()) {

            visual.append("[ EMPTY ]\n");

        } else {

            visual.append("FRONT -> ");

            for (int item : linearValues) {

                visual.append("[ ")
                        .append(item)
                        .append(" ] -> ");
            }

            visual.append("REAR\n");
        }

        visual.append("\nSize: ").append(linearQueue.size());
        visual.append("\nCapacity: ").append(capacity);
        visual.append("\nIs Full: ").append(linearQueue.isFull());

        if (linearQueue.isFull()) {

            visual.append(
                    "\n\nThe rear has reached the final array position."
            );
        }

        if (linearQueue.isEmpty() && linearValues.isEmpty()) {
            visual.append("\nQueue is currently empty.");
        }

        showResult(
                "LINEAR_QUEUE",
                visual.toString(),
                message,
                ""
        );
    }

    // =====================================================
    // CIRCULAR QUEUE OPERATIONS
    // =====================================================

    private void handleCircularQueue(
            String operation,
            int value,
            int capacity
    ) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Queue capacity must be greater than zero."
            );
        }

        if (circularQueue == null || circularCapacity != capacity) {

            circularQueue = new CircularQueue(capacity);
            circularCapacity = capacity;
            circularValues.clear();
        }

        String message;

        switch (operation) {

            case "Enqueue":

                circularQueue.enqueue(value);
                circularValues.add(value);

                message = "Enqueued " + value + ".";
                break;

            case "Dequeue":

                int removed = circularQueue.dequeue();

                circularValues.remove(0);

                message = "Dequeued " + removed + ".";
                break;

            case "Front":

                message = "Front element: " + circularQueue.peek();
                break;

            case "Size":

                message = "Queue size: " + circularQueue.size();
                break;

            case "Is Empty":

                message = "Is empty: " + circularQueue.isEmpty();
                break;

            case "Is Full":

                message = "Is full: " + circularQueue.isFull();
                break;

            case "Display":

                message = "Displaying circular queue.";
                break;

            default:

                message = "Unknown queue operation.";
        }

        StringBuilder visual = new StringBuilder();

        visual.append("CIRCULAR QUEUE\n");
        visual.append("==============\n\n");

        if (circularValues.isEmpty()) {

            visual.append("[ EMPTY ]\n");

        } else {

            visual.append("FRONT -> ");

            for (int item : circularValues) {

                visual.append("[ ")
                        .append(item)
                        .append(" ] ");
            }

            visual.append("<- REAR\n");
        }

        visual.append("\nFront Index: ")
                .append(circularQueue.getFrontIndex());

        visual.append("\nRear Index: ")
                .append(circularQueue.getRearIndex());

        visual.append("\nSize: ")
                .append(circularQueue.size());

        visual.append("\nCapacity: ")
                .append(capacity);

        visual.append("\nIs Full: ")
                .append(circularQueue.isFull());

        visual.append(
                "\n\nThe circular queue reuses available array positions."
        );

        showResult(
                "CIRCULAR_QUEUE",
                visual.toString(),
                message,
                ""
        );
    }

    // =====================================================
    // SINGLY LINKED LIST OPERATIONS
    // =====================================================

    private void handleSinglyList(
            String operation,
            int value,
            int target
    ) {

        if (singlyList == null) {
            singlyList = new SinglyLinkedList();
        }

        String message;
        boolean success;

        switch (operation) {

            case "Insert at Beginning":

                singlyList.insertAtBeginning(value);
                singlyValues.add(0, value);

                message = "Inserted " + value + " at the beginning.";
                break;

            case "Insert at End":

                singlyList.insertAtEnd(value);
                singlyValues.add(value);

                message = "Inserted " + value + " at the end.";
                break;

            case "Insert Before":

                success = singlyList.insertBefore(target, value);

                if (success) {

                    int index = singlyValues.indexOf(target);
                    singlyValues.add(index, value);

                    message = "Inserted " + value + " before " + target + ".";

                } else {

                    message = "Target element " + target + " was not found.";
                }

                break;

            case "Insert After":

                success = singlyList.insertAfter(target, value);

                if (success) {

                    int index = singlyValues.indexOf(target);
                    singlyValues.add(index + 1, value);

                    message = "Inserted " + value + " after " + target + ".";

                } else {

                    message = "Target element " + target + " was not found.";
                }

                break;

            case "Delete at Beginning":

                success = singlyList.deleteAtBeginning();

                if (success) {

                    singlyValues.remove(0);
                    message = "Deleted the first element.";

                } else {

                    message = "List is empty.";
                }

                break;

            case "Delete at End":

                success = singlyList.deleteAtEnd();

                if (success) {

                    singlyValues.remove(singlyValues.size() - 1);
                    message = "Deleted the last element.";

                } else {

                    message = "List is empty.";
                }

                break;

            case "Delete Before":

                success = singlyList.deleteBefore(target);

                if (success) {

                    int index = singlyValues.indexOf(target);
                    singlyValues.remove(index - 1);

                    message = "Deleted the element before " + target + ".";

                } else {

                    message = "Cannot delete before " + target + ".";
                }

                break;

            case "Delete After":

                success = singlyList.deleteAfter(target);

                if (success) {

                    int index = singlyValues.indexOf(target);
                    singlyValues.remove(index + 1);

                    message = "Deleted the element after " + target + ".";

                } else {

                    message = "Cannot delete after " + target + ".";
                }

                break;

            case "Delete Element":

                success = singlyList.deleteValue(value);

                if (success) {

                    singlyValues.remove(Integer.valueOf(value));
                    message = "Deleted element " + value + ".";

                } else {

                    message = "Element " + value + " was not found.";
                }

                break;

            case "Search":

                message = singlyList.search(value)
                        ? "Element " + value + " was found."
                        : "Element " + value + " was not found.";

                break;

            case "Size":

                message = "List size: " + singlyList.size();
                break;

            case "Display Forward":

                message = "Displaying list from head to tail.";
                break;

            default:

                message = "Unknown linked-list operation.";
        }

        showResult(
                "SINGLY_LINKED_LIST",
                formatList(singlyValues, false),
                message,
                ""
        );
    }

    // =====================================================
    // DOUBLY LINKED LIST OPERATIONS
    // =====================================================

    private void handleDoublyList(
            String operation,
            int value,
            int target
    ) {

        if (doublyList == null) {
            doublyList = new DoublyLinkedList();
        }

        String message;
        boolean success;

        switch (operation) {

            case "Insert at Beginning":

                doublyList.insertAtBeginning(value);
                doublyValues.add(0, value);

                message = "Inserted " + value + " at the beginning.";
                break;

            case "Insert at End":

                doublyList.insertAtEnd(value);
                doublyValues.add(value);

                message = "Inserted " + value + " at the end.";
                break;

            case "Insert Before":

                success = doublyList.insertBefore(target, value);

                if (success) {

                    int index = doublyValues.indexOf(target);
                    doublyValues.add(index, value);

                    message = "Inserted " + value + " before " + target + ".";

                } else {

                    message = "Target element " + target + " was not found.";
                }

                break;

            case "Insert After":

                success = doublyList.insertAfter(target, value);

                if (success) {

                    int index = doublyValues.indexOf(target);
                    doublyValues.add(index + 1, value);

                    message = "Inserted " + value + " after " + target + ".";

                } else {

                    message = "Target element " + target + " was not found.";
                }

                break;

            case "Delete at Beginning":

                success = doublyList.deleteAtBeginning();

                if (success) {

                    doublyValues.remove(0);
                    message = "Deleted the first element.";

                } else {

                    message = "List is empty.";
                }

                break;

            case "Delete at End":

                success = doublyList.deleteAtEnd();

                if (success) {

                    doublyValues.remove(doublyValues.size() - 1);
                    message = "Deleted the last element.";

                } else {

                    message = "List is empty.";
                }

                break;

            case "Delete Before":

                success = doublyList.deleteBefore(target);

                if (success) {

                    int index = doublyValues.indexOf(target);
                    doublyValues.remove(index - 1);

                    message = "Deleted the element before " + target + ".";

                } else {

                    message = "Cannot delete before " + target + ".";
                }

                break;

            case "Delete After":

                success = doublyList.deleteAfter(target);

                if (success) {

                    int index = doublyValues.indexOf(target);
                    doublyValues.remove(index + 1);

                    message = "Deleted the element after " + target + ".";

                } else {

                    message = "Cannot delete after " + target + ".";
                }

                break;

            case "Delete Element":

                success = doublyList.deleteValue(value);

                if (success) {

                    doublyValues.remove(Integer.valueOf(value));
                    message = "Deleted element " + value + ".";

                } else {

                    message = "Element " + value + " was not found.";
                }

                break;

            case "Search":

                message = doublyList.search(value)
                        ? "Element " + value + " was found."
                        : "Element " + value + " was not found.";

                break;

            case "Size":

                message = "List size: " + doublyList.size();
                break;

            case "Display Forward":

                message = "Displaying list from head to tail.";
                break;

            case "Display Backward":

                message = "Displaying list from tail to head.";
                break;

            default:

                message = "Unknown linked-list operation.";
        }

        boolean backwards = operation.equals("Display Backward");

        showResult(
                "DOUBLY_LINKED_LIST",
                formatList(doublyValues, backwards),
                message,
                ""
        );
    }

    // =====================================================
    // BRACKET CHECKER
    // =====================================================

    @Override
    public void checkBrackets(String expression) {

        List<String> steps = new ArrayList<>();

        if (expression == null || expression.trim().isEmpty()) {

            steps.add("No expression was entered.");

            panel.updateBracketResult(
                    steps,
                    false,
                    "Please enter an expression."
            );

            return;
        }

        ArrayStack bracketStack = new ArrayStack(expression.length());

        boolean balanced = true;
        String message = "Expression is balanced.";

        for (int i = 0; i < expression.length(); i++) {

            char current = expression.charAt(i);

            if (current == '(' || current == '[' || current == '{') {

                bracketStack.push((int) current);

                steps.add(
                        current + " -> Opening bracket pushed. Stack: "
                                + bracketStack.getContentsAsBrackets()
                );

            } else if (current == ')' || current == ']' || current == '}') {

                if (bracketStack.isEmpty()) {

                    steps.add(
                            current + " -> ERROR: No matching opening bracket."
                    );

                    balanced = false;
                    message = "Expression is not balanced.";
                    break;
                }

                char opening = (char) bracketStack.pop();

                if (!matches(opening, current)) {

                    steps.add(
                            current + " -> ERROR: Does not match " + opening
                    );

                    balanced = false;
                    message = "Mismatched brackets.";
                    break;
                }

                steps.add(
                        current + " -> Matching bracket. Stack: "
                                + bracketStack.getContentsAsBrackets()
                );

            } else {

                steps.add(
                        current + " -> Not a bracket. Stack: "
                                + bracketStack.getContentsAsBrackets()
                );
            }
        }

        if (balanced && !bracketStack.isEmpty()) {

            balanced = false;
            message = "Unmatched opening brackets remain.";

            steps.add(
                    "Remaining opening brackets: "
                            + bracketStack.getContentsAsBrackets()
            );
        }

        if (balanced) {

            steps.add("RESULT: Expression is BALANCED.");

        } else {

            steps.add("RESULT: Expression is NOT BALANCED.");
        }

        panel.updateBracketResult(
                steps,
                balanced,
                message
        );
    }

    private boolean matches(char opening, char closing) {

        return (opening == '(' && closing == ')')
                || (opening == '[' && closing == ']')
                || (opening == '{' && closing == '}');
    }

    // =====================================================
    // VISUALISATION HELPERS
    // =====================================================

    private String formatList(
            List<Integer> values,
            boolean backwards
    ) {

        StringBuilder visual = new StringBuilder();

        if (backwards) {

            visual.append("TAIL <-> ");

            for (int i = values.size() - 1; i >= 0; i--) {

                visual.append("[ ")
                        .append(values.get(i))
                        .append(" ] <-> ");
            }

            visual.append("HEAD");

        } else {

            visual.append("HEAD -> ");

            for (int item : values) {

                visual.append("[ ")
                        .append(item)
                        .append(" ] -> ");
            }

            visual.append("NULL");
        }

        visual.append("\n\nSize: ").append(values.size());

        return visual.toString();
    }

    private String getVisualisation(String structure) {

        switch (structure) {

            case "STACK":
                return formatList(stackValues, false);

            case "LINEAR_QUEUE":
                return formatList(linearValues, false);

            case "CIRCULAR_QUEUE":
                return formatList(circularValues, false);

            case "SINGLY_LINKED_LIST":
                return formatList(singlyValues, false);

            case "DOUBLY_LINKED_LIST":
                return formatList(doublyValues, false);

            default:
                return "";
        }
    }

    private void showResult(
            String structure,
            String visualisation,
            String message,
            String details
    ) {

        panel.updateStructureView(
                structure,
                visualisation,
                message,
                details
        );
    }
}
