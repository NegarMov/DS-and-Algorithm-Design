import java.util.LinkedList;
import java.util.Scanner;

class Pair {
    int f, s;

    Pair(int f, int s) {
        this.f = f;
        this.s = s;
    }
}

public class Main {

    private static int findMin(int[] key, boolean[] selected) {
        int min = Integer.MAX_VALUE, index = -1;
        for (int i=0; i<key.length; i++)
            if (!selected[i] && key[i] < min) {
                min = key[i];
                index = i;
            }
        selected[index] = true;
        return index;
    }

    private static void traverse(int i, int[] gp, LinkedList<Integer>[] ch) {
        gp[i] = 1;
        if (ch[i] != null)
            for (int child : ch[i])
                traverse(child, gp, ch);
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt();
        int[][] w = new int[n][n];
        int[] wi = new int[m];
        int[][] index = new int[n][n];
        Pair[] gc = new Pair[m];

        for (int i=0; i<n; i++) // forming adjacency matrix
            for (int j=0; j<n; j++)
                w[i][j] = Integer.MAX_VALUE;
        for (int i=0; i<m; i++) {
            int u = scn.nextInt() - 1, v = scn.nextInt() - 1;
            w[u][v] = w[v][u] = scn.nextInt();
            wi[i] = w[v][u];
            gc[i] = new Pair(u, v);
            index[u][v] = index[v][u] = i;
        }

        boolean[] selected = new boolean[n]; // finding MST using Prim
        int[] key = new int[n];
        int[] p = new int[n];
        boolean[] inMST = new boolean[m];
        for (int i = 0; i < n; i++) {
            selected[i] = false;
            key[i] = Integer.MAX_VALUE;
            p[i] = -1;
        }
        key[0] = 0;
        for (int i = 0; i < n; i++) {
            int u = findMin(key, selected);
            if (i != 0) {
                inMST[index[u][p[u]]] = true;
            }
            for (int j = 0; j < n; j++)
                if (!selected[j] && w[u][j] < key[j]) {
                    key[j] = w[u][j];
                    p[j] = u;
                }
        }

        LinkedList<Integer>[] ch = new LinkedList[n]; // determining each vertex's children
        for (int i=0; i<n; i++)
            if (p[i] != -1) {
                if (ch[p[i]] == null)
                    ch[p[i]] = new LinkedList<>();
                ch[p[i]].add(i);
            }

        int[] gp = new int[n];
        for (int i=0; i<m; i++) { // printing the result
            if (inMST[i]) {
                for (int j=0; j<n; j++)
                    gp[j] = 0;
                int chi = (gc[i].f == p[gc[i].s])? gc[i].s : gc[i].f;
                traverse(chi, gp, ch);
                boolean flag = true;
                for (int j=0; j< m; j++)
                    if (!inMST[j] && wi[j] == wi[i] && gp[gc[j].f] != gp[gc[j].s])
                        flag = false;
                if (flag) {
                    System.out.println(i + 1);
                    return;
                }
            }
        }
        System.out.println(-1);
    }
}