package com.hepeng.view;

import android.text.TextUtils;

import java.util.List;

/**
 * @author YangHePeng
 * @description
 * @data 18-8-6
 * @email yanghp@91power.com
 */
public class TableBuild {
    float textSize;
    int textColor;
    int itemWidth, itemHeight;
    int itemLeftRightMargin;
    int itemBgIntervalColor1, itemBgIntervalColor2;
    int tableHeaderBgColor, rowHeaderBgColor;
    int itemBgPureColor;
    int width, height;
    boolean tableHeaderFixed;
    String rowItemMergeIndex;
    TextUtils.TruncateAt itemTextEllipsize = TextUtils.TruncateAt.END;

    public TableBuild setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public TableBuild setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public TableBuild setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
        return this;
    }

    public TableBuild setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
        return this;
    }

    public TableBuild setItemLeftRightMargin(int itemLeftRightMargin) {
        this.itemLeftRightMargin = itemLeftRightMargin;
        return this;
    }

    public TableBuild setItemBgIntervalColor1(int itemBgIntervalColor1) {
        this.itemBgIntervalColor1 = itemBgIntervalColor1;
        return this;
    }

    public TableBuild setItemBgIntervalColor2(int itemBgIntervalColor2) {
        this.itemBgIntervalColor2 = itemBgIntervalColor2;
        return this;
    }

    public TableBuild setTableHeaderBgColor(int tableHeaderBgColor) {
        this.tableHeaderBgColor = tableHeaderBgColor;
        return this;
    }

    public TableBuild setRowHeaderBgColor(int rowHeaderBgColor) {
        this.rowHeaderBgColor = rowHeaderBgColor;
        return this;
    }

    public TableBuild setItemBgPureColor(int itemBgPureColor) {
        this.itemBgPureColor = itemBgPureColor;
        return this;
    }

    public TableBuild setWidth(int width) {
        this.width = width;
        return this;
    }

    public TableBuild setHeight(int height) {
        this.height = height;
        return this;
    }

    public TableBuild setItemTextEllipsize(TextUtils.TruncateAt itemTextEllipsize) {
        this.itemTextEllipsize = itemTextEllipsize;
        return this;
    }

    public TableBuild setRowItemMergeIndex(String rowItemMergeIndex) {
        this.rowItemMergeIndex = rowItemMergeIndex;
        return this;
    }

    public void excute(SuperTableLayout tableLayout) {
        tableLayout.setTableBuild(this);
    }
}