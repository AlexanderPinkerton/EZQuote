package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.message.BufferedHeader;
import org.json.JSONException;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

public class JSONNewsAsyncTask extends AsyncTask<String, Void, ArrayList<Headline>>{

	@Override
	protected ArrayList<Headline> doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
		URL url = new URL(params[0]);
		HttpURLConnection con;	
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int statusCode = con.getResponseCode();
		if(statusCode == HttpURLConnection.HTTP_OK){
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while(line != null){
				sb.append(line);
				line = reader.readLine();
			}
			
			return JSONUtility.StockJSONParser.parseNews(sb.toString());
			
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(ArrayList<Headline> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result != null){
			//Log.d("DEMO", result.toString());
		}
		
	}
	
	

}
