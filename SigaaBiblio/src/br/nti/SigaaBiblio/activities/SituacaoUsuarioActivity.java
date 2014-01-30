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
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import br.nti.SigaaBiblio.layouts.HistoricoLayout;
import br.nti.SigaaBiblio.model.Emprestimo;
import br.nti.SigaaBiblio.model.Usuario;

import com.nti.SigaaBiblio.R;



public class SituacaoUsuarioActivity extends Activity {
	ViewFlipper page;
	TextView emprestimosAbertos;
    TextView podeEmprestimo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_situacao_usuario);
		setBackground();
		emprestimosAbertos = (TextView)findViewById(R.id.textViewSituacaoUsuario1);
		podeEmprestimo = (TextView)findViewById(R.id.textViewSituacaoUsuario2);
		
		page = (ViewFlipper)findViewById(R.id.viewFlipper1);	      
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
        
        
        Bundle bund = getIntent().getExtras();
        ArrayList<Emprestimo> lista = (ArrayList<Emprestimo>) bund.get("Emprestimos");
        podeEmprestimo.setText(bund.getString("Mensagem"));
        emprestimosAbertos.setText("Total de Empréstimos em Aberto: "+Usuario.INSTANCE.getUserVinculo().getTotalEmprestimosAbertos());
        
        LinearLayout l1;
        for(Emprestimo emp : lista){
        	l1 = HistoricoLayout.TabelaSituacao(this, emp.getBiblioteca(), emp.getDataEmprestimo(), emp.getDataRenovacao(),
        			emp.getDataDevolucao(), emp.isRenovavel(), emp.getInformacoesLivro());
        	page.addView(l1);
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