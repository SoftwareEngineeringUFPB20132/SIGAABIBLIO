package br.nti.SigaaBiblio.activities;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;



import com.google.android.gms.maps.CameraUpdateFactory;
>>>>>>> ce8d4db10f3607c56d0e844061e1f61bc7126364
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
<<<<<<< HEAD
=======
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
>>>>>>> ce8d4db10f3607c56d0e844061e1f61bc7126364
import com.nti.SigaaBiblio.R;
import com.nti.SigaaBiblio.R.layout;
import com.nti.SigaaBiblio.R.menu;
import com.nti.SigaaBiblio.utils.GMapV2Direction;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



@SuppressLint("UseSparseArrays")
public class MapsActivity extends  FragmentActivity {
    
	
	private GoogleMap map;
	private GMapV2Direction md;
	private Map<Integer,LatLng> bibliotecaGeoPo;
	private String[] bibliotecas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		
		SupportMapFragment map_s = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		map = map_s.getMap(); 
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		
		Spinner bibliotecasDisponiveis = (Spinner)findViewById(R.id.spinnerMaps);
		bibliotecas = new String[]{"Biblioteca Central", "Biblioteca Setorial CCEN","Biblioteca Setorial CCS","Biblioteca Setorial do CCJ"};
		Double[][] geopos = {{-7.137867,-34.846693},{-7.140454,-34.84666},{-7.13545,-34.841618},{-7.140656,-34.849879}};
		bibliotecaGeoPo = new HashMap<Integer, LatLng>();
		for(int i=0;i<bibliotecas.length;i++){
			bibliotecaGeoPo.put(i, new LatLng(geopos[i][0], geopos[i][1]));
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bibliotecas);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bibliotecasDisponiveis.setAdapter(spinnerArrayAdapter);
		bibliotecasDisponiveis.setSelection(arrayAdapter.getPosition("Biblioteca Central"));
		bibliotecasDisponiveis.setOnItemSelectedListener(new BiblioItemSelectedListener());
		
		
	}
	
	
	
	public void setMap(LatLng fromPosition,LatLng toPosition, long id){
		
		

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(fromPosition, 16));
		
		map.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
		map.addMarker(new MarkerOptions().position(toPosition).title("End"));
	    
		md = new GMapV2Direction();
		Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
		int duration = md.getDurationValue(doc);
		String distance = md.getDistanceText(doc);
		String start_address = md.getStartAddress(doc);

		  ArrayList<LatLng> directionPoint = md.getDirection(doc);
		  PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
			
		  for(int i = 0 ; i < directionPoint.size() ; i++) {			
				rectLine.add(directionPoint.get(i));
			}
			
		  map.addPolyline(rectLine);
		  int idInt = (int) id;
		  String mensagem= "Chegar a Biblioteca "+bibliotecas[idInt]+" partindo de "+start_address+"\n Distância: "+distance+"\n Duração do Caminho:  "+duration;
			Toast.makeText(MapsActivity.this, mensagem, Toast.LENGTH_LONG)
			.show();

	}
	
	
	public LatLng getCurrentLocation(){
		
		LocationManager mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String best= mgr.getBestProvider(criteria, true);
		Location location = mgr.getLastKnownLocation(best);
		double lat,lng;
		if(location!=null){
			lat = location.getLatitude();
			lng = location.getLongitude();
			
			return new LatLng(lat, lng);
		}
		
		return null;
	}
	
	private class BiblioItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	       int a = (int) id;
	    
	        setMap(getCurrentLocation(), bibliotecaGeoPo.get(a), id);
	      //Central: , CCEN: , CCS:, CCJ: 
	        
	    }

	    public void onNothingSelected(AdapterView parent) {
	        // Do nothing.
	    }
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}

}
