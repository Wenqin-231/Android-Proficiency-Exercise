package com.test.gank.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.ui.adapter.base.ItemViewDelegate;
import com.test.gank.ui.adapter.base.MultiItemTypeAdapter;
import com.test.gank.ui.adapter.base.RvViewHolder;
import com.test.gank.utils.StringUtils;

import java.util.List;

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
    }

    public class TextDelegate implements ItemViewDelegate<GankItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_text;
        }

        @Override
        public boolean isForViewType(GankItem item, int position) {
            return true;
        }

        @Override
        public void convert(RvViewHolder holder, GankItem gankItem, int position) {
            holder.setText(R.id.item_title_text, gankItem.getDesc());
            holder.setText(R.id.item_author_text, gankItem.getWho());
            holder.setText(R.id.item_date_text, StringUtils.getDateText(gankItem.getCreatedAt()));
        }
    }
}
