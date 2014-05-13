package br.com.drfacil.android.model.search;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.InsuranceApi;
import br.com.drfacil.android.endpoints.SpecialtyApi;
import br.com.drfacil.android.ext.future.FutureCallbackAdapter;
import br.com.drfacil.android.ext.observing.AbstractObservable;
import br.com.drfacil.android.helpers.AsyncHelper;
import br.com.drfacil.android.helpers.ModelsHelper;
import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Specialty;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Search extends AbstractObservable implements Parcelable {

    private List<Specialty> mSpecialties = new ArrayList<>();
    private List<Insurance> mInsurances = new ArrayList<>();
    private Date mStartDate = new Date();
    private Date mEndDate = new Date();

    public Search() {
        /* Empty */
    }

    public Search(Parcel in) {
        in.readTypedList(mSpecialties, Specialty.CREATOR);
        in.readTypedList(mInsurances, Insurance.CREATOR);
        mStartDate = new Date(in.readLong());
        mEndDate = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mSpecialties);
        dest.writeTypedList(mInsurances);
        dest.writeLong(mStartDate.getTime());
        dest.writeLong(mEndDate.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /* TODO: Context parameter is ugly */
    public ListenableFuture<Void> permitEverything(Context context) {
        ApiManager apiManager = ApiManager.getInstance(context);
        final SpecialtyApi specialtyApi = apiManager.getApi(SpecialtyApi.class);
        final InsuranceApi insuranceApi = apiManager.getApi(InsuranceApi.class);
        ListenableFuture<Void> specialtiesFuture = AsyncHelper.executeTask(new Runnable() {
            @Override
            public void run() {
                mSpecialties = specialtyApi.all();
            }
        });
        ListenableFuture<Void> insurancesFuture = AsyncHelper.executeTask(new Runnable() {
            @Override
            public void run() {
                mInsurances = insuranceApi.all();
            }
        });
        @SuppressWarnings("unchecked")
        ListenableFuture<List<Void>> all = Futures.allAsList(specialtiesFuture, insurancesFuture);
        ListenableFuture<Void> joint = Futures.transform(all, Functions.<Void>constant(null));
        Futures.addCallback(joint, new FutureCallbackAdapter<Void>() {
            @Override
            public void onSuccess(Void result) {
                notifyObservers();
            }
        });
        return joint;
    }

    public List<Specialty> getSpecialties() {
        return mSpecialties;
    }

    public String getSpecialtyIdsAsCsv() {
        return ModelsHelper.getModelIdsAsCsv(mSpecialties);
    }

    public void setSpecialties(Collection<Specialty> specialties) {
        if (mSpecialties.equals(specialties)) return;
        mSpecialties = ImmutableList.copyOf(specialties);
        notifyObservers();
    }

    public List<Insurance> getInsurances() {
        return mInsurances;
    }

    public String getInsuranceIdsAsCsv() {
        return ModelsHelper.getModelIdsAsCsv(mInsurances);
    }

    public void setInsurances(Collection<Insurance> insurances) {
        if (mInsurances.equals(insurances)) return;
        mInsurances = ImmutableList.copyOf(insurances);
        notifyObservers();
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        if (mStartDate.equals(startDate)) return;
        mStartDate = startDate;
        notifyObservers();
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        if (mEndDate.equals(endDate)) return;
        mEndDate = endDate;
        notifyObservers();
    }

    public String getLocation() {
        /* TODO: Implement */
        return "todo";
    }

    public void setLocation() {
        /* TODO: Implement */
        throw new AssertionError("TODO");
    }

    /* TODO: Remove */
    public void triggerUpdate() {
        notifyObservers();
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {

        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
