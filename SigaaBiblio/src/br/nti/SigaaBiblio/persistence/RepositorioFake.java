package br.nti.SigaaBiblio.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RepositorioFake {

	private Context contexto;
	
	public RepositorioFake(Context contexto){
		this.contexto=contexto;
	}
	
	/*
	 * adiciona dados no banco de dados para que possam ser feitas consultas 
	 */
	
	
	
	public void gerarRepositorioFake(){
		//add usuario fake
				SQLiteDatabase db = new RepositorioLocalSigaa(contexto).getWritableDatabase();
				
				db.execSQL("INSERT INTO usuario(idUsuarioBiblioteca,nome,matricula,isAluno,curso,urlFoto,unidade,login,senha) VALUES (1234,'Marcilio Lemos','11021635',1,'Ciência da Computação','http://2.bp.blogspot.com/-4NdMRwn7Bx0/Tudv3VHUMyI/AAAAAAAAAhc/l9USqlkIj58/s320/3.+Shaka+de+Virgem.+Dohko.jpg','-','marciliolemos','123')");
				//add vinculo fake
				db.execSQL("INSERT INTO vinculoUsuarioSistema(idUsuarioBiblioteca, situacao,totalEmprestimosAbertos,totalEmprestimosAtrasados,podeRealizarEmprestimos) VALUES (1234,'regular',2,0,1)");
//				//add bibliotecas fake
				db.execSQL("INSERT INTO biblioteca(id,nome) VALUES ('2730','CT- Biblioteca Setorial do CT')");
				db.execSQL("INSERT INTO biblioteca(id,nome) VALUES ('3730','CC- Biblioteca CENTRAL')");
				//add emprestimos fake
				db.execSQL("INSERT INTO emprestimo(idUsuarioBiblioteca,codigoEmprestimo,autor,titulo, ano, dataEmprestimo, dataDevolucao, tipoEmprestimo, dataRenovacao, prazoDevolucao, biblioteca, localizacao, emAberto , codigoBarras, informacoes,renovavel) VALUES (1234,'111','Tanenbaum, Andrew S','Sistemas Operacionais Modernos','2003','29/8/2013','-','normal','-','10/9/2013','Biblioteca Setorial do CT','L2532',1,'000141','Sistemas Operacionais Modernos - Biblioteca Setorial do CT',1)");
				db.execSQL("INSERT INTO emprestimo(idUsuarioBiblioteca,codigoEmprestimo,autor,titulo, ano, dataEmprestimo, dataDevolucao, tipoEmprestimo, dataRenovacao, prazoDevolucao, biblioteca, localizacao, emAberto , codigoBarras, informacoes,renovavel) VALUES (1234,'222','Tanenbaum, Andrew S','Redes de Computadores','2005','2013-8-29','10/8/2013','normal','-','01/09/2013','Biblioteca Central','R4332',0,'222141','Redes de Computadores - Biblioteca Central',0)");
//				
				//add livro fake
				db.execSQL("INSERT INTO livro(registroNoSistema, autor, titulo, edicao, ano, quantidade, numeroChamada, subTitulo, assunto, localDePublicacao, editora, autorSecundario, notas) VALUES ('3333', 'Cormen', 'Introdução a Algoritmos','3.e.d','2009','1','004.421I61','-','Algoritmos','MIT','MIT Press','-','-')");
				db.execSQL("INSERT INTO livro(registroNoSistema, autor, titulo, edicao, ano, quantidade, numeroChamada, subTitulo, assunto, localDePublicacao, editora, autorSecundario, notas) VALUES ('4444', 'Foley', 'Fundamentals of Interative Computer Graphics', '2 e.d.', '1982','1','004.92F66','-','Computacao Grafica', 'Los Angeles','Pressman','-','-')");
//				
//				//add exemplar fake
				db.execSQL("INSERT INTO exemplarLivro(id , registroLivro, codigoDeBarras, tipoDeMaterial, colecao, biblioteca, localizacao, situacao) VALUES (666, '3333','19662','Papel','Biblioteca CT','Biblioteca CT','CT - Engenharia','Disponível')");
				db.execSQL("INSERT INTO exemplarLivro(id , registroLivro, codigoDeBarras, tipoDeMaterial, colecao, biblioteca, localizacao, situacao) VALUES (777, '4444','12892','Papel','Biblioteca Central - Computação','Biblioteca Central','Biblioteca Cental', 'Disponível')");
				
				//add artigo fake
				db.execSQL("INSERT INTO artigo(id , autor, titulo, palavrasChave, autoresSecundarios, paginas, localPublicacao, editora, ano, resumo, biblioteca, codigoDeBarras, localizacao, situacao, volume, numero, edicao, anoCronologico, diaMes) VALUES (5555,'Ana Emilia','Algoritmo SIMPLEX','algoritmos','-','8','Paraíba','SVR','2010','O artigo fala sobre o método SIMPLEX', 'Biblioteca Central','213627','CCENL23','Disponível','2','5','3 nd', '-','-')");

				db.close();
	}
	
	
	public void resetRepositorioFake() {
		SQLiteDatabase db = new RepositorioLocalSigaa(contexto).getWritableDatabase();
		db.execSQL("DELETE  FROM vinculoUsuarioSistema");
		db.execSQL("DELETE  FROM usuario");
		db.execSQL("DELETE  FROM biblioteca");
		db.execSQL("DELETE  FROM livro");
		db.execSQL("DELETE  FROM exemplarLivro");
		db.execSQL("DELETE  FROM artigo");
		db.execSQL("DELETE  FROM emprestimo");
		db.close();
	}
}
