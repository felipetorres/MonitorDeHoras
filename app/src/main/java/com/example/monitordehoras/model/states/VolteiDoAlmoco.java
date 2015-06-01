package com.example.monitordehoras.model.states;

import static com.example.monitordehoras.model.Constants.ALMOCO;
import static com.example.monitordehoras.model.Constants.ALMOCO_TOTAL;
import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.WIFI_NAME;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class VolteiDoAlmoco implements PrefsState {
	
	private Context context;
	private String wifiName;
	private SharedPreferences preferences;

	public VolteiDoAlmoco(Context context) {
		this.context = context;
		this.wifiName = getWifiName();
		this.preferences = context.getSharedPreferences(FILENAME, 0);
	}

	@Override
	public boolean isOnThisState() {
		return wifiName.contains(WIFI_NAME) && preferences.contains(ALMOCO);
	}

	@Override
	public void doIt() {
		DateTime dateTime = new DateTime();
		
		Editor editor = preferences.edit();
		
		long almoco = preferences.getLong(ALMOCO, 0);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String voltaDoAlmoco = formatter.format(dateTime.toDate());
		Log.i("Voltou do almoco em", voltaDoAlmoco);
		
		String diff = format(dateTime, almoco);
		
		Log.i("Duracao do almoco", diff);
		editor.remove(ALMOCO);
		editor.putString(ALMOCO_TOTAL, diff);
		
		editor.commit();
	}

	private String getWifiName() {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		return info.getSSID();
	}
	
	private String format(DateTime inicio, long fim) {
		NumberFormat instance = NumberFormat.getInstance();
		instance.setMinimumIntegerDigits(2);
		
		String h = instance.format(Hours.hoursBetween(new DateTime(fim), inicio).getHours() % 24);
		String min = instance.format(Minutes.minutesBetween(new DateTime(fim), inicio).getMinutes() % 60);
		String s = instance.format(Seconds.secondsBetween(new DateTime(fim), inicio).getSeconds() % 60);
		
		return h + ":" + min + ":" + s;
	}
}
