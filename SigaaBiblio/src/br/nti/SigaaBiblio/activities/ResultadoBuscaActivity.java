package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Livro;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.Operations;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ResultadoBuscaActivity extends Activity {

	private List<Livro> listaLivros;
	private ListView listaResultados;
	private ArrayList<String> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_resultado_busca);
		setBackground();
		lista = new ArrayList<String>(); 
		listaLivros = new ArrayList<Livro>();
		
		listaLivros = getIntent().getParcelableArrayListExtra("Livros");	
		
		if(listaLivros==null)
			lista.add("Não foram encontrados resultados para sua pesquisa.");
			else
				for(Livro l : listaLivros){
				lista.add(l.toString());
				}
			
		
	
				
		listaResultados = (ListView) findViewById(R.id.listViewResultados);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, lista);

	    listaResultados.setAdapter(adapter);

	    final ProgressDialog pd = new ProgressDialog(ResultadoBuscaActivity.this);
		pd.setMessage("Processando...");
		pd.setTitle("Aguarde");
		pd.setIndeterminate(false);
		
		final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);

		listaResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				// TODO Auto-generated method stub
				@Override
				public void onItemClick(AdapterView<?> parent, final View view, int position,
						long id) {
					// TODO Auto-generated method stub
					String selected = parent.getItemAtPosition(position).toString();
					final String livroSelecionado=listaLivros.get(lista.indexOf(selected)).getRegistroNoSistema();
					
					new AsyncTask<Void,Void,Void>(){
						
						/**
						 * Retorno:   Registro         : int
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
						 * 
						 */
						
						@Override
						protected void onPreExecute() {
							// TODO Auto-generated method stub
							
							super.onPreExecute();
							pd.show();
						}
						
						
						@Override
						protected Void doInBackground(Void... arg0) {
							
							
							Livro livro = operacao.informacoesLivro(livroSelecionado);
							Intent intent = new Intent(ResultadoBuscaActivity.this, DadosTituloActivity.class );
							intent.putExtra("InformacoesLivro", livro);
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

					
			}

		});

	}	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
			
		}
		return false;
	}
	
	
	@Override
	protected void onResume(){
		
		super.onResume();
		setBackground();
				
	}
	


	public void setBackground(){
		LinearLayout lb = (LinearLayout) findViewById(R.id.login_body);
		LinearLayout lh = (LinearLayout) findViewById(R.id.login_header);
		
//		
		
		
		if(PrefsActivity.getCor(this).equals("Azul")){
			lb.setBackgroundResource(R.color.background_softblue);
			lh.setBackgroundResource(R.drawable.background_azul1);
			
		}else 
			if(PrefsActivity.getCor(this).equals("Vermelho")){
				lb.setBackgroundResource(R.color.background_softred);
				lh.setBackgroundResource(R.drawable.background_vermelho1);
				
			}else
				if(PrefsActivity.getCor(this).equals("Verde")){
					lb.setBackgroundResource(R.color.background_softgreen);
					lh.setBackgroundResource(R.drawable.background_verde1);
					
				}

		}

	
	



}