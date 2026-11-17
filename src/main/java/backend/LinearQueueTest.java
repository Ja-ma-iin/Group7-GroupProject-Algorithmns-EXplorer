
package backend;

import datastructures.LinearQueue;

public class LinearQueueTest {

    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("      LINEAR QUEUE TEST");
        System.out.println("==============================");

        // Create queue with capacity 5.
        LinearQueue queue = new LinearQueue(5);

        // Test ENQUEUE
        System.out.println("\n1. ENQUEUE OPERATIONS");

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        queue.display();

        // Test PEEK
        System.out.println("\n2. FRONT ELEMENT");
        System.out.println("Front: " + queue.peek());

        // Test SIZE
        System.out.println("\n3. SIZE");
        System.out.println("Queue size: " + queue.size());

        // Test DEQUEUE
        System.out.println("\n4. DEQUEUE OPERATION");
        System.out.println("Removed: " + queue.dequeue());

        queue.display();

        // Test IS EMPTY
        System.out.println("\n5. IS EMPTY");
        System.out.println("Is queue empty? " + queue.isEmpty());

        // Test IS FULL
        System.out.println("\n6. IS FULL");
        System.out.println("Is queue full? " + queue.isFull());

        System.out.println("\n==============================");
        System.out.println("      TEST COMPLETED");
        System.out.println("==============================");
    }
}
