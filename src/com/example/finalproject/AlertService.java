package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import com.example.finalproject.StockListFragment.JSONQuoteAsyncTask;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AlertService extends Service {
	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	// Random number generator
	private final Random mGenerator = new Random();
	
	ArrayList<StockAlert> Alerts;
	ArrayList<Security> Stocks;
 
	public static final long NOTIFY_INTERVAL = 5 * 60 * 1000; // 5 minutes

	// run on another Thread to avoid crash
	private Handler mHandler = new Handler();
	// timer handling
	private Timer mTimer = null;

	@Override
	public void onCreate() {

		Log.d("SERVICE", "THE SERVICE WAS STARTED SUCCESSFULLY");

		// cancel if already existed
		if (mTimer != null) {
			mTimer.cancel();
		} else {
			// recreate new
			mTimer = new Timer();
		}
		// schedule task
		mTimer.scheduleAtFixedRate(new CheckAlertTask(), 0, NOTIFY_INTERVAL);
		//sendAlert();
	}

	class CheckAlertTask extends TimerTask {

		@Override
		public void run() {
			// run on another thread
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// display toast
					sendAlert();
				}

			});
		}
	}
	
	
	
	
	
	
	
	
	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
		AlertService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return AlertService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	/** method for clients */
	public int getRandomNumber() {
		return mGenerator.nextInt(100);
	}

	/**
	 * Go to parse.com and query the users alerts, if one is active, send
	 * notification.
	 */
	public void checkAlerts() {

		Alerts = new ArrayList<StockAlert>();
		
		ParseQuery<ParseObject> alertQuery = ParseQuery.getQuery("Alert");
		alertQuery.whereEqualTo("UserName", ParseUser.getCurrentUser().getUsername());
		alertQuery.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				for(ParseObject obj: objects){
					Alerts.add(new StockAlert(obj.getString("oldPrice"), obj.getString("targetPrice"), obj.getString("symbol")));
				}
				
				//new JSONQuoteAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"+ stocks +"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");	

				
			}
		});
		
		
		
		
		
		
	}

	public void sendAlert() {
		Notification.Builder mBuilder = new Notification.Builder(this)
				.setSmallIcon(R.drawable.appicon)
				.setContentTitle("My notification")
				.setContentText("Hello World!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MarketSummaryActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MarketSummaryActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	
	
	
	
	
	
	
	
	
	public class JSONQuoteAsyncTask extends AsyncTask<String, Void, ArrayList<Security>>{
		ProgressDialog pd;
		@Override
		protected ArrayList<Security> doInBackground(String... params) {

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
		protected void onPostExecute(ArrayList<Security> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
					
			if(result != null){
				Stocks = result;
				
				
				
				Log.d("DEMO", result.toString());
			}
			
		}
		
		

	}
	
	
	

}
