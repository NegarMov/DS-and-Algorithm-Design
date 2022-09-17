import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt();
        LinkedList<Integer>[] g = new LinkedList[n];
        PriorityQueue<Integer> q = new PriorityQueue<>(n);
        int[] key = new int[n];
        for (int i=0; i<n; i++) { //initializing values
            key[i] = Integer.MAX_VALUE;
            g[i] = new LinkedList<>();
        }

        for (int i=0; i<n; i++) { //forming the graph
            int cnt = scn.nextInt();
            for (int j=0; j<cnt; j++) {
                int adj = scn.nextInt() - 1;
                g[i].add(adj);
            }
        }

        if (n < m) {
            System.out.println(-1);
            return;
        }

        // running Prim's algorithm
        int count = 0;
        boolean[] visited = new boolean[n];
        long sum = 0;
        key[0] = 0;
        q.add(0);
        for (int i=0; i<n; i++) {
            Integer u = q.poll();
            count++;
            if (u == null && count <= m) {
                System.out.println(-1);
                return;
            }
            else {
                visited[u] = true;
                sum += u + 1;
                for (int j : g[u])
                    if (!visited[j] && j < key[j]) {
                        key[j] = j;
                        q.add(j);
                    }
                if (count == m) {
                    System.out.println(sum);
                    return;
                }
            }
        }
    }
}
