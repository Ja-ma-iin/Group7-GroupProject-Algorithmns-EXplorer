
package backend;

import datastructures.CircularQueue;

public class CircularQueueTest {

    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("     CIRCULAR QUEUE TEST");
        System.out.println("==============================");

        CircularQueue queue = new CircularQueue(5);

        // Test ENQUEUE
        System.out.println("\n1. ENQUEUE OPERATIONS");

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        queue.enqueue(50);

        queue.display();

        // Test IS FULL
        System.out.println("\n2. IS FULL");
        System.out.println("Is queue full? " + queue.isFull());

        // Test DEQUEUE
        System.out.println("\n3. DEQUEUE OPERATIONS");

        System.out.println("Removed: " + queue.dequeue());
        System.out.println("Removed: " + queue.dequeue());

        queue.display();

        // Test WRAP AROUND
        System.out.println("\n4. WRAP AROUND");

        queue.enqueue(60);
        queue.enqueue(70);

        queue.display();

        // Test PEEK
        System.out.println("\n5. FRONT ELEMENT");
        System.out.println("Front: " + queue.peek());

        // Test SIZE
        System.out.println("\n6. SIZE");
        System.out.println("Queue size: " + queue.size());

        // Test IS EMPTY
        System.out.println("\n7. IS EMPTY");
        System.out.println("Is queue empty? " + queue.isEmpty());

        System.out.println("\n==============================");
        System.out.println("      TEST COMPLETED");
        System.out.println("==============================");
    }
}
