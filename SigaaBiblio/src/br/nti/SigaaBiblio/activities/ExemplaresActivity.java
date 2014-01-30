package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;

import br.nti.SigaaBiblio.layouts.HistoricoLayout;
import br.nti.SigaaBiblio.model.ExemplarLivro;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class ExemplaresActivity extends Activity {

	Button retornarPesquisa;
	Button retornarMenu;
	ViewFlipper page;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exemplares);
		setBackground();
		retornarPesquisa = (Button) findViewById(R.id.retornarPesquisa);
		retornarMenu = (Button) findViewById(R.id.retornarMenu);
		page = (ViewFlipper)findViewById(R.id.viewFlipper3);	      
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
        
        
		//Começa daqui Iron
		ArrayList<ExemplarLivro> exemplares = getIntent().getParcelableArrayListExtra("ExemplaresLivro");
		
		for(ExemplarLivro ex : exemplares){
			page.addView(HistoricoLayout.Exemplares(this, ex.getCodigoDeBarras(), ex.getTipoDeMaterial(),
					ex.getColecao(), ex.getSituacao(), ex.getBiblioteca()));
		}
		
		//seja feliz
		
		
		
		
		retornarPesquisa.setOnClickListener(new OnClickListener() {			
			@Override 
			public void onClick(View v) {
				Intent intent = new Intent(ExemplaresActivity.this, BuscaLivroActivity.class );
				finish();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		retornarMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExemplaresActivity.this, MenuActivity.class );
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

	    

}