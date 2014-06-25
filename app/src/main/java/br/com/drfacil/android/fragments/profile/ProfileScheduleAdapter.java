package br.com.drfacil.android.fragments.profile;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.drfacil.android.helpers.SlotsHelper;
import br.com.drfacil.android.model.Slot;
import br.com.drfacil.android.views.ProfileScheduleItemView;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileScheduleAdapter extends BaseAdapter {

    private final Context mContext;
    private List<DayEntry> mDayEntries = new ArrayList<>();
    private ProfileScheduleItemView.OnSlotClickListener mOnSlotClickListener;

    public ProfileScheduleAdapter(Context context) {
        mContext = context;
    }

    public void setOnSlotClickListener(ProfileScheduleItemView.OnSlotClickListener onSlotClickListener) {
        mOnSlotClickListener = onSlotClickListener;
    }

    public void update(List<Slot> slots) {
        mDayEntries = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Slot>> entry : SlotsHelper.distribute(slots).entrySet()) {
            DayEntry dayEntry = new DayEntry(entry.getKey(), entry.getValue());
            mDayEntries.add(dayEntry);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDayEntries.size();
    }

    @Override
    public DayEntry getItem(int position) {
        return mDayEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).day.toDateTimeAtStartOfDay().getMillis();
    }

    @Override
    public ProfileScheduleItemView getView(int position, View convertView, ViewGroup parent) {
        ProfileScheduleItemView view = (convertView instanceof ProfileScheduleItemView)
                ? (ProfileScheduleItemView) convertView
                : new ProfileScheduleItemView(mContext);
        if (mOnSlotClickListener != null) view.setOnSlotClickListener(mOnSlotClickListener);
        DayEntry entry = getItem(position);
        view.setDay(entry.day);
        view.setSlots(entry.slots);
        return view;
    }

    private static class DayEntry {
        public LocalDate day;
        public List<Slot> slots;

        private DayEntry(LocalDate day, List<Slot> slots) {
            this.day = day;
            this.slots = slots;
        }
    }
}
