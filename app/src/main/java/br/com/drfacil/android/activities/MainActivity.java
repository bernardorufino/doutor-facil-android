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
import br.com.drfacil.android.fragments.AppointmentsFragment;
import br.com.drfacil.android.fragments.search.SearchFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    /* TODO: Resourcify strings */
    public static final String[] TABS = { "Pesquisa", "Minhas Consultas" };
    public static final List<Class<? extends Fragment>> FRAGMENTS = Arrays.asList(
            SearchFragment.class,
            AppointmentsFragment.class);

    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(savedInstanceState);
        initializeTabs();
    }

    private void initializeView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initializeTabs() {
        // Sets the adapter, who will provide the fragments, to the ViewPager (in layout)
        mPagerAdapter = new SimpleFragmentPagerAdapter.Builder()
            .byFragmentClasses(FRAGMENTS)
            .setFragmentManager(getSupportFragmentManager())
            .build();
        mViewPager.setAdapter(mPagerAdapter);

        // Tabs > view pager
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new SynchronizedTabListenerAdapter().setViewPager(mViewPager);
        for (String label : TABS) {
            ActionBar.Tab tab = actionBar
                    .newTab()
                    .setText(label)
                    .setTabListener(tabListener);
            actionBar.addTab(tab);
        }

        // View pager > tabs
        ViewPager.OnPageChangeListener pageChangeListener = new SynchronizedViewChangeListenerAdapter().setActionBar(actionBar);
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
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
