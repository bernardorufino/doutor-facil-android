package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialty extends Model implements Parcelable {

    private String mName;

    public Specialty(int id, String name) {
        super(id);
        mName = name;
    }

    public Specialty(Parcel in) {
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

    public static final Creator<Specialty> CREATOR = new Creator<Specialty>() {

        @Override
        public Specialty createFromParcel(Parcel in) {
            return new Specialty(in);
        }

        @Override
        public Specialty[] newArray(int size) {
            return new Specialty[size];
        }
    };
}
