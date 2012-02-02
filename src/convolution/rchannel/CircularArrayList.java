package convolution.rchannel;
 
import java.util.*;
 
/**
 * If you use this code, please retain this comment block.
 * @author Isak du Preez
 * isak at du-preez dot com
 * www.du-preez.com
 */
public class CircularArrayList {
 
    private final int n; // buffer length
    private final long[] buf; // a List implementing RandomAccess
    private int head = 0;
    private int tail = 0;
 
    public CircularArrayList(int capacity) {
        n = capacity + 1;
        buf = new long[ n ];
    }
 
    public int capacity() {
        return n - 1;
    }
 
    private int wrapIndex(int i) {
        if (i>=n) { // java modulus can be negative
            return i-n;
        }
        return i;
    }
 
 /*   private int wrapIndex(int i) {
        int m = i;
        if (m >= n) {
            m = m-n;
        }
        return m;
    }
 */
    // This method is O(n) but will never be called if the
    // CircularArrayList is used in its typical/intended role.
    private void shiftBlock(int startIndex, int endIndex) {
        assert (endIndex > startIndex);
        for (int i = endIndex - 1; i >= startIndex; i--) {
            set(i + 1, get(i));
        }
    }
 
    public int size() {
        return tail - head + (tail < head ? n : 0);
    }
 
    public long get(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return buf[wrapIndex(head + i)];
    }
 
    public long set(int i, long e) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return buf[wrapIndex(head + i)] = e;
    }
 
    public void add(int i, long e) {
        int s = size();
        if (s == n - 1) {
            throw new IllegalStateException("Cannot add element."
                    + " CircularArrayList is filled to capacity.");
        }
        if (i < 0 || i > s) {
            throw new IndexOutOfBoundsException();
        }
        tail = wrapIndex(tail + 1);
        if (i < s) {
            shiftBlock(i, s);
        }
        set(i, e);
    }
 
    public long remove(int i) {
        int s = size();
        if (i < 0 || i >= s) {
            throw new IndexOutOfBoundsException();
        }
        long e = get(i);
        if (i > 0) {
            shiftBlock(0, i);
        }
        head = wrapIndex(head + 1);
        return e;
    }
}
