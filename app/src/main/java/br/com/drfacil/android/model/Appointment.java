package br.com.drfacil.android.model;

import org.joda.time.DateTime;

// TODO: Implement Parcelable
// TODO: DateAndTime
public class Appointment extends Model {

    private Professional mProfessional;
    private DateTime mDateTime;

    public Appointment(int id, Professional professional, DateTime dateTime) {
        super(id);
        mProfessional = professional;
        mDateTime = dateTime;
    }

    public Professional getProfessional() {
        return mProfessional;
    }

    public void setProfessional(Professional mProfessional) {
        this.mProfessional = mProfessional;
    }

    public String getDate() {
        return mDateTime.toString("MMMM, dd") + " of "+ mDateTime.toString("yyyy");
    }

    public String getTime() {
        return mDateTime.toString("HH:mm");
    }

    public Address getAddress() {
        return getProfessional().getAddress();
    }
}
