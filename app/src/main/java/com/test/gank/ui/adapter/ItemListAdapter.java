package com.test.gank.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.ui.adapter.base.ItemViewDelegate;
import com.test.gank.ui.adapter.base.MultiItemTypeAdapter;
import com.test.gank.ui.adapter.base.RvViewHolder;
import com.test.gank.ui.widget.banner.BannerView;
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
    }

    private class TextDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_text;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return !isItemImage(item);
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, int position) {
            holder.setText(R.id.item_title_text, gankItem.getDesc());
            holder.setText(R.id.item_author_text, gankItem.getWho());
            holder.setText(R.id.item_date_text, StringUtils.getDateWithoutTime(gankItem.getCreatedAt()));
        }
    }

    private class ImageDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_image;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return isItemImage(item);
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, int position) {
            BannerView bannerView = holder.getView(R.id.item_banner);
            bannerView.setGankItem(gankItem);
        }
    }

    private boolean isItemImage(GankItem item) {
        return item.getImages() != null && item.getImages().size() > 0;
    }

    private void initPoints(LinearLayout layout, int viewSize, int lightPointIndex) {
        layout.removeAllViews();
        if (viewSize <= 1) {
            return;
        }
        for (int i = 0; i < viewSize; i++) {
            ImageView v = new ImageView(getContext());
            if (i == lightPointIndex) {
                v.setImageResource(R.drawable.shape_cir_wihite);
            } else {
                v.setImageResource(R.drawable.shape_cir_white_50);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                lp.leftMargin = 20;
            }
            v.setLayoutParams(lp);
            layout.addView(v);
        }
    }


}
