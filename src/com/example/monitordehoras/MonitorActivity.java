package com.example.monitordehoras;

import static com.example.monitordehoras.PrefsConstants.ALMOCO;
import static com.example.monitordehoras.PrefsConstants.ALMOCO_TOTAL;
import static com.example.monitordehoras.PrefsConstants.ENTRADA;
import static com.example.monitordehoras.PrefsConstants.FILENAME;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MonitorActivity extends Activity {
	
	private DateTime dateTime = new DateTime();
	private CustomApplication application;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitor);
		
		application = (CustomApplication) getApplication();
		
		Button yes = (Button) findViewById(R.id.button_yes);
		Button lunching = (Button) findViewById(R.id.button_lunching);
		Button no = (Button) findViewById(R.id.button_no);
		Button list = (Button) findViewById(R.id.button_list);
		Button clear = (Button) findViewById(R.id.button_clear);
		
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				application.cancelService();
				finish();
			}
		});
		
		lunching.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences preferences = getSharedPreferences(FILENAME, 0);
				Editor editor = preferences.edit();
				editor.putLong(ALMOCO, dateTime.getMillis());
				editor.commit();
				Log.i("Saiu para almoco em", formatter.format(dateTime.toDate()));
				application.cancelService();
				finish();
			}
		});
		
		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences preferences = getSharedPreferences(FILENAME, 0);
				Editor editor = preferences.edit();
				long entrada = preferences.getLong(ENTRADA, 0);
				
				if(entrada != 0) {
					editor.remove(ENTRADA);
					
					String saida = formatter.format(dateTime.toDate());
					Log.i("Saiu em", saida);
		
					String diff = Days.daysBetween(new DateTime(entrada), dateTime).getDays() + " days, "
							+ Hours.hoursBetween(new DateTime(entrada), dateTime).getHours() % 24 + " hours, "
					        + Minutes.minutesBetween(new DateTime(entrada), dateTime).getMinutes() % 60 + " minutes, "
					        + Seconds.secondsBetween(new DateTime(entrada), dateTime).getSeconds() % 60 + " seconds.";
					
					Log.i("Conectado por", diff);
					
					MonitorDao dao = new MonitorDao(MonitorActivity.this);
					
					String almoco = preferences.getString(ALMOCO_TOTAL, "-");
					dao.insert(new DiaDeTrabalho(entrada, dateTime.getMillis(), almoco));
					editor.remove(ALMOCO_TOTAL);
					editor.commit();
				}
				application.cancelService();
				finish();
			}
		});
		
		list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MonitorActivity.this, HistoryActivity.class);
				startActivity(intent);
			}
		});
		
		
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				criaDialog().show();
			}
		});
	}
	
	private AlertDialog criaDialog() {
		TypedValue iconValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, iconValue, true);
		
		final AlertDialog.Builder dialog = new AlertDialog.Builder(MonitorActivity.this);
		dialog.setIcon(iconValue.resourceId);
		dialog.setTitle(R.string.dialog_title);
		dialog.setMessage(R.string.dialog_message);
		dialog.setPositiveButton(R.string.dialog_erase, new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MonitorDao dao = new MonitorDao(MonitorActivity.this);
					dao.clear();
				}
			});				
		dialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		return dialog.create();
	}

}
