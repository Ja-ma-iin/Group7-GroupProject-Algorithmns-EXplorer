
package datastructures;

public class DoublyLinkedList {

    // Represents one node in the doubly linked list.
    private static class Node {
        int data;
        Node next;
        Node prev;

        Node(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int count;

    // Constructor
    public DoublyLinkedList() {
        head = null;
        tail = null;
        count = 0;
    }

    // Insert at the beginning.
    // Time Complexity: O(1)
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        count++;
    }

    // Insert at the end.
    // Time Complexity: O(1)
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        count++;
    }

    // Insert after a specified value.
    // Time Complexity: O(n)
    public boolean insertAfter(int target, int value) {
        Node current = head;

        while (current != null) {
            if (current.data == target) {

                if (current == tail) {
                    insertAtEnd(value);
                    return true;
                }

                Node newNode = new Node(value);

                newNode.next = current.next;
                newNode.prev = current;

                current.next.prev = newNode;
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
        Node current = head;

        while (current != null) {
            if (current.data == target) {

                if (current == head) {
                    insertAtBeginning(value);
                    return true;
                }

                Node newNode = new Node(value);

                newNode.prev = current.prev;
                newNode.next = current;

                current.prev.next = newNode;
                current.prev = newNode;

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

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        count--;
        return true;
    }

    // Delete from the end.
    // Time Complexity: O(1)
    public boolean deleteAtEnd() {
        if (tail == null) {
            return false;
        }

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        count--;
        return true;
    }

    // Delete a specified value.
    // Time Complexity: O(n)
    public boolean deleteValue(int value) {
        Node current = head;

        while (current != null) {
            if (current.data == value) {

                if (current == head) {
                    return deleteAtBeginning();
                }

                if (current == tail) {
                    return deleteAtEnd();
                }

                current.prev.next = current.next;
                current.next.prev = current.prev;

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
        Node current = head;

        while (current != null) {
            if (current.data == target) {

                if (current == head || current.prev == head) {
                    return false;
                }

                Node nodeToDelete = current.prev;

                nodeToDelete.prev.next = current;
                current.prev = nodeToDelete.prev;

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

                Node nodeToDelete = current.next;

                current.next = nodeToDelete.next;

                if (nodeToDelete == tail) {
                    tail = current;
                } else {
                    nodeToDelete.next.prev = current;
                }

                count--;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Search for an element.
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

    // Display from head to tail.
    // Time Complexity: O(n)
    public void displayForward() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = head;

        System.out.print("NULL <-> ");

        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }

        System.out.println("NULL");
    }

    // Display from tail to head.
    // Time Complexity: O(n)
    public void displayBackward() {
        if (tail == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = tail;

        System.out.print("NULL <-> ");

        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }

        System.out.println("NULL");
    }
}
