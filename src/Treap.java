public class Treap {
    TreapNode root;

    public Treap() {
        root = null;
    }

    public void insert(int key) {
        root = insertRec(key, root);
    }

    public TreapNode insertRec(int key, TreapNode current) {
        if (current == null) {
            return new TreapNode(key, null);
        } else if (key < current.key) {
            if (current.left == null) {
                current.left = new TreapNode(key, current);
            } else {
                current.left = insertRec(key, current.left);
            }
            if (current.priority < current.left.priority) {
                current = LL_Rotate(current);
            }
        } else if (key > current.key) {
            if (current.right == null) {
                current.right = new TreapNode(key, current);
            } else {
                current.right = insertRec(key, current.right);
            }
            if (current.priority < current.right.priority) {
                current = RR_Rotate(current);
            }
        }
        return current;
    }

//    public TreapNode deleteRec(int key, TreapNode current) {
//        if (current == null) {
//            return null;
//        } else if (key < current.key) {
//            deleteRec(key, current.left);
//        } else if (key > current.key) {
//            deleteRec(key, current.right);
//        } else { // If key == current.key
//            if (current.left == null && current.right == null) { // If current is a leaf
//                if (current.parent.left == current) {
//                    current.parent.left = null;
//                } else {
//                    current.parent.right = null;
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        Treap treap1 = new Treap();
        treap1.insert(10);
        treap1.insert(1);
        treap1.insert(5);
        treap1.insert(20);
//        TreePrinter.printNode(treap1.root);
    }

    // TODO: Fix Left rotate and right rotate to reassign parent value
    // We will use this image to guide our naming conventions
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
        System.out.println("Left Rotation: A: " + A + " B: " + B);

        // Reorder children
        A.left = Br;
        B.right = A;

        // Reassign parents
        B.parent = A.parent;
        A.parent = B;
        if (Br != null)
            Br.parent = A;

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

        // Reassign parents
        B.parent = A.parent;
        A.parent = B;
        if (Bl != null)
            Bl.parent = A;

        // Return new root
        return B;
    }
}