package br.com.drfacil.android.model;

// TODO: Implement Parcelable
public class Appointment extends Model {

    private Professional mProfessional;
    private String mDate;
    private String mTime;

    public Appointment(int id, Professional professional, String date, String time) {
        super(id);
        mProfessional = professional;
        mDate = date;
        mTime = time;
    }

    public Professional getProfessional() {
        return mProfessional;
    }

    public void setProfessional(Professional mProfessional) {
        this.mProfessional = mProfessional;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }
}
