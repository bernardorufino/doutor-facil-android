package br.com.drfacil.android.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.helpers.CustomViewHelper;

public class TabView extends FrameLayout {

    private ImageView vIcon;

    public TabView(Context context, Drawable normalStateIcon, Drawable pressedStateIcon) {
        super(context);
        init(normalStateIcon, pressedStateIcon);
    }

    private void init(Drawable normalStateIcon, Drawable pressedStateIcon) {
        initView();
        setIcon(normalStateIcon, pressedStateIcon);
    }

    private void initView() {
        inflate(getContext(), R.layout.view_tab, this);
        vIcon = (ImageView) findViewById(R.id.tab_icon);
    }

    private void setIcon(Drawable normalStateIcon, Drawable pressedStateIcon) {
        StateListDrawable stateIcon = new StateListDrawable();
        stateIcon.addState(new int[]{android.R.attr.state_selected}, pressedStateIcon);
        stateIcon.addState(new int[]{android.R.attr.state_pressed}, pressedStateIcon);
        stateIcon.addState(new int[]{}, normalStateIcon);
        vIcon.setImageDrawable(stateIcon);
    }

    public void setPressedBackground(Drawable pressedBackground) {
        StateListDrawable stateBackground = new StateListDrawable();
        stateBackground.addState(new int[]{android.R.attr.state_pressed}, pressedBackground);
        stateBackground.addState(new int[]{}, getResources().getDrawable(android.R.color.transparent));
        CustomViewHelper.setBackground(this, stateBackground);
    }

    public ImageView getIcon() {
        return vIcon;
    }

    public void select(boolean selected) {
        getIcon().setSelected(selected);
    }
}
