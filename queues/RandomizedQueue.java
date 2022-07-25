import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int HEAD = 0;
    private int numElements;
    private Item[] randomQ;
    private int tail;
    private int currCapacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        numElements = 0;
        tail = 0;
        currCapacity = 10;
        randomQ = (Item[]) new Object[currCapacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numElements;
    }

    // add the item
    public void enqueue(Item item) {
        // If item is null.
        if (item == null) {
            throw new IllegalArgumentException("Bad item.");
        }
        // If the queue is empty.
        if (isEmpty()) {
            randomQ[HEAD] = item;
        }
        // If there is more space to add element in the queue.
        else if (tail < currCapacity) {
            randomQ[tail] = item;
        }
        // If there is no more capacity, double the array and copy all elements.
        // Then add element to tail.
        else if (size() == currCapacity) {
            randomQ = doubleArray(currCapacity);
            randomQ[tail] = item;
        }

        // Increment tail and the number of elements.
        tail++;
        numElements++;
    }

    private Item[] doubleArray(int capacity) {
        // Copy over the array and all its elements.
        currCapacity = capacity * 2;
        Item[] newArray = (Item[]) new Object[currCapacity];
        for (int idx = 0; idx < randomQ.length; idx++) {
            newArray[idx] = randomQ[idx];
        }

        // Return new array with double capacity.
        return newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("IsEmpty - Can't Call dequeue().");
        }

        int randIndex = StdRandom.uniform(tail-HEAD);
        Item itemToRemove = randomQ[randIndex];

        randomQ[randIndex] = randomQ[tail-1];
        tail--;
        numElements--;

        return itemToRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("IsEmpty - Can't Call sample().");
        }
        int randIndex = StdRandom.uniform(tail-HEAD);
        Item itemToReturn = randomQ[randIndex];

        return itemToReturn;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Private Iterator class adapted from the Algorithms - Part I
     * Iterators Lecture from Week 2.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        private int currentIdx = HEAD;

        public boolean hasNext() {
            return currentIdx < tail;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Cannot Call Next on Null");
            }
            else {
                Item currentItem = randomQ[currentIdx];
                currentIdx++;

                return currentItem;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot Call Remove from Iterator");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        System.out.println("Size: " + queue.size());

        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(1);
        queue.enqueue(6);

        for (int i: queue) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());


        System.out.println();
        System.out.println(queue.dequeue());

        for (int i: queue) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println(queue.dequeue());

        for (int i: queue) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println(queue.dequeue());

        for (int i: queue) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("Size: " + queue.size());


        Iterator<Integer> it = queue.iterator();
        Iterator<Integer> it2 = queue.iterator();

        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        System.out.println();

        while (it2.hasNext()) {
            System.out.print(it2.next() + " ");
        }
    }


}