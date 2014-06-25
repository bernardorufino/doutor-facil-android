package br.com.drfacil.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.tabbed.SimpleFragmentPagerAdapter;
import br.com.drfacil.android.fragments.appointments.AppointmentsFragment;
import br.com.drfacil.android.fragments.login.LoginFragment;
import br.com.drfacil.android.fragments.search.SearchFragment;
import br.com.drfacil.android.managers.AppStateManager;
import br.com.drfacil.android.views.TabContainerView;
import br.com.drfacil.android.views.TabView;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends FragmentActivity {

    private static List<HostInfo> sFragmentsInfo = Lists.newArrayList(
        SearchFragment.HOST_INFO,
        LoginFragment.HOST_INFO
    );

    private ViewPager vViewPager;
    private TabContainerView vTabContainer;
    private TextView vDescription;
    private SimpleFragmentPagerAdapter mPagerAdapter;
    private List<Stack<CharSequence>> mTitleStacks = new ArrayList<>(sFragmentsInfo.size());
    private AppStateManager mAppStateManager = AppStateManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initTabs();
        initTitleStacks();
    }

    private void initTitleStacks() {
        for (HostInfo info : sFragmentsInfo) {
            Stack<CharSequence> stack = new Stack<>();
            stack.push(getResources().getString(info.labelStringId));
            mTitleStacks.add(stack);
        }
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        vViewPager = (ViewPager) findViewById(R.id.pager);
        vTabContainer = (TabContainerView) findViewById(R.id.main_bar_tab_container);
        vDescription = (TextView) findViewById(R.id.main_bar_description);
    }

    private void initTabs() {
        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>(sFragmentsInfo.size());
        for (HostInfo info : sFragmentsInfo) {
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

    public Stack<CharSequence> getCurrentTitleStack() {
        return mTitleStacks.get(vViewPager.getCurrentItem());
    }

    public void pushAndSetTitle(CharSequence title) {
        int i = vViewPager.getCurrentItem();
        mTitleStacks.get(i).push(title);
        vDescription.setText(title);
    }

    public void popAndUpdateTitle() {
        int i = vViewPager.getCurrentItem();
        Stack<CharSequence> stack = mTitleStacks.get(i);
        stack.pop();
        vDescription.setText(stack.peek());
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        pushAndSetTitle(title);
    }

    // Propagate back to nested fragments
    @Override
    public void onBackPressed() {
        int i = vViewPager.getCurrentItem();
        Fragment fragment = mPagerAdapter.getItem(i);
        boolean popped = fragment.getChildFragmentManager().popBackStackImmediate();
        if (!popped) {
            super.onBackPressed();
        } else {
            popAndUpdateTitle();
        }
    }

    private void updateFragments() {
        if (mAppStateManager.getLoginState() == AppStateManager.LoginState.LOGGED_OUT) {
            switchFromLoginToAppointmentsTab();
        } else if (mAppStateManager.getLoginState() == AppStateManager.LoginState.LOGGED_IN) {
            switchFromAppointmentsToLoginTab();
        }
    }

    public void switchFromLoginToAppointmentsTab() {
        int loginIndex = sFragmentsInfo.indexOf(LoginFragment.HOST_INFO);
        if (loginIndex == -1) return;

        sFragmentsInfo.set(loginIndex, AppointmentsFragment.HOST_INFO);
        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>(sFragmentsInfo.size());
        for (HostInfo info : sFragmentsInfo) {
            fragmentClasses.add(info.fragmentClass);
        }

        // Update view pager
        mPagerAdapter.swapFragment(loginIndex, AppointmentsFragment.HOST_INFO.fragmentClass);
    }

    public void switchFromAppointmentsToLoginTab() {
        int appointmentsIndex = sFragmentsInfo.indexOf(AppointmentsFragment.HOST_INFO);
        if (appointmentsIndex == -1) return;

        sFragmentsInfo.set(appointmentsIndex, LoginFragment.HOST_INFO);
        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>(sFragmentsInfo.size());
        for (HostInfo info : sFragmentsInfo) {
            fragmentClasses.add(info.fragmentClass);
        }

        // Update view pager
        mPagerAdapter.swapFragment(appointmentsIndex, LoginFragment.HOST_INFO.fragmentClass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_logout);
        checkNotNull(item).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AppStateManager.getInstance().logOut();
                switchFromAppointmentsToLoginTab();
                return true;
            }
        });

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
            vDescription.setText(mTitleStacks.get(position).peek());
        }
    }

    private class MainBarPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if (vTabContainer.getSelectedTabPosition() != position) {
                vTabContainer.selectTabAt(position);
            }
            vDescription.setText(mTitleStacks.get(position).peek());
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { /* Empty */ }

        @Override
        public void onPageScrollStateChanged(int state) { /* Empty */ }
    }
}
