package br.nti.SigaaBiblio.activities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.Operations;
import br.nti.SigaaBiblio.operations.PreferenciasOperation;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelecionarHistoricoActivity extends Activity {

	EditText inputDataInicial;
	EditText inputDataFinal;
	ImageButton dataInicialButton;
	ImageButton dataFinalButton;
	Button pesquisar;
	DateFormat formatDateTime;
	Calendar dateTime;
	DatePickerDialog.OnDateSetListener dataInicioListener;
	DatePickerDialog.OnDateSetListener dataFimListener;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selecionar_historico);
		setBackground();
		pesquisar = (Button) findViewById(R.id.consultarHistorico);
		dataInicialButton = (ImageButton) findViewById(R.id.calendario_img1);
		dataFinalButton = (ImageButton) findViewById(R.id.calendario_img2);
		inputDataInicial = (EditText) findViewById(R.id.dataInicialText);
		inputDataFinal= (EditText) findViewById(R.id.editTextLoginUsuario);

		inputDataInicial.setText("");
		inputDataFinal.setText("");
		//obtem a instancia do calendario
		formatDateTime=DateFormat.getDateTimeInstance();
		dateTime=Calendar.getInstance();

		//listeners dos dialog picker  

		dataInicioListener=new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int ano, int mes,int dia) {
					// TODO Auto-generated method stub
					String  mesS = (mes+1)<10?"0"+(mes+1):""+(mes+1);
					String  diaS = (dia)<10?"0"+(dia):""+(dia);
					String 	anoS = ""+ano;
					inputDataInicial.setText(diaS+"-"+mesS+"-"+anoS);
				}
			};

		dataFimListener=new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int ano, int mes,int dia) {
					// TODO Auto-generated method stub
					String  mesS = (mes+1)<10?"0"+(mes+1):""+(mes+1);
					String  diaS = (dia)<10?"0"+(dia):""+(dia);
					String 	anoS = ""+ano;
					inputDataFinal.setText(diaS+"-"+mesS+"-"+anoS);
				}
			};



		dataInicialButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(SelecionarHistoricoActivity.this, dataInicioListener, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();


			}
		});

		dataFinalButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(SelecionarHistoricoActivity.this, dataFimListener, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();

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
		inputDataInicial.setText("");
		inputDataFinal.setText("");
				
	}
	
	
	


	
	/*
	 * CONSULTAR HISTORICO
	 */
	
	public void consultarHistorico(View v){
		
		final ProgressDialog pd = new ProgressDialog(SelecionarHistoricoActivity.this);
		pd.setMessage("Processando...");
		pd.setTitle("Aguarde");
		pd.setIndeterminate(false);
		
		final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);
		final Context contexto = getApplicationContext();
			
		
		
		new AsyncTask<Void,Void,Void>(){

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd.show();
			}
			
			
			@Override
			protected Void doInBackground(Void... arg0) {
				String usuario=Usuario.INSTANCE.getLogin();
				String senha = Usuario.INSTANCE.getSenha();
				
				String dataInicial="",dataFinal="";
				
				ArrayList<Emprestimo> emprestimos = null;
				if(!inputDataInicial.getText().toString().equals("")){
					String[] dataInicalraw = inputDataInicial.getText().toString().trim().split("-");
					dataInicial = dataInicalraw[2]+"-"+dataInicalraw[1]+"-"+dataInicalraw[0];
				}
				if(!inputDataFinal.getText().toString().equals("")){
					String[] dataFinalraw = inputDataFinal.getText().toString().trim().split("-");
					dataFinal= dataFinalraw[2]+"-"+dataFinalraw[1]+"-"+dataFinalraw[0];
				}

				
				emprestimos=operacao.historicoEmprestimos(usuario,senha,dataInicial,dataFinal);
				
						
				
				if(PrefsActivity.getHistorico(contexto)){
					PreferenciasOperation pref = new PreferenciasOperation(contexto);
					pref.salvarHistorico(emprestimos);
				}
				Intent intent = new Intent(SelecionarHistoricoActivity.this, HistoricoEmprestimosActivity.class );
				intent.putExtra("Historico", emprestimos);
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





}