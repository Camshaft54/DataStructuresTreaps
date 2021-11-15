import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
public class Treap<T> {
    TreapNode<T> root;

    public Treap() {
        root = null;
    }

    /**
     * Inserts a node into the treap
     * @param key the key of the node to be added
     * @param data the data of the node to be added
     */
    public void insert(int key, T data) {
        root = insertRec(root, new TreapNode<>(key, data));
    }

    /**
     * Recursively inserts a node in accordance to the BST constraint of the heap. Once satisfied, rotations are performed
     * to conform to the heap priority for the node.
     * @param current the current node in the recursion
     * @param newNode the node that will be added
     * @return the new root of the (sub)tree
     */
    private TreapNode<T> insertRec(TreapNode<T> current, TreapNode<T> newNode) {
        // Make the new node the root of the tree if there isn't one
        if (current == null) return newNode;

        // If the new node's key is less than the current node's key, call insert on the left child of the current node.
        // If the key is greater than the current node's key, call insert on the right child.
        if (newNode.key <= current.key)
            current.left = insertRec(current.left, newNode);
        else
            current.right = insertRec(current.right, newNode);

        // Perform rotations on left and/or right side if heap priority is violated (which happens when the priority of the child is greater than its parent)
        if (current.left != null && current.left.priority > current.priority)
            current = LL_Rotate(current);
        if (current.right != null && current.right.priority > current.priority)
            current = RR_Rotate(current);

        return current;
    }

    private void insertHash(HashMap<Integer, T> hash){
        hash.forEach(this::insert);
    }

    /**
     * Recursively searches for a key using the BST search algorithm
     * @param key the key to search for
     * @return if the treap contains the key
     */
    public boolean containsKey(int key) {
        TreapNode<T> currentNode = root;
        while (currentNode != null && currentNode.key != key) {
            currentNode = (key < currentNode.key) ? currentNode.left : currentNode.right;
        }
        return currentNode != null;
    }

    /**
     * Checks every node in the treap to see if it contains the data
     * @param data the data to check for
     * @return if the treap contains the data
     */
    public boolean containsData(T data) {
        return containsDataRec(data, root);
    }

    /**
     * Recursively checks all nodes in the tree for the specified data
     * @param data the data to search for
     * @param current the current node in the tree
     * @return if the data is present
     */
    private boolean containsDataRec(T data, TreapNode<T> current) {
        if (current == null) {
            return false;
        } else if (current.data.equals(data)) {
            return true;
        } else {
            return containsDataRec(data, current.left) || containsDataRec(data, current.right);
        }
    }

    /**
     * Gets the data corresponding to the specified key. Returns null if key is not present.
     * @param key the key for the data to get
     * @return the data associated with the key
     */
    public T get(int key) {
        return getRec(key, root);
    }

    /**
     * Recursively traverses the tree using the BST search algorithm
     * @param key the key for the data to retrieve
     * @param current the current node in treap
     * @return the retrieved data
     */
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

    /**
     * Deletes the node with the specified key
     * @param key the key of the node to delete
     */
    public void delete(int key) {
        root = deleteRec(key, root);
    }

    /**
     * Recursively deletes the node with the specified key and performs the necessary rotations of the tree to maintain
     * the BST structure and heap priority.
     * @param key the key of the node to delete
     * @param current the current node in the tree
     * @return the new root of the (sub)tree
     */
    private TreapNode<T> deleteRec(int key, TreapNode<T> current) {
        if (current == null) {
            return null;
        } else if (key < current.key) {
            current.left = deleteRec(key, current.left);
            return current;
        } else if (key > current.key) {
            current.right = deleteRec(key, current.right);
            return current;
        } else { // If the current node is the node to be deleted
            if (current.left != null && current.right != null) { // If current has two children, perform rotations to fix heap priorities
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
            } else if (current.left != null) { // If current has a left child
                return current.left;
            } else if (current.right != null) { // If current has a right child
                return current.right;
            } else { // If current is a leaf
                return null;
            }
        }
    }

    /**
     * Splits treap into two where all the nodes less than the key are in one treap, and all nodes greater than or equal
     * to the key are in another
     * @param key the key to split the treap at
     * @return A list containing the two treaps
     */
    public List<Treap<T>> splitTree(int key) {
        // Insert a node with max priority to rearrange the tree so that all values to the left of the node are less
        // than the key and all values to the right are greater than or equal
        root = insertRec(root, new TreapNode<>(key, Integer.MAX_VALUE, null));

        Treap<T> leftTreap = new Treap<>();
        leftTreap.root = root.left;
        Treap<T> rightTreap = new Treap<>();
        rightTreap.root = root.right;
        return Arrays.asList(leftTreap, rightTreap);
    }

    //         A                                      B
    //        / \                                   /   \
    //       B   Ar         LL_Rotate (A)          C      A
    //      / \          - - - - - - - - ->      /  \    /  \
    //     C   Br                               Cl  Cr  Br  Ar
    //    / \
    //  Cl   Cr
    /**
     * Rotates the subtree of the specified node so that the root node's left node becomes the root
     * @param A the parent of the subtree to rotate left
     * @return the root of the rotated tree
     */
    private TreapNode<T> LL_Rotate(TreapNode<T> A) {
        TreapNode<T> B = A.left;
        TreapNode<T> Br = B.right;

        A.left = Br;
        B.right = A;

        return B;
    }

    //   A                                B
    //  /  \                            /   \
    // Al   B       RR_Rotate(A)       A      C
    //     /  \   - - - - - - - ->    / \    / \
    //    Bl   C                     Al  Bl Cl  Cr
    //        / \
    //      Cl  Cr
    /**
     * Rotates the subtree of the specified node so that the root node's right node becomes the root
     * @param A the parent of the subtree to rotate left
     * @return the root of the rotated tree
     */
    private TreapNode<T> RR_Rotate(TreapNode<T> A) {
        TreapNode<T> B = A.right;
        TreapNode<T> Bl = B.left;

        A.right = Bl;
        B.left = A;

        return B;
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

        // Hash Insertion
        Treap<Integer> treapMap = new Treap<>();
        HashMap<Integer, Integer> hash =  new HashMap<>();
        hash.put(15, 1);
        hash.put(24, 2);
        hash.put(45, 3);
        hash.put(7, 4);
        hash.put(26, 5);
        treapMap.insertHash(hash);
        System.out.println("\n\nTreapMap (15,24,45,7,26)");
        TreePrinter.printNode(treapMap.root);


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
        System.out.println("Treap less than 10:");
        TreePrinter.printNode(treapParts.get(0).root);
        System.out.println("Treap greater than 10:");
        TreePrinter.printNode(treapParts.get(1).root);
    }
}