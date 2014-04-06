package br.com.drfacil.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import br.com.drfacil.android.R;

import static com.google.common.base.Preconditions.checkArgument;

public class RatingView extends ImageView {

    public static final int MAX_RATING = 5;
    private static final int BACKGROUND_ALPHA = 140;
    private static final float IMAGE_FRACTION_TO_USE = 265 / 320f;
    private static final int CLIP_MAX = 10_000;

    private ClipDrawable mClipDrawable;

    public RatingView(Context context) {
        super(context);
        init(null);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initView();
        if (attrs != null) {
            initAttrs(attrs);
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RatingView);
        try {
            setRating(array.getFloat(R.styleable.RatingView_rating, 2.5f));
        } finally {
            array.recycle();
        }
    }

    private void initView() {
        setBackgroundResource(R.drawable.rating_inactive);
        getBackground().setAlpha(BACKGROUND_ALPHA);
        Drawable drawable = getResources().getDrawable(R.drawable.rating_active);
        mClipDrawable = new ClipDrawable(drawable, Gravity.CENTER_VERTICAL | Gravity.LEFT, ClipDrawable.HORIZONTAL);
        setImageDrawable(mClipDrawable);
        setScaleType(ScaleType.FIT_XY);
    }

    public void setRating(float rating) {
        checkArgument(rating <= MAX_RATING);
        float value = rating / MAX_RATING * CLIP_MAX * IMAGE_FRACTION_TO_USE;
        value += (1 - IMAGE_FRACTION_TO_USE) * CLIP_MAX / 2;
        mClipDrawable.setLevel((int) value);
    }

}
