package br.com.drfacil.android.model;

import static com.google.common.base.Preconditions.checkArgument;

public class Professional extends Model {

    public static final int MAX_RATING = 5;

    private String mName;
    private String mImageUrl;
    private Address mAddress;
    private Specialty mSpecialty;
    private Insurance mInsurance;
    private int mRating;

    public Professional(
            int id,
            String name,
            Address address,
            Specialty specialty,
            Insurance insurance,
            String imageUrl,
            int rating) {
        super(id);
        mName = name;
        mAddress = address;
        mSpecialty = specialty;
        mInsurance = insurance;
        mImageUrl = imageUrl;
        setRating(rating);
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

    public Specialty getSpecialty() {
        return mSpecialty;
    }

    public Insurance getInsurance() {
        return mInsurance;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("-- Professional: ").append(mName).append("\n");
        s.append("image_url = ").append(mImageUrl).append("\n");
        s.append("rating = ").append(mRating).append("\n");
        s.append("specialty = ").append(mSpecialty).append("\n");
        s.append("insurance = ").append(mInsurance).append("\n");
        s.append("address = ").append(mAddress).append("\n");
        return s.toString();
    }
}
