import java.util.Iterator;
import java.util.NoSuchElementException;


/* Corner cases. Throw a java.lang.NullPointerException if the
client attempts to add a null item; throw a java.util.NoSuchElementException
if the client attempts to remove an item from an empty deque; throw a java.lang.UnsupportedOperationException
if the client calls the remove() method in the iterator;
throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there
are no more items to return. */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        public Node(Item value) {
            this.value = value;
        }

        Item value;
        Node previous;
        Node next;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNotNullOnAdd(item);
        if(isEmpty()) {
            addWhenEmpty(item);
        } else {
            Node node = new Node(item);
            first.previous = node;
            node.next = first;
            first = node;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNotNullOnAdd(item);
        if(isEmpty()) {
            addWhenEmpty(item);
        } else {
            Node node = new Node(item);
            last.next = node;
            node.previous = last;
            last = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkNotEmpty();
        Item result;
        if(size() == 1) {
            result = removeWhenOne();
        } else {
            result = first.value;
            first = first.next;
            if(first != null) {
                first.previous = null;
            }
        }
        size--;
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkNotEmpty();
        Item result;
        if(size() == 1) {
            result = removeWhenOne();
        } else {
            result = last.value;
            last = last.previous;
            if(last != null) {
                last.next = null;
            }
        }
        size--;
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {

    }

    // unit testing
    public static void main(String[] args) {

    }

    private void checkNotNullOnAdd(Item item) {
        if(item == null) {
            throw new NullPointerException();
        }
    }

    private void addWhenEmpty(Item item) {
        Node node = new Node(item);
        first = last = node;
    }

    private void checkNotEmpty() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private Item removeWhenOne() {
        Item result = first.value;
        first = null;
        last = null;
        return result;
    }
}