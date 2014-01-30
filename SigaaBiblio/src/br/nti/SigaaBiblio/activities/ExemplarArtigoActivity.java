package br.nti.SigaaBiblio.activities;

import br.nti.SigaaBiblio.model.Artigo;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExemplarArtigoActivity extends Activity {

	TextView codigoBarras;
	TextView numero;
	TextView volume;
	TextView ano;
	TextView situacao;
	TextView biblioteca;
	TextView localizacao;
	Button retornarPesquisa;
	Button retornarMenu;
	
	//InfoCodigoBarra
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exemplar_artigo);
		setBackground();
		retornarPesquisa = (Button) findViewById(R.id.retornarPesquisa);
		retornarMenu = (Button) findViewById(R.id.retornarMenu);

		Artigo artigo = (Artigo) getIntent().getExtras().getParcelable("ExemplarArtigo");
		
		codigoBarras= (TextView) findViewById(R.id.InfoCodigoBarra);
		numero= (TextView) findViewById(R.id.InfoNumero);
		volume= (TextView) findViewById(R.id.InfoVolume);
		ano= (TextView) findViewById(R.id.InfoAno);
		situacao= (TextView) findViewById(R.id.InfoStatus);
		biblioteca= (TextView) findViewById(R.id.textView1);
		localizacao= (TextView) findViewById(R.id.textView2);
		
		codigoBarras.setText(artigo.getCodigoDeBarras());
		numero.setText(artigo.getNumero());
		volume.setText(artigo.getVolume());
		ano.setText(artigo.getAno());
		situacao.setText(artigo.getSituacao());
		biblioteca.setText("Biblioteca: "+artigo.getBiblioteca());
		localizacao.setText("Localização: "+artigo.getLocalizacao());
		
		retornarPesquisa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExemplarArtigoActivity.this, BuscaArtigoActivity.class );
				finish();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				

			}
		});

		retornarMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExemplarArtigoActivity.this, MenuActivity.class );
				finish();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
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
