package com.test.gank.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wenqin on 2016/12/15.
 */

@SuppressWarnings("ALL")
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<RvViewHolder> {
    protected Context mContext;
    protected List<T> data;

    protected OnItemClickListener mItemClickListener;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnChlidViewClickListener mOnChlidViewClickListener;

    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        this.data = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(data.get(position), position);
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        RvViewHolder holder = RvViewHolder.createViewHolder(mContext, parent, layoutId);
        setListener(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        convert(holder, data.get(position));
    }

    public void convert(RvViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    protected void setListener(final RvViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    mItemClickListener.onItemClick(v, holder, position);
                }
            }
        });
    }

    public List<T> getData() {
        return data;
    }

    public void clearData() {
        data.clear();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnChlidViewClickListener(OnChlidViewClickListener onChlidViewClickListener) {
        mOnChlidViewClickListener = onChlidViewClickListener;
    }

    public OnChlidViewClickListener getOnChlidViewClickListener() {
        return mOnChlidViewClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
    }
}
