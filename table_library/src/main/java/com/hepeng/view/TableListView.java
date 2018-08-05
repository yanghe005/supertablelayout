package com.hepeng.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hepeng.R;

public class TableListView extends ListView {
    private int maxWidth;

    public TableListView(Context context) {
        super(context);

        initView(context, null);
    }

    public TableListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public TableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.expand_height_list_view);

        maxWidth = (int) ta.getDimension(R.styleable.expand_height_list_view_max_width, 0f);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 加上这句不可滑动，但是想要实现两个listview同时上下滚动效果需要有下面这句话且在两个lisview外加上ScrollView
        int expandHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(maxWidth == 0 ? widthMeasureSpec : maxWidth, expandHeightSpec);
    }
}