import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt() + 1;
        int[] h = new int[n];
        for (int i=1; i<n; i++)
            h[i] = scn.nextInt();

        for (int i=1; i<n; i++) {
            if (h[i] == -1) {
                if (i != 1) {
                    h[i] = h[i / 2] - 1;
                    if (h[i] < 1) {
                        System.out.println(-1);
                        return;
                    }
                }
                else {
                    if (n > 3)
                        h[1] = Math.max(h[2], h[3]) + 1;
                    else if (n > 2)
                        h[1] = h[2] + 1;
                    h[1] = (int) Math.max(Math.pow(10, 9), h[1]);
                    if (h[1] > Math.pow(10, 9)) {
                        System.out.println(-1);
                        return;
                    }
                }
            }
        }

        for (int i=1; i<n; i++) {
            if ((i * 2  < n && h[i] <= h[i * 2]) || (i * 2 + 1 < n && h[i] <= h[i * 2 + 1])) {
                System.out.println(-1);
                return;
            }
        }

        for (int i=1; i<n; i++)
            System.out.print(h[i] + " ");
    }
}
