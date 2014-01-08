package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.ALMOCO;
import static com.example.monitordehoras.model.Constants.ENTRADA;
import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.WIFI_NAME;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.monitordehoras.service.MonitorService;

public class State3 implements PrefsState {
	
	private Context context;
	private String wifiName;
	private SharedPreferences preferences;

	public State3(Context context) {
		this.context = context;
		this.wifiName = getWifiName();
		this.preferences = context.getSharedPreferences(FILENAME, 0);
	}

	@Override
	public boolean isOnThisState() {
		return !wifiName.contains(WIFI_NAME) 
				&& preferences.contains(ENTRADA)
				&& !preferences.contains(ALMOCO);
	}

	@Override
	public void doIt() {
		Intent monitorService = new Intent(context, MonitorService.class);
		context.startService(monitorService);
	}

	private String getWifiName() {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		return info.getSSID();
	}
}
