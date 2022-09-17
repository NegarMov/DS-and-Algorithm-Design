import java.util.LinkedList;
import java.util.Scanner;

class MaxHeap {
    int[] heap;
    int size;

    public MaxHeap(int size) {
        heap = new int[size + 1];
        this.size = 0;
    }

    private void maxHeapify(int i) {
        int largest;
        if (2*i <= size && heap[2*i] > heap[i])
            largest = 2*i;
        else
            largest = i;
        if (2*i + 1 <= size && heap[2*i + 1] > heap[largest])
            largest = 2*i + 1;

        if (largest != i) {
            int temp = heap[largest];
            heap[largest] = heap[i];
            heap[i] = temp;
            maxHeapify(largest);
        }
    }

    private void increaseKey(int i, int k) {
        if (k > heap[i]) {
            heap[i] = k;
            while (i > 1 && heap[i/2] < heap[i]) {
                int temp = heap[i];
                heap[i] = heap[i/2];
                heap[i/2] = temp;
                i = i/2;
            }
        }
    }

    public void insert(int k) {
        heap[size+1] = -1;
        increaseKey(size + 1, k);
        size++;
    }

    public void delete(int k) {
        int s = -1;
        for (int i = 1; i <= size; i++)
            if (heap[i] == k) {
                s = i;
                break;
            }
        heap[s] = heap[size];
        heap[size] = 0;
        maxHeapify(s);
    }

    public int max() {
        return (size > 0)? heap[1] : 0;
    }

}

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt(), m = scn.nextInt();
        LinkedList<Integer>[] l = new LinkedList[m];
        LinkedList<Integer>[] r = new LinkedList[m];

        for (int i=0; i<m; i++) {
            l[i] = new LinkedList<>();
            r[i] = new LinkedList<>();
        }

        for (int i=0; i<n; i++) {
            int li = scn.nextInt() - 1, ri = scn.nextInt(), c = scn.nextInt();
            l[li].add(c);
            if (ri < m)
                r[ri].add(c);
        }

        MaxHeap heap = new MaxHeap(n);
        for (int i=0; i<m; i++) {
            for (int li : l[i])
                heap.insert(li);
            if (r[i] != null)
                for (int ri : r[i])
                    heap.delete(ri);
            System.out.print(heap.max() + " ");
        }
    }
}
