package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;

import br.nti.SigaaBiblio.operations.PreferenciasOperation;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RegistroRenovacoesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_renovacoes);
		setBackground();
		
		//realiza a pesquisa de renovacoes no banco de dados
		ArrayList<String> values = new ArrayList<String>();
		
		if(PrefsActivity.getRenovacao(this)){
			PreferenciasOperation pref = new PreferenciasOperation(this);
			values=pref.recuperarRenovacoes();
			if(values.isEmpty())
				values.add("Ainda não foi registrado nenhuma renovação");
		}else{
			values.add("O registro de renovações de emprestimo esta desabilitado");
		}
				
		ListView l = (ListView) findViewById(R.id.listViewDadosTitulo);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    android.R.layout.simple_list_item_1, values){

	  /*
	   * Desabilita o focus da listView que aqui foi utilizada apenas por
	   * questão de estetica.  
	   */
	  @Override 
	  public boolean isEnabled(int position) 
        { 
                return false; 
        } 
    };

l.setAdapter(adapter);

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
