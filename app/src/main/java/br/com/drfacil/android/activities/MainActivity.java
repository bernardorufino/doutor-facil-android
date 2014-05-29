package br.com.drfacil.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.tabbed.SimpleFragmentPagerAdapter;
import br.com.drfacil.android.fragments.appointments.AppointmentsFragment;
import br.com.drfacil.android.fragments.search.SearchFragment;
import br.com.drfacil.android.views.TabContainerView;
import br.com.drfacil.android.views.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final HostInfo[] FRAGMENTS = {
            SearchFragment.HOST_INFO,
            AppointmentsFragment.HOST_INFO
    };

    private ViewPager vViewPager;
    private TabContainerView vTabContainer;
    private TextView vDescription;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initTabs();
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        vViewPager = (ViewPager) findViewById(R.id.pager);
        vTabContainer = (TabContainerView) findViewById(R.id.main_bar_tab_container);
        vDescription = (TextView) findViewById(R.id.main_bar_description);
    }

    private void initTabs() {
        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>(FRAGMENTS.length);
        for (HostInfo info : FRAGMENTS) {
            fragmentClasses.add(info.fragmentClass);
        }

        // Sets the adapter, who will provide the fragments, to the ViewPager (in layout)
        mPagerAdapter = new SimpleFragmentPagerAdapter.Builder()
                .byFragmentClasses(fragmentClasses)
                .setFragmentManager(getSupportFragmentManager())
                .build();
        vViewPager.setAdapter(mPagerAdapter);

        // Add Tabs
        vTabContainer.addTabs(Arrays.asList(
                new TabView(
                        getApplicationContext(),
                        getResources().getDrawable(R.drawable.ic_action_search_holo_light),
                        getResources().getDrawable(R.drawable.ic_action_search_white)),
                new TabView(
                        getApplicationContext(),
                        getResources().getDrawable(R.drawable.ic_action_event_holo_light),
                        getResources().getDrawable(R.drawable.ic_action_event_white))));

        // Tabs > ViewPager, Description
        vTabContainer.setOnTabSelectedListener(new MainBarTabListener());

        // ViewPager > Tabs, Description
        vViewPager.setOnPageChangeListener(new MainBarPagerListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, getString(R.string.settings_label), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class HostInfo {

        public final int labelStringId;
        public final Class<? extends Fragment> fragmentClass;

        public HostInfo(int labelStringId, Class<? extends Fragment> fragmentClass) {
            this.labelStringId = labelStringId;
            this.fragmentClass = fragmentClass;
        }
    }

    private class MainBarTabListener implements TabContainerView.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabView tab, int position) {
            if (vViewPager.getCurrentItem() != position) {
                vViewPager.setCurrentItem(position, true);
            }
            vDescription.setText(FRAGMENTS[position].labelStringId);
        }
    }

    private class MainBarPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if (vTabContainer.getSelectedTabPosition() != position) {
                vTabContainer.selectTabAt(position);
            }
            vDescription.setText(FRAGMENTS[position].labelStringId);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { /* Empty */ }

        @Override
        public void onPageScrollStateChanged(int state) { /* Empty */ }
    }
}
