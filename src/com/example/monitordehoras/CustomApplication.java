package com.example.monitordehoras;

import android.app.Application;
import android.content.Intent;

public class CustomApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void cancelService() {
		Intent monitorService = new Intent(this, MonitorService.class);
		stopService(monitorService);
	}
}
