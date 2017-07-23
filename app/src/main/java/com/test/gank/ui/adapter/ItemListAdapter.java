package com.test.gank.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.model.LoadMoreBean;
import com.test.gank.ui.adapter.base.ItemViewDelegate;
import com.test.gank.ui.adapter.base.MultiItemTypeAdapter;
import com.test.gank.ui.adapter.base.RvViewHolder;
import com.test.gank.ui.widget.banner.PageAdapter;
import com.test.gank.ui.widget.banner.PointsAdapter;
import com.test.gank.utils.ConvertUtils;
import com.test.gank.utils.StringUtils;

import java.util.List;

import static com.test.gank.utils.App.getContext;

/**
 * Created by wenqin on 2017/7/22.
 */

public class ItemListAdapter extends MultiItemTypeAdapter<GankItem> {

    // 用于Glide等开源框架的生命周期回调
    private Fragment mFragment;

    public ItemListAdapter(Fragment fragment, Context context, List<GankItem> data) {
        super(context, data);
        mFragment = fragment;
        addItemViewDelegate(new TextDelegate());
        addItemViewDelegate(new ImageDelegate());
        addItemViewDelegate(new LoadMoreDelegate());
    }

    private class TextDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_text;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return !isItemImage(item) && !isLoadMore(item);
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, int position) {
            holder.setText(R.id.item_title_text, gankItem.getDesc());
            holder.setText(R.id.item_author_text, gankItem.getWho());
            holder.setText(R.id.item_date_text, StringUtils.getDateWithoutTime(gankItem.getPublishedAt()));
            holder.addOnChldViewClickListener(R.id.item_layout, mOnChlidViewClickListener);
        }
    }

    private class ImageDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_banner;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return isItemImage(item) && !isLoadMore(item);
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, final int position) {
            holder.setText(R.id.title_text, gankItem.getDesc());
            holder.setText(R.id.author_text, gankItem.getWho());
            holder.setText(R.id.date_text, StringUtils.getDateWithoutTime(gankItem.getPublishedAt()));

            if (gankItem.getImages().size() > 1) { // show page view
                RecyclerView pointsRv = holder.getView(R.id.points_list);
                pointsRv.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                final PointsAdapter pointsAdapter = new PointsAdapter(getContext(), gankItem.getImages());
                pointsRv.setAdapter(pointsAdapter);

                final RecyclerViewPager imagePage = holder.getView(R.id.image_page);
                imagePage.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                PageAdapter pageAdapter = new PageAdapter(getContext(), gankItem.getImages());
                imagePage.setAdapter(pageAdapter);

                imagePage.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        // scroll stop
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            pointsAdapter.setLightPosition(imagePage.getCurrentPosition());
                        }
                    }
                });
                // show page
                holder.setVisible(R.id.single_image, false);
                holder.setVisible(R.id.image_page, true);
            } else { // show single image to make Glide Cache the image in recyclerView
                // 数目为空的在Text类型，数目大于1在上面，所以这里的图片数量必为1
                Glide.with(mFragment).load(gankItem.getImages().get(0)
                        + "?imageView2/0/h/" + ConvertUtils.dp2px(188))
                        .crossFade()
                        .placeholder(R.mipmap.load)
                        .error(R.mipmap.notfound)
                        .into((ImageView) holder.getView(R.id.single_image));
                // hide page
                holder.setVisible(R.id.single_image, true);
                holder.setVisible(R.id.image_page, false);
            }

            holder.addOnChldViewClickListener(R.id.banner_layout, mOnChlidViewClickListener);
            holder.addOnChldViewClickListener(R.id.bottom_layout, mOnChlidViewClickListener);
        }
    }

    private class LoadMoreDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.load_more_view;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return isLoadMore(item);
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, int position) {
            if (gankItem.getLoadMoreStatus() == LoadMoreBean.STATUS_END) {
                holder.setText(R.id.load_more_text, "无更多数据");
            } else if (gankItem.getLoadMoreStatus() == LoadMoreBean.STATUS_LOADING) {
                holder.setText(R.id.load_more_text, "加载更多…");
            }
        }
    }

    public void hideLoadMore() {
        int lastPosition = getData().size() - 1;
        if (lastPosition >= 0 && isLoadMore(getData().get(lastPosition))) {
            getData().remove(lastPosition);
            notifyItemRemoved(lastPosition);
        }
    }

    public void showLoadMore(boolean isLoading) {
        GankItem gankItem = new GankItem();
        if (isLoading) {
            gankItem.setLoadMoreStatus(LoadMoreBean.STATUS_LOADING);
        } else {
            gankItem.setLoadMoreStatus(LoadMoreBean.STATUS_END);
        }
        if (getData().size() > 0 && !isLoadMore(getData().get(getData().size() - 1))) {
            getData().add(gankItem);
            notifyItemChanged(getData().size() - 1);
        }
    }

    private boolean isItemImage(GankItem item) {
        return item.getImages() != null && item.getImages().size() > 0;
    }

    private boolean isLoadMore(GankItem item) {
        return item.getLoadMoreStatus() != LoadMoreBean.STATUS_DEFAULT;
    }
}
