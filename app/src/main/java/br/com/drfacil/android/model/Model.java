package br.com.drfacil.android.model;

import android.os.Parcel;

public abstract class Model {

    private String mId;

    protected Model(Parcel in) {
        mId = in.readString();
    }

    public Model(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
    }

    @Override
    public boolean equals(Object another) {
        if (another == null || another.getClass() != getClass()) return false;
        Model model = (Model) another;
        return mId.equals(model.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}
