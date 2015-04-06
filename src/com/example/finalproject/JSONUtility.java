package com.example.finalproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class JSONUtility {
	
	static public class StockJSONParser{

		
		static public ArrayList<Security> parseStocks(String in) throws JSONException, IOException{	
			ArrayList<Security> stocks = new ArrayList<Security>();			
			Security stock = new Security();


			
			JSONObject root = new JSONObject(in);
			JSONObject query = root.getJSONObject("query");
			JSONObject results = query.getJSONObject("results");
			
			int count = query.getInt("count");
			
			if(count > 1){
				
				JSONArray quotes = results.getJSONArray("quote");
				
				for(int i=0;i<quotes.length();i++){
					JSONObject stockObject = quotes.getJSONObject(i);
					stock.setCompanyName(stockObject.getString("Name"));
					stock.setSymbol(stockObject.getString("symbol"));
					stock.setCurrentPrice(stockObject.getDouble("Ask"));
					stock.setChange(stockObject.getDouble("Change"));
					stocks.add(stock);
				}
				
				
			}else if (count == 1){
				
				JSONObject quote = results.getJSONObject("quote");
				stock.setCompanyName(quote.getString("Name"));
				stock.setSymbol(quote.getString("symbol"));
				stock.setCurrentPrice(quote.getDouble("Ask"));
				stock.setChange(quote.getDouble("Change"));
				Log.d("ULTRONICS", stock.toString());
				stocks.add(stock);
			}
			
			
			return stocks;
		}

		
		//=================================================================================================
		
		static public ArrayList<Headline> parseNews(String in) throws JSONException, IOException{	
			ArrayList<Headline> headlines = new ArrayList<Headline>();
			Headline headline = new Headline();
			
			JSONObject root = new JSONObject(in);
			JSONObject query = root.getJSONObject("query");
			JSONObject results = query.getJSONObject("results");
			
			int count = query.getInt("count");
			
			if(count > 1){
				
				JSONArray headlinez = results.getJSONArray("a");
				
				for(int i=0;i<headlinez.length();i++){
					headline = new Headline();
					JSONObject headlineObject = headlinez.getJSONObject(i);
					headline.setTitle(headlineObject.getString("content"));
					headline.setLink(headlineObject.getString("href"));
					headlines.add(headline);
					Log.d("ULTRONICS", headline.toString());
				}
				
				
			}else if (count == 1){

				JSONObject headlineObject = results.getJSONObject("a");
				headline.setTitle(headlineObject.getString("content"));
				headline.setLink(headlineObject.getString("href"));
				headlines.add(headline);
				//Log.d("ULTRONICS", headline.toString());
			}
			
			
			return headlines;
		}
		
		
	}
	
	
	
}
