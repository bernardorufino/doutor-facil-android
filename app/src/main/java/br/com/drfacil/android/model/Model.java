package br.com.drfacil.android.model;

import android.os.Parcel;

public abstract class Model {

    private long mId;

    protected Model(Parcel in) {
        mId = in.readInt();
    }

    public Model(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
    }
}
