package br.com.drfacil.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.joda.time.DateTime;

public class Slot extends Model
        implements Parcelable, Comparable<Slot> {

    private DateTime mStartDate;

    public Slot(
            String id,
            DateTime startDate) {
        super(id);
        mStartDate = startDate;
    }

    private Slot(Parcel in) {
        super(in);
        mStartDate = DateTime.parse(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mStartDate.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public DateTime getStartDate() {
        return mStartDate;
    }

    @Override
    public int compareTo(Slot another) {
        return mStartDate.compareTo(another.mStartDate);
    }
}
