package br.com.drfacil.android.ext.tabbed;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    private SimpleFragmentPagerAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public static class Builder {

        private List<Fragment> mFragments;
        private FragmentManager mFragmentManager;

        public Builder setFragmentManager(FragmentManager fragmentManager) {
            mFragmentManager = fragmentManager;
            return this;
        }

        public Builder byFragments(List<Fragment> fragments) {
            mFragments = new ArrayList<>(fragments);
            return this;
        }

        public Builder byFragmentClasses(List<? extends Class<? extends Fragment>> fragmentClasses) {
            mFragments = new ArrayList<>();
            for (Class<? extends Fragment> fragmentClass : fragmentClasses) {
                try {
                    mFragments.add(fragmentClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new AssertionError();
                }
            }
            return this;
        }

        public SimpleFragmentPagerAdapter build() {
            return new SimpleFragmentPagerAdapter(mFragments, mFragmentManager);
        }
    }
}
