package br.nti.SigaaBiblio.layouts;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nti.SigaaBiblio.R;

public class HistoricoLayout {
	
	
	//@Author :Iron Araújo 06/07/2013
	public static LinearLayout TabelaSituacao(Activity activity, String biblioteca, String dataEmprestimo,
			String dataRenovacao, String dataDevolucao, Boolean renovavel, String informacoes){
		
		LinearLayout ll1 = new LinearLayout(activity);
        ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ll1.setOrientation(LinearLayout.VERTICAL);
        
        /***************    Componentes do Layout */
        
        // Componente 1
        LinearLayout ll2 = new LinearLayout(activity);
        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp2.setMargins(5, 5, 5, 5);
        ll2.setLayoutParams(llp2);
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        
        TextView tv1 = new TextView(activity);
        LinearLayout.LayoutParams ltv1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        ltv1.weight = (float) 0.50;
        tv1.setLayoutParams(ltv1);
        tv1.setText("Biblioteca");
        tv1.setTextSize(15);
        tv1.setTypeface(null, Typeface.BOLD);
        
        TextView tv2 = new TextView(activity);
        LinearLayout.LayoutParams ltv2 = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT);
        ltv2.weight = (float) 0.50;
        tv2.setText(biblioteca);
        tv2.setLayoutParams(ltv2);
        
        ll2.addView(tv1);
        ll2.addView(tv2);
        ll1.addView(ll2);
        
        // Componente 2
        LinearLayout ll3 = new LinearLayout(activity);
        LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        llp3.setMargins(5, 5, 5, 5);
        ll3.setOrientation(LinearLayout.HORIZONTAL);
        ll3.setLayoutParams(llp3);
        
        TextView tv3 = new TextView(activity);
        LinearLayout.LayoutParams ltv3 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        ltv3.weight = (float) 0.50;
        tv3.setLayoutParams(ltv3);
        tv3.setText("Data de Empréstimo");
        tv3.setTextSize(15);
        tv3.setTypeface(null, Typeface.BOLD);
        
