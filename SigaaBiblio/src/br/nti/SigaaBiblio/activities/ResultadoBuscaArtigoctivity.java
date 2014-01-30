package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import br.nti.SigaaBiblio.model.Artigo;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ResultadoBuscaArtigoctivity extends Activity {

	List<Artigo> listaArtigos;
	List<String> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultado_busca_artigoctivity);
		setBackground();
		lista = new ArrayList<String>(); 
		listaArtigos = new ArrayList<Artigo>();
		
		listaArtigos = getIntent().getParcelableArrayListExtra("Artigos");	
		
		if(listaArtigos==null)
			lista.add("Não foram encontrados resultados para sua pesquisa.");
			else
				for(Artigo a : listaArtigos){
				lista.add(a.toString());
				}
			
		
		ListView listaResultados = (ListView) findViewById(R.id.listViewResultadosArtigo);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, lista);

	    listaResultados.setAdapter(adapter);

	    final ProgressDialog pd = new ProgressDialog(ResultadoBuscaArtigoctivity.this);
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
				final String artigoSelecionado=listaArtigos.get(lista.indexOf(selected)).getId();
				
				new AsyncTask<Void,Void,Void>(){
					
					/**
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
					 */
					
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						pd.show();
					}
					
					
					@Override
					protected Void doInBackground(Void... arg0) {
						
						
						Artigo artigo = operacao.informacoesExemplarArtigo(artigoSelecionado);
						Intent intent = new Intent(ResultadoBuscaArtigoctivity.this,DadosTituloActivity.class);
						intent.putExtra("ExemplarArtigo", artigo);
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

	    
	    
//	    listaResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, final View view, int position,
//					long id) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(ResultadoBuscaArtigoctivity.this, DadosTituloActivity.class );
//				startActivity(intent);
//			}
//
//		});
	
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
