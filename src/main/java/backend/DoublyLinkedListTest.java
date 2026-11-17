
package backend;

import datastructures.DoublyLinkedList;

public class DoublyLinkedListTest {

    public static void main(String[] args) {

        DoublyLinkedList list = new DoublyLinkedList();

        System.out.println("==============================");
        System.out.println("   DOUBLY LINKED LIST TEST");
        System.out.println("==============================");

        // 1. Insert at beginning and end.
        System.out.println("\n1. INSERT OPERATIONS");

        list.insertAtBeginning(20);
        list.insertAtBeginning(10);
        list.insertAtEnd(40);

        list.displayForward();

        // 2. Insert before and after.
        System.out.println("\n2. INSERT BEFORE AND AFTER");

        list.insertBefore(40, 30);
        list.insertAfter(40, 50);

        list.displayForward();

        // 3. Display backwards.
        System.out.println("\n3. BACKWARD DISPLAY");

        list.displayBackward();

        // 4. Search.
        System.out.println("\n4. SEARCH");

        System.out.println("Search 30: " + list.search(30));
        System.out.println("Search 99: " + list.search(99));

        // 5. Size.
        System.out.println("\n5. SIZE");

        System.out.println("List size: " + list.size());

        // 6. Delete specified value.
        System.out.println("\n6. DELETE VALUE");

        list.deleteValue(30);
        list.displayForward();

        // 7. Delete before.
        System.out.println("\n7. DELETE BEFORE");

        list.deleteBefore(50);
        list.displayForward();

        // 8. Delete after.
        System.out.println("\n8. DELETE AFTER");

        list.deleteAfter(10);
        list.displayForward();

        // 9. Delete at beginning.
        System.out.println("\n9. DELETE AT BEGINNING");

        list.deleteAtBeginning();
        list.displayForward();

        // 10. Delete at end.
        System.out.println("\n10. DELETE AT END");

        list.deleteAtEnd();
        list.displayForward();

        System.out.println("\nFinal size: " + list.size());

        System.out.println("\n==============================");
        System.out.println("       TEST COMPLETED");
        System.out.println("==============================");
    }
}
