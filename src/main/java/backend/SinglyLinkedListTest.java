
package backend;

import datastructures.SinglyLinkedList;

public class SinglyLinkedListTest {

    public static void main(String[] args) {

        SinglyLinkedList list = new SinglyLinkedList();

        System.out.println("==============================");
        System.out.println("   SINGLY LINKED LIST TEST");
        System.out.println("==============================");

        // 1. Insert at beginning and end.
        System.out.println("\n1. INSERT OPERATIONS");

        list.insertAtBeginning(20);
        list.insertAtBeginning(10);
        list.insertAtEnd(40);

        list.display();

        // 2. Insert before and after.
        System.out.println("\n2. INSERT BEFORE AND AFTER");

        list.insertBefore(40, 30);
        list.insertAfter(40, 50);

        list.display();

        // 3. Search.
        System.out.println("\n3. SEARCH");

        System.out.println("Search 30: " + list.search(30));
        System.out.println("Search 99: " + list.search(99));

        // 4. Size.
        System.out.println("\n4. SIZE");

        System.out.println("List size: " + list.size());

        // 5. Delete a specified value.
        System.out.println("\n5. DELETE VALUE");

        list.deleteValue(30);
        list.display();

        // 6. Delete before.
        System.out.println("\n6. DELETE BEFORE");

        list.deleteBefore(50);
        list.display();

        // 7. Delete after.
        System.out.println("\n7. DELETE AFTER");

        list.deleteAfter(10);
        list.display();

        // 8. Delete at beginning.
        System.out.println("\n8. DELETE AT BEGINNING");

        list.deleteAtBeginning();
        list.display();

        // 9. Delete at end.
        System.out.println("\n9. DELETE AT END");

        list.deleteAtEnd();
        list.display();

        System.out.println("\nFinal size: " + list.size());

        System.out.println("\n==============================");
        System.out.println("       TEST COMPLETED");
        System.out.println("==============================");
    }
}
