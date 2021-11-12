import java.util.Arrays;
import java.util.List;

public class Treap<T> {
    TreapNode<T> root;

    public Treap() {
        root = null;
    }

    public void insert(int key, T data) {
        root = insertRec(root, new TreapNode<>(key, data));
    }

    public TreapNode<T> insertRec(TreapNode<T> current, TreapNode<T> newNode) {
        int key = newNode.key;
        if (current == null || key == current.key) {
            return newNode;
        } else if (key < current.key) {
            current.left = (current.left == null) ? newNode : insertRec(current.left, newNode);
            if (current.priority < current.left.priority) {
                current = LL_Rotate(current);
            }
            return current;
        } else {
            current.right = (current.right == null) ? newNode : insertRec(current.right, newNode);
            if (current.priority < current.right.priority) {
                current = RR_Rotate(current);
            }
            return current;
        }
    }

    public boolean containsKey(int key) {
        TreapNode<T> currentNode = root;
        while (currentNode != null && currentNode.key != key) {
            currentNode = (key < currentNode.key) ? currentNode.left : currentNode.right;
        }
        return currentNode != null;
    }

    public boolean containsData(T data) {
        return containsDataRec(data, root);
    }

    private boolean containsDataRec(T data, TreapNode<T> current) {
        if (current == null) {
            return false;
        } else if (current.data.equals(data)) {
            return true;
        } else {
            return containsDataRec(data, current.left) || containsDataRec(data, current.right);
        }
    }

    public T get(int key) {
        return getRec(key, root);
    }

    private T getRec(int key, TreapNode<T> current) {
        if (current == null) {
            return null;
        } else if (current.key == key) {
            return current.data;
        } else if (key > current.key) {
            return getRec(key, current.right);
        } else {
            return getRec(key, current.left);
        }
    }

    public void delete(int key) {
        root = deleteRec(key, root);
    }

    private TreapNode<T> deleteRec(int key, TreapNode<T> current) {
        if (current == null) {
            return null;
        } else if (key < current.key) {
            current.left = deleteRec(key, current.left);
            return current;
        } else if (key > current.key) {
            current.right = deleteRec(key, current.right);
            return current;
        } else { // If key == current.key
            if (current.left == null && current.right == null) { // If current is a leaf
                return null;
            } else if (current.left != null && current.right == null) { // If current has a left child
                return current.left;
            } else if (current.left == null) { // If current has a right child
                return current.right;
            } else { // current has two children
                TreapNode<T> newRoot;
                if (current.left.priority < current.right.priority) {
                    newRoot = current.right;
                    RR_Rotate(current);
                    newRoot.left = deleteRec(key, current.left);
                } else {
                    newRoot = current.left;
                    LL_Rotate(current);
                    newRoot.right = deleteRec(key, current.right);
                }
                return newRoot;
            }
        }
    }

    public List<Treap<T>> splitTree(int key) {
        root = insertRec(root, new TreapNode<>(key, Integer.MAX_VALUE, null));
        Treap<T> leftTreap = new Treap<>();
        leftTreap.root = root.left;
        Treap<T> rightTreap = new Treap<>();
        rightTreap.root = root.right;
        return Arrays.asList(leftTreap, rightTreap);
    }

    public static void main(String[] args) {
        Treap<Integer> treap1 = new Treap<>();

        // Insertion
        treap1.insert(10, 1000);
        treap1.insert(20, 200);
        treap1.insert(5, 500);
        treap1.insert(1, 100);
        treap1.insert(8, 800);
        System.out.println("Inserted 1, 5, 8, 10, 20:");
        TreePrinter.printNode(treap1.root);

        // Get data
        System.out.println("\nGet data for 5:");
        System.out.println(treap1.get(5));
        System.out.println("Get data for 8:");
        System.out.println(treap1.get(8));
        System.out.println("Get data for 11:");
        System.out.println(treap1.get(11));

        // Search for key
        System.out.println("\nSearching to see if treap has index 30 (Expected false):");
        System.out.println(treap1.containsKey(30));
        System.out.println("Searching to see if treap has index 8 (Expected true):");
        System.out.println(treap1.containsKey(8));

        // Search for data
        System.out.println("\nSearching to see if treap has data 800 (Expected true):");
        System.out.println(treap1.containsData(800));
        System.out.println("Searching to see if treap has data 97 (Expected false):");
        System.out.println(treap1.containsData(97));

        // Deletion
        treap1.delete(5);
        System.out.println("\nDeleted 5:");
        TreePrinter.printNode(treap1.root);
        treap1.delete(10);
        System.out.println("Deleted 10:");
        TreePrinter.printNode(treap1.root);
        treap1.delete(70);
        System.out.println("Attempted to delete 70 (Not in tree):");
        TreePrinter.printNode(treap1.root);
        treap1.delete(20);
        treap1.delete(1);
        System.out.println("Deleted 20, 1:");
        TreePrinter.printNode(treap1.root);

        // Split Tree
        Treap<Integer> treap2 = new Treap<>();
        treap2.insert(3, 10);
        treap2.insert(16, 10);
        treap2.insert(87, 10);
        treap2.insert(9, 10);
        treap2.insert(10, 10);
        treap2.insert(1, 10);
        treap2.insert(48, 10);
        treap2.insert(92, 10);

        System.out.println("\n\nTreap 2 (1,3,9,10,16,48,87,97):");
        TreePrinter.printNode(treap2.root);
        List<Treap<Integer>> treapParts = treap2.splitTree(10);
        System.out.println("Treap 2 after split at 10:");
        TreePrinter.printNode(treap2.root);
        System.out.println("Treap less than 10:");
        TreePrinter.printNode(treapParts.get(0).root);
        System.out.println("Treap greater than 10:");
        TreePrinter.printNode(treapParts.get(1).root);
    }

    //         A                                      B
    //        / \                                   /   \
    //       B   Ar         LL_Rotate (A)          C      A
    //      / \          - - - - - - - - ->      /  \    /  \
    //     C   Br                               Cl  Cr  Br  Ar
    //    / \
    //  Cl   Cr
    private TreapNode<T> LL_Rotate(TreapNode<T> A) {
        TreapNode<T> B = A.left;
        TreapNode<T> Br = B.right;

        // Reorder children
        A.left = Br;
        B.right = A;

//        // Reassign parents
//        B.parent = A.parent;
//        A.parent = B;
//        if (Br != null)
//            Br.parent = A;

        // Return new root
        return B;
    }

    //   A                                B
    //  /  \                            /   \
    // Al   B       RR_Rotate(A)       A      C
    //     /  \   - - - - - - - ->    / \    / \
    //    Bl   C                     Al  Bl Cl  Cr
    //        / \
    //      Cl  Cr
    private TreapNode<T> RR_Rotate(TreapNode<T> A) {
        TreapNode<T> B = A.right;
        TreapNode<T> Bl = B.left;

        // Reorder children
        A.right = Bl;
        B.left = A;

//        // Reassign parents
//        B.parent = A.parent;
//        A.parent = B;
//        if (Bl != null)
//            Bl.parent = A;

        // Return new root
        return B;
    }
}