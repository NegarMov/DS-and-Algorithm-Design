import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt();
        int[][] t = new int[n][m];

        int vCount = 0;
        LinkedList<Integer>[] v = new LinkedList[m*n];
        for (int i=0; i<n*m; i++)
            v[i] = new LinkedList<>();
        for (int i=0; i<n; i++) {
            String c = scn.next();
            for (int j = 0; j < m; j++) {
                if (c.charAt(j) == '#')
                    t[i][j] = -1;
                else {
                    t[i][j] = vCount++;
                    if (i-1 >= 0 && t[i-1][j] != -1) {
                        v[t[i][j]].add(t[i - 1][j]);
                        v[t[i - 1][j]].add(t[i][j]);
                    }
                    if (j-1 >= 0 && t[i][j-1] != -1) {
                        v[t[i][j]].add(t[i][j - 1]);
                        v[t[i][j - 1]].add(t[i][j]);
                    }
                }
            }
        }

        boolean[] visited = new boolean[vCount];
        int[] group = new int[vCount];
        int gCount = 0;
        for (int i=0; i<vCount; i++) {
            if (!visited[i]) {
                LinkedList q = new LinkedList<Integer>();
                q.add(i);
                visited[i] = true;
                group[i] = gCount;
                while (!q.isEmpty()) {
                    int u = (int) q.poll();
                    for (int adj : v[u]) {
                        int a = adj;
                        if (!visited[a]) {
                            visited[a] = true;
                            group[a] = gCount;
                            q.add(a);
                        }
                    }
                }
            }
            gCount++;
        }

        int q = scn.nextInt();
        for (int i=0; i<q; i++) {
            int r1 = scn.nextInt()-1, c1 = scn.nextInt()-1, r2 = scn.nextInt()-1, c2 = scn.nextInt()-1;
            if (group[t[r1][c1]] == group[t[r2][c2]])
                System.out.println("YES");
            else
                System.out.println("NO");
        }

    }
}