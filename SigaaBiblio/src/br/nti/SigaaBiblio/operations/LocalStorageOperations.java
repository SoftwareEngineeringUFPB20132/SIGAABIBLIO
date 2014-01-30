package br.nti.SigaaBiblio.operations;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.nti.SigaaBiblio.model.Artigo;
import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Livro;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.model.VinculoUsuarioSistema;
import br.nti.SigaaBiblio.persistence.RepositorioLocalSigaa;

public class LocalStorageOperations implements Operations {

	private Context context;
	
	public LocalStorageOperations(Context context){
		this.context=context;
	}
	
	@Override
	public String realizarLogin(String... parametrosDoUsuario) {
		// TODO Auto-generated method stub
		Usuario user = Usuario.prepareUsuario();
		String login = parametrosDoUsuario[0];
		String senha = parametrosDoUsuario[1];		
		user.setLogin(login);
		user.setSenha(senha);
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getReadableDatabase();
		String tabela="usuario";
		String[] colunas = {"idUsuarioBiblioteca","nome","matricula","isAluno","curso","urlFoto","unidade","login","senha"};
		//verifica se o Login está correto
		String where = "login = ?";
		String args[] = {login};
		Cursor resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
		
		String mensagem="";
		//verifica login
		if (resultados.getCount() > 0){ //login correto
			resultados.moveToFirst();
			where = "senha = ?";
			args = new String[] {senha};
			resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
			if (resultados.getCount() > 0){ //senha correta
					resultados.moveToFirst();
					user.setNome(resultados.getString(resultados.getColumnIndex("nome")));
					user.setIdUsuarioBiblioteca(""+resultados.getInt(resultados.getColumnIndex("idUsuarioBiblioteca")));
					boolean aluno = resultados.getInt(resultados.getColumnIndex("isAluno"))==0?false:true;
					user.setAluno(aluno);
					user.setUrlFoto(resultados.getString(resultados.getColumnIndex("urlFoto")));
					
					//recupera o vinculo
					tabela="vinculoUsuarioSistema";
					colunas = new String[]{"totalEmprestimosAbertos","podeRealizarEmprestimos"};
					//verifica se o Login está correto
					where = "idUsuarioBiblioteca = ?";
					args = new String[]{user.getIdUsuarioBiblioteca()};
					Cursor resultadosVinculos = sqLite.query(tabela,colunas,where,args,null,null,null);
					if (resultadosVinculos.getCount() > 0){
						resultadosVinculos.moveToFirst();
						int emprestimos = resultadosVinculos.getInt(resultadosVinculos.getColumnIndex("totalEmprestimosAbertos"));
						boolean realizaEmprestimo = resultadosVinculos.getInt(resultadosVinculos.getColumnIndex("podeRealizarEmprestimos"))==0?false:true;
						 
						user.setUserVinculo(new VinculoUsuarioSistema(emprestimos, realizaEmprestimo));
						resultadosVinculos.close();
					}
					
					if (user.isAluno()) {
						user.setMatricula(resultados.getString(resultados.getColumnIndex("matricula")));
						user.setCurso(resultados.getString(resultados.getColumnIndex("curso")));
	
					} else {
						user.setUnidade(resultados.getString(resultados.getColumnIndex("unidade")));
					}
					
					mensagem = user.toString();
					resultados.close();
					sqLite.close();

			}else{
				resultados.close();
				sqLite.close();
				return "SERVER#ERROSenha Incorreta";
			}
				
				
		}
		else{
			resultados.close();
			sqLite.close();
			return "SERVER#ERROLogin Incorreto";
		}
		
		 
		
		return mensagem;
	}
	
