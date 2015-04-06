package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StockNewsFragment extends Fragment{
	
	private NewsDelegate mListener;
	ArrayList<Headline> newsItems;
	ListView newsLister;

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mListener = (NewsDelegate) activity;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		newsLister = (ListView) getView().findViewById(R.id.lv_news);
		updateNewsList();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_newslist, container,false);
		return rootView;
	}
	
	
	public void updateNewsList(){
		//newsItems = news;
		new JSONNewsAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D'http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3DGOOG'%20and%20xpath%3D'%2F%2Fdiv%5B%40id%3D%22yfi_headlines%22%5D%2Fdiv%5B2%5D%2Ful%2Fli%2Fa'&format=json&diagnostics=true&callback=");
	}
	
	
	public void test(){
		newsLister.invalidateViews();
	}
	
	
	
	
	
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
				Log.d("ADLKHASDJHASDKHASD", result.size()+"");
				newsItems = result;
				ArrayAdapter adapter = new ArrayAdapter<Headline>(getActivity(), android.R.layout.simple_list_item_1, newsItems);
				adapter.setNotifyOnChange(true);
				newsLister.setAdapter(adapter);	
			}
			
			
			
		}
		
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public interface NewsDelegate{
		
		public void updateNews();
		
	}

	
	
}
