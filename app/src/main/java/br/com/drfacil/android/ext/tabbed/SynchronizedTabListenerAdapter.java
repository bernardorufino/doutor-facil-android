package br.com.drfacil.android.ext.tabbed;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class SynchronizedTabListenerAdapter implements ActionBar.TabListener {

    private ViewPager mPager;

    // In order not to have to rewrite constructor when subclassing, it's preferable to chain a call to setViewPager()
    public SynchronizedTabListenerAdapter setViewPager(ViewPager pager) {
        mPager = pager;
        return this;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) { /* Override */ }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) { /* Override */ }
}
