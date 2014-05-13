package br.com.drfacil.android.model;

/* TODO: Modelize */
public class Insurance {

    private String mName;

    public Insurance(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }
}
