package com.example.finalproject;

import java.util.ArrayList;

import com.example.finalproject.StockListFragment.StockDelegate;
import com.example.finalproject.StockNewsFragment.NewsDelegate;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.os.Build;

public class MarketSummary extends Activity implements NewsDelegate, StockDelegate{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getFragmentManager()
			.beginTransaction()
			.add(R.id.container, new StockListFragment(),"stocklist")
			.add(R.id.newscontainer, new StockNewsFragment(),"newslist")
			.add(R.id.searchcontainer, new SearchFragment(),"searchbar")
			.commit();
			
		}
		
		//Quote Search
		//new JSONQuoteAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22GOOG%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");	
		
		//News Search
		//new JSONNewsAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D'http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3DGOOG'%20and%20xpath%3D'%2F%2Fdiv%5B%40id%3D%22yfi_headlines%22%5D%2Fdiv%5B2%5D%2Ful%2Fli%2Fa'&format=json&diagnostics=true&callback=");
		
		
		//select * from html where url='http://finance.yahoo.com/q?s=yhoo' and xpath='//div[@id="yfi_headlines"]/div[2]/ul/li/a'
		//https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D'http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3Dyhoo'%20and%20xpath%3D'%2F%2Fdiv%5B%40id%3D%22yfi_headlines%22%5D%2Fdiv%5B2%5D%2Ful%2Fli%2Fa'&diagnostics=true
		//select * from yahoo.finance.quotes where symbol in ("GOOG")
		
		StockListFragment stockLister = (StockListFragment) getFragmentManager().findFragmentByTag("stocklist");
		StockNewsFragment newsLister = (StockNewsFragment) getFragmentManager().findFragmentByTag("newslist");
		//stockLister.updateSecurities(stocks);

		
		
	}

	
	
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	





	@Override
	public void updateStocks() {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void updateNews() {
		// TODO Auto-generated method stub
		
	}
	
	


}
