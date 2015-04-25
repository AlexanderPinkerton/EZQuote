package com.example.finalproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class QuoteViewFragment extends Fragment implements OnClickListener{
	LineChart chart;
	String stockSymbol;
	Security stockObj;
	TextView openTv,mktCapTv,highTv,lowTv,wHighTv,wLowTv,volTv,avgVolTv,peTv,yieldTv,stockNameTv;
	TextView oneMonth,threeMonth,sixMonth,oneYear,threeYear;
	ImageView favIcon,notFavIcon;
	
	public QuoteViewFragment(Security stockObj){
		this.stockObj = stockObj;
		this.stockSymbol = stockObj.getSymbol();
	}
    
    public void stockhistory(String stock){
    	
    }
    
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		chart = (LineChart) getView().findViewById(R.id.chart);
		new getCsv().execute(stockSymbol,"oneYear");
		favIcon = (ImageView) getView().findViewById(R.id.favIcon);
		notFavIcon = (ImageView) getView().findViewById(R.id.notFavIcon);
		
		ParseQuery<ParseObject> isFavQuery = ParseQuery.getQuery("Favorites");
		isFavQuery.whereEqualTo("UserName", ParseUser.getCurrentUser().getUsername());
		isFavQuery.whereEqualTo("StockName", stockSymbol);
		
		isFavQuery.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				
				if(e == null){
					if(object != null){
						favIcon.setVisibility(View.VISIBLE);
						notFavIcon.setVisibility(View.GONE);
					}else{
						favIcon.setVisibility(View.GONE);
						notFavIcon.setVisibility(View.VISIBLE);
					}
				}else{
					favIcon.setVisibility(View.GONE);
					notFavIcon.setVisibility(View.VISIBLE);
					Log.d("Error in Fav", e.getLocalizedMessage());
				}
				
			}
		});
		
		
		openTv = (TextView) getView().findViewById(R.id.openValTv);
		mktCapTv = (TextView) getView().findViewById(R.id.mktCapVal);
		highTv = (TextView) getView().findViewById(R.id.highValTv);
		lowTv = (TextView) getView().findViewById(R.id.lowValTv);
		wHighTv = (TextView) getView().findViewById(R.id.wHighValTv);
		wLowTv = (TextView) getView().findViewById(R.id.wLowValTv);
		volTv = (TextView) getView().findViewById(R.id.volValTv);
		avgVolTv = (TextView) getView().findViewById(R.id.avgVolValTv);
		peTv = (TextView) getView().findViewById(R.id.peValTv);
		yieldTv = (TextView) getView().findViewById(R.id.yieldValTv);
		stockNameTv = (TextView) getView().findViewById(R.id.stockNameTv);
		
		oneMonth = (TextView) getView().findViewById(R.id.oneMonth);
		threeMonth = (TextView) getView().findViewById(R.id.threeMonth);
		sixMonth = (TextView) getView().findViewById(R.id.sixMonth);
		oneYear = (TextView) getView().findViewById(R.id.oneYear);
		threeYear = (TextView) getView().findViewById(R.id.threeYear);
		
		oneMonth.setOnClickListener(this);
		threeMonth.setOnClickListener(this);
		sixMonth.setOnClickListener(this);
		oneYear.setOnClickListener(this);
		threeYear.setOnClickListener(this);
		
		openTv.setText(stockObj.getAskPrice());
		mktCapTv.setText(stockObj.getMarketCapitalization());
		highTv.setText(stockObj.getDaysHigh());
		lowTv.setText(stockObj.getDaysLow());
		wHighTv.setText(stockObj.getYearHigh());
		wLowTv.setText(stockObj.getYearLow());
		volTv.setText(stockObj.getVolume());
		avgVolTv.setText(stockObj.getAvgVolume());
		peTv.setText(stockObj.getPeRatio());
		yieldTv.setText(stockObj.getYield());
		stockNameTv.setText(stockObj.getCompanyName());
		
		favIcon.setOnClickListener(this);
		notFavIcon.setOnClickListener(this);
		 
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.favIcon:
			
			removeFromFavorites();
			
			break;

		case R.id.notFavIcon:
			saveInFavorites();
			break;
			
		case R.id.oneMonth:
			//saveInFavorites();
			oneMonth.setTypeface(oneMonth.getTypeface(),Typeface.BOLD);
			oneMonth.setTextColor(Color.WHITE);
			threeMonth.setTextColor(Color.GRAY);
			sixMonth.setTextColor(Color.GRAY);
			oneYear.setTextColor(Color.GRAY);
			threeYear.setTextColor(Color.GRAY);
			threeMonth.setTypeface(Typeface.SANS_SERIF);
			sixMonth.setTypeface(Typeface.SANS_SERIF);
			oneYear.setTypeface(Typeface.SANS_SERIF);
			threeYear.setTypeface(Typeface.SANS_SERIF);
			new getCsv().execute(stockSymbol,"oneMonth");
			break;
		case R.id.threeMonth:
			//saveInFavorites();
			threeMonth.setTypeface(threeMonth.getTypeface(),Typeface.BOLD);
			threeMonth.setTextColor(Color.WHITE);
			oneMonth.setTextColor(Color.GRAY);
			sixMonth.setTextColor(Color.GRAY);
			oneYear.setTextColor(Color.GRAY);
			threeYear.setTextColor(Color.GRAY);
			oneMonth.setTypeface(Typeface.SANS_SERIF);
			sixMonth.setTypeface(Typeface.SANS_SERIF);
			oneYear.setTypeface(Typeface.SANS_SERIF);
			threeYear.setTypeface(Typeface.SANS_SERIF);
			new getCsv().execute(stockSymbol,"threeMonth");
			break;
		case R.id.sixMonth:
			//saveInFavorites();
			sixMonth.setTypeface(sixMonth.getTypeface(),Typeface.BOLD);
			sixMonth.setTextColor(Color.WHITE);
			threeMonth.setTextColor(Color.GRAY);
			oneMonth.setTextColor(Color.GRAY);
			oneYear.setTextColor(Color.GRAY);
			threeYear.setTextColor(Color.GRAY);
			threeMonth.setTypeface(Typeface.SANS_SERIF);
			oneMonth.setTypeface(Typeface.SANS_SERIF);
			oneYear.setTypeface(Typeface.SANS_SERIF);
			threeYear.setTypeface(Typeface.SANS_SERIF);
			new getCsv().execute(stockSymbol,"sixMonth");
			break;
		case R.id.oneYear:
			//saveInFavorites();
			threeMonth.setTextColor(Color.GRAY);
			sixMonth.setTextColor(Color.GRAY);
			oneMonth.setTextColor(Color.GRAY);
			threeYear.setTextColor(Color.GRAY);
			oneYear.setTypeface(oneYear.getTypeface(),Typeface.BOLD);
			oneYear.setTextColor(Color.WHITE);
			threeMonth.setTypeface(Typeface.SANS_SERIF);
			sixMonth.setTypeface(Typeface.SANS_SERIF);
			oneMonth.setTypeface(Typeface.SANS_SERIF);
			threeYear.setTypeface(Typeface.SANS_SERIF);
			new getCsv().execute(stockSymbol,"oneYear");
			break;
		case R.id.threeYear:
			//saveInFavorites();
			threeMonth.setTextColor(Color.GRAY);
			sixMonth.setTextColor(Color.GRAY);
			oneYear.setTextColor(Color.GRAY);
			oneMonth.setTextColor(Color.GRAY);
			threeYear.setTypeface(threeYear.getTypeface(),Typeface.BOLD);
			threeYear.setTextColor(Color.WHITE);
			threeMonth.setTypeface(Typeface.SANS_SERIF);
			sixMonth.setTypeface(Typeface.SANS_SERIF);
			oneYear.setTypeface(Typeface.SANS_SERIF);
			oneMonth.setTypeface(Typeface.SANS_SERIF);
			new getCsv().execute(stockSymbol,"threeYear");
			break;
		}
		
	}

	private void removeFromFavorites() {
		
		ParseQuery<ParseObject> favQuery = ParseQuery.getQuery("Favorites");
		favQuery.whereEqualTo("UserName", ParseUser.getCurrentUser().getUsername());
		favQuery.whereEqualTo("StockName", stockSymbol);
		favQuery.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				
				if(e == null){
					try {
						object.delete();
						favIcon.setVisibility(View.GONE);
						notFavIcon.setVisibility(View.VISIBLE);
						Toast.makeText(getActivity(), "Successfully removed from Favorites", Toast.LENGTH_SHORT).show();
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
				}else{
					Log.d("Error in Fav",e.getLocalizedMessage());
				}
			}
		});
		
	}

	private void saveInFavorites() {
		ParseObject favObj = new ParseObject("Favorites");
    	favObj.put("UserName", ParseUser.getCurrentUser().getUsername());
    	favObj.put("StockName", stockSymbol);
    	
    	favObj.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				
				if(e == null){
					favIcon.setVisibility(View.VISIBLE);
					notFavIcon.setVisibility(View.GONE);
					Toast.makeText(getActivity(), "Successfully saved in Favorites", Toast.LENGTH_SHORT).show();
				}else{
					Log.d("Error in Fav", e.getLocalizedMessage());
				}
			}
		});
    	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_quoteview, container,false);
		
		
		
		
		return rootView;
	}
	
	
	
	
	
	public interface QuoteViewDelegate{
		
		public void delegateFromQuoteView();
		
	}


	class getCsv extends AsyncTask<String, Integer, LineData>{
		ProgressDialog pd;
		@Override
		protected LineData doInBackground(String... params) {
			// TODO Auto-generated method stub
		    ArrayList<String> stocklist = new ArrayList<String>();
			ArrayList<String> stockDate = new ArrayList<String>();
			ArrayList<Float> stockVolume = new ArrayList<Float>();
			ArrayList<Float> stockClose = new ArrayList<Float>();
			
		    ArrayList<Entry> valsPrice = new ArrayList<Entry>();
		    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		    ArrayList<String> xVals = new ArrayList<String>();
		    
		    
		    String quoteCompany = params[0];//Company Stock Code!!!!
		    String timePeriod = params[1];
		    
		    Calendar cal = Calendar.getInstance(); 
			int presentYear = cal.get(Calendar.YEAR);
			int presentMonth = cal.get(Calendar.MONTH);
			int presentDay = cal.get(Calendar.DATE);
			
			int pastMonth = 0,pastYear=0;
			
		    if(timePeriod.equals("oneMonth")){
		    	if(presentMonth == 0){
		    		pastMonth = 11;
		    		pastYear = presentYear - 1;
		    	}else{
		    		pastMonth = presentMonth - 1;
		    		pastYear = presentYear;
		    	}
		    }else if(timePeriod.equals("threeMonth")){
		    	if(presentMonth >= 3){
		    		pastMonth = presentMonth - 3;
		    		pastYear = presentYear;
		    	}else {
		    		pastMonth = presentMonth + 9;
		    		pastYear = presentYear - 1;
		    	}
		    	
		    }else if(timePeriod.equals("sixMonth")){
		    	if(presentMonth >= 6){
		    		pastMonth = presentMonth - 6;
		    		pastYear = presentYear;
		    	}else {
		    		pastMonth = presentMonth + 6;
		    		pastYear = presentYear - 1;
		    	}
		    	
		    }else if(timePeriod.equals("oneYear")){
		    	pastYear = presentYear - 1;
		    	pastMonth = presentMonth;
		    }else if(timePeriod.equals("threeYear")){
		    	pastYear = presentYear - 3;
		    	pastMonth = presentMonth;
		    }
		   
			URL url = null;
			try {
				if (presentMonth == 1 && presentDay == 29) {
					url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+quoteCompany+"&d=1&e=29&f="
									+ presentYear + "&g=d&a=1&b=28&c=" + (presentYear - 1)
									+ "&ignore.csv");
				} else {
					url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+quoteCompany+"&d="
									+ presentMonth + "&e=" + presentDay + "&f=" + presentYear
									+ "&g=d&a=" + pastMonth + "&b=" + presentDay + "&c="
									+ (pastYear) + "&ignore.csv");
				}
			}catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		    try {
				stocklist = getText(url);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    for(int i = 1; i < stocklist.size();i++){
		    	String[] stock = stocklist.get(i).split(",");
				stockDate.add(stock[0]);
				stockVolume.add(Float.parseFloat(stock[5]));
				stockClose.add(Float.parseFloat(stock[6]));
		    	
		    }
			
			Collections.reverse(stockDate);
			Collections.reverse(stockVolume);
			Collections.reverse(stockClose);
			
			for(int i = 0; i < stockDate.size(); i++){
				Entry temp = new Entry(stockClose.get(i), i);
				valsPrice.add(temp);
				xVals.add(stockDate.get(i));
			}
			
			LineDataSet setComp1 = new LineDataSet(valsPrice, params[0]);
			setComp1.setCircleColor(Color.GRAY);
			setComp1.setCircleSize(1f);
			setComp1.setColor(Color.GRAY);
			
			
			
			dataSets.add(setComp1);
			
			
			
			LineData data = new LineData(xVals, dataSets);
			

			return data;
		}
		
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			pd = new ProgressDialog(getActivity(),AlertDialog.THEME_HOLO_DARK);
			pd.setTitle("Loading Chart");
			pd.setMessage("Loading chart data...");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected void onPostExecute(LineData result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			chart.getXAxis().setTextColor(Color.WHITE);
			chart.getAxisLeft().setTextColor(Color.WHITE);
			chart.getAxisRight().setTextColor(Color.WHITE);
			chart.getLegend().setTextColor(Color.WHITE);
			
			chart.getAxisLeft().setStartAtZero(false);
			chart.getAxisRight().setStartAtZero(false);
			
			chart.setDescription("");
			chart.setGridBackgroundColor(Color.DKGRAY);
			chart.setBackgroundColor(Color.BLACK);
			chart.setData(result);
			chart.invalidate();
			pd.dismiss();

		}
		
	}
	
	
    public static ArrayList<String> getText(URL website) throws Exception {
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        ArrayList<String> response = new ArrayList<String>();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.add(inputLine);

        in.close();
        return response;
    }

	
}
