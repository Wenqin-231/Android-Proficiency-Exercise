package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.test.gank.R;
import com.test.gank.search.SearchFragment;
import com.test.gank.ui.adapter.HomePagerAdapter;
import com.test.gank.ui.baseview.BaseFragment;
import com.test.gank.ui.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenqin on 2017/7/22.
 * 主页
 */

public class HomeFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    Unbinder unbinder;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    public static final String[] TAB_TITLES = {"Android", "iOS", "前端"};

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup Toolbar
        mToolbar.inflateMenu(R.menu.menu_home);
        mToolbar.setOnMenuItemClickListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < TAB_TITLES.length; i++) {
            fragmentList.add(ItemListFragment.newInstance(TAB_TITLES[i]));
            mViewPager.setOffscreenPageLimit(i);
        }
        mTabs.setupWithViewPager(mViewPager);
        // 禁止viewPager滚动
        mViewPager.setNoScroll(true);
        mViewPager.setAdapter(new HomePagerAdapter(getFragmentManager(), fragmentList, TAB_TITLES));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                switchFragment(this, SearchFragment.newInstance());
                break;
        }
        return false;
    }
}
