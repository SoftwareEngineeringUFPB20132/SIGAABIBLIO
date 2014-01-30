package br.nti.SigaaBiblio.operations;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.nti.SigaaBiblio.utils.HttpUtils;

import br.nti.SigaaBiblio.activities.BuscaLivroActivity;
import br.nti.SigaaBiblio.activities.ExemplarArtigoActivity;
import br.nti.SigaaBiblio.activities.ResultadoBuscaActivity;
import br.nti.SigaaBiblio.model.Artigo;
import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.ExemplarLivro;
import br.nti.SigaaBiblio.model.Livro;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.model.VinculoUsuarioSistema;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class JSONOperations implements Operations {

	
	public static String SISTEMA = "http://testes.nti.ufpb.br/sigaa";
//	public static String SISTEMA = "http://150.165.250.55:8080/sigaa";
	public static String HOST = SISTEMA
			+ "/public/biblioteca/SigaaAndroidServlet";
	public Context contextoAplicacao;
	
	public JSONOperations(Context contextoAplicacao){
		this.contextoAplicacao=contextoAplicacao;
	}
	
	
	
	public String realizarLogin(String ... parametrosDoUsuario){
		
		Usuario user = Usuario.prepareUsuario();
		String login = parametrosDoUsuario[0];
		String senha = getMd5Hash(parametrosDoUsuario[1]);		
		user.setLogin(login);
		user.setSenha(senha);
		
		String erro = "";
		String mensagem = "";
		String jsonString = "";
		try {
			// JSONObject pode ser um String, HashMap ou um MBean
			Map<String, String> map = new HashMap<String, String>();
			map.put("Operacao", String.valueOf(Operations.LOGIN));
			map.put("Login", login);
			map.put("Senha", senha);
			JSONObject inputsJson = new JSONObject(map);

			// Parametros: HOST, Identificador do Hash, Hash
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());

			JSONObject jsonResult = new JSONObject(jsonString);
			
			
			erro = jsonResult.getString("Error");
			mensagem = jsonResult.getString("Mensagem");

			if(!erro.isEmpty()){
				return "SERVER#ERRO"+erro;
			}
			
			user.setNome(jsonResult.getString("Nome"));
			user.setIdUsuarioBiblioteca(jsonResult.getString("IdUsuarioBiblioteca"));
			user.setAluno( Boolean.valueOf(jsonResult.getString("isAluno")));
			user.setUrlFoto(SISTEMA	+ jsonResult.getString("Foto"));
			user.setUserVinculo(new VinculoUsuarioSistema(jsonResult.getInt("EmprestimosAbertos"),jsonResult.getBoolean("PodeRealizarEmprestimo")));
			if (user.isAluno()) {
				user.setMatricula(jsonResult.getString("Matricula"));
				user.setCurso(jsonResult.getString("Curso"));

			} else {
				user.setUnidade(jsonResult.getString("Unidade"));
			}


			
		} catch (Exception e) {
			
				mensagem = "Ocorreu um erro";
				e.printStackTrace();
				return mensagem;
				
			}
		
		if (!erro.isEmpty()) {
			return "SERVER#ERRO"+erro;
		} 
		
		mensagem=user.toString();
//		Toast.makeText(contextoAplicacao, user.toString(), Toast.LENGTH_LONG)
//		.show();

		return mensagem;
	}
	
		
		
	
	
	/*
	 * Encriptografa a senha do usuário para que possa ser autenticada
	 * no servidor SIGAA
	 */
	public static String getMd5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String md5 = number.toString(16);

			while (md5.length() < 32)
				md5 = "0" + md5;

			return md5;
		} catch (NoSuchAlgorithmException e) {
			Log.e("MD5", e.getLocalizedMessage());
			return null;
		}
	}
	
	
	@Override
	public ArrayList<Biblioteca> listarBibliotecas() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.LISTAR_BIBLIOTECAS));
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		String bibliotecas="";
		ArrayList<Biblioteca> bibliotecasLista = new ArrayList<Biblioteca>();
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);
			bibliotecas = resposta.getString("Bibliotecas");
			JSONObject bibliotecasJson = new JSONObject(bibliotecas);
			bibliotecasLista.add(new Biblioteca("0", "Buscar em Todas as Unidades")); //desativa o filtro da biblioteca lá no servidor
			Iterator<String> keys = bibliotecasJson.keys(); //descobre as chaves que são os ids das bibliotecas
			while(keys.hasNext()){
				String key=keys.next();
				bibliotecasLista.add(new Biblioteca(key, bibliotecasJson.getString(key)));
				//Log.d("MARCILIO_DEBUG", "isso é uma key "+key);
			}
			Log.d("MARCILIO_DEBUG", bibliotecas);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return bibliotecasLista;
	}
	


	@Override
	public ArrayList consultarAcervoLivro(String... parametrosConsulta) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.CONSULTAR_ACERVO_LIVRO));
		map.put("IdBiblioteca",parametrosConsulta[0]);
		map.put("TituloBusca", parametrosConsulta[1]);
		map.put("AutorBusca", parametrosConsulta[2]);
		map.put("AssuntoBusca", parametrosConsulta[3]);
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		String livros="";
		ArrayList<Livro> listaLivros = new ArrayList<Livro>();
		
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);
			livros =resposta.getString("Livros");
			JSONObject livrosJson = new JSONObject(livros);
		
			
			String autor="", titulo="", edicao="", ano="", quantidade="", registroSistema="";
			Iterator<String> keys = livrosJson.keys(); //descobre as chaves que são os ids das bibliotecas
			if(keys.hasNext())
				while(keys.hasNext()){
					String key=keys.next();
					JSONObject livroJSON = livrosJson.getJSONObject(key);
					autor = JSONObject.NULL.equals(livroJSON.get("Autor"))?"":livroJSON.getString("Autor");
					titulo = JSONObject.NULL.equals(livroJSON.get("Titulo"))?"":livroJSON.getString("Titulo");
					edicao = JSONObject.NULL.equals(livroJSON.get("Edicao"))?"":livroJSON.getString("Edicao");
					ano = JSONObject.NULL.equals(livroJSON.get("Ano"))?"":livroJSON.getString("Ano");
					quantidade = JSONObject.NULL.equals(livroJSON.get("QuantidadeAtivos"))?"":livroJSON.getString("QuantidadeAtivos");
					 //= key;
					registroSistema= JSONObject.NULL.equals(livroJSON.get("IdDetalhes"))?"-":livroJSON.getString("IdDetalhes");

					Livro livro = new Livro(autor,titulo,edicao,ano,quantidade,registroSistema);
					listaLivros.add(livro);
					//Log.d("MARCILIO_DEBUG", "isso é uma key "+key);
				}
				else{
					return null;
				}
				//Log.d("MARCILIO_DEBUG",livros);//ou Artigos
		} catch (Exception ex){ ex.printStackTrace();}
		
		
		return listaLivros;
	}

	//
	@Override
	public ArrayList consultarAcervoArtigo(String... parametrosConsulta) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.CONSULTAR_ACERVO_LIVRO));
		map.put("IdBiblioteca",parametrosConsulta[0]);
		map.put("TituloBusca", parametrosConsulta[1]);
		map.put("AutorBusca", parametrosConsulta[2]);
		map.put("AssuntoBusca", parametrosConsulta[3]);
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		String artigosRaw="";
		ArrayList<Artigo> listaArtigos = new ArrayList<Artigo>();
		
		String autor="", titulo="", palavrasChave="",id="";
		

		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);
			artigosRaw =resposta.getString("Artigos");
			JSONObject artigosJSON = new JSONObject(artigosRaw);
			Iterator<String> keys = artigosJSON.keys();
			if(keys.hasNext())
				while(keys.hasNext()){
						String key=keys.next();
					
						JSONObject artigoJSON = artigosJSON.getJSONObject(key);
						autor = JSONObject.NULL.equals(artigoJSON.get("Autor"))?"-":artigoJSON.getString("Autor");
						titulo = JSONObject.NULL.equals(artigoJSON.get("Titulo"))?"-":artigoJSON.getString("Titulo");
						palavrasChave = JSONObject.NULL.equals(artigoJSON.get("Assunto"))?"-":artigoJSON.getString("Assunto");
						id= JSONObject.NULL.equals(artigoJSON.get("IdDetalhes"))?"-":artigoJSON.getString("IdDetalhes");
						String[] pChaves = palavrasChave.split("\\#\\$\\&");
						String palavraChaveFinal="";
						for(String c : pChaves){
							palavraChaveFinal+=c+"; ";
						}
						Artigo artigo = new Artigo(autor,titulo,palavraChaveFinal,id);
						listaArtigos.add(artigo);
				}
				else
					listaArtigos=null;
			//Log.d("MARCILIO_DEBUG",livros);//ou Artigos
			}catch (Exception ex){ ex.printStackTrace();}
			
		return listaArtigos;
	}



	@Override
	public Livro informacoesLivro(String... pararametrosLivro) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.INFORMACOES_EXEMPLAR_ACERVO));
		map.put("IdDetalhes", pararametrosLivro[0]);					
		JSONObject inputsJson = new JSONObject(map);
		Livro livro=null;
		String registro="", numero="", titulo="",subtitulo="",assunto="",autor="",
				autorSecundario="",publicacao="",editora="",ano="",notas="";
		
			
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			JSONObject resposta = new JSONObject(jsonString); 
			registro= JSONObject.NULL.equals(resposta.get("Registro"))?"-":resposta.getString("Registro");
			numero= JSONObject.NULL.equals(resposta.get("NumeroChamada"))?"-":resposta.getString("NumeroChamada");
			titulo=JSONObject.NULL.equals(resposta.get("Titulo"))?"-":resposta.getString("Titulo");
			subtitulo=JSONObject.NULL.equals(resposta.get("SubTitulo"))?"-":resposta.getString("SubTitulo");
			assunto=JSONObject.NULL.equals(resposta.get("Assunto"))?"-":resposta.getString("Assunto");
			autor=JSONObject.NULL.equals(resposta.get("Autor"))?"-":resposta.getString("Autor");
			publicacao=JSONObject.NULL.equals(resposta.get("Publicacao"))?"-":resposta.getString("Publicacao");
			editora=JSONObject.NULL.equals(resposta.get("Editora"))?"-":resposta.getString("Editora");
			ano=JSONObject.NULL.equals(resposta.get("AnoPublicacao"))?"-":resposta.getString("AnoPublicacao");
			notas=JSONObject.NULL.equals(resposta.get("NotasGerais"))?"-":resposta.getString("NotasGerais");
			autorSecundario= JSONObject.NULL.equals(resposta.get("AutorSecundario"))?"-":resposta.getString("AutorSecundario");	 
			
			livro = new Livro(autor,titulo,ano,registro,numero,subtitulo,assunto,publicacao,editora,notas,autorSecundario);
			
			JSONObject exemplaresJson = new JSONObject(resposta.getString("Exemplares"));
			Iterator<String> keys = exemplaresJson.keys();
			String codigoBarras="",tipoMaterial="",colecao="",status="",disponivel="",localizacao="";
			ArrayList<ExemplarLivro> exemplares= new ArrayList<ExemplarLivro>();
			while(keys.hasNext()){
				String key=keys.next();

				JSONObject exemplarJson= exemplaresJson.getJSONObject(key);
				codigoBarras= JSONObject.NULL.equals(exemplarJson.get("CodigoBarras"))?"-":exemplarJson.getString("CodigoBarras");
				tipoMaterial= JSONObject.NULL.equals(exemplarJson.get("TipoMaterial"))?"-":exemplarJson.getString("TipoMaterial");
				colecao= JSONObject.NULL.equals(exemplarJson.get("Colecao"))?"-":exemplarJson.getString("Colecao");
				status= JSONObject.NULL.equals(exemplarJson.get("Status"))?"-":exemplarJson.getString("Status");
				disponivel= JSONObject.NULL.equals(exemplarJson.get("Disponivel"))?"-":exemplarJson.getString("Disponivel");
				localizacao= JSONObject.NULL.equals(exemplarJson.get("Localizacao"))?"-":exemplarJson.getString("Localizacao");
				exemplares.add(new ExemplarLivro(codigoBarras,tipoMaterial,colecao,localizacao,disponivel));
			}
			
			livro.setExemplares(exemplares); //adiciona o exemplar
			Log.d("MARCILIO_DEBUG", resposta.toString());
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return livro;
	}



	@Override
	public Artigo informacoesExemplarArtigo(String... pararametrosArtigo) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.INFORMACOES_EXEMPLAR_ARTIGO));
		map.put("IdDetalhes", pararametrosArtigo[0]);					
		JSONObject inputsJson = new JSONObject(map);
		Artigo artigo = null;
		String biblioteca="", codigoDeBarras="", localizacao="",situacao="",
				anoCronologico="",ano="",diaMes="",volume="",numero="",autorSecundario="",
				intervaloPaginas="",localPublicacao="", editora="",anoExemplar="",resumo="";
				
		
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			JSONObject resposta = new JSONObject(jsonString);					
			
			biblioteca= JSONObject.NULL.equals(resposta.get("Biblioteca"))?"-":resposta.getString("Biblioteca");
			codigoDeBarras= JSONObject.NULL.equals(resposta.get("CodigoBarras"))?"-":resposta.getString("CodigoBarras");
			localizacao= JSONObject.NULL.equals(resposta.get("Localizacao"))?"-":resposta.getString("Localizacao");
			situacao= JSONObject.NULL.equals(resposta.get("Situacao"))?"-":resposta.getString("Situacao");
			anoCronologico= JSONObject.NULL.equals(resposta.get("AnoCronologico"))?"-":resposta.getString("AnoCronologico");
			ano= JSONObject.NULL.equals(resposta.get("Ano"))?"-":resposta.getString("Ano");
			diaMes= JSONObject.NULL.equals(resposta.get("DiaMes"))?"-":resposta.getString("DiaMes");
			volume= JSONObject.NULL.equals(resposta.get("Volume"))?"-":resposta.getString("Volume");
			numero= JSONObject.NULL.equals(resposta.get("Numero"))?"-":resposta.getString("Numero");
			autorSecundario= JSONObject.NULL.equals(resposta.get("AutorSecundario"))?"-":resposta.getString("AutorSecundario");
			intervaloPaginas= JSONObject.NULL.equals(resposta.get("IntervaloPaginas"))?"-":resposta.getString("IntervaloPaginas");
			localPublicacao= JSONObject.NULL.equals(resposta.get("LocalPublicacao"))?"-":resposta.getString("LocalPublicacao");
			editora= JSONObject.NULL.equals(resposta.get("Editora"))?"-":resposta.getString("Editora");
			anoExemplar= JSONObject.NULL.equals(resposta.get("AnoExemplar"))?"-":resposta.getString("AnoExemplar");
			resumo= JSONObject.NULL.equals(resposta.get("Resumo"))?"-":resposta.getString("Resumo");

			artigo = new Artigo(autorSecundario,intervaloPaginas,localPublicacao,editora,ano,
					  resumo,biblioteca,codigoDeBarras,localizacao,situacao,volume,numero,
					  anoCronologico,diaMes);
			Log.d("MARCILIO_DEBUG", resposta.toString());
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return artigo;
	}



	@Override
	public ArrayList<Emprestimo> consultarSituacao(String... parametrosUsuario) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao",
				String.valueOf(Operations.MINHA_SITUACAO));
		map.put("Login", parametrosUsuario[0]);
		map.put("Senha", parametrosUsuario[1]);
		ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);
			parametrosUsuario[2] = resposta.getString("Mensagem"); //informa a mensagem
			resposta = new JSONObject(resposta.getString("Emprestimos"));
			//intent.putExtra("Mensagem", mensagem);
			String key = "" ;
			JSONObject content;
			
			Emprestimo emp;
			for(Iterator obj = resposta.keys(); obj.hasNext();){
				key = (String)obj.next();
				content = new JSONObject(resposta.getString(key));
					
				emp = new Emprestimo(content.getString("CodigoDeBarras"),content.getString("Autor"),
						content.getString("Titulo"),content.getString("Ano"),content.getString("DataEmprestimo"), 
						content.getString("DataRenovacao"), content.getString("Devolucao"), "", content.getString("Biblioteca"),
						content.getBoolean("Renovavel"));
				emprestimos.add(emp);
				}
										
		
		
	} catch (Exception ex) {
		ex.printStackTrace();
	}


		
		return emprestimos;
	}



	@Override
	public ArrayList<Emprestimo> consultarEmprestimosRenovaveis(
			String... parametrosUsuario) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.LIVROS_EMPRESTADOS));
		map.put("Login", parametrosUsuario[0]);
		map.put("Senha", parametrosUsuario[1]);
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		ArrayList<Emprestimo> emprestimos= null ;
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);					
			resposta = new JSONObject(resposta.getString("EmprestimosAbertos"));
			
			String informacao="",dataEmprestimo="",prazo="",idMaterial="";

			
			Log.d("MARCILIO_DEBUG", resposta.toString());
			Iterator it = resposta.keys();
			if(it.hasNext())
				emprestimos=new ArrayList<Emprestimo>();
			while (it.hasNext()) {
			String key = (String) it.next();
			JSONObject emprestimoJson = resposta.getJSONObject(key);
			//Log.d("MARCILIO_DEBUG", emprestimoJson.toString());
			dataEmprestimo= JSONObject.NULL.equals(emprestimoJson.get("DataEmprestimo"))?"-":emprestimoJson.getString("DataEmprestimo");
			idMaterial= JSONObject.NULL.equals(emprestimoJson.get("IdMaterial"))?"-":emprestimoJson.getString("IdMaterial");
			informacao= JSONObject.NULL.equals(emprestimoJson.get("Informacao"))?"-":emprestimoJson.getString("Informacao");
			prazo= JSONObject.NULL.equals(emprestimoJson.get("Prazo"))?"-":emprestimoJson.getString("Prazo");
			
			Emprestimo emprestimo = new Emprestimo(dataEmprestimo,idMaterial,informacao,prazo);
	
					
			emprestimos.add(emprestimo);
			Log.d("MARCILIO_DEBUG", emprestimoJson.toString());
		}
		} catch (Exception ex){
			ex.printStackTrace();
		}

		
		return emprestimos;
	}



	@Override
	public ArrayList<Emprestimo> historicoEmprestimos(
			String... parametrosUsuario) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String jsonString;
		map.put("Operacao", String.valueOf(Operations.MEUS_EMPRESTIMOS));
		map.put("Login", parametrosUsuario[0]);
		map.put("Senha", parametrosUsuario[1]);
		map.put("Inicio", parametrosUsuario[2]);
		map.put("Fim", parametrosUsuario[3]);
		JSONObject inputsJson = new JSONObject(map);
		JSONObject resposta;
		ArrayList<Emprestimo> emprestimos= new ArrayList<Emprestimo>();
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			resposta = new JSONObject(jsonString);					
			resposta = new JSONObject(resposta.getString("Emprestimos"));
			
			String tipoEmprestimo="",dataEmprestimo="",dataRenovacao="",informacao="",
					prazoDevolucao="",dataDevolucao="";
			
			Iterator it = resposta.keys();
			while (it.hasNext()) {
			JSONObject emprestimoJson = resposta.getJSONObject((String) it.next());
			tipoEmprestimo= JSONObject.NULL.equals(emprestimoJson.get("TipoEmprestimo"))?"-":emprestimoJson.getString("TipoEmprestimo");
			dataEmprestimo= JSONObject.NULL.equals(emprestimoJson.get("DataEmprestimo"))?"-":emprestimoJson.getString("DataEmprestimo");
			dataRenovacao= (!emprestimoJson.has("DataRenovacao")) || JSONObject.NULL.equals(emprestimoJson.get("DataRenovacao"))?"-":emprestimoJson.getString("DataRenovacao");
			informacao= JSONObject.NULL.equals(emprestimoJson.get("Informacao"))?"-":emprestimoJson.getString("Informacao");
			prazoDevolucao= JSONObject.NULL.equals(emprestimoJson.get("PrazoDevolucao"))?"-":emprestimoJson.getString("PrazoDevolucao");
			dataDevolucao =(!emprestimoJson.has("DataDevolucao")) || JSONObject.NULL.equals(emprestimoJson.get("DataDevolucao"))?"-":emprestimoJson.getString("DataDevolucao");
			Emprestimo emprestimo = new Emprestimo(tipoEmprestimo,dataEmprestimo,dataRenovacao,
													informacao,prazoDevolucao,dataDevolucao);
	
					
			emprestimos.add(emprestimo);
			//Log.d("MARCILIO_DEBUG", obj.toString());
		}
		} catch (Exception ex){
			ex.printStackTrace();
		}

		
		return emprestimos;
	}



	@Override
	public String renovarEmprestimo(String... parametrosEmprestimos) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();					
		
		map.put("Operacao", String.valueOf(Operations.RENOVACAO));
		map.put("Login", parametrosEmprestimos[0]);
		map.put("Senha", parametrosEmprestimos[0]);		
		map.put("IdLivrosRenovacao", parametrosEmprestimos[2]); 
		JSONObject inputsJson = new JSONObject(map);					
		String respostaRenovacao=null;
		String jsonString;
		try {
			jsonString = HttpUtils.urlContentPost(HOST, "sigaaAndroid", inputsJson.toString());
			JSONObject resposta = new JSONObject(jsonString);	
			resposta = new JSONObject(resposta.getString(("RenovacaoEmprestimo")));
			Log.d("MARCILIO_DEBUG","LOL "+ resposta.toString());
			String autenticacao = resposta.getString("CodigoAutenticacao");
			String info = resposta.getString("InfoRenovacao");
			respostaRenovacao= info+"Código de Autenticação: "+autenticacao; 
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respostaRenovacao;
	}
	
	
	
	
}//end class