package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import static com.google.common.base.Preconditions.checkArgument;

public class Professional extends Model implements Parcelable {

    public static final int MAX_RATING = 5;

    private String mName;
    private Address mAddress;
    private int mRating;

    public Professional(int id, String name, Address address, int rating) {
        super(id);
        mName = name;
        mAddress = address;
        setRating(rating);
    }

    private Professional(Parcel in) {
        super(in);
        mName = in.readString();
        mAddress = in.readParcelable(Address.class.getClassLoader());
        setRating(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mName);
        dest.writeParcelable(mAddress, flags);
        dest.writeInt(mRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Address getAddress() {
        return mAddress;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        checkArgument(rating <= MAX_RATING);
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public static final Creator<Professional> CREATOR = new Creator<Professional>() {

        @Override
        public Professional createFromParcel(Parcel in) {
            return new Professional(in);
        }

        @Override
        public Professional[] newArray(int size) {
            return new Professional[size];
        }
    };
}
