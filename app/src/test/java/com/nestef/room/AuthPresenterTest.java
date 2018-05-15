package com.nestef.room;

import android.content.Context;
import android.content.SharedPreferences;

import com.nestef.room.auth.AuthContract;
import com.nestef.room.auth.AuthPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Noah Steffes on 5/14/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthPresenterTest {

    @Mock
    Context mContext;
    @Mock
    SharedPreferences sharedPrefs;
    @Mock
    private AuthContract.AuthView mView;
    private AuthPresenter mPresenter;

    @Before
    public void setup() {
        mPresenter = new AuthPresenter();
        mPresenter.setView(mView);
        when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
    }

    @Test
    public void checkUserAuth_true() {
        when(sharedPrefs.getString(anyString(), (String) any())).thenReturn("");
        assertTrue(mPresenter.checkUserAuth(mContext));
    }

    @Test
    public void checkUserAuth_false() {
        when(sharedPrefs.getString(anyString(), (String) any())).thenReturn(null);
        assertFalse(mPresenter.checkUserAuth(mContext));
    }


}
