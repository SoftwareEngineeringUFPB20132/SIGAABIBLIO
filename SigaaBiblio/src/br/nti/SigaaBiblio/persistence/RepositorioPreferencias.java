package br.nti.SigaaBiblio.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RepositorioPreferencias extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "preferencias.db";
	private static final int DATABASE_VERSION=1;
	
	public RepositorioPreferencias(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		
		/* Criação das tabelas que representam as entidades*/
		database.execSQL("CREATE TABLE emprestimo(dataEmprestimo TEXT, dataDevolucao TEXT, tipoEmprestimo TEXT, dataRenovacao TEXT, prazoDevolucao TEXT, informacoes TEXT)");
		database.execSQL("CREATE TABLE renovacoes(informacoes TEXT)");
		
		//geraRepositorioFake(database);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST emprestimo");
		db.execSQL("DROP TABLE IF EXIST renovacoes");
		onCreate(db);
		
	}


	
}
