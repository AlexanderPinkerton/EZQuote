package com.example.finalproject;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*
 * Author: Alexander Pinkerton, Udeep Manchanda, Tianyi Xie
 */


public class LoginActivity extends Activity implements OnClickListener{

	EditText email, pass;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getActionBar().setTitle("Login");
        
        email = (EditText)findViewById(R.id.editTextEmail);
        pass = (EditText)findViewById(R.id.editTextPassword);
        
        Button b = (Button)findViewById(R.id.buttonCreateNewAccount);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.buttonLogin);
        b.setOnClickListener(this);
        
        
        if (ParseUser.getCurrentUser() != null){
        	Intent I = new Intent(LoginActivity.this, MarketSummaryActivity.class);
			startActivity(I);
			finish();
        }
        
  
    }

    
    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonCreateNewAccount:
			
			Intent i = new Intent(this, SignUpActivity.class);
			startActivity(i);
			
			
		case R.id.buttonLogin:

			
		
			if(email.getText() != null && email.getText().toString().trim().length() == 0){
				Toast.makeText(LoginActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
			}else if(pass.getText() != null && pass.getText().toString().trim().length() == 0){
				Toast.makeText(LoginActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
			}else{
				
				ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
						if (user != null) {
						    // Hooray! The user is logged in.
							Intent I = new Intent(LoginActivity.this, MarketSummaryActivity.class);
							//I.putExtra(name, value)
							startActivity(I);
							finish();
						    } else {
						      // Signup failed. Look at the ParseException to see what happened.
						    	Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
						    }
					}
					});
			}

			
			
			break;
		
		}
	}
	
	
	
	
	
	
	
}
