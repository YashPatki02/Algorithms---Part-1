import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int numItems;
    private Node first, last;

    private class Node {
        private Item data;
        private Node next;
        private Node prev;

        public Node(Item data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    // construct an empty deque
    public Deque() {
        first = new Node(null, null, null);
        last = new Node(null, null, null);

        numItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // Throw error if we are adding a null Item.
        if (item == null) {
            throw new IllegalArgumentException("Null Argument Not Valid.");
        }
        // Case when the size is 0, first and last are both still null so set
        // both of them to the new Node we created.
        else if (isEmpty()) {
            Node newNode = new Node(item, null, null);
            first = newNode;
            last = newNode;
            numItems++;
        }
        // Else case for all other instances.
        else {
            // Get the original first.
            Node oldFirst = first;

            // Create a new first Node with data: item, next: oldFirst, and
            // prev: null. Increment the number of items.
            first = new Node(item, oldFirst, null);
            oldFirst.prev = first;
            numItems++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        // Throw error if we are adding a null Item.
        if (item == null) {
            throw new IllegalArgumentException("Null Argument Not Valid.");
        }
        // Case when the size is 0, first and last are both still null so set
        // both of them to the new Node we created.
        else if (isEmpty()) {
            Node newNode = new Node(item, null, null);
            first = newNode;
            last = newNode;
            numItems++;
        }
        // Else case for all other instances.
        else {
            // Get the original last.
            Node oldLast = last;

            // Create a new last Node with data: item, next: null, and
            // prev: oldLast.
            last = new Node(item, null, oldLast);

            // Set oldLast's next pointer to the new last we created. Increment
            // the number of items.
            oldLast.next = last;
            numItems++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot Remove from Empty Deque");
        }

        // Get the next and data of the first Node.
        Node newFirst = first.next;
        Item firstItem = first.data;

        // Remove the first node by setting it's data to null.
        first.data = null;

        // Declare a new first and set it's previous to null since we deleted
        // the original first.
        first = newFirst;

        if (first != null) {
            first.prev = null;
        }

        numItems--;

        // Return the data of the Node we removed.
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot Remove from Empty Deque");
        }

        // Get the data of the last Node.
        Item lastItem = last.data;

        // Set the data of the last Node to null to delete it. Then set the
        // next pointer of the original last Node's prev to null.
        last.data = null;

        if (last.prev != null) {
            Node newLast = last.prev;
            newLast.next = null;
            last = newLast;
        }

        numItems--;

        // Return the data of the Node we removed.
        return lastItem;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Private Iterator class adapted from the Algorithms - Part I
     * Iterators Lecture from Week 2.
     */
    private class DequeIterator implements Iterator<Item> {

        private Node current = new Node(null, first, null);

        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Cannot Call Next on Null");
            }
            else {
                current = current.next;
                Item currentItem = current.data;

                return currentItem;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot Call Remove from Iterator");
        }
    }


    // unit testing (required)
    public static void main(String[] args) {

        /**
        Deque<Integer> deque = new Deque<Integer>();


        System.out.println(deque.size());

        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(2);
        deque.addLast(1);
        deque.addLast(6);

        for (int i: deque) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println(deque.removeFirst());

        for (int i: deque) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println(deque.size());
         */

        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        deque.addFirst(1);
        Iterator<Integer> it = deque.iterator();

        while(it.hasNext()) {
            System.out.print("First: " + it.next() + " ");
        }

        deque.removeLast();
        System.out.println();

        while(it.hasNext()) {
            System.out.print("Second: " + it.next() + " ");
        }

        System.out.println(deque.size());



    }

}