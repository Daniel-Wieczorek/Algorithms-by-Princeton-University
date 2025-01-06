import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic Deque (double-ended queue) implementation using a doubly linked
 * list.
 * 
 * Supports adding and removing items from both ends in constant time.
 * 
 * Performance:
 * - isEmpty, size: O(1)
 * - addFirst, addLast: O(1)
 * - removeFirst, removeLast: O(1)
 * - iterator operations: O(1) per operation
 * 
 * @param <Item> the type of elements held in this deque
 */
public final class Deque<Item> implements Iterable<Item> {

    /** Node class representing an element in the Deque. */
    private class Node {
        Item data;
        Node prev;
        Node next;

        Node(Item value) {
            data = value;
            prev = null;
            next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs an empty Deque.
     */
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Checks if the Deque is empty.
     * 
     * @return true if the Deque is empty, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the Deque.
     * 
     * @return the size of the Deque
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the front of the Deque.
     * 
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed");
        }

        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds an item to the back of the Deque.
     * 
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed");
        }

        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the item from the front of the Deque.
     * 
     * @return the item from the front
     * @throws NoSuchElementException if the Deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty!");
        }

        Item result = head.data;
        head = head.next;

        if (head == null) {
            tail = null; // Deque is now empty
        } else {
            head.prev = null;
        }

        size--;
        return result;
    }

    /**
     * Removes and returns the item from the back of the Deque.
     * 
     * @return the item from the back
     * @throws NoSuchElementException if the Deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty!");
        }

        Item result = tail.data;
        tail = tail.prev;

        if (tail == null) {
            head = null; // Deque is now empty
        } else {
            tail.next = null;
        }

        size--;
        return result;
    }

    /**
     * Iterator for the Deque.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the Deque");
            }
            Item tmp = current.data;
            current = current.next;
            return tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    /**
     * Returns an iterator over items in order from front to back.
     * 
     * @return an iterator for the Deque
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Unit testing of the Deque class.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        assert deque.isEmpty() : "Deque should be empty initially";
        assert deque.size() == 0 : "Size should be 0 initially";

        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);

        assert deque.size() == 3 : "Size should be 3 after adding elements";
        assert deque.removeFirst() == 0 : "First element should be 0";
        assert deque.removeLast() == 2 : "Last element should be 2";
        assert deque.size() == 1 : "Size should be 1 after removals";

        deque.addLast(3);
        deque.addFirst(-1);

        Iterator<Integer> iterator = deque.iterator();
        assert iterator.hasNext() : "Iterator should have next element";
        assert iterator.next() == -1 : "First element from iterator should be -1";

        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for null input");
        }

        try {
            deque.removeFirst();
            deque.removeFirst();
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for empty deque");
        }

        System.out.println("All tests passed.");
    }
}