	@Override
	public ArrayList<Biblioteca> listarBibliotecas() {
		// TODO Auto-generated method stub
		
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getReadableDatabase();
		String tabela="biblioteca";
		String[] colunas = {"id","nome"};
		
		Cursor resultados = sqLite.query(tabela,colunas,null,null,null,null,null);
		ArrayList<Biblioteca> lista = new ArrayList<Biblioteca>();
		if (resultados.getCount() > 0){
		    resultados.moveToFirst();
		
		do{
			
			lista.add(new Biblioteca(resultados.getString(resultados.getColumnIndex("id")),
					resultados.getString(resultados.getColumnIndex("nome"))));
		}while(resultados.moveToNext());
	}
		resultados.close();
		sqLite.close();
		return lista;
	}

	@Override
	public ArrayList<Livro> consultarAcervoLivro(String... parametrosConsulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Artigo> consultarAcervoArtigo(String... parametrosConsulta) {
		// TODO Auto-generated method stub
		
		return  null;
	}

	@Override
	public Livro informacoesLivro(String... pararametrosLivro) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Artigo informacoesExemplarArtigo(String... pararametrosArtigo) {
		// TODO Auto-generated method stub
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getReadableDatabase();
		String tabela="artigo";
		String[] colunas = {"id" , "autor", "titulo", "palavrasChave", "autoresSecundarios", "paginas", "localPublicacao", "editora", "ano", "resumo", "biblioteca", "codigoDeBarras",
				"localizacao", "situacao", "volume", "numero", "edicao", "anoCronologico", "diaMes"};
		String where = "id = ?";
		String args[] = {""+pararametrosArtigo[0]};
		Cursor resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
		Artigo artigo = null;
		String biblioteca="", codigoDeBarras="", localizacao="",situacao="",
				anoCronologico="",ano="",diaMes="",volume="",numero="",autorSecundario="",
				intervaloPaginas="",localPublicacao="", editora="",anoExemplar="",resumo="";
		
		if (resultados.getCount() > 0){
			resultados.moveToFirst();
			//localPublicacao, editora, ano, resumo, biblioteca, codigoDeBarras, localizacao, situacao, volume, numero, edicao, anoCronologico, diaMes
			biblioteca= resultados.getString(resultados.getColumnIndex("biblioteca"));
			codigoDeBarras= resultados.getString(resultados.getColumnIndex("codigoDeBarras"));
			localizacao= resultados.getString(resultados.getColumnIndex("localizacao"));
			situacao= resultados.getString(resultados.getColumnIndex("situacao"));
			anoCronologico= resultados.getString(resultados.getColumnIndex("anoCronologico"));
			ano= resultados.getString(resultados.getColumnIndex("ano"));
			diaMes= resultados.getString(resultados.getColumnIndex("diaMes"));
			volume= resultados.getString(resultados.getColumnIndex("volume"));
			numero= resultados.getString(resultados.getColumnIndex("biblioteca"));
			autorSecundario= resultados.getString(resultados.getColumnIndex("autoresSecundarios"));
			intervaloPaginas= resultados.getString(resultados.getColumnIndex("paginas"));
			localPublicacao= resultados.getString(resultados.getColumnIndex("localPublicacao"));
			editora= resultados.getString(resultados.getColumnIndex("editora"));
			resumo= resultados.getString(resultados.getColumnIndex("resumo"));
			
			artigo = new Artigo(autorSecundario,intervaloPaginas,localPublicacao,editora,ano,
					  resumo,biblioteca,codigoDeBarras,localizacao,situacao,volume,numero,
					  anoCronologico,diaMes);
			
		}
		resultados.close();
		sqLite.close();
		
		return artigo;

	}

	@Override
	public ArrayList<Emprestimo> consultarSituacao(String... parametrosUsuario) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getReadableDatabase();
		
		String tabela="usuario";
		String[] colunas = {"idUsuarioBiblioteca"};
		String where = "login = ? AND senha =  ?";
		String[] args = {parametrosUsuario[0],parametrosUsuario[1]};
		Cursor resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
		ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		int idUsuario;
		if (resultados.getCount() > 0){
			resultados.moveToFirst();
			idUsuario = resultados.getInt(resultados.getColumnIndex("idUsuarioBiblioteca"));
			resultados.close();
			
			tabela="emprestimo";
			colunas = new String[]{"codigoBarras" , "autor", "titulo", "ano", "dataEmprestimo", "dataRenovacao","dataDevolucao", "prazoDevolucao", "biblioteca", "renovavel"};
			where = "idUsuarioBiblioteca = ? AND dataRenovacao = ?";
			args = new String[]{""+idUsuario,"-"};
			
			resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
			
			if (resultados.getCount() > 0){
				
				resultados.moveToFirst();
				
				do{
					
					
					String codigoBarras="",autor="",titulo="",ano="",dataEmprestimo="",dataRenovacao="",devolucao="",biblioteca="",prazo="";
					Boolean renovavel=false;
					
					biblioteca= resultados.getString(resultados.getColumnIndex("biblioteca"));
				    codigoBarras= resultados.getString(resultados.getColumnIndex("codigoBarras"));
					ano= resultados.getString(resultados.getColumnIndex("ano"));
					autor=resultados.getString(resultados.getColumnIndex("autor"));
					dataEmprestimo=resultados.getString(resultados.getColumnIndex("dataEmprestimo"));
					dataRenovacao=resultados.getString(resultados.getColumnIndex("dataRenovacao"));
					devolucao=resultados.getString(resultados.getColumnIndex("dataDevolucao"));
					renovavel=resultados.getInt(resultados.getColumnIndex("renovavel"))==1?true:false;
					prazo = resultados.getString(resultados.getColumnIndex("prazoDevolucao"));
					Emprestimo emprestimo = new Emprestimo(codigoBarras, autor, titulo, ano, dataEmprestimo, dataRenovacao, devolucao, prazo, biblioteca, renovavel);
					emprestimos.add(emprestimo);
			
				}while(resultados.moveToNext());
				
					
			}//if emprestimo
				
		} //if autenticacao
				
	  resultados.close();
	  sqLite.close();
	  return emprestimos;	
		
	}

