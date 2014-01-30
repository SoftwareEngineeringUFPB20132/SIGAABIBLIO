package br.nti.SigaaBiblio.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ExemplarLivro implements Parcelable {

	
	private String codigoDeBarras;
	private String tipoDeMaterial;
	private String colecao;
	private String biblioteca;
	private String localizacao;
	private String situacao;
	
	public ExemplarLivro(String codigoDeBarras, String tipoDeMaterial,
			String colecao, /*String biblioteca, */String localizacao,
			String situacao) {
		super();
		this.codigoDeBarras = codigoDeBarras;
		this.tipoDeMaterial = tipoDeMaterial;
		this.colecao = colecao;
		//this.biblioteca = biblioteca;
		this.localizacao = localizacao;
		this.situacao = situacao;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.codigoDeBarras);
		dest.writeString(this.tipoDeMaterial);
		dest.writeString(this.colecao);
		dest.writeString(this.biblioteca);
		dest.writeString(this.localizacao);
		dest.writeString(this.situacao);
		
	}

	public ExemplarLivro(Parcel in) {
		
		readFromParcel(in); }
	
	private void readFromParcel(Parcel in) {
		this.codigoDeBarras = in.readString();;
		this.tipoDeMaterial = in.readString();;
		this.colecao = in.readString();;
		this.biblioteca = in.readString();;
		this.localizacao = in.readString();;
		this.situacao = in.readString();;

	}
	
	public static final Parcelable.Creator<ExemplarLivro> CREATOR = new Parcelable.Creator<ExemplarLivro>() {
		public ExemplarLivro createFromParcel(Parcel in){
			return new ExemplarLivro(in);
		}
		public ExemplarLivro[] newArray(int size) { 
			return new ExemplarLivro[size]; 
			}
		
	};

	
	
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public String getTipoDeMaterial() {
		return tipoDeMaterial;
	}

	public void setTipoDeMaterial(String tipoDeMaterial) {
		this.tipoDeMaterial = tipoDeMaterial;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public String getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(String biblioteca) {
		this.biblioteca = biblioteca;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
	
}
