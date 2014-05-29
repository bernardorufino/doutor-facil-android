package br.com.drfacil.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import br.com.drfacil.android.R;
import br.com.drfacil.android.helpers.CustomViewHelper;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class TabContainerView extends RelativeLayout {

    private static int DEFAULT_PRESSED_BG_COLOR = R.color.transparent;
    private static int DEFAULT_TICK_COLOR = R.color.transparent;
    private static int DEFAULT_TICK_HEIGHT = R.dimen.tab_container_view_default_tick_height;

    private LinearLayout vTabs;
    private View vTick;
    private List<TabView> mTabList;
    private int mSelectedTabPosition;
    private int mPressedBgColor;
    private int mTickColor;
    private int mTickHeight;
    private OnTabSelectedListener mOnTabSelectedListener;

    public TabContainerView(Context context) {
        super(context);
        init(null);
    }

    public TabContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TabContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init (AttributeSet attrs) {
        mTabList = new ArrayList<>();
        mSelectedTabPosition = -1;
        if (attrs != null) {
            initAttrs(attrs);
        }
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TabContainerView);
        try {
            setPressedBgColor(array.getColor(R.styleable.TabContainerView_tab_container_pressed_bg_color, DEFAULT_PRESSED_BG_COLOR));
            setTickColor(array.getColor(R.styleable.TabContainerView_tab_container_tick_color, DEFAULT_TICK_COLOR));
            setTickHeight(array.getDimension(R.styleable.TabContainerView_tab_container_tick_height, DEFAULT_TICK_HEIGHT));
        } finally {
            array.recycle();
        }
    }

    private void initView() {
        inflate(getContext(), R.layout.view_tab_container, this);

        // Initialize vTabs
        vTabs = (LinearLayout) findViewById(R.id.tabs);

        // Initialize vTick
        vTick = new View(getContext());
        vTick.setLayoutParams(new LayoutParams(0, mTickHeight));
        addView(vTick);

        // Called once, when the layout is drawn, to update vTick width and other properties. To suppress warning:
        // noinspection ConstantConditions
        vTabs.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mTabList.isEmpty()) {
                    // Get first tab
                    TabView firstTab = mTabList.get(0);

                    // Set tick
                    int tabWidth = firstTab.getWidth();
                    vTick.setLayoutParams(new LayoutParams(tabWidth, mTickHeight));
                    vTick.setBackgroundColor(mTickColor);
                    setTickPosition(firstTab);

                    // Set first tab as selected
                    selectTabAt(0);
                }
                CustomViewHelper.removeOnGlobalLayoutListener(vTabs.getViewTreeObserver(), this);
            }
        });
    }

    public void setPressedBgColor(int color) {
        mPressedBgColor = color;
    }

    public void setTickColor(int color) {
        mTickColor = color;
    }

    public void setTickHeight(float dimension) {
        mTickHeight = (int) dimension;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mOnTabSelectedListener = listener;
    }

    public void addTabs(List<? extends TabView> newTabList) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        Drawable pressedBackground = new ColorDrawable(mPressedBgColor);
        for (TabView tab : newTabList) {
            int position = mTabList.size();
            tab.setOnClickListener(new TabOnClickListener(position));
            tab.setLayoutParams(lp);
            tab.setPressedBackground(pressedBackground);
            mTabList.add(tab);
            vTabs.addView(tab);
        }
    }

    public void selectTabAt(int position) {
        checkArgument(0 <= position);
        checkArgument(position  < mTabList.size());
        if (position == mSelectedTabPosition) {
            return;
        }

        TabView tab = mTabList.get(position);
        tab.select(true);
        setTickPosition(tab);
        if (mSelectedTabPosition != -1) {
            mTabList.get(mSelectedTabPosition).select(false);
        }
        mSelectedTabPosition = position;
        if (mOnTabSelectedListener != null) {
            mOnTabSelectedListener.onTabSelected(tab, position);
        }
    }

    public int getSelectedTabPosition() {
        return mSelectedTabPosition;
    }

    private void setTickPosition(View v) {
        // Sums the position of LinearLayout with the position of the tab
        float x = ((ViewGroup) v.getParent()).getX() + v.getX();
        float y = ((ViewGroup) v.getParent()).getY() + v.getY();

        vTick.setX(x);
        vTick.setY(y + v.getHeight() - vTick.getHeight());
    }

    private class TabOnClickListener implements OnClickListener {
        int mPosition;

        TabOnClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            selectTabAt(mPosition);
        }
    }

    public interface OnTabSelectedListener {
        public void onTabSelected(TabView tab, int position);
    }
}
