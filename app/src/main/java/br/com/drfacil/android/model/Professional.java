package br.com.drfacil.android.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/* TODO: Implement Parcelable */
/* TODO: Remove hardcoded phones and email */
public class Professional extends Model {

    public static final int MAX_RATING = 5;

    private String mEmail = "bermonruf@gmail.com";
    private String mPhone = "(12) 99428-4547";
    private String mFirstName;
    private String mLastName;
    private String mImageUrl;
    private Address mAddress;
    private List<Specialty> mSpecialties;
    private List<Insurance> mInsurances;
    private int mRating;
    private int mAboutMeText;

    public Professional(
            String id,
            String firstName,
            String lastName,
            Address address,
            List<Specialty> specialties,
            List<Insurance> insurances,
            String imageUrl,
            int rating) {
        super(id);
        mFirstName = firstName;
        mLastName = lastName;
        mAddress = address;
        mSpecialties = ImmutableList.copyOf(specialties);
        mInsurances = ImmutableList.copyOf(insurances);
        mImageUrl = imageUrl;
        setRating(rating);
    }

    /* TODO: remove backwards compatibility constructor */
    public Professional(
            String id,
            String name,
            Address address,
            List<Specialty> specialties,
            List<Insurance> insurances,
            String imageUrl,
            int rating) {
        this(id, name, "bermonruf@gmail.com", "(12) 99428-4547", address, specialties, insurances, imageUrl, rating);
    }

    public Professional(
            String id,
            String name,
            String email,
            String phone,
            Address address,
            List<Specialty> specialties,
            List<Insurance> insurances,
            String imageUrl,
            int rating) {
        super(id);
        mEmail = email;
        mPhone = phone;
        String[] names = name.split("[ ]");
        mFirstName = names[0];
        mLastName = names[1];
        mAddress = address;
        mSpecialties = ImmutableList.copyOf(specialties);
        mInsurances = ImmutableList.copyOf(insurances);
        mImageUrl = imageUrl;
        setRating(rating);
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

    public String getName() {
        return mFirstName + " " + mLastName;
    }

    public List<Specialty> getSpecialties() {
        return mSpecialties;
    }

    public Specialty getSpecialty() {
        return mSpecialties.get(0);
    }

    public List<Insurance> getInsurances() {
        return mInsurances;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getAboutMeText() {
        return "<b>Oi</b>, meu nome é fulano e queria fazer alguma coisa pra escrever aqui. Mas também não tenho tempo para isso.";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("-- Professional: ").append(getName()).append("\n");
        s.append("image_url = ").append(mImageUrl).append("\n");
        s.append("rating = ").append(mRating).append("\n");
        s.append("specialty = ").append(mSpecialties).append("\n");
        s.append("insurance = ").append(mInsurances).append("\n");
        s.append("address = ").append(mAddress).append("\n");
        return s.toString();
    }
}
