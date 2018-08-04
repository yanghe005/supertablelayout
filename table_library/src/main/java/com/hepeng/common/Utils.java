package com.hepeng.common;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author YangHePeng
 * @description
 * @data 18-8-5
 * @email yanghp@91power.com
 */
public class Utils {
    /**
     *
     * @param text
     * @param textSize 以px作为单位
     * @return px作为参数返回
     */
    public static int measureTextWidth(String text, float textSize) {
        int textWidth, textHeight; // Our calculated text bounds
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize);

        // Now lets calculate the size of the text
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        textWidth = textBounds.width();
//            mTextHeight = textBounds.height();

        return textWidth;
    }

    /**
     * 测量任意view的宽度和高度
     *
     * @param view 任意view或者ViewGroup
     * @param sizeCallback 自定义回调
     */
    public static void getViewSize(String tag, final View view, final MeasureViewSizeCallback sizeCallback) {
        if (sizeCallback == null) {
            Log.e(tag, "DisplayUtils的getViewHeight的heightCallback为null");
            return;
        }

        if (view == null) {
            sizeCallback.sizeCallback(0, 0);
            Log.e(tag, "DisplayUtils的getViewHeight的view为null");
            return;
        }

        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver tempObserver = observer;
                if (!tempObserver.isAlive()) {
                    tempObserver = view.getViewTreeObserver();
                }
                if (tempObserver.isAlive() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tempObserver.removeOnGlobalLayoutListener(this);
                }

                sizeCallback.sizeCallback(view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
    }

    public interface MeasureViewSizeCallback {
        void sizeCallback(int width, int height);
    }
}