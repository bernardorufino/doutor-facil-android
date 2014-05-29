package br.com.drfacil.android.ext.scroll;

import android.widget.AbsListView;
import br.com.drfacil.android.helpers.CustomHelper;
import br.com.drfacil.android.helpers.CustomViewHelper;

public abstract class InfinityScrollListener implements AbsListView.OnScrollListener {

    private int mBaseScroll = 0;
    private int mLastScroll;
    private int mLastFirstVisibleItem;
    private boolean mStarted = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /* Empty */
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int scroll = CustomViewHelper.getScrollY(view);
        if (!mStarted) {
            mLastScroll = scroll;
            mLastFirstVisibleItem = firstVisibleItem;
            mStarted = true;
        }
        if (firstVisibleItem > mLastFirstVisibleItem) {
            mBaseScroll += mLastScroll;
            mLastFirstVisibleItem = firstVisibleItem;
        } else if (firstVisibleItem < mLastFirstVisibleItem) {
            mBaseScroll -= scroll;
            mLastFirstVisibleItem = firstVisibleItem;
        }
        int cumulativeScroll = mBaseScroll + scroll;
        CustomHelper.log("scroll = " + cumulativeScroll);
        onInfinityScroll(view, cumulativeScroll);
        mLastScroll = scroll;
    }

    public abstract void onInfinityScroll(AbsListView view, int scrollAmount);
}
