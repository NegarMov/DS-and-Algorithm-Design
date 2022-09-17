import java.util.LinkedList;
import java.util.Scanner;

class Edge {
    int index;
    int value;

    public Edge(int index, int value) {
        this.index = index;
        this.value = value;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt();
        LinkedList<Edge>[] v = new LinkedList[n+1];
        boolean[] visited = new boolean[n+1];
        for (int i=1; i<n+1; i++)
            v[i] = new LinkedList<>();

        for (int i=0; i<m; i++) {
            int a = scn.nextInt(), b = scn.nextInt();
            v[a].add(new Edge(i+1, b));
            v[b].add(new Edge(i+1, a));
        }

        LinkedList q = new LinkedList<Integer>();
        LinkedList<Integer> chosen = new LinkedList<>();
        q.add(1);
        visited[1] = true;
        while (!q.isEmpty()) {
            int u = (int) q.poll();
            for (Edge adj : v[u]) {
                if (!visited[adj.value]) {
                    chosen.add(adj.index);
                    visited[adj.value] = true;
                    q.add(adj.value);
                }
            }
        }

        System.out.println(chosen.size());
        for (int i : chosen)
            System.out.print(i + " ");
    }
}