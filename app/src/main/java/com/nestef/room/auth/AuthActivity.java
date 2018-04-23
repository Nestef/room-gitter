package com.nestef.room.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nestef.room.R;

public class AuthActivity extends AppCompatActivity implements AuthContract.AuthView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
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
