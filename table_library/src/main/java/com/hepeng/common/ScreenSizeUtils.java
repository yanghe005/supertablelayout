package com.hepeng.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * TODO 单例模式 获取屏幕宽高的帮助类
 */
public class ScreenSizeUtils {
    private volatile static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeight, applicationHeight, statusHeight;

    public static ScreenSizeUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {
                if (instance == null) {
                    instance = new ScreenSizeUtils(context);
                }
            }
        }
        return instance;
    }

    private ScreenSizeUtils(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeight = dm.heightPixels;// 获取屏幕分辨率高度

        if (context instanceof Activity) {
            getAreaApplication((Activity) context);
        }
    }

    // 获取屏幕宽度
    public int getScreenWidth() {
        return screenWidth;
    }

    // 获取屏幕高度
    public int getScreenHeight() {
        return screenHeight;
    }

    // 获取应用最大可显示高度(如果输入法显示的话不包括输入法高度)
    public int getApplicationHeight() {
        return applicationHeight;
    }

    // 获取状态栏高度
    public int getStatusHeight() {
        return statusHeight;
    }

    /**
     * 得到应用的大小
     */
    protected void getAreaApplication(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        applicationHeight = outRect.height();
        statusHeight = outRect.top;
    }
}