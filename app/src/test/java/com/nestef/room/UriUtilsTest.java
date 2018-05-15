package com.nestef.room;

import com.nestef.room.util.UriUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Noah Steffes on 5/14/18.
 */
@RunWith(RobolectricTestRunner.class)
public class UriUtilsTest {

    @Test
    public void verifyAuthUri() {
        String url = "https://gitter.im/login/oauth/authorize?client_id=0a5b570fc85d6881b2fa7b0a6fd763c03abf7496&response_type=code&redirect_uri=app%3A%2F%2Ftest";

        assertThat(UriUtils.buildAuthRequestUrl(), is(equalTo(url)));
    }
}
