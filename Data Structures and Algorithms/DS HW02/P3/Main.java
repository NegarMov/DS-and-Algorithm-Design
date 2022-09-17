import java.util.Scanner;

class Box {
    Box next;
    Box tail;
    long value;

    Box(long value) {
        this.value = value;
        next = null;
        tail = this;
    }

    void print() {
        Box boxD = this;
        while (boxD != null) {
            System.out.print(boxD.value + " ");
            boxD = boxD.next;
        }
    }

    long size() {
        long i = 0;
        Box boxD = this;
        while (boxD != null) {
            boxD = boxD.next;
            i++;
        }
        return i;
    }
}

public class Main {

    public static void main(String[] args) {
	    Scanner scn = new Scanner(System.in);
	    int n = scn.nextInt(), m = scn.nextInt();
	    Box[] p = new Box[n];

        for (int i=0; i<n; i++)
            p[i] = new Box(i+1);

	    for (int i=0; i<m; i++) {
            int r = scn.nextInt()-1, t = scn.nextInt()-1;
	        if (p[r] != null) {
                if (p[t] != null)
                    p[t].tail.next = p[r];
                else
                    p[t] = p[r];
                p[t].tail = p[r].tail;
                p[r] = null;
            }
        }

	    int d = scn.nextInt()-1;

	    if (p[d] != null) {
            System.out.print(p[d].size() + " ");
            p[d].print();
        }
	    else
            System.out.println("0");
    }
}