import java.util.Random;

public class TreapNode {
    public static Random rand = new Random();

    int key;
    int priority;
    TreapNode left;
    TreapNode right;
    TreapNode parent;

    public TreapNode(int key, TreapNode parent) {
        this.parent = parent;
        this.key = key;
        this.priority = Math.abs(rand.nextInt());
    }
}
