package com.hepeng.common;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by renyj@northloong.com on 2017/10/12.
 */

public class DensityUtils {
    private final static String TAG = DensityUtils.class.getSimpleName();

    public final static String DENSITY = "density";
    public final static String DENSITY_DPI = "densityDpi";

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     * @Context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}