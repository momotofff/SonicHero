package com.momotoff.sonichero.scene;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.momotoff.my_framework.CoreFW;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;

public class BannerAdvertising extends AppCompatActivity
{
    BannerAdView banner;

    public BannerAdvertising(CoreFW coreFW, String id)
    {
        banner = new BannerAdView(coreFW.getApplication());
        banner.setAdSize(BannerAdSize.stickySize(coreFW.getApplication(), coreFW.getDisplaySize().x));
        banner.setAdUnitId(id);
    }

    public void setBannerVisibility(int visibility)

    {
        if (banner.getVisibility() == visibility)
            return;

        if (visibility == View.VISIBLE)
            banner.loadAd(new AdRequest.Builder().build());

        this.runOnUiThread(() -> banner.setVisibility(visibility));
    }


}
