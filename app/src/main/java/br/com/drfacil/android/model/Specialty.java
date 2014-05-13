package br.com.drfacil.android.model;

/* TODO: Modelize */
public class Specialty {

    private String mName;

    public Specialty(String name) {
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
