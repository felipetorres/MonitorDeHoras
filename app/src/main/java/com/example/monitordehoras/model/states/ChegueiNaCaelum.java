package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.ENTRADA;
import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.NOME_DO_WIFI;
import static com.example.monitordehoras.model.Constants.ULTIMA_ENTRADA;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.monitordehoras.model.WifiUtils;

public class ChegueiNaCaelum implements PrefsState {
	
	private Context context;
	private WifiUtils wifiUtils;
	private SharedPreferences preferences;

	public ChegueiNaCaelum(Context context, WifiUtils wifiUtils) {
		this.context = context;
		this.wifiUtils = wifiUtils;
		this.preferences = context.getSharedPreferences(FILENAME, 0);
	}

	@Override
	public boolean isOnThisState() {
        String nomeDoWifi = this.preferences.getString(NOME_DO_WIFI, "");
		return !wifiUtils.mudeiDeWifiDesdeOsUltimosCincoMinutos() &&
				wifiUtils.getWifiName().matches(nomeDoWifi) && !preferences.contains(ENTRADA);
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

}
