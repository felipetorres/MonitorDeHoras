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
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HistoryActivity extends ActionBarActivity {

    private MonitorDao dao;
    private ListView listView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		listView = (ListView) findViewById(R.id.lista);
        registerForContextMenu(listView);

        carregaLista();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final DiaDeTrabalho diaDeTrabalho = (DiaDeTrabalho) listView.getAdapter().getItem(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao.deleta(diaDeTrabalho);
                carregaLista();
                return false;
            }
        });
    }

    private void carregaLista() {
        dao = new MonitorDao(this);
        List<DiaDeTrabalho> lista = dao.lista();

        CustomAdapter adapter = new CustomAdapter(this, lista);

        listView.setAdapter(adapter);
    }
}
