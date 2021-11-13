import java.util.Random;

/**
 * Class representing a TreapNode
 * @param <T> the type of data that the treap node will store
 */
public class TreapNode<T> {
    public static Random rand = new Random();

    int key;
    int priority;
    T data;
    TreapNode<T> left;
    TreapNode<T> right;

    /**
     * Creates a new treap node with a specified key and data value. Randomly generates a heap priority.
     * @param key the key
     * @param data the data
     */
    public TreapNode(int key, T data) {
        this.key = key;
        this.priority = Math.abs(rand.nextInt());
        this.data = data;
    }

    /**
     * Creates a new treap node with a specified key, data value, and heap priority.
     * @param key the key
     * @param priority the heap priority
     * @param data the data
     */
    public TreapNode(int key, int priority, T data) {
        this.key = key;
        this.priority = priority;
        this.data = data;
    }
}
