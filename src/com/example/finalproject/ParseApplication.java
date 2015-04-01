package com.example.finalproject;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

/*
 * Author: Alexander Pinkerton, Udeep Manchanda, Tianyi Xie
 */

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    //Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "c2uyIWfEZUScMWx1YbSwciAAVudvy4wpKrMhdXbd", "j6frxypwCpsIkbr5gJiGwPLxhcXVUOGfwFyRO48s");
    //Parse.initialize(this);


    //ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = ParseUser.getCurrentUser().getACL();//new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
