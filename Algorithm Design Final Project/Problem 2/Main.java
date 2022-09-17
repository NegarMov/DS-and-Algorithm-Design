import java.util.Scanner;

public class Main {

    static int n; // size of the board
    static int m; // number of obstacles
    static int count = 0; // number of total answers
    static int[] p; // the position of the queens, where i = p / n, j = p % n
    static int[] o; // the position of the obstacles, where i = o / m, j = o % m

    /**
     * Prints the solution we have found so far
     */
    static void displayAnswer() {
        System.out.println("_____________________________________\n");
        for (int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                boolean empty = true;
                for (int queen : p)
                    if (i * n + j == queen) {
                        System.out.print("\u265B  ");
                        empty = false;
                        break;
                    }
                for (int obstacle : o)
                    if (i * n + j == obstacle) {
                        System.out.print("\u25A0\u200A  ");
                        empty = false;
                        break;
                    }
                if (empty)
                    System.out.print("\u25A1\u200A  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if the ith and the kth queen threaten each other or not
     */
    static boolean isThreatened(int i, int k) {
        for (int value : o)
            if (p[i] == value)
                return true;

        int xi = p[i] / n, yi = p[i] % n;
        int xk = p[k] / n, yk = p[k] % n;
        boolean isSafe = true;
        if (xi == xk) { // they are on the same row
            isSafe = false;
            for (int obstacle : o) { // is there any obstacle to block the attack?
                int xo = obstacle / n, yo = obstacle % n;
                if (xo == xi && (yo > Math.min(yi, yk) && yo < Math.max(yi, yk))) {
                    isSafe = true;
                    break;
                }
            }
        }
        if (yi == yk) { // they are on the same column
            isSafe = false;
            for (int obstacle : o) { // is there any obstacle to block the attack?
                int xo = obstacle / n, yo = obstacle % n;
                if (yo == yi && (xo > Math.min(xi, xk) && xo < Math.max(xi, xk))) {
                    isSafe = true;
                    break;
                }
            }
        }
        if (Math.abs(xi - xk) == Math.abs(yi - yk)) { // they are on the same diagonal
            isSafe = false;
            for (int obstacle : o) { // is there any obstacle to block the attack?
                int xo = obstacle / n, yo = obstacle % n;
                if (Math.abs(xi - xo) == Math.abs(yi - yo) &&
                        (xo > Math.min(xi, xk) && xo < Math.max(xi, xk)) &&
                        (yo > Math.min(yi, yk) && yo < Math.max(yi, yk))) {
                    isSafe = true;
                    break;
                }
            }
        }
        return !isSafe;
    }

    /**
     * If all the possible positions are checked for the ith queen, goes back
     * one step and checks the (i-1)th queen
     */
    static int backtrack(int i) {
        while (p[i] >= n * n) {
            if (i == 0) { // we have checked all the possible positions
                System.out.println("Total number of answers: " + count);
                System.exit(0);
            }
            p[i] = 0;
            p[--i]++;
        }
        return i;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Size of the board: ");
        n = scn.nextInt();
        System.out.print("Number of obstacles: ");
        m = scn.nextInt();
        p = new int[n];
        o = new int[m];
        for (int i=0; i<m; i++) {
            System.out.printf("Position of the obstacle number %d: ", i + 1);
            o[i] = (scn.nextInt() - 1) * n + (scn.nextInt() - 1);
        }

        boolean initiate = true;
        for (int i=0; i<n; i++) {
            if (initiate)
                p[i] = i == 0 ? 0 : p[i - 1] + 1;
            initiate = true;

            i = backtrack(i);
            for (int k=0; k<i; k++) {
                if (isThreatened(i, k)) {
                    p[i]++;
                    k = -1;
                }
                i = backtrack(i);
            }

            if (i == n-1) {
                boolean valid = true;
                for (int obstacle : o) // if any of the queens is placed on an obstacle, the answer is not valid
                    for (int queen : p)
                        if (obstacle == queen) {
                            valid = false;
                            break;
                        }
                if (valid) {
                    count++;
                    displayAnswer();
                }
                p[i--]++; // continue incrementing the position of the last queen
                initiate = false;
            }
        }
    }
}
