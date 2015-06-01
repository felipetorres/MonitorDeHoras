package com.example.monitordehoras.model;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiUtils {

    private WifiManager manager;
    private WifiInfo info;

    public WifiUtils(Context context){
        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        info = manager.getConnectionInfo();
    }

    public String getWifiName(){
        return info.getSSID();
    }
}