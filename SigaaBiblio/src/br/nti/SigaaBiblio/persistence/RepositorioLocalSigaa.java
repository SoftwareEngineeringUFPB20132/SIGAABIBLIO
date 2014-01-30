package br.nti.SigaaBiblio.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RepositorioLocalSigaa extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "sigaaBiblio.db";
	private static final int DATABASE_VERSION=1;
	
	public RepositorioLocalSigaa (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		
		/* Criação das tabelas que representam as entidades*/
		database.execSQL("CREATE TABLE vinculoUsuarioSistema(idUsuarioBiblioteca INTEGER PRIMARY KEY, situacao TEXT, totalEmprestimosAbertos INTEGER, totalEmprestimosAtrasados, podeRealizarEmprestimos INTEGER)");
		
		database.execSQL("CREATE TABLE usuario(idUsuarioBiblioteca INTEGER, nome TEXT, matricula TEXT, isAluno INTEGER, curso TEXT, urlFoto TEXT, unidade TEXT, login TEXT, senha TEXT)");
		
		database.execSQL("CREATE TABLE biblioteca(id TEXT PRIMARY KEY, nome TEXT)");
		
		database.execSQL("CREATE TABLE livro (registroNoSistema TEXT PRIMARY KEY, autor TEXT, titulo TEXT, edicao TEXT, ano TEXT, quantidade TEXT, numeroChamada TEXT, subTitulo TEXT, assunto TEXT, localDePublicacao TEXT, editora TEXT, autorSecundario TEXT, notas TEXT)");

		database.execSQL("CREATE TABLE exemplarLivro(id INTEGER PRIMARY KEY, registroLivro TEXT, codigoDeBarras TEXT, tipoDeMaterial TEXT, colecao TEXT, biblioteca TEXT, localizacao TEXT, situacao TEXT)");
		
		database.execSQL("CREATE TABLE artigo(id TEXT PRIMARY KEY, autor TEXT, titulo TEXT, palavrasChave TEXT, autoresSecundarios TEXT, paginas TEXT, localPublicacao TEXT, editora TEXT, ano TEXT, resumo TEXT, biblioteca TEXT, codigoDeBarras TEXT, localizacao TEXT, situacao TEXT, volume TEXT, numero TEXT, edicao TEXT, anoCronologico TEXT, diaMes TEXT)");
		
		database.execSQL("CREATE TABLE emprestimo(idUsuarioBiblioteca INTEGER, codigoEmprestimo TEXT PRIMARY KEY, autor TEXT, titulo TEXT, ano TEXT, dataEmprestimo TEXT, dataDevolucao TEXT, tipoEmprestimo TEXT, dataRenovacao TEXT, prazoDevolucao TEXT, biblioteca TEXT, localizacao TEXT, emAberto INTEGER, codigoBarras TEXT, informacoes TEXT, renovavel INTEGER)");
		
		//geraRepositorioFake(database);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST vinculoUsuarioSistema");
		db.execSQL("DROP TABLE IF EXIST usuario");
		db.execSQL("DROP TABLE IF EXIST biblioteca");
		db.execSQL("DROP TABLE IF EXIST livro");
		db.execSQL("DROP TABLE IF EXIST exemplarLivro");
		db.execSQL("DROP TABLE IF EXIST artigo");
		db.execSQL("DROP TABLE IF EXIST emprestimo");
		onCreate(db);
		
	}
	
	
	

	
}
