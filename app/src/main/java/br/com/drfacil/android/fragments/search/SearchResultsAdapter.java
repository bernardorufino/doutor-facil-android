package br.com.drfacil.android.fragments.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.views.ProfessionalCardView;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends BaseAdapter {

    private final Context mContext;
    private final View.OnClickListener mOnCardClickListener;
    private List<Professional> mProfessionals = new ArrayList<>();

    public SearchResultsAdapter(Context context, View.OnClickListener onCardClickListener) {
        mContext = context;
        mOnCardClickListener = onCardClickListener;
    }

    public void update(List<Professional> professionals) {
        mProfessionals = ImmutableList.copyOf(professionals);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProfessionals.size();
    }

    @Override
    public Professional getItem(int position) {
        return mProfessionals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProfessionalCardView card = (convertView instanceof ProfessionalCardView)
                ? (ProfessionalCardView) convertView
                : new ProfessionalCardView(mContext);
        card.setOnClickListener(mOnCardClickListener);
        Professional professional = getItem(position);
        if (card.getProfessional() != professional) {
            card.setProfessional(professional);
        }
        return card;
    }
}
