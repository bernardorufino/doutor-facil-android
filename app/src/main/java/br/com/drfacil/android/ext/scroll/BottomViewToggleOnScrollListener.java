package br.com.drfacil.android.ext.scroll;

import android.view.View;
import android.widget.AbsListView;

public class BottomViewToggleOnScrollListener extends InfinityScrollListener {

    private final View mView;
    private boolean mReversed = false;
    private ScrollState mState = ScrollState.FULLY_VISIBLE;
    private boolean mStarted = false;
    private int mInitialScroll;
    public int mHeight;

    public BottomViewToggleOnScrollListener(View view) {
        mView = view;
    }

    @Override
    public void onInfinityScroll(AbsListView view, int scroll) {
        if (mReversed) scroll = -scroll;
        if (!mStarted) {
            mHeight = mView.getHeight();
            if (mHeight == 0) return;
            mInitialScroll = scroll;
            mStarted = true;
        }
        int delta = scroll - mInitialScroll;
        switch (mState) {
            case FULLY_VISIBLE:
                if (delta <= 0) mInitialScroll = scroll;
                else mState = ScrollState.PARTIALLY_VISIBLE;
                break;
            case PARTIALLY_VISIBLE:
                if (0 < delta && delta < mHeight) {
                    translateView(mView, delta);
                } else {
                    mState = (delta >= mHeight) ? ScrollState.HIDDEN : ScrollState.FULLY_VISIBLE;
                    translateView(mView, (mState == ScrollState.HIDDEN) ? mHeight : 0);
                }
                break;
            case HIDDEN:
                if (delta >= mHeight) mInitialScroll = scroll - mHeight;
                else mState = ScrollState.PARTIALLY_VISIBLE;
                break;
        }
    }

    private void translateView(View view, int delta) {
        view.setTranslationY(delta);
    }

    public BottomViewToggleOnScrollListener setReversed(boolean reversed) {
        mReversed = reversed;
        return this;
    }

    private static enum ScrollState { FULLY_VISIBLE, PARTIALLY_VISIBLE, HIDDEN }
}
