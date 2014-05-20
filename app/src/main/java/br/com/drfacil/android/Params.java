package br.com.drfacil.android;

public class Params {

    public static final int APP_VERSION = 1;

    // Prevents instantiation
    private Params() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
