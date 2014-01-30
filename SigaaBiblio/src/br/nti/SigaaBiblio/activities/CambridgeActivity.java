package br.nti.SigaaBiblio.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.nti.SigaaBiblio.model.LivroCambridge;

import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.utils.HttpUtils;

public class CambridgeActivity extends Activity {

		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_cambridge);
			Bundle bundle = getIntent().getExtras();
			String busca=bundle.getString("busca");
			EditText editText = (EditText) findViewById(R.id.editTextCambridge1);
			editText.setText(busca);
			Button goSearch = (Button) findViewById(R.id.buttonCambridge1);
			goSearch.performClick();
			
		}

		
		public void buscarCatalogo(View v) {
			EditText editText = (EditText) findViewById(R.id.editTextCambridge1);
			String inputString = editText.getText().toString().trim();

			//caso tenha espaco em branco 
			inputString = inputString.replace(" ","%20");
					
			
			final String url = "http://www.lib.cam.ac.uk/api/aquabrowser/abSearchThin.cgi?searchArg="
					+ inputString.trim() + "&format=json&resultsPage=1";

			try {
				AsyncTask<Void, Void, JSONArray> search = new AsyncTask<Void, Void, JSONArray>() {
					
					ProgressDialog pd;
					
					@Override
					protected void onPostExecute(JSONArray result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						if(pd != null && pd.isShowing())
							pd.dismiss();
					}

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						pd = new ProgressDialog(CambridgeActivity.this);
						pd.setMessage("Processando...");
						pd.setTitle("Aguarde");
						pd.setIndeterminate(false);
					}

					@Override
					protected JSONArray doInBackground(Void... arg0) {
						String jsonString;
						JSONArray resultados = null;
						try {
							jsonString = HttpUtils.urlContent(url);
							JSONObject jsonResult = new JSONObject(jsonString);
							resultados = jsonResult.getJSONObject("search_results").getJSONArray("bib_record");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return resultados;
					}

				}.execute();

				JSONArray resultados = null;

				resultados = search.get();

				List<LivroCambridge> lista_resultados = new ArrayList<LivroCambridge>();
				for (int i = 0; i < resultados.length(); i++) {
					JSONObject r = resultados.getJSONObject(i);
					lista_resultados.add(new LivroCambridge((String) r.get("edition"), r.getString("title")));

					;
				}
				ListView listaResultados = (ListView) findViewById(R.id.listViewCrambridge1);
				ArrayList<String> values = new ArrayList<String>();
				for (LivroCambridge p : lista_resultados) {
					values.add(p.toString());
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);

				listaResultados.setAdapter(adapter);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "A Busca não Retornou Resultados!", Toast.LENGTH_LONG)
						.show();
				e.printStackTrace();
			}
		
		}
	}

