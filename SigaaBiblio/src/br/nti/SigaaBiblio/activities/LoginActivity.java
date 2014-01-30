package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;


import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.model.VinculoUsuarioSistema;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.Operations;
import br.nti.SigaaBiblio.operations.PreferenciasOperation;
import br.nti.SigaaBiblio.persistence.RepositorioFake;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.utils.HttpUtils;

public class LoginActivity extends Activity implements OnClickListener {

	Button login;
	EditText etLogin;
	EditText etSenha;
	String logPref, senhaPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);
		
		setBackground(); //seleciona a cor escolhida
		
		//gera dados para teste com persistencia local
		RepositorioFake db = new RepositorioFake(getApplicationContext());
		db.resetRepositorioFake();
		db.gerarRepositorioFake();
		
		if(!PrefsActivity.getRenovacao(this)){ //se nao quiser mater registro de renovacos
			PreferenciasOperation pref = new PreferenciasOperation(this);
			pref.resetRenovacoesRepositorio(); //apaga renovacoes atualmentes salvas 
		}
		
		if(!PrefsActivity.getHistorico(this)){ //se nao quiser mater o historico
			PreferenciasOperation pref = new PreferenciasOperation(this);
			pref.resetHistoricoRepositorio();
		}
		
		
	  
		logPref = "";
		senhaPref = "";

		login = (Button) findViewById(R.id.consultarHistorico);
		etLogin = (EditText) findViewById(R.id.editTextLoginUsuario);
		etSenha = (EditText) findViewById(R.id.editTextSenhaUsuario);
		login.setOnClickListener(this);
		
		
		if (PrefsActivity.getLembrarLogin(this)) {

			//descobre se quer logar como outro usuário
			Bundle bund = getIntent().getExtras();
			
			if(bund==null){ //se nao quiser logar como outro usuário
			
				if (getPreferences(MODE_PRIVATE).contains("login"))
					logPref = getPreferences(MODE_PRIVATE).getString("login", "");

				if (getPreferences(MODE_PRIVATE).contains("senha"))
					senhaPref = getPreferences(MODE_PRIVATE).getString("senha", "");
				//se nao forem nulos, ele ja realiza o login chamando o botao de login
				if (!logPref.isEmpty() && logPref != null && !senhaPref.isEmpty()
						&& senhaPref != null){
					etLogin.setText(logPref);
					etSenha.setText(senhaPref);
					login.performClick();
				}

			}
			else{ //vai logar com outro usuario logo eu devo apagar historico e renovacoes
				getPreferences(MODE_PRIVATE).edit().remove("login").commit();
				getPreferences(MODE_PRIVATE).edit().remove("senha").commit();
				PreferenciasOperation pref = new PreferenciasOperation(this);
				pref.resetHistoricoRepositorio();
				pref.resetRenovacoesRepositorio();
			}
		}//end preferencias 
	
	}

	@Override
	protected void onStop() {
		super.onStop();
		//finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	

	String mensagem;
	
	@Override
	public void onClick(View v) {

		final String login = etLogin.getText().toString().trim();
		final String senha = etSenha.getText().toString().trim();		
		
		
		
		
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setMessage("Processando...");
		pd.setTitle("Aguarde");
		pd.setIndeterminate(false);
		
		
		final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);
		
		final Semaphore sincronizador = new Semaphore(0,true); //para exclusão mutua
		
		
		new AsyncTask<Void,Void,Void>(){

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd.show();
			}
			
			
			@Override
			protected Void doInBackground(Void... arg0) {
				mensagem = operacao.realizarLogin(login,senha);
				sincronizador.release();
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
				sincronizador.acquire(); //espera a assyncTask obter as mensagens
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Log.d("MARCILIO_DEBUG",mensagem.substring(0,11));
			if(mensagem.equals("Ocorreu um erro") || mensagem.substring(0,11).equals("SERVER#ERRO")){
				mensagem= mensagem.substring(11,mensagem.length());
				Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_LONG)
				.show();
				mensagem=null;
			}else{			
				Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
				Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_LONG)
				.show();
				startActivity(intent);
				
				
			}
	
			if(PrefsActivity.getLembrarLogin(this)){
				
				
				if(mensagem!=null){
					getPreferences(MODE_PRIVATE).edit().putString("login", login).commit();
					
					getPreferences(MODE_PRIVATE).edit().putString("senha", senha).commit();
					
					finish();
				}else{
					getPreferences(MODE_PRIVATE).edit().remove("login").commit();
					getPreferences(MODE_PRIVATE).edit().remove("senha").commit();
					
				}
				
			}
			
	}
	
	
	/*
	 * Setta a cor de background
	 */
	
	public void setBackground(){
		LinearLayout lb = (LinearLayout) findViewById(R.id.login_body);
		LinearLayout lh = (LinearLayout) findViewById(R.id.login_head);
		TextView t = (TextView) findViewById(R.id.login_subheader);
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
	

}