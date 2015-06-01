package com.example.monitordehoras.model.states;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.monitordehoras.application.CustomApplication;
import com.example.monitordehoras.model.WifiUtils;

import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.NOME_DO_WIFI;


/**
 * Created by yuripadilha on 6/1/15.
 */
public class PegueiWifi implements PrefsState {

    private final SharedPreferences preferences;
    private Context context;
    private WifiUtils wifiUtils;
    private CustomApplication application;

    public PegueiWifi(Context context, WifiUtils wifiUtils) {
        this.context = context;
        this.wifiUtils = wifiUtils;
        this.application = (CustomApplication) context.getApplicationContext();
        this.preferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isOnThisState() {
        String nomeDoWifi = this.preferences.getString(NOME_DO_WIFI, "");
        return wifiUtils.getWifiName().matches(nomeDoWifi);
    }

    @Override
    public void doIt() {

    }
}
