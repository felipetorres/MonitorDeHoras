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

import com.example.monitordehoras.model.WifiUtils;
import com.example.monitordehoras.service.MonitorService;

public class FuiEmboraOuFuiAlmocar implements PrefsState {
	
	private Context context;
	private WifiUtils wifiUtils;
	private SharedPreferences preferences;

	public FuiEmboraOuFuiAlmocar(Context context, WifiUtils wifiUtils) {
		this.context = context;
		this.wifiUtils = wifiUtils;
		this.preferences = context.getSharedPreferences(FILENAME, 0);
	}

	@Override
	public boolean isOnThisState() {
		return !wifiUtils.getWifiName().contains(WIFI_NAME)
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
