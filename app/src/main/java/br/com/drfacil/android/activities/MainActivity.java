package br.com.drfacil.android.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.tabbed.SimpleFragmentPagerAdapter;
import br.com.drfacil.android.ext.tabbed.SynchronizedTabListenerAdapter;
import br.com.drfacil.android.ext.tabbed.SynchronizedViewChangeListenerAdapter;
import br.com.drfacil.android.fragments.appointments.AppointmentsFragment;
import br.com.drfacil.android.fragments.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final HostInfo[] FRAGMENTS = {
            SearchFragment.HOST_INFO,
            AppointmentsFragment.HOST_INFO
    };

    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initTabs();
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
        mViewPager.setAdapter(mPagerAdapter);

        // Tabs > view pager
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new SynchronizedTabListenerAdapter().setViewPager(mViewPager);
        for (HostInfo info : FRAGMENTS) {
            String label = getString(info.labelStringId);
            ActionBar.Tab tab = actionBar
                    .newTab()
                    .setText(label)
                    .setTabListener(tabListener);
            actionBar.addTab(tab);
        }

        // View pager > tabs
        ViewPager.OnPageChangeListener pageChangeListener = new SynchronizedViewChangeListenerAdapter()
                .setActionBar(actionBar);
        mViewPager.setOnPageChangeListener(pageChangeListener);
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
}
