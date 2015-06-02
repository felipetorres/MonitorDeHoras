package com.example.monitordehoras.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.monitordehoras.model.DiaDeTrabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MonitorDao extends SQLiteOpenHelper{
	
	private static final String TABELA = "Horas";
	private static final String[] COLUNAS = {"id", "entrada", "saida", "almoco"};

	public MonitorDao(Context context) {
		super(context, "monitorDeHoras", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE " + TABELA + " (" +
				" " + COLUNAS[0] + " INTEGER PRIMARY KEY," +
				" " + COLUNAS[1] + " INTEGER," +
				" " + COLUNAS[2] + " INTEGER," +
				" " + COLUNAS[3] + " TEXT" +
				" );";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		clear();
	}

	public void clear() {
		String ddl = "DROP TABLE IF EXISTS " + TABELA;
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(ddl);
		onCreate(db);
	}
	
	public void insert(DiaDeTrabalho diaDeTrabalho) {
		ContentValues cv = new ContentValues();
		cv.put(COLUNAS[1], diaDeTrabalho.getEntrada());
		cv.put(COLUNAS[2], diaDeTrabalho.getSaida());
		cv.put(COLUNAS[3], diaDeTrabalho.getAlmoco());
		
		getWritableDatabase().insert(TABELA, null, cv);
	}
	
	public List<DiaDeTrabalho> lista() {
		List<DiaDeTrabalho> dias = new ArrayList<DiaDeTrabalho>();
		
		Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA, null);
		while(cursor.moveToNext()) {
			DiaDeTrabalho diaDeTrabalho = new DiaDeTrabalho(cursor.getLong(1), cursor.getLong(2), cursor.getString(3));
            diaDeTrabalho.setId(cursor.getLong(0));
			dias.add(diaDeTrabalho);
		}
		
		return dias;
	}

    public void deleta(DiaDeTrabalho diaDeTrabalho) {
        String[] arg = {diaDeTrabalho.getId().toString()};
        getWritableDatabase().delete(TABELA, "id = ?", arg);
    }
}
