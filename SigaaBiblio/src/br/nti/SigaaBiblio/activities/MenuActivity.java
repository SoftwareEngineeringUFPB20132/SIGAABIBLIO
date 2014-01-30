package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import br.nti.SigaaBiblio.model.Biblioteca;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Usuario;
import br.nti.SigaaBiblio.operations.Operations;
import br.nti.SigaaBiblio.operations.OperationsFactory;
import br.nti.SigaaBiblio.operations.PreferenciasOperation;

import com.nti.SigaaBiblio.R;


public class MenuActivity extends Activity {
	Button situacao;
	Button renovacao;
	Button buscaAcervo;
	Button buscaArtigo;
	Button historico;
	Button sair;
	TextView textViewSituacaoUsuario;
	TextView textViewPodeFazerEmprestimo;
	TextView textViewTotalEmprestimosAbertos;
	ImageView imageView1; 
	String bibliotecas;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		
		setBackground();

		textViewSituacaoUsuario = (TextView)findViewById(R.id.textViewSituacaoUsuario1);
		textViewPodeFazerEmprestimo = (TextView)findViewById(R.id.textViewPodeFazerEmprestimo);
		textViewTotalEmprestimosAbertos = (TextView)findViewById(R.id.textViewTotalEmprestimos);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		buscaAcervo = (Button)findViewById(R.id.consultarAcervo);
		buscaArtigo= (Button)findViewById(R.id.consultarArtigo);
		situacao = (Button)findViewById(R.id.button4);
		renovacao = (Button) findViewById(R.id.button3);
		historico = (Button) findViewById(R.id.button5);
		sair = (Button) findViewById(R.id.button6);

		carregaDados();
		

		final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);


		situacao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MenuActivity.this, SituacaoUsuarioActivity.class);
				final ProgressDialog pd = new ProgressDialog(MenuActivity.this);
				pd.setMessage("Processando...");
				pd.setTitle("Aguarde");
				pd.setIndeterminate(false);
				
				
				
				AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						pd.show();
					}

					@Override
					protected Void doInBackground(Void... params) {
												
						String mensagem = new String("");
						ArrayList<Emprestimo> emprestimos = operacao.consultarSituacao(Usuario.INSTANCE.getLogin(),Usuario.INSTANCE.getSenha(),mensagem);
						intent.putExtra("Mensagem", mensagem);
						intent.putExtra("Emprestimos", emprestimos);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (pd != null && pd.isShowing())
							pd.dismiss();
					}

				};

				task.execute();
				try {
					task.get();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				startActivity(intent);

			}
		});





		final Context contexto = getApplicationContext();
		
		historico.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(PrefsActivity.getHistorico(contexto)){
					//realiza a consulta no banco de dados
					Intent intent = new Intent(MenuActivity.this, HistoricoEmprestimosActivity.class );
					PreferenciasOperation pref = new PreferenciasOperation(contexto);
					ArrayList<Emprestimo> emprestimos = pref.recuperarHistorico();
					intent.putExtra("Historico", emprestimos);
					startActivity(intent);

				}else{
					Intent intent = new Intent(MenuActivity.this, SelecionarHistoricoActivity.class );
					startActivity(intent);

				}
				
			}
		});

	}
	
	
	public void localizarBibliotecas(View v){
		Intent intent = new Intent(MenuActivity.this, MapsActivity.class );
		startActivity(intent);

		
	}
	
	@Override
	protected void onResume(){
		
		super.onResume();
		setBackground();
				
	}

	private void carregaDados() {		
		Usuario user = Usuario.INSTANCE;	
		String situacaoUsuario = "";
		
		if(user.isAluno()){
		
			String nom[] = user.getNome().split(" ");
			situacaoUsuario = user.getMatricula()+"\n"+nom[0]+" "+nom[1];//+ "\n";
			
			String curso = user.getCurso();//.substring(0, user.getCurso().length() > 18 ? 18 : user.getCurso().length());
			String cursoShort = curso.substring(0, curso.indexOf('/'));
			String cursoTokens[] = cursoShort.split(" ");
			int cont = 0;
			int sum = 0;
			int ultimo = 0;
			for(int i=0;i<cursoTokens.length;i++){
				sum = sum + cursoTokens[i].length();
				cont++;
				if(sum > 15){
					cont-=2;
					String soFar = "";
					for(int j = 0;j<=cont;j++)
						soFar = soFar + " " + cursoTokens[ultimo+j]; 
					
					situacaoUsuario = situacaoUsuario + "\n" + soFar.trim();
					cont=0;
					ultimo=i+1;
					sum=0;
				}
			}
			situacaoUsuario = situacaoUsuario + "\n" + cursoTokens[cursoTokens.length-1]; 
			
		}else{
			situacaoUsuario = user.getNome()+"\n"+user.getUnidade();
		}
		
		try {
			textViewSituacaoUsuario.setText(situacaoUsuario);
			imageView1.setImageBitmap(user.geraBitmap());
			textViewPodeFazerEmprestimo.setText("Posso fazer Empréstimos: "+(user.getUserVinculo().isPodeRealzarEmprestimos()?"SIM":"NÃO"));
			textViewTotalEmprestimosAbertos.setText("Total de Empréstimos em Aberto: "+user.getUserVinculo().getTotalEmprestimosAbertos());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

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
	      // More items go here (if any) ...
	    case R.id.logout:
	    	Intent intent = new Intent(MenuActivity.this, LoginActivity.class );
			intent.putExtra("logarComOutroUsuario", true); //setta para que seja escolhido um novo usuário
			startActivity(intent);
			finish();
	    }
	    return false;
	  }
	
	
		//Consulta um acervo no sistema
		public void consultarAcervo(View v){
			
			
			final ProgressDialog pd = new ProgressDialog(MenuActivity.this);
			pd.setMessage("Processando...");
			pd.setTitle("Aguarde");
			pd.setIndeterminate(false);
			bibliotecas=null;
			final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);

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
						ArrayList<Biblioteca> bibliotecas = operacao.listarBibliotecas();
						Intent intent = new Intent(MenuActivity.this, BuscaLivroActivity.class );
						intent.putExtra("Bibliotecas", bibliotecas);
						startActivity(intent);
//						
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
			
		}
	
		
		/*
		 * CONSULTAR ARTIGO
		 */
		
		public void consultarArtigo(View v){
			
			
			final ProgressDialog pd = new ProgressDialog(MenuActivity.this);
			pd.setMessage("Processando...");
			pd.setTitle("Aguarde");
			pd.setIndeterminate(false);
			bibliotecas=null;
			final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);

			
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
					ArrayList<Biblioteca> bibliotecas = operacao.listarBibliotecas();
					Intent intent = new Intent(MenuActivity.this, BuscaArtigoActivity.class );
					intent.putExtra("Bibliotecas", bibliotecas);
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
		

		/*
		 * CONSULTAR EMPRESTIMOS RENOVAVEIS
		 */
		
		public void emprestimosRenovaveis(View v){
			
			
			final ProgressDialog pd = new ProgressDialog(MenuActivity.this);
			pd.setMessage("Processando...");
			pd.setTitle("Aguarde");
			pd.setIndeterminate(false);
			
			final Operations operacao = new OperationsFactory().getOperation(OperationsFactory.REMOTA,this);

			
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
					ArrayList<Emprestimo> emprestimos = operacao.consultarEmprestimosRenovaveis(usuario,senha);
					Intent intent = new Intent(MenuActivity.this, RenovacaoActivity.class);
					intent.putExtra("EmprestimosRenovaveis", emprestimos);
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
			LinearLayout t = (LinearLayout) findViewById(R.id.login_header_3);
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


