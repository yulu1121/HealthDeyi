package com.anshi.healthdeyi.view.self;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 *
 * Created by yulu on 2017/8/30.
 */

public class CanScrollRecyclerView extends RecyclerView {
    public CanScrollRecyclerView(Context context) {
        super(context);
    }


    public CanScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //将ListView的高度设为最大
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得最大值
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
