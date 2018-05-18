package com.nestef.room.auth;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nestef.room.R;
import com.nestef.room.preferences.ThemeChanger;
import com.nestef.room.util.UriUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements AuthContract.AuthView {

    private static final String TAG = "AuthActivity";

    AuthPresenter presenter;

    @BindView(R.id.login_wv)
    WebView webView;
    @BindView(R.id.auth_appbar)
    AppBarLayout appBarLayout;

    protected void onCreate(Bundle savedInstanceState) {

        ThemeChanger.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        presenter = new AuthPresenter();
        presenter.setView(this);
        if (presenter.checkUserAuth(this)) {
            presenter.startMainActivity(this);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                appBarLayout.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return presenter.handleUri(Uri.parse(url), getApplicationContext());
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return presenter.handleUri(request.getUrl(), getApplicationContext());
            }

        });
        webView.loadUrl(UriUtils.buildAuthRequestUrl());

    }

    @Override
    public void onBackPressed() {
        if (webView.copyBackForwardList().getCurrentIndex() > 0) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showEmpty() {

    }
}
