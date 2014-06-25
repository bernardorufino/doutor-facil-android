package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address extends Model implements Parcelable {

    private String mStreet;
    private String mZipCode;
    private String mNumber;
    private String mComplement;
    private String mCity;
    private String mState;
    private String mCountry;

    public Address(
            String id,
            String street,
            String zip,
            String number,
            String complement,
            String city,
            String state,
            String country) {
        super(id);
        mStreet = street;
        mZipCode = zip;
        mNumber = number;
        mComplement = complement;
        mCity = city;
        mState = state;
        mCountry = country;
    }

    private Address(Parcel in) {
        super(in);
        mStreet = in.readString();
        mZipCode = in.readString();
        mNumber = in.readString();
        mComplement = in.readString();
        mCity = in.readString();
        mState = in.readString();
        mCountry = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mStreet);
        dest.writeString(mZipCode);
        dest.writeString(mNumber);
        dest.writeString(mComplement);
        dest.writeString(mCity);
        dest.writeString(mState);
        dest.writeString(mCountry);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getStreet() {
        return mStreet;
    }

    public String getZip() {
        return mZipCode;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getComplement() {
        return mComplement;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }

    public String getCountry() {
        return mCountry;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s (%s), %s, %s, %s", mStreet, mNumber, mComplement, mZipCode, mCity, mState, mCountry);
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {

        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
