import java.util.Random;

public class TreapNode {
    public static Random rand = new Random();

    int key;
    int priority;
    TreapNode left;
    TreapNode right;

    public TreapNode(int key) {
        this.key = key;
        this.priority = rand.nextInt();
    }
}
