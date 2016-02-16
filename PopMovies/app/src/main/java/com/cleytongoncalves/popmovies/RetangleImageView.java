package com.cleytongoncalves.popmovies;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Cleyton on 15/02/2016.
 */
public class RetangleImageView extends ImageView {

    public RetangleImageView(Context context) {
        super(context);
    }

    public RetangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RetangleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(300, 400);
    }
}
