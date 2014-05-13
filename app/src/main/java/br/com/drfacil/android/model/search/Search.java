package br.com.drfacil.android.model.search;

import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Specialty;

import java.util.Date;
import java.util.List;

public class Search {

    private Specialty mSpecialty;
    private Insurance mInsurance;
    private List<Date> mDates;

    public void setSpecialty(Specialty specialty) {
        mSpecialty = specialty;
    }

    public void setInsurance(Insurance insurance) {
        mInsurance = insurance;
    }

    public Insurance getInsurance() {
        return mInsurance;
    }

    public void setDates(List<Date> dates) {
        mDates = dates;
    }

    public List<Date> getDates() {
        return mDates;
    }
}
