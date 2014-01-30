package br.ufrn.sigaa.biblioteca.android_service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.json.JSONObject;

@SuppressWarnings("serial")
public class SigaaAndroidServlet extends HttpServlet {
	// Iron Araujo 23/08/2013
	/**
	 * Recebe Requisição
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String inputString = request.getParameter("sigaaAndroid");
		String error = "";
		Map<String, String> map = new HashMap<String, String>();

		try {
			JSONObject inputValues = new JSONObject(inputString);			
			int operation = inputValues.getInt("Operacao");
			String login;
			String senha;
			switch(operation){	
				/**
				 * Realizar Login
				 * 
				 * Input: Operacao : LOGIN
				 * 		  Login : String
				 * 		  Senha : String (Senha em md5)
				 * 
				 * Output: Nome    				  : String
				 * 		   isAluno 				  : String
				 * 		   IdUsuarioBiblioteca    : int
				 * 		   Foto    				  : String (URL da foto)
				 * 	       EmprestimosAbertos 	  : int (Quantidade de empréstimos abertos)
				 * 		   PodeRealizarEmprestimo : boolean
				 * 		   Matricula 			  : String (Discente)
				 * 		   Curso 				  : String (Discente)
				 * 		   Unidade 				  : String   (Servidor)
				 */
				case Operations.LOGIN:
					login = inputValues.getString("Login");
					senha = inputValues.getString("Senha");
					GeneralOperationAndroid.validaLogin(login, senha, map, request);
					break;
				
				/**
				 * Lista das Bibliotecas
				 * 
				 * Input: 	Operacao: LISTAR_BIBLIOTECAS
				 * 
				 * Output:  Bibliotecas : String
				 */
				case Operations.LISTAR_BIBLIOTECAS:
					GeneralOperationAndroid.listaBibliotecas(map);
					break;
					
				/**
				 * Consulta de Material no Acervo
				 * 
				 * Input: Operacao: CONSULTAR_ACERVO_ARTIGO / CONSULTAR_ACERVO_LIVRO
				 * 		  IdBiblioteca : int
				 * 		  TituloBusca  : String
				 * 		  AutorBusca   : String
				 * 		  AssuntoBusca : String
				 * 
				 * Output: Id_Detalhes     : int
				 * 		   Autor           : String
				 * 		   Titulo 		   : String
				 * 		   Edicao 		   : String
				 * 		   Ano    		   : int
				 * 		   QuantidadeAtivos: int
				 */    
				case Operations.CONSULTAR_ACERVO_ARTIGO:
				case Operations.CONSULTAR_ACERVO_LIVRO:
					int idBiblioteca = inputValues.getInt("IdBiblioteca");
					String tituloBusca = inputValues.getString("TituloBusca");
					String autorBusca = inputValues.getString("AutorBusca");
					String assuntoBusca = inputValues.getString("AssuntoBusca");
					GeneralOperationAndroid.pesquisarAcervo(map, idBiblioteca, tituloBusca, autorBusca, assuntoBusca);
					break;
					
				/**
				 * Situacao do Usuario
				 * 
				 * Input: Operacao: MINHA_SITUACAO (Emprestimos Realizados Pelos Usuario)
				 * 		  Login : String
				 * 		  Senha : String
				 * 
				 * Output: Lista de Materiais:
				 * 			CodigoDeBarras : String
				 * 			Autor          : String
				 * 			Titulo         : String
				 * 			Ano            : int
				 * 		    DataEmprestimo : Date
				 * 			DataRenovacao  : Date
				 * 			Devolucao      : Date
				 * 			Biblioteca     : String
				 * 			Renovavel      : boolean
				 * 				
				 */
				
				case Operations.MINHA_SITUACAO:
					login = inputValues.getString("Login");
					senha = inputValues.getString("Senha");
					GeneralOperationAndroid.minhaSituacao(login, senha,map);
					break;
					
				/** Emprestimos do Usuario por intervalo de Tempo
				 * 
				 * 	Input : Operacao : MEUS_EMPRESTIMOS
				 * 			Login    : String
				 * 			Senha    : String
				 * 			Inicio   : Date
				 * 			Fim      : Date
				 * 
				 * Output : TipoEmprestimo : String
				 * 			DataEmprestimo : Date
				 * 			DataRenovacao  : Date
				 * 			PrazoDevolucao : Date
				 * 			DataDevolucao  : Date
				 * 			Informacao     : String
				 * 
				 */
					
