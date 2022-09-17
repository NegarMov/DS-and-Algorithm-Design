import java.util.Scanner;

class Node {
    int index;
    int value;
    Node right;
    Node left;

    Node (int index) {
        this.index = index;
    }
}

public class Main {

    private static int order = 1;

    private static void inorderTreeWalk(Node x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            x.value = order++;
            inorderTreeWalk(x.right);
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), r, l, index;
        Node[] nodes = new Node[n];
        for (int i=0; i<n; i++)
            nodes[i] = new Node(i+1);
        Node root = null;
        int[] findRoot = new int[n];
        for (int i=0; i<n; i++)
            findRoot[i] = 1;

        for (int i=0; i<n; i++) { //Read input and form the tree
            index = scn.nextInt();
            l = scn.nextInt();
            r = scn.nextInt();

            if (r != -1)
                findRoot[r-1] = 0;
            if (l != -1)
                findRoot[l-1] = 0;

            if (l != -1)
                nodes[index-1].left = nodes[l-1];
            if (r != -1)
                nodes[index-1].right = nodes[r-1];
        }

        for (int i=0; i<n; i++) //Find the root
            if (findRoot[i] == 1) {
                root = nodes[i];
                break;
            }

        inorderTreeWalk(root); //Assign nodes' values

        for (int i=0; i<n; i++) //Print values
            System.out.print(nodes[i].value + " ");
    }
}
