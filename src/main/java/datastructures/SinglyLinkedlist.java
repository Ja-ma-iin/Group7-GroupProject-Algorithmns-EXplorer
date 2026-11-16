package datastructures;

public class SinglyLinkedList {

    // Represents one node in the linked list.
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int count;

    // Constructor
    public SinglyLinkedList() {
        head = null;
        count = 0;
    }

    // Insert at the beginning.
    // Time Complexity: O(1)
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);

        newNode.next = head;
        head = newNode;
        count++;
    }

    // Insert at the end.
    // Time Complexity: O(n)
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        count++;
    }

    // Insert after a specified value.
    // Time Complexity: O(n)
    public boolean insertAfter(int target, int value) {
        Node current = head;

        while (current != null) {
            if (current.data == target) {
                Node newNode = new Node(value);

                newNode.next = current.next;
                current.next = newNode;

                count++;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Insert before a specified value.
    // Time Complexity: O(n)
    public boolean insertBefore(int target, int value) {

        if (head == null) {
            return false;
        }

        if (head.data == target) {
            insertAtBeginning(value);
            return true;
        }

        Node current = head;

        while (current.next != null) {

            if (current.next.data == target) {
                Node newNode = new Node(value);

                newNode.next = current.next;
                current.next = newNode;

                count++;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Delete from the beginning.
    // Time Complexity: O(1)
    public boolean deleteAtBeginning() {

        if (head == null) {
            return false;
        }

        head = head.next;
        count--;

        return true;
    }

    // Delete from the end.
    // Time Complexity: O(n)
    public boolean deleteAtEnd() {

        if (head == null) {
            return false;
        }

        if (head.next == null) {
            head = null;
            count--;
            return true;
        }

        Node current = head;

        while (current.next.next != null) {
            current = current.next;
        }

        current.next = null;
        count--;

        return true;
    }

    // Delete a specified value.
    // Time Complexity: O(n)
    public boolean deleteValue(int value) {

        if (head == null) {
            return false;
        }

        if (head.data == value) {
            return deleteAtBeginning();
        }

        Node current = head;

        while (current.next != null) {

            if (current.next.data == value) {
                current.next = current.next.next;
                count--;

                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Delete the node before a specified value.
    // Time Complexity: O(n)
    public boolean deleteBefore(int target) {

        if (head == null || head.next == null
                || head.data == target) {
            return false;
        }

        if (head.next.data == target) {
            return deleteAtBeginning();
        }

        Node current = head;

        while (current.next.next != null) {

            if (current.next.next.data == target) {
                current.next = current.next.next;
                count--;

                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Delete the node after a specified value.
    // Time Complexity: O(n)
    public boolean deleteAfter(int target) {

        Node current = head;

        while (current != null) {

            if (current.data == target) {

                if (current.next == null) {
                    return false;
                }

                current.next = current.next.next;
                count--;

                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Search for a value.
    // Time Complexity: O(n)
    public boolean search(int value) {

        Node current = head;

        while (current != null) {

            if (current.data == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Return the number of nodes.
    // Time Complexity: O(1)
    public int size() {
        return count;
    }

    // Display all nodes.
    // Time Complexity: O(n)
    public void display() {

        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = head;

        System.out.print("HEAD -> ");

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("NULL");
    }
}
