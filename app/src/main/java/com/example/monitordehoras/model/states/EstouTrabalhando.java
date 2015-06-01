package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.WIFI_NAME;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.monitordehoras.application.CustomApplication;
import com.example.monitordehoras.model.WifiUtils;

public class EstouTrabalhando implements PrefsState {
	
	private Context context;
	private WifiUtils wifiUtils;
	private CustomApplication application;

	public EstouTrabalhando(Context context, WifiUtils wifiUtils) {
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
		application.cancelService();
	}
}
