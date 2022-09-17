import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt(), q = scn.nextInt();
        int[][] sp;
        int[][] w = new int[n][n];
        boolean[] allowed = new boolean[n];

        for (int i=0; i<n; i++) // forming the adjacency matrix
            for (int j=0; j<n; j++)
                if (i == j)
                    w[i][j] = 0;
                else
                    w[i][j] = Integer.MAX_VALUE;
        for (int i=0; i<m; i++) {
            int u = scn.nextInt() - 1, v = scn.nextInt() - 1, wi = scn.nextInt();
            w[u][v] = w[v][u] = wi;
        }

        sp = w;

        for (int i=0; i<q; i++) {
            char c = scn.next().charAt(0);
            if (c == '?') {
                int u = scn.nextInt() - 1, v = scn.nextInt() - 1;
                if (allowed[u] && allowed[v])
                    System.out.println((sp[u][v] == Integer.MAX_VALUE)? -1 : sp[u][v]);
                else
                    System.out.println(-1);
            }
            else {
                int u = scn.nextInt() - 1;
                allowed[u] = true;
                for (int j=0; j<n; j++)
                    for (int k=0; k<n; k++)
                        if (sp[j][u] != Integer.MAX_VALUE && sp[u][k] != Integer.MAX_VALUE)
                            sp[j][k] = sp[k][j] = Math.min(sp[j][k], sp[j][u] + sp[u][k]);
            }
        }
    }
}