import java.util.*;

class Node {
    String desc;
    int height;
    int maxHeight;
    String topColor;
    Node parent;
    ArrayList<Node> children;

    Node(String topColor, int height, int maxHeight, String desc, Node parent) {
        this.topColor = topColor;
        this.height = height;
        this.maxHeight = maxHeight;
        this.desc = desc;
        this.parent = parent;
    }

    void addChild(Node child) {
        if (this.children == null)
            this.children = new ArrayList<>();
        this.children.add(child);
    }
}

class Cube implements Comparable<Cube> {
    int weight;
    String[] colors;
    int index;

    Cube(int weight, String[] colors, int index) {
        this.weight = weight;
        this.colors = colors;
        this.index = index;
    }

    HashSet<String> getTopColors(String color) {
        HashSet<String> topColors = new HashSet<>();
        for (int i=0; i<6; i++) {
            if (color == null) // the bottom color doesn't matter
                topColors.add(this.colors[i]);
            else if (color.equals(this.colors[i])) {
                int topIndex = (i % 2 == 0)? i+1 : i-1;
                topColors.add(this.colors[topIndex]);
            }
        }
        return topColors;
    }

    @Override
    public int compareTo(Cube c) {
        return Integer.compare(this.weight, c.weight);
    }
}

public class Main {

    static int n; // number of cubes
    static Cube[] cubes; // list of all the cubes
    static int maxHeight = 0; // the maximum height reached so far
    static int count = 0; // the total number of answers

    static void buildTree(Node current, int i) {
        if (i == n || current.maxHeight < maxHeight) // bound the branch
            return;
        // possible ways to choose the ith cube
        HashSet<String> topColors = cubes[i].getTopColors(current.topColor);
        for (String topColor : topColors) {
            String desc = String.format("cube %d | top color: %s", cubes[i].index, topColor);
            current.addChild(new Node(topColor, current.height + 1, current.maxHeight, desc, current));
        }
        // the ith cube is not included
        current.addChild(new Node(current.topColor, current.height, current.maxHeight - 1, null, current));
        // do the same thing for its children
        for (Node child : current.children) {
            maxHeight = Math.max(maxHeight, child.height);
            buildTree(child, i + 1);
        }
    }

    static void printMaxPath(Node current) {
        if (current.children == null) // current node is a leaf
            return;
        for (Node child : current.children) {
            if (child.children == null && child.height == maxHeight) { // child node is a leaf and a solution
                count++;
                System.out.println("\nSolution number " + count + ":");
                Node c = child;
                while (c.parent != null) { // iterate to reach the root via this child
                    if (c.desc != null)
                        System.out.println("    " + c.desc);
                    c = c.parent;
                }
            }
            printMaxPath(child);
        }
    }

    public static void main(String[] args) {
        // get inputs
        Scanner scn = new Scanner(System.in);
        System.out.print("Number of cubes: ");
        n = scn.nextInt();
        cubes = new Cube[n];
        for (int i=0; i<n; i++) {
            System.out.printf("The weight of the cube number %d: ", i + 1);
            int w = scn.nextInt();
            System.out.printf("The colors of the cube number %d: ", i + 1);
            String[] c = new String[6];
            for (int j=0; j<6; j++)
                c[j] = scn.next();
            cubes[i] = new Cube(w, c, i+1);
        }

        // sort the cubes according to their wrights
        Arrays.sort(cubes, Collections.reverseOrder());

        // build the tree
        Node root = new Node(null, 0, n, "root node", null);
        buildTree(root, 0);

        // print the maximum height and print the solutions to get it
        System.out.printf("\nMAXIMUM HEIGHT: %d\n", maxHeight);
        printMaxPath(root);
    }
}
