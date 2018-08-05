package com.hepeng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TableListAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> datas;

    protected int itemLayoutId;
    protected CreateViewHolder createViewHolder;

    public TableListAdapter(Context context, int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;

        initStatisticsTableAdapter(context);
    }

    public TableListAdapter(Context context, CreateViewHolder createViewHolder) {
        this.createViewHolder = createViewHolder;

        initStatisticsTableAdapter(context);
    }

    private void initStatisticsTableAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void addItemData(T bean, boolean isRefresh) {
        datas.add(bean);

        if (isRefresh) {
            notifyDataSetChanged();
        }
    }

    public void addItemData(T bean, int index, boolean isRefresh) {
        datas.add(index, bean);

        if (isRefresh) {
            notifyDataSetChanged();
        }
    }

    public void addItemData(Collection<? extends T> beans, int index, boolean isRefresh) {
        datas.addAll(index, beans);

        if (isRefresh) {
            notifyDataSetChanged();
        }
    }

    public void addItemData(Collection<? extends T> beans, boolean isRefresh) {
        datas.addAll(beans);

        if (isRefresh) {
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> beans, boolean isMore) {
        if (isMore) {
            if (beans != null) {
                datas.addAll(beans);
            }
        } else {
            datas.clear();
            if (beans != null) {
                datas.addAll(beans);
            }
        }
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        if (datas != null && datas.size() > 0) {
            datas.remove(pos);
            notifyDataSetChanged();
        }
    }

    public void clearData(boolean clear) {
        if (clear) {
            if (datas != null && datas.size() > 0) {
                datas.clear();
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        if (datas != null && position >= 0 && position <= (datas.size() - 1)) {
            return datas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableListViewHolder viewHolder;
        if (convertView == null) {
            if (itemLayoutId == 0) {
                View view = createViewHolder.createView(context, position, parent);
                viewHolder = getViewHolder(view, position);
            } else {
                viewHolder = getViewHolder(LayoutInflater.from(context).inflate(itemLayoutId, parent, false), position);
            }
        } else {
            viewHolder = (TableListViewHolder) convertView.getTag();
        }

        convert(context, viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    private TableListViewHolder getViewHolder(View view, int position) {
        return TableListViewHolder.get(view, position);
    }

    public List<T> getDatas() {
        return datas;
    }

    public abstract void convert(Context context, TableListViewHolder helper, T item, int pos);

    public interface CreateViewHolder {
        ViewGroup createView(Context context, int position, ViewGroup parent);
    }
}