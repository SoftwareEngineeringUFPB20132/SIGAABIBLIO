package br.nti.SigaaBiblio.activities;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;

import com.nti.SigaaBiblio.R;

public class PrefsActivity extends PreferenceActivity {
  
  private static final String OPT_LEMBRAR_LOGIN = "lembrarLogin";
  private static final boolean OPT_LEMBRAR_DEF = false;
  
  private static final String OPT_COR = "cor";
  private static final String OPT_COR_DEF = "Azul";
  
  private static final String OPT_HISTORICO = "guardarHistorico";
  private static final boolean OPT_HISTORICO_DEF = false;
  
  private static final String OPT_CAMPOS_PESQUISA = "guardarCamposPesquisa";
  private static final boolean OPT_CAMPOS_PESQUISA_DEF = false;

  private static final String OPT_RENOVACAO = "guardarRenovacao";
  private static final boolean OPT_RENOVACAO_DEF = false;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 //   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    addPreferencesFromResource(R.layout.settings);
  }


  
  public static boolean getLembrarLogin(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
        OPT_LEMBRAR_LOGIN, OPT_LEMBRAR_DEF);
    
  }

  
  public static String getCor(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(
        OPT_COR, OPT_COR_DEF);
  }
  
  
  public static boolean getHistorico(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
    		OPT_HISTORICO, OPT_HISTORICO_DEF);
  }
  
  public static boolean getCamposPesquisa(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
    		OPT_CAMPOS_PESQUISA, OPT_CAMPOS_PESQUISA_DEF);
  }
  
  public static boolean getRenovacao(Context context) {
	    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
	    		OPT_RENOVACAO, OPT_RENOVACAO_DEF);
	  }
}
