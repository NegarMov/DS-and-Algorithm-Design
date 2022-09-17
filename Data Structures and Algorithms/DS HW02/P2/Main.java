import java.util.Scanner;

class Node {
    int value;
    int times;
    Node next;

    Node(int value, int times) {
        this.value = value;
        this.times = times;
        next = null;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), t, d;
        Node head = null, tail = null;

        for (int i = 0; i < n; i++) {
            switch (scn.next()) {
                case "+":
                    d = scn.nextInt();
                    t = scn.nextInt();
                    Node newNode = new Node(d, t);
                    if (head == null)
                        tail = head = newNode;
                    else {
                        tail.next = newNode;
                        tail = newNode;
                    }
                    break;
                case ("-"):
                    t = scn.nextInt();
                    if (head != null) {
                        Node it = head;
                        while (t > 0 && it != null) {
                            int temp = t;
                            t -= it.times;
                            it.times -= temp;
                            if (it.times <= 0) {
                                head = it.next;
                                it = head;
                            }
                        }
                    }
                    break;
                default:
                    System.out.println((head == null) ? "empty" : head.value);
            }
        }
    }
}