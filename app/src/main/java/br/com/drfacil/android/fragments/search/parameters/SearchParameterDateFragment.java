package br.com.drfacil.android.fragments.search.parameters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.search.Search;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SearchParameterDateFragment extends SearchParameterFragment {

    private CalendarPickerView vCalendar;
    private Button vOkButton;
    private Button vDismissButton;

    public SearchParameterDateFragment(Search search) {
        super(search);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vCalendar = (CalendarPickerView) getView().findViewById(R.id.search_param_date_calendar);
        vOkButton = (Button) getView().findViewById(R.id.search_parameter_ok_button);
        vOkButton.setOnClickListener(mOnOkButtonClickListener);
        vDismissButton = (Button) getView().findViewById(R.id.search_parameter_dismiss_button);
        vDismissButton.setOnClickListener(mOnDismissButtonClickListener);
        setupCalendar();
    }

    private View.OnClickListener mOnOkButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFinished();
            dismiss();
        }
    };
    private View.OnClickListener mOnDismissButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setAborted();
            dismiss();
        }
    };

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

    private CalendarPickerView.OnDateSelectedListener mOnDateSelectedListener = new CalendarPickerView.OnDateSelectedListener() {
        @Override
        public void onDateSelected(Date date) {
            getSearch().setDates(vCalendar.getSelectedDates());
        }
        @Override
        public void onDateUnselected(Date date) {
            getSearch().setDates(vCalendar.getSelectedDates());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_date, container, false);
    }
}
