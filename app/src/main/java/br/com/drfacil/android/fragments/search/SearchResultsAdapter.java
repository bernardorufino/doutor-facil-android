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

    private static final String[] URLS = {
            "https://scontent-a-mia.xx.fbcdn.net/hphotos-ash3/t1.0-9/1558596_10202889535225577_1261402839_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c49.49.617.617/s160x160/601429_500061950087868_1709153825_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c1.0.666.666/s160x160/1377243_441574975962329_298782807_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c202.4.631.631/s160x160/1538948_10201413755281546_1420471922_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c170.50.621.621/s160x160/307956_4073677414871_1654557525_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/t1.0-1/c0.2.385.385/s160x160/531935_468245489905565_2092884356_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/c93.68.774.774/s160x160/249111_623480910998558_1110487713_n.jpg",
            "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/t1.0-1/c62.1.295.295/s160x160/554227_544640392216976_633225573_n.jpg"
    };

    private final Context mContext;
    private List<Professional> mProfessionals = new ArrayList<>();

    public SearchResultsAdapter(Context context) {
        mContext = context;
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
        Professional professional = getItem(position);
        card.setImageUrl(URLS[position % URLS.length]);
        if (card.getProfessional() != professional) {
            card.setProfessional(professional);
        }
        return card;
    }
}
