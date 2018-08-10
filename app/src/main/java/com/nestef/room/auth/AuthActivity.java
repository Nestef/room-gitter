package com.nestef.room.auth;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.main.MainActivity;
import com.nestef.room.preferences.ThemeChanger;
import com.nestef.room.util.UriUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;

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

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        presenter = new AuthPresenter(PrefManager.getInstance(getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE)));
        presenter.setView(this);
        if (presenter.checkUserAuth()) {
            startMainActivity();
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
                return presenter.handleUri(Uri.parse(url));
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return presenter.handleUri(request.getUrl());
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
    public void startMainActivity() {

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    @Override
    public void showNetworkError() {
        Toast toast = new Toast(this);
        toast.setText(getString(R.string.network_error));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
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