	@Override
	public ArrayList<Emprestimo> consultarEmprestimosRenovaveis(
			String... parametrosUsuario) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getReadableDatabase();
		
		String tabela="usuario";
		String[] colunas = {"idUsuarioBiblioteca"};
		String where = "login = ? AND senha =  ?";
		String[] args = {parametrosUsuario[0],parametrosUsuario[1]};
		Cursor resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
		ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		int idUsuario;
		if (resultados.getCount() > 0){
			resultados.moveToFirst();
			idUsuario = resultados.getInt(resultados.getColumnIndex("idUsuarioBiblioteca"));
			resultados.close();
			
			tabela="emprestimo";
			colunas = new String[]{"dataEmprestimo", "prazoDevolucao", "informacoes", "codigoEmprestimo"};
			where = "idUsuarioBiblioteca = ? AND dataRenovacao = ?";
			args = new String[]{""+idUsuario,"-"};
			
			resultados = sqLite.query(tabela,colunas,where,args,null,null,null);
			
			if (resultados.getCount() > 0){
				
				resultados.moveToFirst();
				
				do{
					
					
					String dataEmprestimo="",prazo="",informacoes="",codigoEmprestimo="";
					Boolean renovavel=false;
					
					dataEmprestimo=resultados.getString(resultados.getColumnIndex("dataEmprestimo"));
					prazo = resultados.getString(resultados.getColumnIndex("prazoDevolucao"));
					informacoes= resultados.getString(resultados.getColumnIndex("informacoes"));
					codigoEmprestimo=resultados.getString(resultados.getColumnIndex("codigoEmprestimo"));
					Emprestimo emprestimo = new Emprestimo(dataEmprestimo,codigoEmprestimo,informacoes,prazo);
					emprestimos.add(emprestimo);
			
				}while(resultados.moveToNext());
				
					
			}//if emprestimo
				
		} //if autenticacao
				
