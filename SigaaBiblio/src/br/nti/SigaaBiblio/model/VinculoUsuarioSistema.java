package br.nti.SigaaBiblio.model;

import java.util.ArrayList;
import java.util.List;

public class VinculoUsuarioSistema {

	
	/** 
	 * <p>SEM_PENDENCIA = O usuário está sem nenhum pedência na biblioteca.</p>
	 * <p>POSSUI_EMPRESTIMOS_ATIVOS =  Situação quando o usuário possui empréstimos mas não estão atrasados nem o usuario está suspenso.</p>
	 * <p>ESTA_SUSPENSO =  Situação quando o usuário está suspenso.</p>
	 * <p>ESTA_MULTADO =  Situação quando o usuário está multado.</p>
	 * <p>POSSUI_EMPRESTIMOS_ATRASADOS = Situação quando o usuário está com empréstimo atrasados.</p>
	 * <p>ESTA_BLOQUEADO = Não pode realizar empréstimo pois seu cadastro foi bloqueado</p>
	 */
	private String situacao;
	private int totalEmprestimosAbertos;
	private int totalEmprestimosAtrasados;
	private boolean podeRealzarEmprestimos;
	
	
	/* Construtor utilizado para exibir informacoes na activity principal */
	public VinculoUsuarioSistema(int totalEmprestimosAbertos,
			boolean podeRealzarEmprestimos) {
		super();
		this.totalEmprestimosAbertos = totalEmprestimosAbertos;
		this.podeRealzarEmprestimos = podeRealzarEmprestimos;
		//this.emprestimosPedendentes=new ArrayList<Emprestimo>(); 
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getTotalEmprestimosAbertos() {
		return totalEmprestimosAbertos;
	}

	public void setTotalEmprestimosAbertos(int totalEmprestimosAbertos) {
		this.totalEmprestimosAbertos = totalEmprestimosAbertos;
	}

	public int getTotalEmprestimosAtrasados() {
		return totalEmprestimosAtrasados;
	}

	public void setTotalEmprestimosAtrasados(int totalEmprestimosAtrasados) {
		this.totalEmprestimosAtrasados = totalEmprestimosAtrasados;
	}

	public boolean isPodeRealzarEmprestimos() {
		return podeRealzarEmprestimos;
	}

	public void setPodeRealzarEmprestimos(boolean podeRealzarEmprestimos) {
		this.podeRealzarEmprestimos = podeRealzarEmprestimos;
	}

//	public List<Emprestimo> getEmprestimosPedendentes() {
//		return emprestimosPedendentes;
//	}
//
//	public void setEmprestimosPedendentes(List<Emprestimo> emprestimosPedendentes) {
//		this.emprestimosPedendentes = emprestimosPedendentes;
//	}
	
	
	
	
}
