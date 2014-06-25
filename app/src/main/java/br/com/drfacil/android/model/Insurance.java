package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Insurance extends Model implements Parcelable {

    private String mName;

    public Insurance(String id, String name) {
        super(id);
        mName = name;
    }

    public Insurance(Parcel in) {
        super(in);
        mName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }

    public static final Creator<Insurance> CREATOR = new Creator<Insurance>() {

        @Override
        public Insurance createFromParcel(Parcel in) {
            return new Insurance(in);
        }

        @Override
        public Insurance[] newArray(int size) {
            return new Insurance[size];
        }
    };
}
