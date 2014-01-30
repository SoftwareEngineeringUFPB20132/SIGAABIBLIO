package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import br.nti.SigaaBiblio.layouts.HistoricoLayout;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.operations.PreferenciasOperation;

import com.nti.SigaaBiblio.R;

public class HistoricoEmprestimosActivity extends Activity {

	Button retornaPesquisaButton;
	Button retornaMenuButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_historico_emprestimos);
		setBackground();
		page = (ViewFlipper)findViewById(R.id.viewFlipper2);	      
		animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
        animFlipOutForeward = AnimationUtils.loadAnimation(this, R.anim.flipout);
        animFlipInBackward = AnimationUtils.loadAnimation(this, R.anim.flipin_reverse);
        animFlipOutBackward = AnimationUtils.loadAnimation(this, R.anim.flipout_reverse);	
        page.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return gestureDetector.onTouchEvent(event);
			}
		});
        
		retornaMenuButton = (Button) findViewById(R.id.retornarMenu);
		retornaPesquisaButton = (Button) findViewById(R.id.retornarPesquisa);	
		
		ArrayList<Emprestimo> listaHistorico=null;
		
		if(PrefsActivity.getHistorico(this) && getIntent().getExtras()==null){ //consulta historico do bd
			
			PreferenciasOperation pref = new PreferenciasOperation(this);
			listaHistorico= pref.recuperarHistorico();
		}
		else{
			Intent intent = getIntent();

			listaHistorico = (ArrayList<Emprestimo>) intent.getExtras().get("Historico");

		}
						
		LinearLayout table;
		for (Emprestimo historico : listaHistorico) {

			table = HistoricoLayout.TabelaHistorico(this,
					historico.getTipoEmprestimo(),
					historico.getDataEmprestimo(),
					historico.getDataRenovacao(),
					historico.getPrazoDevolucao(),
					historico.getDataDevolucao(),
					historico.getInformacoes());
			
			
			page.addView(table);
		}
		
		
		retornaMenuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HistoricoEmprestimosActivity.this, MenuActivity.class );
				finish();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				

			}
		});
		retornaPesquisaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HistoricoEmprestimosActivity.this, SelecionarHistoricoActivity.class );
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
	

	
	
	
	// Implementacao da mudanca entre tabelas
		//
		ViewFlipper page;
		Animation animFlipInForeward;
	    Animation animFlipOutForeward;
	    Animation animFlipInBackward;
	    Animation animFlipOutBackward;
		
		private void SwipeRight(){
	    	page.setInAnimation(animFlipInBackward);
			page.setOutAnimation(animFlipOutBackward);
			page.showPrevious();
	    }
	    
	    private void SwipeLeft(){
	    	page.setInAnimation(animFlipInForeward);
			page.setOutAnimation(animFlipOutForeward);
			page.showNext();
	    }
	    
	    @Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
	    	return gestureDetector.onTouchEvent(event);
		}

		SimpleOnGestureListener simpleOnGestureListener 
	    = new SimpleOnGestureListener(){

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {

				float sensitvity = 50;
				if((e1.getX() - e2.getX()) > sensitvity){
					SwipeLeft();
				}else if((e2.getX() - e1.getX()) > sensitvity){
					SwipeRight();
				}
				
				return true;
			}
	    	
	    };
	    
	    GestureDetector gestureDetector
		= new GestureDetector(simpleOnGestureListener);
	    
	    
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
