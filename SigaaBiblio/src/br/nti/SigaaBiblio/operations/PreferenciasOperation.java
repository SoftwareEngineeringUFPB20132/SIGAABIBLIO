package br.nti.SigaaBiblio.operations;

import java.util.ArrayList;



import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.persistence.RepositorioLocalSigaa;
import br.nti.SigaaBiblio.persistence.RepositorioPreferencias;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PreferenciasOperation {
	
	private Context context;
	
	public PreferenciasOperation(Context context){
		this.context=context;
	}
	
	public boolean salvarHistorico(ArrayList<Emprestimo> emprestimos){
		
		SQLiteDatabase sqLite = new RepositorioPreferencias(context).getWritableDatabase();
		 
		ContentValues content = new ContentValues();
 
        for(Emprestimo e : emprestimos){
        
        	content.put("dataEmprestimo", e.getDataEmprestimo());
        	content.put("dataDevolucao", e.getDataDevolucao());
        	content.put("tipoEmprestimo", e.getTipoEmprestimo());
    		content.put("dataRenovacao", e.getDataRenovacao());
    		content.put("prazoDevolucao", e.getPrazoDevolucao());
    		content.put("informacoes", e.getInformacoes());
    		
    		sqLite.insert("emprestimo", null, content);
    		
        }
		
        sqLite.close();
		
		return true;
	}
	
	public ArrayList<Emprestimo> recuperarHistorico(){
		
		SQLiteDatabase sqLite = new  RepositorioPreferencias(context).getWritableDatabase();
		
		String tabela="emprestimo";
		String[] colunas = {"dataEmprestimo", "dataRenovacao","dataDevolucao", "prazoDevolucao", "tipoEmprestimo", "informacoes"};
		
		Cursor resultados = sqLite.query(tabela,colunas,null,null,null,null,null);
		
		ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		
		
		if (resultados.getCount() > 0){
			
			resultados.moveToFirst();
			
			do{
				
				String prazo="",informacoes="",devolucao="",tipo="",dataRenovacao="";
				
				String dataEmprestimo=resultados.getString(resultados.getColumnIndex("dataEmprestimo"));
				prazo = resultados.getString(resultados.getColumnIndex("prazoDevolucao"));
				informacoes= resultados.getString(resultados.getColumnIndex("informacoes"));
				dataRenovacao=resultados.getString(resultados.getColumnIndex("dataRenovacao"));
				devolucao=resultados.getString(resultados.getColumnIndex("dataDevolucao"));
				tipo = resultados.getString(resultados.getColumnIndex("tipoEmprestimo"));
				
				Emprestimo emprestimo = new Emprestimo(tipo,dataEmprestimo,dataRenovacao,informacoes,prazo,devolucao);
				emprestimos.add(emprestimo);
				
			}while(resultados.moveToNext());
		
		}
		
		resultados.close();
		sqLite.close();
		
		return emprestimos;
	}

	public boolean salvaRenovacoes(String infoRenovacao){
		
		SQLiteDatabase sqLite = new RepositorioPreferencias(context).getWritableDatabase();
		 
		ContentValues content = new ContentValues();
		
		content.put("informacoes", infoRenovacao);
		
		sqLite.insert("renovacoes", null, content);
		
		sqLite.close();
		
		return true;
 
	}
	
	public ArrayList<String> recuperarRenovacoes(){
		
		SQLiteDatabase sqLite = new  RepositorioPreferencias(context).getWritableDatabase();
		
		String tabela="renovacoes";
		String[] colunas = {"informacoes"};
		Cursor resultados = sqLite.query(tabela,colunas,null,null,null,null,null);
		
		ArrayList<String> renovacoes = new ArrayList<String>();
		
		if(resultados.getCount()>0){
			
			resultados.moveToFirst();
			
			do{
				String renovacao = resultados.getString(0);
				renovacoes.add(renovacao);
			}while(resultados.moveToNext());
			
		}
		
		resultados.close();
		sqLite.close();
		return renovacoes;
	}
	
	public void resetHistoricoRepositorio(){
	
		SQLiteDatabase db = new RepositorioPreferencias(context).getWritableDatabase();
		db.execSQL("DELETE  FROM emprestimo");
		db.close();
	}
	
	public void resetRenovacoesRepositorio(){
		
		SQLiteDatabase db = new RepositorioPreferencias(context).getWritableDatabase();
		db.execSQL("DELETE  FROM renovacoes");
		db.close();
		
		}
	

}

