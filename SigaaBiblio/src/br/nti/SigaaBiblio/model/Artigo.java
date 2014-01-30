package br.nti.SigaaBiblio.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artigo implements Parcelable {
	
	/* campos basicos */
	private String autor;
	private String titulo;
	private String palavrasChave;
	private String id;
	
	/* campos dados do titulo */
	private String autoresSecundarios;
	private String paginas;
	
	private String localPublicacao;
	private String editora;
	private String ano;
	private String resumo;
	
	/* campos dados do exemplar */
	private String biblioteca;
	private String codigoDeBarras;
	private String localizacao;
	private String situacao;
	
	private String volume;
	private String numero;
	private String edicao;
	private String anoCronologico;
	private String diaMes;
	
	public Artigo(String autor, String titulo, String palavrasChave,String id) {
		super();
		this.autor = autor;
		this.titulo = titulo;
		this.palavrasChave = palavrasChave;
		this.id=id;
	}
	
	public Artigo(String autoresSecundarios, String paginas,
			String localPublicacao, String editora, String ano, String resumo,
			String biblioteca, String codigoDeBarras, String localizacao,
			String situacao, String volume, String numero, 
			String anoCronologico, String diaMes) {
		super();
		this.autoresSecundarios = autoresSecundarios;
		this.paginas = paginas;
		this.localPublicacao = localPublicacao;
		this.editora = editora;
		this.ano = ano;
		this.resumo = resumo;
		this.biblioteca = biblioteca;
		this.codigoDeBarras = codigoDeBarras;
		this.localizacao = localizacao;
		this.situacao = situacao;
		this.volume = volume;
		this.numero = numero;
		this.anoCronologico = anoCronologico;
		this.diaMes = diaMes;
	}

	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
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

	public String getPalavrasChave() {
		return palavrasChave;
	}

	public void setPalavrasChave(String palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

	public String getAutoresSecundarios() {
		return autoresSecundarios;
	}

	public void setAutoresSecundarios(String autoresSecundarios) {
		this.autoresSecundarios = autoresSecundarios;
	}

	public String getPaginas() {
		return paginas;
	}

	public void setPaginas(String paginas) {
		this.paginas = paginas;
	}

	public String getLocalPublicacao() {
		return localPublicacao;
	}

	public void setLocalPublicacao(String localPublicacao) {
		this.localPublicacao = localPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(String biblioteca) {
		this.biblioteca = biblioteca;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
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

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getAnoCronologico() {
		return anoCronologico;
	}

	public void setAnoCronologico(String anoCronologico) {
		this.anoCronologico = anoCronologico;
	}

	public String getDiaMes() {
		return diaMes;
	}

	public void setDiaMes(String diaMes) {
		this.diaMes = diaMes;
	}
	
	public String toString(){
		return "Autor: "+this.autor+"\nTítulo: "+this.titulo+"\nPalavras-Chave: "+this.palavrasChave;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		/* campos basicos */
		
		dest.writeString(this.autor);
		dest.writeString(this.titulo);
		dest.writeString(this.palavrasChave);
		dest.writeString(this.id);
		dest.writeString(this.ano);
		dest.writeString(this.autoresSecundarios);
		dest.writeString(this.paginas);
		dest.writeString(this.localPublicacao);
		dest.writeString(this.editora);
		dest.writeString(this.ano);
		dest.writeString(this.resumo);
		dest.writeString(this.biblioteca);
		dest.writeString(this.codigoDeBarras);
		dest.writeString(this.localizacao);
		dest.writeString(this.situacao);
		dest.writeString(this.volume);
		dest.writeString(this.numero);
		dest.writeString(this.edicao);
		dest.writeString(this.anoCronologico);
		dest.writeString(this.diaMes);
		
	}
	
	public Artigo(Parcel in) {
		readFromParcel(in); }
	
	private void readFromParcel(Parcel in) {
		this.autor=in.readString(); 
		this.titulo=in.readString();
		this.palavrasChave=in.readString();
		this.id=in.readString();
		this.ano=in.readString();
		this.autoresSecundarios=in.readString();
		this.paginas=in.readString();
		this.localPublicacao=in.readString();
		this.editora=in.readString();
		this.ano=in.readString();
		this.resumo=in.readString();
		this.biblioteca=in.readString();
		this.codigoDeBarras=in.readString();
		this.localizacao=in.readString();
		this.situacao=in.readString();
				
		
		this.volume=in.readString();
		this.numero=in.readString();
		this.edicao=in.readString();
		this.anoCronologico=in.readString();
		this.diaMes=in.readString();
	
		
	
	}
	
	public static final Parcelable.Creator<Artigo> CREATOR = new Parcelable.Creator<Artigo>() {
		public Artigo createFromParcel(Parcel in){
			return new Artigo(in);
		}
		public Artigo[] newArray(int size) { 
			return new Artigo[size]; 
			}
		
	};
	
	
	
	

	
		
}