	  resultados.close();
	  sqLite.close();
	  return emprestimos;	

	}

	
	@Override
	public String renovarEmprestimo(String... parametrosEmprestimos) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getWritableDatabase();
		 
		
		Calendar now = Calendar.getInstance();
		String dataRenovacao= ""+now.get(Calendar.DATE)+"/"+(now.get(Calendar.MONTH)+1)+"/"+now.get(Calendar.YEAR);
		String prazoDevolucao="";
		try {
			Date d = new SimpleDateFormat("dd/MM/yyyy").parse(dataRenovacao);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			gc.add(Calendar.DATE, 20);
			prazoDevolucao = ""+gc.get(Calendar.DATE)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String tabela="emprestimo";
		String where = "codigoEmprestimo = ?";
		String[] args = {parametrosEmprestimos[2]};
		
		
		ContentValues valores = new ContentValues();
	    
	    valores.put("dataRenovacao", dataRenovacao);
	    valores.put("prazoDevolucao",prazoDevolucao);
	    valores.put("renovavel",0);
	    
	    int sucess = sqLite.update(tabela, valores, where, args);
	    
	    String mensagem="";
	    if(sucess>0){
	    	mensagem="Emprestimo renovado com Sucesso";
	    }else
	    	mensagem="Ocorreu um erro na operação";
	//codigoEmprestimo
		return mensagem;
	}

	
	
	@Override
	public ArrayList<Emprestimo> historicoEmprestimos(
			String... parametrosUsuario) {
		// TODO Auto-generated method stub
		SQLiteDatabase sqLite = new  RepositorioLocalSigaa(context).getWritableDatabase();
		
		String tabela="emprestimo";
		String[] colunas = {"dataEmprestimo", "dataRenovacao","dataDevolucao", "prazoDevolucao", "tipoEmprestimo", "informacoes"};
		
		
		Date dataInicial=null,dataFinal=null;

		/*
		 * Obtem o periodo de emprestimos
		 */
		try {

			String[] dataInicalraw = parametrosUsuario[2].split("-");
			String dataInicalString = dataInicalraw[0]+"/"+dataInicalraw[1]+"/"+dataInicalraw[2];
			dataInicial = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicalString);
		
			String[] dataFinalraw = parametrosUsuario[3].split("-");
			String dataFinalString = dataFinalraw[0]+"/"+dataFinalraw[1]+"/"+dataFinalraw[2];
			dataFinal = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinalString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		Cursor resultados = sqLite.query(tabela,colunas,null,null,null,null,null);
		
		
		ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		
		
		if (resultados.getCount() > 0){
			
			resultados.moveToFirst();
			
			try{
				do{
					
					String dataEmprestimo=resultados.getString(resultados.getColumnIndex("dataEmprestimo"));
					
					//passa a data que esta em string para date
					Date dataEmprestimoDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataEmprestimo);
					if(dataInicial.compareTo(dataEmprestimoDate)>=0){
						if(dataFinal.compareTo(dataEmprestimoDate)<=0){
							
							String prazo="",informacoes="",devolucao="",tipo="",dataRenovacao="";
							
							prazo = resultados.getString(resultados.getColumnIndex("prazoDevolucao"));
							informacoes= resultados.getString(resultados.getColumnIndex("informacoes"));
							dataRenovacao=resultados.getString(resultados.getColumnIndex("dataRenovacao"));
							devolucao=resultados.getString(resultados.getColumnIndex("dataDevolucao"));
							tipo = resultados.getString(resultados.getColumnIndex("tipoEmprestimo"));
							
							Emprestimo emprestimo = new Emprestimo(tipo,dataEmprestimo,dataRenovacao,informacoes,prazo,devolucao);
							emprestimos.add(emprestimo);
					
							
						}
					}
					
					
				}while(resultados.moveToNext());
			
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
				
				
		}//if emprestimo
				
		resultados.close();
		sqLite.close();
		
		return emprestimos;
	}

	
	

}
