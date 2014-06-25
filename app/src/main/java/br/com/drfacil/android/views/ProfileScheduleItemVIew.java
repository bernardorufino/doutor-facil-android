package br.com.drfacil.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.views.FlowLayout;
import br.com.drfacil.android.model.Slot;
import com.google.common.collect.ImmutableList;
import org.joda.time.LocalDate;

import java.util.List;

public class ProfileScheduleItemView extends RelativeLayout {

    private TextView vDay;
    private FlowLayout vSlots;
    private List<Slot> mSlots;
    private LocalDate mDay;

    public ProfileScheduleItemView(Context context) {
        super(context);
        init();
    }

    public ProfileScheduleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileScheduleItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_profile_schedule, this);
        vDay = (TextView) findViewById(R.id.profile_schedule_day);
        vSlots = (FlowLayout) findViewById(R.id.profile_schedule_slots);
    }

    public LocalDate getDay() {
        return mDay;
    }

    public void setDay(LocalDate date) {
        mDay = date;
        updateView();
    }

    public void setSlots(List<Slot> slots) {
        mSlots = ImmutableList.copyOf(slots);
        updateView();
    }

    private void updateView() {
        vDay.setText(mDay.toString("dd/MM/yyyy"));
        if (mSlots != null) {
            vSlots.removeAllViews();
            for (Slot slot : mSlots) {
                TextView v = (TextView) inflate(getContext(), R.layout.profile_schedule_item_slot, null);
                v.setText(slot.getStartDate().toString("HH:mm"));
                vSlots.addView(v);
            }
        }
    }
}