        TextView tv4 = new TextView(activity);
        LinearLayout.LayoutParams ltv4 = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT);
        ltv4.weight = (float) 0.50;
        tv4.setText(dataEmprestimo);
        tv4.setLayoutParams(ltv4);
        
        ll3.addView(tv3);
        ll3.addView(tv4);
        ll1.addView(ll3);
        
        // Componente 3
        LinearLayout ll4 = new LinearLayout(activity);
        LinearLayout.LayoutParams llp4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp4.setMargins(5, 5, 5, 5);
        ll4.setOrientation(LinearLayout.HORIZONTAL);
        ll4.setLayoutParams(llp4);
        
        TextView tv5 = new TextView(activity);
        LinearLayout.LayoutParams ltv5 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        ltv5.weight = (float) 0.50;
        tv5.setLayoutParams(ltv5);
        tv5.setText("Data de Renovação");
        tv5.setTextSize(15);
        tv5.setTypeface(null, Typeface.BOLD);
        
        TextView tv6 = new TextView(activity);
        LinearLayout.LayoutParams ltv6 = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT);
        ltv6.weight = (float) 0.50;
        tv6.setText(dataRenovacao);
        tv6.setLayoutParams(ltv6);
        
        ll4.addView(tv5);
        ll4.addView(tv6);
        ll1.addView(ll4);
        
        //Componente 4
        
        LinearLayout ll5 = new LinearLayout(activity);
        LinearLayout.LayoutParams llp5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp5.setMargins(5, 5, 5, 5);
        ll5.setOrientation(LinearLayout.HORIZONTAL);
        ll5.setLayoutParams(llp5);
        
        TextView tv7 = new TextView(activity);
        LinearLayout.LayoutParams ltv7 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        ltv7.weight = (float) 0.50;
        tv7.setLayoutParams(ltv7);
        tv7.setText("Data de Devolução");
        tv7.setTextSize(15);
        tv7.setTypeface(null, Typeface.BOLD);
        
        TextView tv8 = new TextView(activity);
        LinearLayout.LayoutParams ltv8 = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT);
        ltv8.weight = (float) 0.50;
        tv8.setText(dataDevolucao);
        tv8.setLayoutParams(ltv8);
        
        ll5.addView(tv7);
        ll5.addView(tv8);
        ll1.addView(ll5);
        
        //Componente 5
        
        LinearLayout ll6 = new LinearLayout(activity);
        LinearLayout.LayoutParams llp6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp6.setMargins(5, 5, 5, 5);
        ll6.setOrientation(LinearLayout.HORIZONTAL);
        ll6.setLayoutParams(llp6);
        
        TextView tv9 = new TextView(activity);
        LinearLayout.LayoutParams ltv9 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        ltv9.weight = (float) 0.50;
        tv9.setLayoutParams(ltv9);
        tv9.setText("Renovável");
        tv9.setTextSize(15);
        tv9.setTypeface(null, Typeface.BOLD);
        
        ImageView im1 = new ImageView(activity);
        LinearLayout.LayoutParams ltv10 = new LinearLayout.LayoutParams(25,25);
        ltv10.weight = (float) 0.50;
        ltv10.gravity = Gravity.LEFT;
        im1.setLayoutParams(ltv10);
        im1.setImageResource(renovavel ? R.drawable.check : R.drawable.not_available);
        
        ll6.addView(tv9);
        ll6.addView(im1);
        ll1.addView(ll6);
        
        //Componente 6
        
        TextView tv10 = new TextView(activity);
        LinearLayout.LayoutParams ltv11 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ltv8.weight = (float) 0.50;
        ltv11.setMargins(0, 15, 0, 0);
        tv10.setLayoutParams(ltv11);
        tv10.setText(informacoes);
        tv10.setTextSize(15);
        tv10.setTextColor(Color.rgb(144,144,144));
        tv10.setTypeface(null, Typeface.BOLD);
        
        ll1.addView(tv10);
        
        
        
        return ll1;
	}
	
	public static LinearLayout TabelaHistorico(Activity activity, String tipoEmprestimo,
			String dataEmprestimo, String dataRenovacao, String prazoDevolucao, String dataDevolucao, String informacoes){
		
		LinearLayout tabela = new LinearLayout(activity);
        tabela.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        tabela.setOrientation(LinearLayout.VERTICAL);
//		tabela.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		/*************************************************/
		//LINHA 1
		
		LinearLayout linha1 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha1Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha1Layout.setMargins(5, 5, 5, 5);
		linha1.setLayoutParams(linha1Layout);
		linha1.setOrientation(LinearLayout.HORIZONTAL);
//		linha1.setBackgroundColor(Color.parseColor("#000000"));
		
		TextView tv1l1 = new TextView(activity);
        LinearLayout.LayoutParams tv1l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l1Layout.setMargins(30, 30, 30, 30);
//        tv1l1.setBackgroundColor(Color.parseColor("#C8D5EC"));
        tv1l1.setText("Tipo de Empréstimo");
        tv1l1.setLayoutParams(tv1l1Layout);
        tv1l1Layout.weight = (float)0.5;
        
        TextView tv2l1 = new TextView(activity);
        LinearLayout.LayoutParams tv2l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l1Layout.setMargins(16, 16, 1, 1);
//        tv2l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv2l1.setText(tipoEmprestimo);
        tv2l1.setLayoutParams(tv2l1Layout);
        tv2l1Layout.weight = (float)0.5;
        
        linha1.addView(tv1l1);
        linha1.addView(tv2l1);
        
        /********************************************************/
        //LINHA 2
        
        LinearLayout linha2 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha2Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha2Layout.setMargins(5, 5, 5, 5);
		linha2.setLayoutParams(linha2Layout);
		linha2.setOrientation(LinearLayout.HORIZONTAL);
//		linha2.setBackgroundColor(Color.parseColor("#000000"));
		
		TextView tv1l2 = new TextView(activity);
        LinearLayout.LayoutParams tv1l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l2Layout.setMargins(30, 30, 30, 30);
//        tv1l2.setBackgroundColor(Color.parseColor("#C8D5EC"));
        tv1l2.setText("Data Empréstimo");
        tv1l2.setLayoutParams(tv1l2Layout);
        tv1l2Layout.weight = (float)0.5;
        
        TextView tv2l2 = new TextView(activity);
        LinearLayout.LayoutParams tv2l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l2Layout.setMargins(16, 16, 1, 1);
//        tv2l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv2l2.setText(dataEmprestimo);
        tv2l2.setLayoutParams(tv2l2Layout);
        tv2l2Layout.weight = (float)0.5;
        
        linha2.addView(tv1l2);
        linha2.addView(tv2l2);
		
		/************************************************************/
        // LINHA 3
        
        LinearLayout linha3 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha3Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha3Layout.setMargins(5, 5, 5, 5);
		linha3.setLayoutParams(linha3Layout);
		linha3.setOrientation(LinearLayout.HORIZONTAL);
//		linha3.setBackgroundColor(Color.parseColor("#000000"));
		
		TextView tv1l3 = new TextView(activity);
        LinearLayout.LayoutParams tv1l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l3Layout.setMargins(30, 30, 30, 30);
//        tv1l3.setBackgroundColor(Color.parseColor("#C8D5EC"));
        tv1l3.setText("Data Renovação");
        tv1l3.setLayoutParams(tv1l3Layout);
        tv1l3Layout.weight = (float)0.5;
        
        TextView tv2l3 = new TextView(activity);
        LinearLayout.LayoutParams tv2l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l3Layout.setMargins(16, 16, 1, 1);
//        tv2l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv2l3.setText(dataRenovacao);
        tv2l3.setLayoutParams(tv2l3Layout);
        tv2l3Layout.weight = (float)0.5;
        
        linha3.addView(tv1l3);
        linha3.addView(tv2l3);
		
		/********************************************************/
		//LINHA 4
        
        LinearLayout linha4 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha4Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha4Layout.setMargins(5, 5, 5, 5);
		linha4.setLayoutParams(linha4Layout);
		linha4.setOrientation(LinearLayout.HORIZONTAL);
//		linha4.setBackgroundColor(Color.parseColor("#000000"));
		
		TextView tv1l4 = new TextView(activity);
        LinearLayout.LayoutParams tv1l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l4Layout.setMargins(30, 30, 30, 30);
//        tv1l4.setBackgroundColor(Color.parseColor("#C8D5EC"));
        tv1l4.setText("Data Renovação");
        tv1l4.setLayoutParams(tv1l4Layout);
        tv1l4Layout.weight = (float)0.5;
        
        TextView tv2l4 = new TextView(activity);
        LinearLayout.LayoutParams tv2l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l4Layout.setMargins(16, 16, 1, 1);
//        tv2l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv2l4.setText(prazoDevolucao);
        tv2l4.setLayoutParams(tv2l4Layout);
        tv2l4Layout.weight = (float)0.5;
        
        linha4.addView(tv1l4);
        linha4.addView(tv2l4);
        
        /*****************************************************************/
        
        //LINHA 5
        
        LinearLayout linha5 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha5Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha5Layout.setMargins(5, 5, 5, 5);
		linha5.setLayoutParams(linha5Layout);
		linha5.setOrientation(LinearLayout.HORIZONTAL);
//		linha5.setBackgroundColor(Color.parseColor("#000000"));
		
		TextView tv1l5 = new TextView(activity);
        LinearLayout.LayoutParams tv1l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l5Layout.setMargins(30, 30, 30, 30);
//        tv1l5.setBackgroundColor(Color.parseColor("#C8D5EC"));
        tv1l5.setText("Data Devolucao");
        tv1l5.setLayoutParams(tv1l5Layout);
        tv1l5Layout.weight = (float)0.5;
        
        TextView tv2l5 = new TextView(activity);
        LinearLayout.LayoutParams tv2l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l5Layout.setMargins(16, 16, 1, 1);
//        tv2l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv2l5.setText(dataDevolucao);
        tv2l5.setLayoutParams(tv2l5Layout);
        tv2l5Layout.weight = (float)0.5;
        
        linha5.addView(tv1l5);
        linha5.addView(tv2l5);    
        
        TextView info = new TextView(activity);
		LinearLayout.LayoutParams infoLayout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		infoLayout.setMargins(0, 10, 0, 0);
		info.setLayoutParams(infoLayout);
		info.setText(informacoes);
        
        
        tabela.addView(linha1);
        tabela.addView(linha2);
        tabela.addView(linha3);
        tabela.addView(linha4);
        tabela.addView(linha5);
        tabela.addView(info);
        
		return tabela;
	}
	
	public static LinearLayout Exemplares(Activity activity, String codigoBarras, String tipoMaterial,
			String colecao, String situacao, String biblioteca){
		
		LinearLayout tabela = new LinearLayout(activity);
        tabela.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        tabela.setOrientation(LinearLayout.VERTICAL);

		
		/*************************************************/
		//LINHA 1
		
		LinearLayout linha1 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha1Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha1Layout.setMargins(5, 5, 5, 5);
		linha1.setLayoutParams(linha1Layout);
		linha1.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tv1l1 = new TextView(activity);
        LinearLayout.LayoutParams tv1l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv1l1.setText("Código de Barras");
        tv1l1.setLayoutParams(tv1l1Layout);
        tv1l1Layout.weight = (float)0.5;
        
        TextView tv2l1 = new TextView(activity);
        LinearLayout.LayoutParams tv2l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tv2l1.setText(codigoBarras);
        tv2l1.setLayoutParams(tv2l1Layout);
        tv2l1Layout.weight = (float)0.5;
        
        linha1.addView(tv1l1);
        linha1.addView(tv2l1);
        
        /********************************************************/
        //LINHA 2
        
        LinearLayout linha2 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha2Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha2Layout.setMargins(5, 5, 5, 5);
		linha2.setLayoutParams(linha2Layout);
		linha2.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tv1l2 = new TextView(activity);
        LinearLayout.LayoutParams tv1l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv1l2.setText("Tipo do \nMaterial");
        tv1l2.setLayoutParams(tv1l2Layout);
        tv1l2Layout.weight = (float)0.5;
        
        TextView tv2l2 = new TextView(activity);
        LinearLayout.LayoutParams tv2l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tv2l2.setText(tipoMaterial);
        tv2l2.setLayoutParams(tv2l2Layout);
        tv2l2Layout.weight = (float)0.5;
        
        linha2.addView(tv1l2);
        linha2.addView(tv2l2);
		
		/************************************************************/
        // LINHA 3
        
        LinearLayout linha3 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha3Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha3Layout.setMargins(5, 5, 5, 5);
		linha3.setLayoutParams(linha3Layout);
		linha3.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tv1l3 = new TextView(activity);
        LinearLayout.LayoutParams tv1l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv1l3.setText("Coleção");
        tv1l3.setLayoutParams(tv1l3Layout);
        tv1l3Layout.weight = (float)0.5;
        
        TextView tv2l3 = new TextView(activity);
        LinearLayout.LayoutParams tv2l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tv2l3.setText(colecao);
        tv2l3.setLayoutParams(tv2l3Layout);
        tv2l3Layout.weight = (float)0.5;
        
        linha3.addView(tv1l3);
        linha3.addView(tv2l3);
		
		/********************************************************/
//		//LINHA 4
//        
//        LinearLayout linha4 = new LinearLayout(activity);
//		LinearLayout.LayoutParams linha4Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		linha4Layout.setMargins(5, 5, 5, 5);
//		linha4.setLayoutParams(linha4Layout);
//		linha4.setOrientation(LinearLayout.HORIZONTAL);
//
//		TextView tv1l4 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l4.setText("Status");
//        tv1l4.setLayoutParams(tv1l4Layout);
//        tv1l4Layout.weight = (float)0.5;
//        
//        TextView tv2l4 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l4.setText(status);
//        tv2l4.setLayoutParams(tv2l4Layout);
//        tv2l4Layout.weight = (float)0.5;
//        
//        linha4.addView(tv1l4);
//        linha4.addView(tv2l4);
        
        /*****************************************************************/
        
        //LINHA 5
        
        LinearLayout linha5 = new LinearLayout(activity);
		LinearLayout.LayoutParams linha5Layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linha5Layout.setMargins(5, 5, 5, 5);
		linha5.setLayoutParams(linha5Layout);
		linha5.setOrientation(LinearLayout.HORIZONTAL);

		
		TextView tv1l5 = new TextView(activity);
        LinearLayout.LayoutParams tv1l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv1l5.setText("Situação");
        tv1l5.setLayoutParams(tv1l5Layout);
        tv1l5Layout.weight = (float)0.5;
        
        TextView tv2l5 = new TextView(activity);
        LinearLayout.LayoutParams tv2l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tv2l5.setText(situacao);
        tv2l5.setLayoutParams(tv2l5Layout);
        tv2l5Layout.weight = (float)0.5;
        
        linha5.addView(tv1l5);
        linha5.addView(tv2l5);    
        
        TextView info = new TextView(activity);
		LinearLayout.LayoutParams infoLayout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		infoLayout.setMargins(0, 10, 0, 0);
		info.setLayoutParams(infoLayout);
		info.setText(biblioteca);
        
        
        tabela.addView(linha1);
        tabela.addView(linha2);
        tabela.addView(linha3);
//        tabela.addView(linha4);
        tabela.addView(linha5);
        tabela.addView(info);
        
		return tabela;
	}
	
	