				case Operations.MEUS_EMPRESTIMOS:
					login = inputValues.getString("Login");
					senha = inputValues.getString("Senha");
					java.util.Date Inicio = inputValues.getString("Inicio").isEmpty() ? null :  new SimpleDateFormat("yyyy-MM-dd").parse(inputValues.getString("Inicio")); 
					java.util.Date Fim = inputValues.getString("Fim").isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(inputValues.getString("Fim"));
					GeneralOperationAndroid.historicoEmprestimos(login, senha, Inicio,Fim, map);
					break;
					
				/** Livros Emprestados 
				 * 
				 *  Input : Operacao : LIVROS_EMPRESTADOS
				 *  		Login 		: String
				 *  		Senha 		: String
				 *  
				 *  Output: Informacao     : String
				 *  		DataEmprestimo : Date
				 *  		Prazo          : Date
				 *  		IdMaterial     : int
				 *  	
				 */
				
				case Operations.LIVROS_EMPRESTADOS:
					login = inputValues.getString("Login");
					senha = inputValues.getString("Senha");
					GeneralOperationAndroid.emprestimosAbertosUsuario(login, senha, map);
					break;
				/**
				 * Renovacao de Emprestimos de Material
				 * 
				 * Input : Operacao : RENOVACAO
				 * 		   Login    : String
				 * 		   Senha    : Senha
				 * 
				 * Output : InfoRenovacao      : String
				 * 			CodigoAutenticacao : String
				 */
					
				case Operations.RENOVACAO:
					login = inputValues.getString("Login");
					senha = inputValues.getString("Senha");
					GeneralOperationAndroid.renovarEmprestimos(login, senha, inputValues, map);
					break;
					
				/**
				 * Obtém informações detalhadas sobre o Exemplar
				 * 
				 * Input   :  Operacao : INFORMACOES_EXEMPLAR_ACERVO
				 * 			  IdDetalhes : int
				 * 
				 * Output  :  Registro         : int
				 * 			  NumeroChamada    : String
				 * 			  Titulo           : String  
				 * 			  SubTitulo        : String
				 * 			  Assunto          : String
				 * 			  Autor            : String
				 * 			  AutorSecundario  : String
				 * 			  Publicacao       : String    (Local de Publicacao)
				 * 		      Editora 		   : String
				 * 			  AnoPublicacao    : int
				 * 			  NotasGerais 	   : String		
				 * 			  Exemplares:      : JSONObject (Lista dos Exemplares)
				 * 			    CodigoBarras   : String
				 * 				TipoMateria    : String
				 * 				Colecao        : String
				 * 				Status         : String
				 * 				Disponivel     : String
				 *				Localizacao    : String				
				 */
					
				case Operations.INFORMACOES_EXEMPLAR_ACERVO:
					int id = inputValues.getInt("IdDetalhes");
					GeneralOperationAndroid.informacoesExemplar(id, map);
					break;
				/**
				 * Input   : Operacao     : INFORMACOES_EXEMPLAR_ARTIGO
				 * 			 IdDetalhes   : int
				 * 
				 * Output  : Biblioteca       : String
				 * 			 CodigoBarras     : String
				 * 			 Localizacao      : String
				 * 			 Situacao         : String
				 * 			 AnoCronologico   : String
				 * 			 Ano			  : String
				 * 			 DiaMes  		  : String
				 * 			 Volume			  : String
				 * 			 Numero			  : String
				 * 			 AutorSecundario  : String
				 * 			 IntervaloPaginas : String
				 * 			 LocalPublicacao  : String
				 * 			 Editora   		  : String
				 * 			 AnoExemplar 	  : String
				 * 		     Resumo			  : String
				 * 				
				 */
				case Operations.INFORMACOES_EXEMPLAR_ARTIGO:
					int idDetalhes = inputValues.getInt("IdDetalhes");
					GeneralOperationAndroid.informacoesArtigo(idDetalhes,map);
					break;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			error = "\nException";
		}
		// map.put....

		map.put("Error", map.get("Error") + error);
		map.put("Mensagem", map.get("Mensagem")==null?"":map.get("Mensagem"));
		PrintWriter out = response.getWriter();
		JSONObject mapJSON = new JSONObject(map);
		out.println(mapJSON);
	}

}
