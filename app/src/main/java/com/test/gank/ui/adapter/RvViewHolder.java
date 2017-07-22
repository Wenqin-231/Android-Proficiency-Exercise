package com.test.gank.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by wenqin on 2016/12/15.
 * RecyclerView ViewHolder
 */

public class RvViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RvViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RvViewHolder createViewHolder(Context context, View itemView) {
        return new RvViewHolder(context, itemView);
    }

    public static RvViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RvViewHolder(context, itemView);
    }

    /**
     * 通过id获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * holder设置UI，可扩展
     */
    public RvViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RvViewHolder setVisibility(int viewId, int visibility) {
        View v = getView(viewId);
        v.setVisibility(visibility);
        return this;
    }

    public RvViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RvViewHolder setTextColor(int viewId, int colorId) {
        TextView tv = getView(viewId);
        tv.setTextColor(ContextCompat.getColor(mContext, colorId));
        return this;
    }

    public RvViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public RvViewHolder setBackgroundResource(int viewId, int resId) {
        View v = getView(viewId);
        v.setBackgroundResource(resId);
        return this;
    }

    public RvViewHolder setImageURL(int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(url).into(iv);
        return this;
    }

    public RvViewHolder setImageURL(int viewId, String url, int errorIcon) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(url).error(errorIcon).into(iv);
        return this;
    }

    /*
    holder设置UI，可扩展end
     */

    /*
    点击事件
     */
    public RvViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RvViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public RvViewHolder addOnChldViewClickListener(final int viewId,
                                                   final OnChlidViewClickListener listener) {
        final View view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChildViewClick(view, getAdapterPosition(), viewId);
                }
            }
        });
        return this;
    }
}
