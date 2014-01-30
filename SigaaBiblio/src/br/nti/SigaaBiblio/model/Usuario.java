package br.nti.SigaaBiblio.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;

public class Usuario {
	
	public static Usuario INSTANCE;
	
	private String idUsuarioBiblioteca;
	private String nome;
	private String matricula;
	private boolean isAluno;
	private String curso;
	private String urlFoto;
	private String unidade;
	private VinculoUsuarioSistema userVinculo;
	private String login;
	private String senha;
	
	public VinculoUsuarioSistema getUserVinculo() {
		return userVinculo;
	}
	public void setUserVinculo(VinculoUsuarioSistema userVinculo) {
		this.userVinculo = userVinculo;
	}
	public String getIdUsuarioBiblioteca() {
		return idUsuarioBiblioteca;
	}
	public void setIdUsuarioBiblioteca(String idUsuarioBiblioteca) {
		this.idUsuarioBiblioteca = idUsuarioBiblioteca;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public boolean isAluno() {
		return isAluno;
	}
	public void setAluno(boolean isAluno) {
		this.isAluno = isAluno;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	@Override
	public String toString(){
		return nome + "\n" + matricula + "\n"
				+ String.valueOf(isAluno ? "Aluno" : "Servidor " + unidade)
				+ "\n" + curso;
	}
	
	public Bitmap geraBitmap() throws MalformedURLException, IOException, InterruptedException, ExecutionException, TimeoutException{
		
		AsyncTask<Void, Void, Bitmap> async = new AsyncTask<Void, Void, Bitmap>(){
			@Override
			protected Bitmap doInBackground(Void... params) {
				try {
					return BitmapFactory.decodeStream(new URL(urlFoto).openConnection().getInputStream());
				} catch (Exception ex){
					return null;
				}
			}
		
		};

		async.execute();
		return async.get(20, TimeUnit.SECONDS);
	}
	
	public static Usuario prepareUsuario() {
		return INSTANCE = new Usuario();
	}
	
public static final class Usuarios implements BaseColumns {
		
		public static final String IDUSUARIOBIBLIOTECA = "idUsuarioBiblioteca";
		public static final String NOME = "nome";
		public static final String MATRICULA = "matricula";
		public static final String ISALUNO = "isAluno";
		public static final String CURSO = "curso";
		public static final String URLFOTO = "urlFoto";
		public static final String UNIDADE = "unidade";
		public static final String LOGIN =  "login";
		public static final String SENHA = "senha";
		
		public static final String TIPO = "usuario";
		
		public static final String [] COLUNAS = {
			Usuarios._ID, Usuarios.IDUSUARIOBIBLIOTECA, Usuarios.NOME, Usuarios.MATRICULA, ""+Usuarios.ISALUNO,Usuarios.CURSO,
			Usuarios.URLFOTO,Usuarios.UNIDADE, Usuarios.LOGIN, Usuarios.SENHA};
		
		// Daqui para baixo � usado para provedor de conte�do (ContentProvider)
		public static final String AUTHORITY = "br.nti.SigaaBiblio.provider";
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY +"/"+ TIPO);

		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static Uri getUriId(long id) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
			return uri;
		}
		
		public static long getIdFromUri(Uri uri) {
			String id = uri.getPathSegments().get(1);
			return Long.parseLong(id);
		}
	}
	
	
}
