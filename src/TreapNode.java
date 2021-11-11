import java.util.Random;

public class TreapNode {
    public static Random rand = new Random();

    int key;
    int priority;
    TreapNode left;
    TreapNode right;

    public TreapNode(int key) {
        this.key = key;
        this.priority = Math.abs(rand.nextInt());
    }

    public TreapNode(int key, int priority) {
        this.key = key;
        this.priority = priority;
    }
}
