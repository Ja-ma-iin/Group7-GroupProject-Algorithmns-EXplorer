package datastructures;

public class ArrayStack {

    private int[] stack;
    private int top;
    private int capacity;

    // Constructor: creates a stack with a fixed capacity.
    public ArrayStack(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Stack capacity must be greater than zero."
            );
        }

        this.capacity = capacity;
        stack = new int[capacity];
        top = -1;
    }

    // Push: adds an element to the top of the stack.
    // Time Complexity: O(1)
    public void push(int value) {

        if (isFull()) {
            throw new IllegalStateException("Stack is full!");
        }

        stack[++top] = value;
    }

    // Pop: removes and returns the top element.
    // Time Complexity: O(1)
    public int pop() {

        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }

        return stack[top--];
    }

    // Peek: returns the top element without removing it.
    // Time Complexity: O(1)
    public int peek() {

        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }

        return stack[top];
    }

    // Returns the number of elements in the stack.
    // Time Complexity: O(1)
    public int size() {
        return top + 1;
    }

    // Checks whether the stack is empty.
    // Time Complexity: O(1)
    public boolean isEmpty() {
        return top == -1;
    }

    // Checks whether the stack is full.
    // Time Complexity: O(1)
    public boolean isFull() {
        return top == capacity - 1;
    }

    // Displays all stack elements from top to bottom.
    // Time Complexity: O(n)
    public void display() {

        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }

        System.out.println("Stack (Top to Bottom):");

        for (int i = top; i >= 0; i--) {
            System.out.println("| " + stack[i] + " |");
        }

        System.out.println("-----");
    }

    // Returns stack contents formatted as brackets from top to bottom.
    // Time Complexity: O(n)
    public String getContentsAsBrackets() {
        StringBuilder result = new StringBuilder();

        result.append("[ ");

        for (int i = top; i >= 0; i--) {
            result.append((char) stack[i]);

            if (i > 0) {
                result.append(", ");
            }
        }

        result.append(" ]");

        return result.toString();
    }
}

