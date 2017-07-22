package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.ui.base.BaseFragment;
import com.test.gank.utils.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenqin on 2017/7/22.
 */

public class WebFragment extends BaseFragment {

    private static final String KEY_URL = "key_url";
    private static final String KEY_TITLE = "key_title";

    @BindView(R.id.web_view)
    WebView mWebView;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private String mURL;
    private String mTitleStr;

    public static WebFragment newInstance(String url, String title) {

        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        args.putString(KEY_TITLE, title);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mURL = getArguments().getString(KEY_URL);
            mTitleStr = getArguments().getString(KEY_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // init ToolBar
        mToolbar.setTitle(mTitleStr);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        initWebSetting();
        mWebView.loadUrl(mURL);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mURL);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(App.getContext(), "加载网页失败", Toast.LENGTH_SHORT).show();
            }
        });

//        mProgressBar.getProgressDrawable().setColorFilter(ContextCompat
//                .getColor(mActivity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }

    private void initWebSetting() {
        WebSettings webSettings;
        if (!mWebView.isInEditMode()) {
            webSettings = mWebView.getSettings();
            webSettings.setSaveFormData(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
