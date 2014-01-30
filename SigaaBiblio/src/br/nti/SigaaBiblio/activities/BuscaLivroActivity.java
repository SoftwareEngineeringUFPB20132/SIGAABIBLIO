package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Livro;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.Operations;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.utils.HttpUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BuscaLivroActivity extends Activity {


	Button goResultados;
	Spinner bibliotecasDisponiveis;
	List<String> bibliotecasLista;
	List<String> bibliotecasIds;
	ArrayList<Biblioteca> bibliotecas;
	String bibliotecaSelecionada;
	EditText titulo;
	EditText autor;
	EditText assunto;
	String tituloPref;
	String autorPref;
	String assuntoPref;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_busca_livro);
		titulo = (EditText) findViewById(R.id.editTextTituloLivro);
		assunto= (EditText)findViewById(R.id.editTextAssuntoLivro);
		autor = (EditText)findViewById(R.id.editTextAutorLivro);
		setBackground();
		//recupera o nome das bibliotecas
		
		bibliotecasLista=new ArrayList<String>();
		bibliotecas = getIntent().getParcelableArrayListExtra("Bibliotecas");
		if(bibliotecas==null){
			bibliotecas=loadBibliotecas();
		}
		for(Biblioteca b : bibliotecas){
			bibliotecasLista.add(b.getNome());
		}
		//Log.d("MARCILIO_DEBUG", d.toString());
		
		//Exibe as bibliotecas no spinner
		bibliotecasDisponiveis = (Spinner)findViewById(R.id.spinnerBibliotecaLivro);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bibliotecasLista);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bibliotecasDisponiveis.setAdapter(spinnerArrayAdapter);
		bibliotecasDisponiveis.setSelection(arrayAdapter.getPosition("Buscar em Todas as Unidades"));
		bibliotecasDisponiveis.setOnItemSelectedListener(new BiblioItemSelectedListener());
		bibliotecaSelecionada="0";
		//seta o onclick
		
		
		if (PrefsActivity.getCamposPesquisa(this)){ //recupera os campos da pesquisa
			if (getPreferences(MODE_PRIVATE).contains("tituloLivro")){
				tituloPref = getPreferences(MODE_PRIVATE).getString("tituloLivro", "");
				titulo.setText(tituloPref);
			}
				
			if (getPreferences(MODE_PRIVATE).contains("autorLivro")){
				autorPref = getPreferences(MODE_PRIVATE).getString("autorLivro", "");
				
				autor.setText(autorPref);
			}
				
			if(getPreferences(MODE_PRIVATE).contains("assuntoLivro")){
				assuntoPref = getPreferences(MODE_PRIVATE).getString("assuntoLivro", "");
				
				assunto.setText(assuntoPref);
			}
			
			
		}//end preferencias
	
		
		
//		
//		((Button)findViewById(R.id.buttonCrambridge)).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				startActivity(new Intent(BuscaLivroActivity.this, CambridgeActivity.class));				
//			}
//		});
		

		
	}
	
	@Override
	protected void onResume(){
		
		super.onResume();
		setBackground();
				
	}
	
		
		
	/* Realiza Uma pesquisa
	 */
		
		public void pesquisar(View v) {
				
			titulo = (EditText)findViewById(R.id.editTextTituloLivro);
			autor = (EditText)findViewById(R.id.editTextAutorLivro);
			assunto = (EditText)findViewById(R.id.editTextAssuntoLivro);
			
			
			if (PrefsActivity.getCamposPesquisa(this)){
				
				getPreferences(MODE_PRIVATE).edit().putString("tituloLivro", titulo.getText().toString().trim()).commit();
				getPreferences(MODE_PRIVATE).edit().putString("autorLivro", autor.getText().toString().trim()).commit();
				getPreferences(MODE_PRIVATE).edit().putString("assuntoLivro", assunto.getText().toString().trim()).commit();
				
			}
			
			
			final ProgressDialog pd = new ProgressDialog(BuscaLivroActivity.this);
			pd.setMessage("Processando...");
			pd.setTitle("Aguarde");
			pd.setIndeterminate(false);
			final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);

			
			new AsyncTask<Void,Void,Void>(){
				//IdBiblioteca":"9763","TituloBusca":"Metodologia","AutorBusca":"","AssuntoBusca":""}
				/**
				 * Retorno: Livros
				 * Autor  (String)
				 * Titulo (String)
				 * Edicao (String)
				 * Ano    (Int)
				 * QuantidadeAtivos (Int)
				 */
				
				
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					pd.show();
				}
				
				
				@Override
				protected Void doInBackground(Void... arg0) {
					
					
					ArrayList<Livro> livros = operacao.consultarAcervoLivro(bibliotecaSelecionada,titulo.getText().toString(),autor.getText().toString(),assunto.getText().toString());
					Intent intent = new Intent(BuscaLivroActivity.this, ResultadoBuscaActivity.class );
					intent.putExtra("Livros", livros);
					startActivity(intent);
					
					return null;
				}
				
				@Override
				protected void onPostExecute(Void v) {
					// TODO Auto-generated method stub
					super.onPostExecute(v);
					if(pd!= null && pd.isShowing())
						pd.dismiss();
				}
				
				
			}.execute();
			
