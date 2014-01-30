package br.nti.SigaaBiblio.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Livro implements Parcelable {

	

	/* Campos dados basico */
	private String autor;
	private String titulo;
	private String edicao;
	private String ano;
	private String quantidade;
	/* Campos dados do Titulo */
	private String registroNoSistema;
	private String numeroChamada;
	private String subTitulo;
	private String assunto;
	private String localDePublicação;
	private String editora;
	private String autorSecundario;
	private String notas;
	
	
	/*Campo Exemplar*/
	ArrayList<ExemplarLivro> exemplares;
	
	
	/* 
	 * Construtor padrão, as outras informações são adquiridas através de outras
	 * solicitações ao servidor
	 */
	
	
	public Livro(String autor, String titulo, String edicao, String ano,
			String quantidade, String registroNoSistema) {
		super();
		this.autor = autor;
		this.titulo = titulo;
		this.edicao = edicao;
		this.ano = ano;
		this.quantidade = quantidade;
		this.registroNoSistema=registroNoSistema;
		
	}
	
	/*
	 * Construtor para operacoes de infomacoes adicionais de livros
	 */
	
	public Livro(String autor, String titulo, String ano,
			String registroNoSistema, String numeroChamada, String subTitulo,
			String assunto, String localDePublicação, String editora, String notas,
			String autorSecundario/*ArrayList<ExemplarLivro> exemplares*/) {
		super();
		this.autor = autor;
		this.titulo = titulo;
		this.ano = ano;
		this.registroNoSistema = registroNoSistema;
		this.numeroChamada = numeroChamada;
		this.subTitulo = subTitulo;
		this.assunto = assunto;
		this.localDePublicação = localDePublicação;
		this.editora = editora;
		//this.exemplares = exemplares;
		this.autorSecundario = autorSecundario;
		this.notas= notas;
	}
	
	/*
	 * construtor para inicializar a lista
	 */

	public Livro(){
		this.exemplares= new ArrayList<ExemplarLivro>();
	}

	
	public String getAutorSecundario() {
		return autorSecundario;
	}

	public void setAutorSecundario(String autorSecundario) {
		this.autorSecundario = autorSecundario;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	
	
	public ArrayList<ExemplarLivro> getExemplares() {
		return exemplares;
	}


	public void setExemplares(ArrayList<ExemplarLivro> exemplares) {
		this.exemplares = exemplares;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getEdicao() {
		return edicao;
	}


	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}


	public String getAno() {
		return ano;
	}


	public void setAno(String ano) {
		this.ano = ano;
	}


	public String getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}


	public String getRegistroNoSistema() {
		return registroNoSistema;
	}


	public void setRegistroNoSistema(String registroNoSistema) {
		this.registroNoSistema = registroNoSistema;
	}


	public String getNumeroChamada() {
		return numeroChamada;
	}


	public void setNumeroChamada(String numeroChamada) {
		this.numeroChamada = numeroChamada;
	}


	public String getSubTitulo() {
		return subTitulo;
	}


	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}


	public String getAssunto() {
		return assunto;
	}


	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}


	public String getLocalDePublicação() {
		return localDePublicação;
	}


	public void setLocalDePublicação(String localDePublicação) {
		this.localDePublicação = localDePublicação;
	}


	public String getEditora() {
		return editora;
	}


	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	public String toString(){
		return "Autor: "+this.autor+"\nTítulo: "+this.titulo+"\nEdição: "+this.edicao+
				"\nAno: "+this.ano+"\nQuantidade: "+this.quantidade;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.autor);
		dest.writeString(this.titulo);
		dest.writeString(this.edicao);
		dest.writeString(this.ano);
		dest.writeString(this.quantidade);
		dest.writeString(this.registroNoSistema);
		dest.writeString(this.numeroChamada);
		dest.writeString(this.subTitulo);
		dest.writeString(this.assunto);
		dest.writeString(this.localDePublicação);
		dest.writeString(this.editora);
		dest.writeString(this.autorSecundario);
		dest.writeString(this.notas);
		dest.writeList(this.exemplares);
		
	}
	
	public Livro(Parcel in) {
		this();
		readFromParcel(in); }
	
	private void readFromParcel(Parcel in) {
		this.autor=in.readString(); 
		this.titulo=in.readString();
		this.edicao=in.readString();
		this.ano=in.readString();
		this.quantidade=in.readString();
		this.registroNoSistema=in.readString();
		this.numeroChamada=in.readString();
		this.subTitulo=in.readString();
		this.assunto=in.readString();
		this.localDePublicação=in.readString();
		this.editora=in.readString();
		this.autorSecundario=in.readString();
		this.notas=in.readString();
		in.readTypedList(exemplares, ExemplarLivro.CREATOR);
		
	}
	
	public static final Parcelable.Creator<Livro> CREATOR = new Parcelable.Creator<Livro>() {
		public Livro createFromParcel(Parcel in){
			return new Livro(in);
		}
		public Livro[] newArray(int size) { 
			return new Livro[size]; 
			}
		
	};
	
}
