package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import static com.google.common.base.Preconditions.checkArgument;

public class Professional extends Model implements Parcelable {

    public static final int MAX_RATING = 5;

    private String mName;
    private Address mAddress;
    private String mSpecialty;
    private String mImageUrl;
    private int mRating;

    public Professional(int id, String name, Address address, String specialty, String imageUrl, int rating) {
        super(id);
        mName = name;
        mAddress = address;
        mSpecialty = specialty;
        mImageUrl = imageUrl;
        setRating(rating);
    }

    private Professional(Parcel in) {
        super(in);
        mName = in.readString();
        mAddress = in.readParcelable(Address.class.getClassLoader());
        mSpecialty = in.readString();
        mImageUrl = in.readString();
        setRating(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mName);
        dest.writeParcelable(mAddress, flags);
        dest.writeString(mSpecialty);
        dest.writeString(mImageUrl);
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

    private void setRating(int rating) {
        checkArgument(rating <= MAX_RATING);
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public String getSpecialty() {
        return mSpecialty;
    }

    public String getImageUrl() {
        return mImageUrl;
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
