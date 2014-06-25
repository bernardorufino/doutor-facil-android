package br.com.drfacil.android.model;

import static com.google.common.base.Preconditions.checkArgument;

/* TODO: Implement Parcelable */
/* TODO: Remove hardcoded phones and email */
public class Professional extends Model {

    public static final int MAX_RATING = 5;

    private String mName;
    private String mEmail = "bermonruf@gmail.com";
    private String mPhone = "(12) 99428-4547";
    private String mImageUrl;
    private Address mAddress;
    private Specialty mSpecialty;
    private Insurance mInsurance;
    private int mRating;
    private int mAboutMeText;

    public Professional(
            int id,
            String name,
            Address address,
            Specialty specialty,
            Insurance insurance,
            String imageUrl,
            int rating) {
        this(id, name, "bermonruf@gmail.com", "(12) 99428-4547", address, specialty, insurance, imageUrl, rating);
    }

    public Professional(
            int id,
            String name,
            String email,
            String phone,
            Address address,
            Specialty specialty,
            Insurance insurance,
            String imageUrl,
            int rating) {
        super(id);
        mName = name;
        mEmail = email;
        mPhone = phone;
        mAddress = address;
        mSpecialty = specialty;
        mInsurance = insurance;
        mImageUrl = imageUrl;
        setRating(rating);
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return "bermonruf@gmail.com";
    }

    public String getPhone() {
        return "(21) 99428-4547";
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

    public Specialty getSpecialty() {
        return mSpecialty;
    }

    public Insurance getInsurance() {
        return mInsurance;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getAboutMeText() {
        return "<b>Oi</b>, meu nome é fulano e queria fazer alguma coisa pra escrever aqui. Mas também não tenho tempo para isso.";
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("-- Professional: ").append(mName).append("\n");
        string.append("image_url = ").append(mImageUrl).append("\n");
        string.append("rating = ").append(mRating).append("\n");
        string.append("specialty = ").append(mSpecialty).append("\n");
        string.append("insurance = ").append(mInsurance).append("\n");
        string.append("address = ").append(mAddress).append("\n");
        return string.toString();
    }
}
