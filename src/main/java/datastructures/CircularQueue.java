
package datastructures;

public class CircularQueue {

    private int[] queue;
    private int front;
    private int rear;
    private int capacity;
    private int count;

    // Constructor: creates a circular queue.
    public CircularQueue(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Queue capacity must be greater than zero."
            );
        }

        this.capacity = capacity;
        queue = new int[capacity];

        front = -1;
        rear = -1;
        count = 0;
    }

    // Adds an element to the rear.
    // Time Complexity: O(1)
    public void enqueue(int value) {

        if (isFull()) {
            throw new IllegalStateException("Queue is full!");
        }

        if (isEmpty()) {
            front = 0;
        }

        rear = (rear + 1) % capacity;

        queue[rear] = value;
        count++;
    }

    // Removes and returns the front element.
    // Time Complexity: O(1)
    public int dequeue() {

        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }

        int removed = queue[front];

        count--;

        if (isEmpty()) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }

        return removed;
    }

    // Returns the front element without removing it.
    // Time Complexity: O(1)
    public int peek() {

        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }

        return queue[front];
    }

    // Returns the number of elements.
    // Time Complexity: O(1)
    public int size() {
        return count;
    }

    // Checks whether the queue is empty.
    // Time Complexity: O(1)
    public boolean isEmpty() {
        return count == 0;
    }

    // Checks whether the queue is full.
    // Time Complexity: O(1)
    public boolean isFull() {
        return count == capacity;
    }

    // Returns the front index.
    public int getFrontIndex() {
        return front;
    }

    // Returns the rear index.
    public int getRearIndex() {
        return rear;
    }

    // Displays elements from front to rear.
    // Time Complexity: O(n)
    public void display() {

        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.print("Queue (Front to Rear): ");

        for (int i = 0; i < count; i++) {

            int index = (front + i) % capacity;

            System.out.print(queue[index] + " ");
        }

        System.out.println();

        System.out.println("Front index: " + front);
        System.out.println("Rear index: " + rear);
    }
}
