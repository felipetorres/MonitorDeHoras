package com.example.monitordehoras.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

import com.example.monitordehoras.activity.MonitorActivity;

public class MonitorService extends Service {

	private final int ONE_SEC = 1000;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		final Context context = getApplicationContext();
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
		handler = new Handler();
		runnable = new Runnable() {
			
			@Override
			public void run() {
				//Ainda está na Caelum? Está almoçando?
				
				vibrator.vibrate(new long[] {0,1000,200,1000}, -1);
				
				Intent monitor = new Intent(context, MonitorActivity.class);
				monitor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				monitor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(monitor);
				
				handler.postDelayed(this, ONE_SEC*10);
				
			}
		};
		handler.postDelayed(runnable, ONE_SEC*30);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(runnable);
	}
}