//			

	    }
	
	/*
	 * Classe interna que trata qual biblioteca foi selecionada
	 */
	
		private class BiblioItemSelectedListener implements OnItemSelectedListener {

		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        String selected = parent.getItemAtPosition(pos).toString();
		        
		        bibliotecaSelecionada=bibliotecas.get(bibliotecasLista.indexOf(selected)).getId();
		        
		        Log.d("MARCILIO_DEBUG", bibliotecaSelecionada);
		    }

		    public void onNothingSelected(AdapterView parent) {
		        // Do nothing.
		    }
		}

	
	
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.busca_livro, menu);
			return true;
		}
		

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, PrefsActivity.class));
				return true;
			case R.id.cambridge_search:{
				Intent intent = new Intent(this, CambridgeActivity.class);
				String tituloC = titulo.getText().toString().trim();
				String autorC = autor.getText().toString().trim();
				String assuntoC = assunto.getText().toString().trim();
				intent.putExtra("busca", tituloC+" "+autorC+" "+assuntoC);
				startActivity(intent);
				return true;
				
			}
				
			}
			return false;
		}
		
		
		
		
		
		
		

	
	public void setBackground(){
		LinearLayout lb = (LinearLayout) findViewById(R.id.login_body);
		LinearLayout lh = (LinearLayout) findViewById(R.id.login_header);
		TextView t = (TextView) findViewById(R.id.login_header_2);
//		
//		
		
		
		if(PrefsActivity.getCor(this).equals("Azul")){
			lb.setBackgroundResource(R.color.background_softblue);
			lh.setBackgroundResource(R.drawable.background_azul1);
			t.setBackgroundResource(R.drawable.background_azul2);
		}else 
			if(PrefsActivity.getCor(this).equals("Vermelho")){
				lb.setBackgroundResource(R.color.background_softred);
				lh.setBackgroundResource(R.drawable.background_vermelho1);
				t.setBackgroundResource(R.drawable.background_vermelho2);
			}else
				if(PrefsActivity.getCor(this).equals("Verde")){
					lb.setBackgroundResource(R.color.background_softgreen);
					lh.setBackgroundResource(R.drawable.background_verde1);
					t.setBackgroundResource(R.drawable.background_verde2);
				}

		}
	
		public ArrayList<Biblioteca> loadBibliotecas() {
			
			final ProgressDialog pd = new ProgressDialog(BuscaLivroActivity.this);
			pd.setMessage("Processando...");
			pd.setTitle("Aguarde");
			pd.setIndeterminate(false);
			bibliotecas=null;
			final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);
			final Semaphore sincronizador = new Semaphore(0);
			/*
			 * OBTEM O NOMES DAS BIBLIOTECAS ATIVAS
			 */
			
			new AsyncTask<Void,Void,Void>(){

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					pd.show();
				}
				
				
				@Override
				protected Void doInBackground(Void... arg0) {
						bibliotecas = operacao.listarBibliotecas();
						sincronizador.release();
						//Log.d("MARCILIO_DEBUG", ""+bibliotecas);
					
					return null;
				}
				
				@Override
				protected void onPostExecute(Void v) {
					// TODO Auto-generated method stub
					super.onPostExecute(v);
					if(pd!= null && pd.isShowing())
						pd.dismiss();
				}
				
				}.execute();			

				try {
					sincronizador.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bibliotecas;
				
		}


}