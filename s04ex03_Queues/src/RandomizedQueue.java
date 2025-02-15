import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final static int kResize = 2;
    private Item[] queue;
    private int size;

    private int getRandomNumber() {
        return StdRandom.uniformInt(size);
    }

    public RandomizedQueue() {
        queue = (Item[]) new Object[kResize];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }

        queue = newQueue;
    }

    public void enqueue(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }

        if (size == queue.length) {
            resize(kResize * queue.length);
        }

        queue[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        Integer elemNum = getRandomNumber();
        Item item = queue[elemNum];

        // Data is random anyway so it does not matter where we remove it:
        queue[elemNum] = queue[size - 1];
        queue[size - 1] = null;
        size--;

        if (size > 0 && size == (queue.length / (kResize * kResize))) {
            resize(queue.length / kResize);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return queue[getRandomNumber()];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledQueue;
        private int current;

        public RandomizedQueueIterator() {
            shuffledQueue = (Item[]) new Object[size];
            System.arraycopy(queue, 0, shuffledQueue, 0, size);
            StdRandom.shuffle(shuffledQueue);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < shuffledQueue.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the RandomizedDeque");
            }

            var tmp = shuffledQueue[current];
            current++;
            return tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }

    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        System.out.println("Sampled item: " + rq.sample());
        System.out.println("Dequeued item: " + rq.dequeue());
        System.out.println("Queue size: " + rq.size());

        for (int item : rq) {
            System.out.println("Iterator item: " + item);
        }

        System.out.println("Is queue empty? " + rq.isEmpty());
    }

}