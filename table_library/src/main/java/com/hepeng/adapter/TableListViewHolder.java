package com.hepeng.adapter;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TableListViewHolder {
    private final SparseArray<View> views;
    private int position;
    private View convertView;

    private TableListViewHolder(View view, int position) {
        this.position = position;

        this.views = new SparseArray<>();

        convertView = view;

        // setTag
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param view
     * @param position
     * @return
     */
    public static TableListViewHolder get(View view, int position) {
        return new TableListViewHolder(view, position);
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return getView(viewId, null);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @param params
     * @return
     */
    public <T extends View> T getView(int viewId, ViewGroup.LayoutParams params) {
        return getView(viewId, params, false);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @param isSt   boolean 是否加中横线
     * @return
     */
    public <T extends View> T getView(int viewId, ViewGroup.LayoutParams params, boolean isSt) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            if (isSt && view instanceof TextView) {
                setTxtThru((TextView) view);
            }
            if (params != null) {
                view.setLayoutParams(params);
            }
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置中划线
     *
     * @param view
     */
    public void setTxtThru(TextView view) {
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        view.getPaint().setAntiAlias(true);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public TableListViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public TableListViewHolder setImageResource(int viewId, int drawableId) {
        return setImageResource(viewId, drawableId, null);
    }

    /**
     * @param viewId
     * @param drawableId
     * @param mParams    ViewGroup
     * @return ViewHolder 返回类型
     * @Title: setImageResource
     * @Description: (这里用一句话描述这个方法的作用)
     */
    public TableListViewHolder setImageResource(int viewId, int drawableId,
                                                ViewGroup.LayoutParams mParams) {
        ImageView view = getView(viewId, mParams);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param mParams
     * @return
     */
    public TableListViewHolder setImageBitmap(int viewId, Bitmap bm, ViewGroup.LayoutParams mParams) {
        ImageView view = getView(viewId, mParams);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * @param viewId
     * @param bm
     * @return ViewHolder    返回类型
     * @Title: setImageBitmap
     * @Description: (这里用一句话描述这个方法的作用)
     */
    public TableListViewHolder setImageBitmap(int viewId, Bitmap bm) {
        return setImageBitmap(viewId, bm, null);
    }

    public int getPosition() {
        return position;
    }
}