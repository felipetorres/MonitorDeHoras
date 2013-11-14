package com.example.monitordehoras;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class HistoryActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		ListView listView = (ListView) findViewById(R.id.lista);
		
		MonitorDao dao = new MonitorDao(this);
		List<DiaDeTrabalho> lista = dao.lista();
		
		CustomAdapter adapter = new CustomAdapter(this, lista);
		
		listView.setAdapter(adapter);

	}
}
