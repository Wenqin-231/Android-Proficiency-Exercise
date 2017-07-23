package com.test.gank.ui.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.test.gank.R;
import com.test.gank.ui.adapter.base.MultiCommonAdapter;
import com.test.gank.ui.adapter.base.RvViewHolder;
import com.test.gank.utils.ConvertUtils;

import java.util.List;

/**
 * Created by wenqin on 2017/7/23.
 */

public class PageAdapter extends MultiCommonAdapter<String> {


    public PageAdapter(Context context, List<String> data) {
        super(context, data, R.layout.adapter_page);
    }

    @Override
    protected void convert(RvViewHolder holder, String s, int position) {
        Glide.with(mContext).load(s + "?imageView2/0/h/" + ConvertUtils.dp2px(188)).crossFade()
                .placeholder(R.mipmap.load)
                .error(R.mipmap.notfound)
                .into((ImageView) holder.getView(R.id.bg_image));
    }
}
