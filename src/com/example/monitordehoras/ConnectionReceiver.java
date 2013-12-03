package com.example.monitordehoras;

import static com.example.monitordehoras.PrefsConstants.ALMOCO;
import static com.example.monitordehoras.PrefsConstants.ALMOCO_TOTAL;
import static com.example.monitordehoras.PrefsConstants.ENTRADA;
import static com.example.monitordehoras.PrefsConstants.FILENAME;
import static com.example.monitordehoras.PrefsConstants.ULTIMA_ENTRADA;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class ConnectionReceiver extends BroadcastReceiver{

	private SharedPreferences preferences;
	private DateTime dateTime;

	@Override
	public void onReceive(Context context, Intent intent) {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String wifiName = info.getSSID();

		dateTime = new DateTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		preferences = context.getSharedPreferences(FILENAME, 0);
		Editor editor = preferences.edit();
		
		if(wifiName.contains("caelum")) {
			if (!preferences.contains(ENTRADA)) {
				//tá na Caelum.
				editor.putLong(ENTRADA, dateTime.getMillis());
				editor.putLong(ULTIMA_ENTRADA, dateTime.getMillis());
				
				String entrada = formatter.format(dateTime.toDate());
				
				Log.i("Entrou em", entrada);
			}
			if(preferences.contains(ALMOCO)) {
				long almoco = preferences.getLong(ALMOCO, 0);
				
				String voltaDoAlmoco = formatter.format(dateTime.toDate());
				Log.i("Voltou do almoco em", voltaDoAlmoco);
				
				String diff = format(dateTime, almoco);
				
				Log.i("Duracao do almoco", diff);
				editor.remove(ALMOCO);
				editor.putString(ALMOCO_TOTAL, diff);
			}
		} else {
			if(preferences.contains(ENTRADA)
					&& minutosDeDiferencaMaiorQue(5)
					&& !preferences.contains(ALMOCO)) {
				Intent monitorService = new Intent(context, MonitorService.class);
				context.startService(monitorService);
			}
		}
		editor.commit();
	}

	private String format(DateTime inicio, long fim) {
		NumberFormat instance = NumberFormat.getInstance();
		instance.setMinimumIntegerDigits(2);
		
		String h = instance.format(Hours.hoursBetween(new DateTime(fim), inicio).getHours() % 24);
		String min = instance.format(Minutes.minutesBetween(new DateTime(fim), inicio).getMinutes() % 60);
		String s = instance.format(Seconds.secondsBetween(new DateTime(fim), inicio).getSeconds() % 60);
		
		return h + ":" + min + ":" + s;
	}
	
	private boolean minutosDeDiferencaMaiorQue(int minutos) {
		long entrada = preferences.getLong(ULTIMA_ENTRADA, 0);
		//atual - entrada > minutos ==> entrada+minutos < atual
		return new DateTime(entrada).plusMinutes(minutos).isBefore(dateTime.getMillis());
	}
}
