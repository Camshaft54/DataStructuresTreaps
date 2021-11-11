public class Treap {
    TreapNode root;

    public Treap() {
        root = null;
    }

    public void insert(int key) {
        root = insertRec(root, new TreapNode(key));
    }

    public TreapNode insertRec(TreapNode current, TreapNode newNode) {
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

    public void delete(int key) {
        root = deleteRec(key, root);
    }

    public TreapNode deleteRec(int key, TreapNode current) {
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
                TreapNode newRoot;
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

    public Treap[] splitTree(int key) {
        root = insertRec(root, new TreapNode(key, Integer.MAX_VALUE));
        Treap leftTreap = new Treap();
        leftTreap.root = root.left;
        Treap rightTreap = new Treap();
        rightTreap.root = root.right;
        return new Treap[]{leftTreap, rightTreap};
    }

    public static void main(String[] args) {
        Treap treap1 = new Treap();
        treap1.insert(10);
        treap1.insert(20);
        treap1.insert(5);
        treap1.insert(1);
        System.out.println("Inserted 1, 5, 10, 20:");
        TreePrinter.printNode(treap1.root);

        treap1.delete(5);
        System.out.println("Deleted 5:");
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

        Treap treap2 = new Treap();
        treap2.insert(3);
        treap2.insert(16);
        treap2.insert(87);
        treap2.insert(9);
        treap2.insert(10);
        treap2.insert(1);
        treap2.insert(48);
        treap2.insert(92);
        System.out.println("\n\nTreap 2 (1,3,9,10,16,48,87,97):");
        TreePrinter.printNode(treap2.root);
        Treap[] treapParts = treap2.splitTree(10);
        System.out.println("Treap 2 after split at 10:");
        TreePrinter.printNode(treap2.root);
        System.out.println("Treap less than 10:");
        TreePrinter.printNode(treapParts[0].root);
        System.out.println("Treap greater than 10:");
        TreePrinter.printNode(treapParts[1].root);
    }

    //         A                                      B
    //        / \                                   /   \
    //       B   Ar         LL_Rotate (A)          C      A
    //      / \          - - - - - - - - ->      /  \    /  \
    //     C   Br                               Cl  Cr  Br  Ar
    //    / \
    //  Cl   Cr
    public static TreapNode LL_Rotate(TreapNode A) {
        TreapNode B = A.left;
        TreapNode Br = B.right;

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
    public static TreapNode RR_Rotate(TreapNode A) {
        TreapNode B = A.right;
        TreapNode Bl = B.left;

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