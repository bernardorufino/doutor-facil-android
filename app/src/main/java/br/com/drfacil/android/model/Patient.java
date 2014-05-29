package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient extends Model implements Parcelable {

    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private String mBirthDate;
    private String mGender;

    public Patient(int id,
                   String email,
                   String firstName,
                   String lastName,
                   String birthDate,
                   String gender) {
        super(id);
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
        mBirthDate = birthDate;
        mGender = gender;
    }

    private Patient(Parcel in) {
        super(in);
        mEmail = in.readString();
        mFirstName = in.readString();
        mLastName = in.readString();
        mBirthDate = in.readString();
        mGender = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mEmail);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mBirthDate);
        dest.writeString(mGender);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public String getGender() {
        return mGender;
    }
}
