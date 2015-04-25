package com.example.finalproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlertService extends Service {
	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	// Random number generator
	private final Random mGenerator = new Random();

	public static final long NOTIFY_INTERVAL = 5 * 60 * 1000; // 10 seconds

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
		sendAlert();
	}

	class CheckAlertTask extends TimerTask {

		@Override
		public void run() {
			// run on another thread
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// display toast
					Toast.makeText(getApplicationContext(), getDateTime(),
							Toast.LENGTH_SHORT).show();
				}

			});
		}

		private String getDateTime() {
			// get date time in custom format
			SimpleDateFormat sdf = new SimpleDateFormat(
					"[yyyy/MM/dd - HH:mm:ss]");
			return sdf.format(new Date());
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

}
