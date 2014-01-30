package br.nti.SigaaBiblio.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Biblioteca implements Parcelable {

	String id;
	String nome;
	
	public Biblioteca(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Biblioteca(Parcel in) {
		readFromParcel(in); }
	
	private void readFromParcel(Parcel in) {
		this.id=in.readString(); 
		this.nome=in.readString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.id); 
		dest.writeString(this.nome); 	}

		public static final Parcelable.Creator<Biblioteca> CREATOR = new Parcelable.Creator<Biblioteca>() {
			public Biblioteca createFromParcel(Parcel in){
				return new Biblioteca(in);
			}
			public Biblioteca[] newArray(int size) { 
				return new Biblioteca[size]; 
				}
			
		};
		    		
		 
}
	
	
	