//	public static TableLayout TabelaHistorico(Activity activity, String tipoEmprestimo,
//			String dataEmprestimo, String dataRenovacao, String prazoDevolucao, String dataDevolucao){
//		
//		TableLayout tabela = new TableLayout(activity);
//		TableLayout.LayoutParams tabelaLayout = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		tabelaLayout.setMargins(0, 25, 0, 0);
//		tabela.setBackgroundColor(Color.parseColor("#FFFFFF"));
////		tabela.setColumnStretchable(2, true);
//		tabela.setLayoutParams(tabelaLayout);
//		
//		/*************************************************/
//		//LINHA 1
//		TableRow linha1 = new TableRow(activity);
//		TableRow.LayoutParams linha1Layout = new TableRow.LayoutParams();
//		linha1Layout.setMargins(1, 0, 1, 0);
//		linha1.setBackgroundColor(Color.parseColor("#000000"));
//		linha1.setLayoutParams(linha1Layout);
//		
//		TextView tv1l1 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l1Layout.setMargins(30, 30, 30, 30);
//        tv1l1.setBackgroundColor(Color.parseColor("#C8D5EC"));
//        tv1l1.setText("Tipo de Empréstimo");
//        tv1l1.setLayoutParams(tv1l1Layout);
//        
//        TextView tv2l1 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l1Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l1Layout.setMargins(16, 16, 1, 1);
//        tv2l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tv2l1.setText(tipoEmprestimo);
//        tv2l1.setLayoutParams(tv2l1Layout);
//        
//        linha1.addView(tv1l1);
//        linha1.addView(tv2l1);
//        
//        /********************************************************/
//        //LINHA 2
//        
//        TableRow linha2 = new TableRow(activity);
//		TableRow.LayoutParams linha2Layout = new TableRow.LayoutParams();
//		linha2Layout.setMargins(1, 0, 1, 0);
//		linha2.setBackgroundColor(Color.parseColor("#000000"));
//        linha2.setLayoutParams(linha2Layout);
//		
//		TextView tv1l2 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l2Layout.setMargins(30, 30, 30, 30);
//        tv1l2.setBackgroundColor(Color.parseColor("#C8D5EC"));
//        tv1l2.setText("Data Empréstimo");
//        tv1l2.setLayoutParams(tv1l2Layout);
//        
//        TextView tv2l2 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l2Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l2Layout.setMargins(16, 16, 1, 1);
//        tv2l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tv2l2.setText(dataEmprestimo);
//        tv2l2.setLayoutParams(tv2l2Layout);
//        
//        linha2.addView(tv1l2);
//        linha2.addView(tv2l2);
//		
//		/************************************************************/
//        // LINHA 3
//        
//        TableRow linha3 = new TableRow(activity);
//		TableRow.LayoutParams linha3Layout = new TableRow.LayoutParams();
//		linha3Layout.setMargins(1, 0, 1, 0);
//		linha3.setBackgroundColor(Color.parseColor("#000000"));
//        linha3.setLayoutParams(linha3Layout);
//		
//		TextView tv1l3 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l3Layout.setMargins(30, 30, 30, 30);
//        tv1l3.setBackgroundColor(Color.parseColor("#C8D5EC"));
//        tv1l3.setText("Data Renovação");
//        tv1l3.setLayoutParams(tv1l3Layout);
//        
//        TextView tv2l3 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l3Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l3Layout.setMargins(16, 16, 1, 1);
//        tv2l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tv2l3.setText(dataRenovacao);
//        tv2l3.setLayoutParams(tv2l3Layout);
//        
//        linha3.addView(tv1l3);
//        linha3.addView(tv2l3);
//		
//		/********************************************************/
//		//LINHA 4
//        
//        TableRow linha4 = new TableRow(activity);
//		TableRow.LayoutParams linha4Layout = new TableRow.LayoutParams();
//		linha4Layout.setMargins(1, 0, 1, 0);
//		linha4.setBackgroundColor(Color.parseColor("#000000"));
//        linha4.setLayoutParams(linha4Layout);
//		
//		TextView tv1l4 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l4Layout.setMargins(30, 30, 30, 30);
//        tv1l4.setBackgroundColor(Color.parseColor("#C8D5EC"));
//        tv1l4.setText("Data Renovação");
//        tv1l4.setLayoutParams(tv1l4Layout);
//        
//        TextView tv2l4 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l4Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l4Layout.setMargins(16, 16, 1, 1);
//        tv2l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tv2l4.setText(prazoDevolucao);
//        tv2l4.setLayoutParams(tv2l4Layout);
//        
//        linha4.addView(tv1l4);
//        linha4.addView(tv2l4);
//        
//        /*****************************************************************/
//        
//        //LINHA 5
//        
//        TableRow linha5 = new TableRow(activity);
//		TableRow.LayoutParams linha5Layout = new TableRow.LayoutParams();
//		linha5Layout.setMargins(1, 0, 1, 0);
//		linha5.setBackgroundColor(Color.parseColor("#000000"));
//        linha5.setLayoutParams(linha5Layout);
//		
//		TextView tv1l5 = new TextView(activity);
//        LinearLayout.LayoutParams tv1l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        tv1l5Layout.setMargins(30, 30, 30, 30);
//        tv1l5.setBackgroundColor(Color.parseColor("#C8D5EC"));
//        tv1l5.setText("Data Devolucao");
//        tv1l5.setLayoutParams(tv1l5Layout);
//        
//        TextView tv2l5 = new TextView(activity);
//        LinearLayout.LayoutParams tv2l5Layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        tv2l5Layout.setMargins(16, 16, 1, 1);
//        tv2l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tv2l5.setText(dataDevolucao);
//        tv2l5.setLayoutParams(tv2l5Layout);
//        
//        linha5.addView(tv1l5);
//        linha5.addView(tv2l5);       
//        
//        
//        tabela.addView(linha1);
//        tabela.addView(linha2);
//        tabela.addView(linha3);
//        tabela.addView(linha4);
//        tabela.addView(linha5);
//       
//        
//		return tabela;
//	}
	
}
