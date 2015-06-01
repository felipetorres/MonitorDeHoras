package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.WIFI_NAME;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.monitordehoras.application.CustomApplication;

public class EstouTrabalhando implements PrefsState {
	
	private Context context;
	private String wifiName;
	private CustomApplication application;

	public EstouTrabalhando(Context context) {
		this.context = context;
		this.wifiName = getWifiName();
		this.application = (CustomApplication) context.getApplicationContext();
	}

	@Override
	public boolean isOnThisState() {
		return wifiName.contains(WIFI_NAME);
	}

	@Override
	public void doIt() {
		application.cancelService();
	}

	private String getWifiName() {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		return info.getSSID();
	}
}
