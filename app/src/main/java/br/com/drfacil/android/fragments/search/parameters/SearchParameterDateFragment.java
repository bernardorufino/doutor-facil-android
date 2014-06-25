package br.com.drfacil.android.fragments.search.parameters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.search.Search;
import com.squareup.timessquare.CalendarPickerView;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class SearchParameterDateFragment extends SearchParameterFragment {

    private CalendarPickerView vCalendar;

    public SearchParameterDateFragment(Search search) {
        super(search);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vCalendar = (CalendarPickerView) getView().findViewById(R.id.search_param_date_calendar);
        setupCalendar();
    }

    private void setupCalendar() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date until = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date next = calendar.getTime();
        vCalendar
                .init(today, until)
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(Arrays.asList(today, next));
        vCalendar.setOnDateSelectedListener(mOnDateSelectedListener);
    }

    private CalendarPickerView.OnDateSelectedListener mOnDateSelectedListener =
            new CalendarPickerView.OnDateSelectedListener() {

        @Override
        public void onDateSelected(Date date) {
            updateSearchDates();
        }

        @Override
        public void onDateUnselected(Date date) {
            updateSearchDates();
        }
    };

    private void updateSearchDates() {
        List<Date> dates = vCalendar.getSelectedDates();
        checkState(dates.size() > 0);
        Date startDate = dates.get(0);
        Date endDate = dates.get(dates.size() - 1);
        getSearch().setStartDate(new DateTime(startDate));
        getSearch().setEndDate(new DateTime(endDate));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_date, container, false);
    }
}
