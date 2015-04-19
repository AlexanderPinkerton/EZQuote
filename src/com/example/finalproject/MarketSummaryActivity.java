package com.example.finalproject;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.QuoteViewFragment.QuoteViewDelegate;
import com.example.finalproject.StockListFragment.StockDelegate;
import com.example.finalproject.StockNewsFragment.NewsDelegate;
import com.parse.ParseUser;

public class MarketSummaryActivity extends Activity implements NewsDelegate, StockDelegate, QuoteViewDelegate, SearchView.OnQueryTextListener{

	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getFragmentManager()
			.beginTransaction()
			.add(R.id.container, new StockListFragment(),"stocklist")
			.add(R.id.newscontainer, new StockNewsFragment(),"newslist")
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
		 	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menuquotesearch, menu);
	        MenuItem searchItem = menu.findItem(R.id.action_search);
	        mSearchView = (SearchView) searchItem.getActionView();
	        setupSearchView(searchItem);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.logout) {
			ParseUser.logOut();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	


	public void getQuote(){
		
		getFragmentManager()
		.beginTransaction()
		.remove(getFragmentManager().findFragmentByTag("stocklist"))
		.remove(getFragmentManager().findFragmentByTag("newslist"))
		.add(R.id.detailcontainer, new QuoteViewFragment(), "quoteview")
		.addToBackStack(null)
		.commit();
		Log.d("ASLKJASDLKJSADLJ", "THIS HAPPENED");
		
	}



	@Override
	public void securityClicked(String stockSymbol) {
		getQuote();	
	}







	@Override
	public void updateNews() {
		
		
	}


	@Override
	public void delegateFromQuoteView() {
		// TODO Auto-generated method stub
		
	}




	 private void setupSearchView(MenuItem searchItem) {

	        if (isAlwaysExpanded()) {
	            mSearchView.setIconifiedByDefault(false);
	        } else {
	            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
	                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	        }

	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        if (searchManager != null) {
	            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

	            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	            for (SearchableInfo inf : searchables) {
	                if (inf.getSuggestAuthority() != null
	                        && inf.getSuggestAuthority().startsWith("applications")) {
	                    info = inf;
	                }
	            }
	            mSearchView.setSearchableInfo(info);
	        }

	        mSearchView.setOnQueryTextListener(this);
	    }

	    public boolean onQueryTextChange(String newText) {

	        return false;
	    }

	    public boolean onQueryTextSubmit(String stockSymbol) {

	        StockNewsFragment snf = (StockNewsFragment) getFragmentManager().findFragmentByTag("newslist");
			
			snf.refresh(stockSymbol);
	        
	        
	        return false;
	    }

	    public boolean onClose() {

	        return false;
	    }

	    protected boolean isAlwaysExpanded() {
	        return false;
	    }







	


}
