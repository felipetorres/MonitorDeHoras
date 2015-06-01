package com.example.monitordehoras.model.states;

import android.content.Context;

import com.example.monitordehoras.application.CustomApplication;
import com.example.monitordehoras.model.WifiUtils;
import static com.example.monitordehoras.model.Constants.WIFI_NAME;


/**
 * Created by yuripadilha on 6/1/15.
 */
public class PegueiWifi  implements PrefsState {

    private Context context;
    private WifiUtils wifiUtils;
    private CustomApplication application;

    public PegueiWifi(Context context, WifiUtils wifiUtils) {
        this.context = context;
        this.wifiUtils = wifiUtils;
        this.application = (CustomApplication) context.getApplicationContext();
    }

    @Override
    public boolean isOnThisState() {
        return wifiUtils.getWifiName().contains(WIFI_NAME);
    }

    @Override
    public void doIt() {

    }
}
