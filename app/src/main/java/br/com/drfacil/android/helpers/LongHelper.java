package br.com.drfacil.android.helpers;

public class LongHelper {

    public static int compare(long lhs, long rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    // Prevents instantiation
    private LongHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
