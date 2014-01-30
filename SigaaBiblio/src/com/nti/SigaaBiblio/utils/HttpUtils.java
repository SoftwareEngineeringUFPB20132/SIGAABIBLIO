package com.nti.SigaaBiblio.utils;

import java.io.IOException;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;

import android.os.AsyncTask;



public class HttpUtils{ 


  
  public static String urlContent(String address) throws IOException, ClientProtocolException {
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(address);
    ResponseHandler<String> handler = new BasicResponseHandler();
    return(client.execute(httpGet, handler));
  }
  
  
  public static String urlContentPost(String address, String ... paramNamesAndValues) 
      throws IOException, ClientProtocolException {
    HttpClient client = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost(address);
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    for(int i=0; i<paramNamesAndValues.length-1; i=i+2) {
      String paramName = paramNamesAndValues[i];
      String paramValue = paramNamesAndValues[i+1];  // NOT URL-Encoded
      params.add(new BasicNameValuePair(paramName, paramValue));
    }
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
    httpPost.setEntity(entity);
    ResponseHandler<String> handler = new BasicResponseHandler();
    return(client.execute(httpPost, handler));
  }
	
}
