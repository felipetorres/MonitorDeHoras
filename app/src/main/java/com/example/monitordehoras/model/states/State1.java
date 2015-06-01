package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.ENTRADA;
import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.ULTIMA_ENTRADA;
import static com.example.monitordehoras.model.Constants.WIFI_NAME;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class State1 implements PrefsState {
	
	private Context context;
	private String wifiName;
	private SharedPreferences preferences;

	public State1(Context context) {
		this.context = context;
		this.wifiName = getWifiName();
		this.preferences = context.getSharedPreferences(FILENAME, 0);
	}

	@Override
	public boolean isOnThisState() {
		return wifiName.contains(WIFI_NAME) && !preferences.contains(ENTRADA);
	}

	@Override
	public void doIt() {
		DateTime dateTime = new DateTime();
		
		Editor editor = preferences.edit();
		
		editor.putLong(ENTRADA, dateTime.getMillis());
		editor.putLong(ULTIMA_ENTRADA, dateTime.getMillis());
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String entrada = formatter.format(dateTime.toDate());
		
		Log.i("Entrou em", entrada);
		
		editor.commit();
	}

	private String getWifiName() {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		return info.getSSID();
	}
}
