package br.com.drfacil.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.drfacil.android.R;

public class CompactSearchParameterView extends FrameLayout {

    private TextView vLabel;
    private ImageView vIcon;
    private TextView vValue;


    public CompactSearchParameterView(Context context) {
        super(context);
        init(null);
    }

    public CompactSearchParameterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CompactSearchParameterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initView();
        if (attrs != null) {
            initAttributes(attrs);
        }
    }

    private void initView() {
        inflate(getContext(), R.layout.view_compact_search_parameter, this);
//        vLabel = (TextView) findViewById(R.id.compact_search_parameter_label);
        vValue = (TextView) findViewById(R.id.compact_search_parameter_value);
        vIcon = (ImageView) findViewById(R.id.compact_search_parameter_icon);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SearchParameterView);
        try {
            setIcon(array.getDrawable(R.styleable.SearchParameterView_icon));
//            setLabel(array.getString(R.styleable.SearchParameterView_label));
            setValue(array.getString(R.styleable.SearchParameterView_value));
        } finally {
            array.recycle();
        }
    }

    private void setValue(String value) {
        vValue.setText(value);
    }

    private void setLabel(String label) {
        vLabel.setText(label);
    }

    private void setIcon(Drawable drawable) {
        vIcon.setImageDrawable(drawable);
    }


}
