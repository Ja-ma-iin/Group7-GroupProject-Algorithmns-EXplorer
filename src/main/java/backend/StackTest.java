
package backend;

import datastructures.ArrayStack;

public class StackTest {

    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("       STACK TEST");
        System.out.println("==============================");

        // Create a stack with capacity 5.
        ArrayStack stack = new ArrayStack(5);

        // Test PUSH
        System.out.println("\n1. PUSH OPERATIONS");

        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.display();

        // Test PEEK
        System.out.println("\n2. PEEK OPERATION");
        System.out.println("Top element: " + stack.peek());

        // Test SIZE
        System.out.println("\n3. SIZE");
        System.out.println("Stack size: " + stack.size());

        // Test POP
        System.out.println("\n4. POP OPERATION");
        System.out.println("Removed: " + stack.pop());

        stack.display();

        // Test IS EMPTY
        System.out.println("\n5. IS EMPTY");
        System.out.println("Is stack empty? " + stack.isEmpty());

        // Test IS FULL
        System.out.println("\n6. IS FULL");
        System.out.println("Is stack full? " + stack.isFull());

        System.out.println("\n==============================");
        System.out.println("       TEST COMPLETED");
        System.out.println("==============================");
    }
}
