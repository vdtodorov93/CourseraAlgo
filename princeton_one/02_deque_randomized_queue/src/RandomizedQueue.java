import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] values;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        values = (Item[])new Object[10];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException();
        if(size() == values.length - 1) {
            resize(values.length * 2);
        }
        values[size] = item;
        int randIndex = 0;
        if(size() != 0) {
            randIndex = StdRandom.uniform(0, size());
        }
        Item prev = values[randIndex];
        values[size] = prev;
        values[randIndex] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if(size() == 0) throw new NoSuchElementException();
        if(size() == values.length / 4 && size() > 20) {
            resize(values.length / 2);
        }
        int randIndex = 0;
        if(size() > 1) {
            randIndex = StdRandom.uniform(0, size() - 1);
        }
        size--;
        Item result = values[randIndex];
        values[randIndex] = values[size()];
        values[size()] = null;
        return result;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if(size() == 0) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, size() - 1);
        return values[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int newSize) {
        Item[] newValues = (Item[])new Object[newSize];
        for(int i = 0; i < size; i++) {
            newValues[i] = values[i];
        }
        values = newValues;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] copy = Arrays.copyOf(values, values.length);
        int copySize = size();

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if(copySize == 0) {
                throw new NoSuchElementException();
            }
            return copy[--copySize];
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for(int i = 0; i < 19; i++) rq.enqueue(i);

        Iterator<Integer> it = rq.iterator();
        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        //Object[] as = rq.values;
//        for(int i = 0; i < as.length; i++) System.out.print(as[i] + " ");
//
//        System.out.println();
//        for(int i = 0; i < 19; i++) System.out.print(rq.dequeue() + " ");
//        System.out.println();
    }
}