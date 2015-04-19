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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;

public class StockListFragment extends Fragment{

	private StockDelegate mListener;
	SwipeMenuListView lv;
	ArrayAdapter<Security> stockListAdapter;
	ArrayList<Security> securityList;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mListener = (StockDelegate) activity;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
	//	TextView title = (TextView) getActivity().findViewById(R.id.tv_listtitle);
	//	title.setText("Market Summary");
		
		
		ArrayList<Security> a = new ArrayList<Security>();
		
		
		lv = (SwipeMenuListView) getActivity().findViewById(R.id.lv_stock);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mListener.securityClicked(stockListAdapter.getItem(position).getSymbol());
				
			}
		});
		
		// step 1. create a MenuCreator
				SwipeMenuCreator creator = new SwipeMenuCreator() {

					@Override
					public void create(SwipeMenu menu) {
						// create "open" item
						/*SwipeMenuItem openItem = new SwipeMenuItem(
								getActivity());
						// set item background
						openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
								0xCE)));
						// set item width
						openItem.setWidth(dp2px(90));
						// set item title
						openItem.setTitle("Open");
						// set item title fontsize
						openItem.setTitleSize(18);
						// set item title font color
						openItem.setTitleColor(Color.WHITE);
						// add to menu
						menu.addMenuItem(openItem);*/

						// create "delete" item
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getActivity());
						// set item background
						deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
								0xCE)));
						/*deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
								0x3F, 0x25)));*/
						// set item width
						deleteItem.setWidth(dp2px(90));
						// set a icon
						deleteItem.setIcon(R.drawable.ic_delete);
						// add to menu
						menu.addMenuItem(deleteItem);
					}
				};
				// set creator
				lv.setMenuCreator(creator);

				// step 2. listener item click event
				lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
						Security item = securityList.get(position);
						switch (index) {
						case 0:
							// open
							//open(item);
							break;
						case 1:
							// delete
//							delete(item);
							securityList.remove(position);
							stockListAdapter.notifyDataSetChanged();
							break;
						}
						return false;
					}
				});
				
				// set SwipeListener
				lv.setOnSwipeListener(new OnSwipeListener() {
					
					@Override
					public void onSwipeStart(int position) {
						// swipe start
					}
					
					@Override
					public void onSwipeEnd(int position) {
						// swipe end
					}
				});

				// other setting
//				listView.setCloseInterpolator(new BounceInterpolator());
				
				// test item long click
				lv.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							int position, long id) {
						Toast.makeText(getActivity(), position + " long click", 0).show();
						return false;
					}
				});
		
		
		
		updateSecurities(a);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_stocklist, container,false);
		return rootView;
	}

	
	public void updateSecurities(ArrayList<Security> stocks){
		new JSONQuoteAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22GOOG,YHOO,ORCL,BCX,MSFT,MSG,QCOM,QQEW%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");	
	}
	
	
	
	
	
	
	public class JSONQuoteAsyncTask extends AsyncTask<String, Void, ArrayList<Security>>{

		@Override
		protected ArrayList<Security> doInBackground(String... params) {
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
				
				return JSONUtility.StockJSONParser.parseStocks(sb.toString());
				
			}
			
			} catch (IOException e) {
		
				e.printStackTrace();
			} catch (JSONException e) {
				
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
		protected void onPostExecute(ArrayList<Security> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			
			if(result != null){
				securityList = result;
				stockListAdapter = new StockListAdapter(getActivity(), R.layout.stock_listview,securityList,StockListFragment.this,"Change");
				lv.setAdapter(stockListAdapter);
				/*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Log.d("here","yes");
						
					}
				});*/
				Log.d("DEMO", result.toString());
			}
			
		}
		
		

	}
	
	
	
	
	public void refreshListView(String which){
		stockListAdapter = new StockListAdapter(getActivity(), R.layout.stock_listview,securityList,StockListFragment.this,which);
		stockListAdapter.notifyDataSetChanged();
		lv.setAdapter(stockListAdapter);
		
	}
	
	
	
	
	public interface StockDelegate{
		
		public void securityClicked(String stockSymbol);
		
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	
}
