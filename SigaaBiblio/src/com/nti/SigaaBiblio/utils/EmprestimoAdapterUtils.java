package com.nti.SigaaBiblio.utils;

/*
 *  Esta classe armazena as informações que serão exibidas na lisview
 *  e diz se elas foram checadas 
 */

public class EmprestimoAdapterUtils {

	 private String dados;
	 private boolean selecionado;

	  public EmprestimoAdapterUtils(String dados) {
	    this.dados = dados;
	    selecionado = false;
	  }
	  
	  public String getDados() {
			return dados;
		}

	  public void setDados(String dados) {
			this.dados = dados;
	  }
	  

	  public boolean isSelected() {
	    return selecionado;
	  }

	  public void setSelected(boolean selected) {
	    this.selecionado = selected;
	  }

}
