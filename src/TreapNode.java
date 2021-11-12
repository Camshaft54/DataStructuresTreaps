import java.util.Random;

public class TreapNode<T> {
    public static Random rand = new Random();

    int key;
    int priority;
    T data;
    TreapNode<T> left;
    TreapNode<T> right;

    public TreapNode(int key, T data) {
        this.key = key;
        this.priority = Math.abs(rand.nextInt());
        this.data = data;
    }

    public TreapNode(int key, int priority, T data) {
        this.key = key;
        this.priority = priority;
        this.data = data;
    }
}
