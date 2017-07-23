package com.test.gank.ui.widget.banner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenqin on 2017/7/23.
 * 首页可滚动的图片自定义布局
 */

public class BannerView extends RelativeLayout {

    TextView mTitleText;
    TextView mAuthorText;
    TextView mDateText;
    RecyclerViewPager mImagePage;
    RecyclerView mPointsList;

    private List<String> mImageList;

    private GankItem mGankItem;
    private PointsAdapter pointsAdapter;
    private PageAdapter mPageAdapter;
    private OnClickListener mParentClickListener;

    private RecyclerView.OnScrollListener mOnScrollListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_banner, this);
        mTitleText = (TextView) findViewById(R.id.title_text);
        mAuthorText = (TextView) findViewById(R.id.author_text);
        mDateText = (TextView) findViewById(R.id.date_text);
        mPointsList = (RecyclerView) findViewById(R.id.points_list);
        mImagePage = (RecyclerViewPager) findViewById(R.id.image_page);

        mImageList = new ArrayList<>();
        mPointsList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        pointsAdapter = new PointsAdapter(getContext(), mImageList);
        mPointsList.setAdapter(pointsAdapter);

        mPageAdapter = new PageAdapter(getContext(), mImageList);
        mImagePage.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mImagePage.setAdapter(mPageAdapter);

        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // scroll stop
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && pointsAdapter != null
                        && mImagePage != null) {
                    pointsAdapter.setLightPosition(mImagePage.getCurrentPosition());
                }
            }
        };

        mImagePage.addOnScrollListener(mOnScrollListener);
    }

    public void setTitleText(String text) {
        mTitleText.setText(text);
    }

    public void setAuthorText(String text) {
        mAuthorText.setText(text);
    }

    public void setDateText(String text) {
        mDateText.setText(text);
    }

    public void setGankItem(GankItem gankItem) {
        mImageList.clear();
        mImageList.addAll(gankItem.getImages());
        pointsAdapter.notifyDataSetChanged();
        mPageAdapter.notifyDataSetChanged();

        setTitleText(gankItem.getDesc());
        setAuthorText(gankItem.getWho());
        setDateText(StringUtils.getDateWithoutTime(gankItem.getCreatedAt()));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
