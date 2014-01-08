package com.example.monitordehoras.activity;

import java.util.List;

import com.example.monitordehoras.R;
import com.example.monitordehoras.R.id;
import com.example.monitordehoras.R.layout;
import com.example.monitordehoras.adapter.CustomAdapter;
import com.example.monitordehoras.dao.MonitorDao;
import com.example.monitordehoras.model.DiaDeTrabalho;

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
