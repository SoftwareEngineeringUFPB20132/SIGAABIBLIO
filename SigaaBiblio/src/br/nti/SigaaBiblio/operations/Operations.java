package br.nti.SigaaBiblio.operations;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import br.nti.SigaaBiblio.model.Artigo;
import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Livro;

public interface Operations {
	
	public  final int LOGIN = 1;
	public  final int CONSULTAR_ACERVO_LIVRO = 2;
	public  final int CONSULTAR_ACERVO_ARTIGO = 3;
	public  final int RENOVACAO = 4;
	public  final int MINHA_SITUACAO = 5;
	public  final int MEUS_EMPRESTIMOS = 6;
	public  final int LISTAR_BIBLIOTECAS = 7;
	public  final int LIVROS_EMPRESTADOS = 8;
	public static final int INFORMACOES_EXEMPLAR_ACERVO = 9;
	public static final int INFORMACOES_EXEMPLAR_ARTIGO = 10;
	
	public String realizarLogin(String ...parametrosDoUsuario);
	public ArrayList<Biblioteca> listarBibliotecas();
	public ArrayList<Livro> consultarAcervoLivro(String ... parametrosConsulta);
	public ArrayList<Artigo> consultarAcervoArtigo(String ... parametrosConsulta);
	public Livro informacoesLivro(String ... pararametrosLivro);
	public Artigo informacoesExemplarArtigo(String ... pararametrosArtigo);
	public ArrayList<Emprestimo> consultarSituacao(String ... parametrosUsuario);
	public ArrayList<Emprestimo> consultarEmprestimosRenovaveis(String ... parametrosUsuario);
	
	public ArrayList<Emprestimo> historicoEmprestimos(String ... parametrosUsuario);
	public String renovarEmprestimo(String ... parametrosEmprestimos);
	
	

}
