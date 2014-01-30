package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;


import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.Operations;
import br.nti.SigaaBiblio.operations.PreferenciasOperation;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;
import com.nti.SigaaBiblio.utils.EmprestimoAdapterUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RenovacaoActivity extends Activity {

	Map<String,Boolean> renovar;
	Map<String,String> keysEmprestimos;
	String resposta;
	ArrayList<EmprestimoAdapterUtils> lista_para_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_renovacao);
		setBackground();
		
		renovar= new HashMap<String, Boolean>();
		keysEmprestimos= new HashMap<String, String>();
		
		 Bundle bund = getIntent().getExtras();
	     ArrayList<Emprestimo> emprestimos = (ArrayList<Emprestimo>) bund.get("EmprestimosRenovaveis");
	     ArrayList<String> lista = new ArrayList<String>();
	     ListView listaLivros = (ListView) findViewById(R.id.listViewResultados);
	     
	     if(emprestimos==null){
	    	
	    	 lista.add("Você não possui empréstimos ativos renováveis");
	    	 ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, lista);
	    	 listaLivros.setAdapter(adapter);
	    	 Button renovar = (Button) findViewById(R.id.renovarEmprestimo);
	    	 renovar.setEnabled(false);
	    	 
	     }else{
		    	 for(Emprestimo e : emprestimos){
			    	 lista.add(e.toString());
			    	 keysEmprestimos.put(e.toString(), e.getCodigoLivro());
			    	 renovar.put(e.getCodigoLivro(),false); 
			     	}
		    	 
		    	 lista_para_adapter = new ArrayList<EmprestimoAdapterUtils>();
			       
			       for(String emprestimo : lista){
			    	   lista_para_adapter.add(new EmprestimoAdapterUtils(emprestimo));
			       	}
			       
			       ArrayAdapter<EmprestimoAdapterUtils> adapter = new EmprestimoAdapter(this,lista_para_adapter);			       	 
			       listaLivros.setAdapter(adapter);

	     	}//end else
	     
	     
	     
		
		
		       	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.renovacao, menu);
		return true;
	}
	
	
	
	/*
	 * Renovacao dos emprestimos
	 */
	
	public void renovar(View e){
		final ProgressDialog pd = new ProgressDialog(RenovacaoActivity.this);
		pd.setMessage("Processando...");
		pd.setTitle("Aguarde");
		pd.setIndeterminate(false);
	
		final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);
		final Context context = getApplicationContext();
		final Semaphore sincronizador = new Semaphore(0);
		
		new AsyncTask<Void,Void,Void>(){

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd.show();
			}
			
			
			@Override
			protected Void doInBackground(Void... arg0) {
					String usuario = Usuario.INSTANCE.getLogin();
					String senha = Usuario.INSTANCE.getSenha();
					
					Set<String> id = renovar.keySet();
					for (String chave : id)  
			        {  
			            if(chave != null){
			            	if(renovar.get(chave)){ //tem que renovar
			            	   resposta = operacao.renovarEmprestimo(usuario,senha,chave);
			            	   PreferenciasOperation pref = new PreferenciasOperation(context); 
			            	   pref.salvaRenovacoes(resposta);
			            	   Log.d("MARCILIO_DEBUG", "ariaria: "+resposta);
			            	}
			            }
			                  
			        }
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
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_LONG).show();
			Intent intent = new Intent(RenovacaoActivity.this, MenuActivity.class );
			finish();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
	}
	
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		case R.id.renovacao_storage:
			startActivity(new Intent(this, RegistroRenovacoesActivity.class));
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

	

	
	
	
	
	
	private class EmprestimoAdapter extends ArrayAdapter<EmprestimoAdapterUtils> {

		  private  List<EmprestimoAdapterUtils> lista_emprestimos;

		  public EmprestimoAdapter(Context context, List<EmprestimoAdapterUtils> lista) {
		    super(context, R.layout.emprestimos_layout, lista);
		    
		    this.lista_emprestimos = lista;
		  }

		  private class ViewHolder {
		    protected TextView text;
		    protected CheckBox checkbox;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View view = null;
		    if (convertView == null) {
		    	  LayoutInflater vi = (LayoutInflater)getSystemService(
		    			     Context.LAYOUT_INFLATER_SERVICE);
		      view = vi.inflate(R.layout.emprestimos_layout, null);
		      final ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) view.findViewById(R.id.label);
		      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
		      viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		            @Override
		            public void onCheckedChanged(CompoundButton buttonView,
		                boolean isChecked) {
		              EmprestimoAdapterUtils element = (EmprestimoAdapterUtils) viewHolder.checkbox.getTag();
		              element.setSelected(buttonView.isChecked());
		             //Aqui
		             String idEmprestimo =keysEmprestimos.get(element.getDados());
		 	    	 renovar.put(idEmprestimo,buttonView.isChecked());
		             //Toast.makeText(getApplicationContext(), ""+renovar.get(idEmprestimo), Toast.LENGTH_SHORT).show();

		            }
		          });
		      view.setTag(viewHolder);
		      viewHolder.checkbox.setTag(lista_emprestimos.get(position));
		    } else {
		      view = convertView;
		      ((ViewHolder) view.getTag()).checkbox.setTag(lista_emprestimos.get(position));
		    }
		    ViewHolder holder = (ViewHolder) view.getTag();
		    holder.text.setText(lista_emprestimos.get(position).getDados());
		    holder.checkbox.setChecked(lista_emprestimos.get(position).isSelected());
		    return view;
		  }
		} 

}
