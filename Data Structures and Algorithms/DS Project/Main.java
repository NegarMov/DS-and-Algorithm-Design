import java.io.*;
import java.util.*;

class Node {
    Node next;
    Node prev;
    char c;

    Node (char c) {
        this.c = c;
    }

    @Override
    public int hashCode() {
        if (next != null)
            return 31 * next.hashCode() + c;
        else
            return 7;
    }
}

public class Main {
    private static void print(Node cursor, Node expr) throws IOException {
        Node it = expr;
        OutputStream out = new BufferedOutputStream(System.out);
        if (cursor == null)
           out.write(("|").getBytes());
        while (it != null) {
            out.write((it.c + "").getBytes());
            if (it == cursor)
                out.write(("|").getBytes());
            it = it.next;
        }
        out.write(("\n").getBytes());
        out.flush();
    }

    private static LinkedList<String> postFix(Node expr) {
        HashMap<Character, Integer> prec = new HashMap<>(4);
        prec.put('*', 3); prec.put('+', 2); prec.put('-', 2); prec.put('(', 1);
        String num = "0123456789";
        LinkedList<String> res = new LinkedList<>();
        LinkedList<Character> opStack = new LinkedList<>();
        boolean numFlag = true;
        Node cn = expr;
        while (cn != null) {
            char c = cn.c;
            if (num.contains(c + "")) {
                if (numFlag)
                    res.add((!res.isEmpty())? res.removeLast() + c : c + "");
                else
                    res.add(c + "");
                numFlag = true;
            }
            else if (c == '(') {
                numFlag = false;
                opStack.push(c);
            }
            else if (c == ')') {
                numFlag = false;
                char top = opStack.pop();
                while (top != '(') {
                    res.add(top + "");
                    top = opStack.pop();
                }
            }
            else {
                numFlag = false;
                while (!opStack.isEmpty() && prec.get(opStack.peek()) >= prec.get(c))
                    res.add(opStack.pop() + "");
                opStack.push(c);
            }
            cn = cn.next;
        }
        while (!opStack.isEmpty())
            res.add(opStack.pop() + "");
        return res;
    }

    private static long evaluate(LinkedList<String> expr) {
        String num = "0123456789";
        long lim = (long) Math.pow(10, 9) + 7;
        LinkedList<Long> opStack = new LinkedList<>();
        for (String c : expr) {
            if (num.contains(c.charAt(0) + "") || (c.length() > 1 && num.contains(c.charAt(1) + "")))
                opStack.push(Long.parseLong(c));
            else {
                long op1 = opStack.pop(), op2 = opStack.pop();
                long temp = (c.equals("*"))? ((op1 * op2) % lim) : (c.equals("+"))? ((op1 + op2) % lim) : ((op2 - op1) % lim);
                opStack.push((temp < 0)? temp + lim : temp);
            }
        }
        return opStack.pop();
    }

    public static void main(String[] args) throws IOException {

        Scanner scn = new Scanner(System.in);
        Node cursor = null;
        int n = scn.nextInt();
        String exprStr = scn.next();
        Node expr = new Node(exprStr.charAt(0));
        Node cn = expr;
        for (int i = 1; i < exprStr.length(); i++) {
            cn.next = new Node(exprStr.charAt(i));
            cn.next.prev = cn;
            cn = cn.next;
        }

        Hashtable<Node, Long> ht = new Hashtable<>(41);
        for (int q = 0; q < n; q++) {
            char op = scn.next().charAt(0);
            switch (op) {
                case '>' :
                    cursor = (cursor == null)? expr : (cursor.next == null)? cursor : cursor.next;
                    break;
                case '<' :
                    cursor = (cursor == null)? null : cursor.prev;
                    break;
                case '?' :
                    print(cursor, expr);
                    break;
                case '-' :
                    if (cursor != null) {
                        if (cursor == expr)
                            expr = cursor.next;
                        else
                            cursor.prev.next = cursor.next;
                        if (cursor.next != null)
                            cursor.next.prev = cursor.prev;
                        cursor = cursor.prev;
                    }
                    break;
                case '+' :
                    char c = scn.next().charAt(0);
                    Node newNode = new Node(c);
                    if (cursor != null) {
                        if (cursor.next != null)
                            cursor.next.prev = newNode;
                        newNode.next = cursor.next;
                        cursor.next = newNode;
                        newNode.prev = cursor;
                        cursor = newNode;
                    }
                    else {
                        cursor = newNode;
                        newNode.next = expr;
                        expr.prev = newNode;
                        expr = newNode;
                    }
                    break;
                case '!' :
                    if (ht.containsKey(expr))
                        System.out.println(ht.get(expr));
                    else {
                        long res = evaluate(postFix(expr));
                        System.out.println(res);
                        ht.put(expr, res);
                    }
                    break;
            }
        }
    }
}
