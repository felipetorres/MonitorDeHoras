package com.example.monitordehoras.adapter;

import java.util.List;

import com.example.monitordehoras.R;
import com.example.monitordehoras.R.id;
import com.example.monitordehoras.R.layout;
import com.example.monitordehoras.model.DiaDeTrabalho;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{

	private Activity activity;
	private List<DiaDeTrabalho> diasDeTrabalho;

	public CustomAdapter(Activity activity, List<DiaDeTrabalho> diasDeTrabalho) {
		this.activity = activity;
		this.diasDeTrabalho = diasDeTrabalho;
	}

	@Override
	public int getCount() {
		return diasDeTrabalho.size();
	}

	@Override
	public Object getItem(int position) {
		return diasDeTrabalho.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View layout = activity.getLayoutInflater().inflate(R.layout.item, null);
		
		TextView data = (TextView) layout.findViewById(R.id.data);
		TextView entrada = (TextView) layout.findViewById(R.id.entrada);
		TextView saida = (TextView) layout.findViewById(R.id.saida);
		TextView almoco = (TextView) layout.findViewById(R.id.almoco);

		DiaDeTrabalho diaDeTrabalho = diasDeTrabalho.get(position);
		
		data.setText(diaDeTrabalho.formataData());
		entrada.setText(diaDeTrabalho.formataEntrada());
		saida.setText(diaDeTrabalho.formataSaida());
		almoco.setText(diaDeTrabalho.getAlmoco());
		
		return layout;
	}

}
