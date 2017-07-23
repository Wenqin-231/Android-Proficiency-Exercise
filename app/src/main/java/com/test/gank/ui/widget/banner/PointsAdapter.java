package com.test.gank.ui.widget.banner;

import android.content.Context;

import com.test.gank.R;
import com.test.gank.ui.adapter.base.MultiCommonAdapter;
import com.test.gank.ui.adapter.base.RvViewHolder;

import java.util.List;

/**
 * Created by wenqin on 2017/7/23.
 */

public class PointsAdapter extends MultiCommonAdapter<String>{

    private int mLightPosition = 0;

    public PointsAdapter(Context context, List<String> data) {
        super(context, data, R.layout.adapter_point);
    }

    public void setLightPosition(int lightPosition) {
        mLightPosition = lightPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(RvViewHolder holder, String s, int position) {
        if (position == mLightPosition) {
            holder.setBackgroundResource(R.id.point, R.drawable.shape_cir_wihite);
        } else {
            holder.setBackgroundResource(R.id.point, R.drawable.shape_cir_white_50);
        }
    }

    @Override
    public int getItemCount() {
        // no show the point when data size less 2
        if (getData().size() == 1) {
            return 0;
        }
        return super.getItemCount();
    }
}
