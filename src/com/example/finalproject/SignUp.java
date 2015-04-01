package com.example.finalproject;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Author: Alexander Pinkerton, Udeep Manchanda, Tianyi Xie
 */


public class SignUp extends Activity implements OnClickListener{

	EditText e1,e2,e3,e4;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		b = (Button)findViewById(R.id.buttonSignup);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.buttonCancel);
		b.setOnClickListener(this);
		
		
		e1 = (EditText)findViewById(R.id.editTextUserName);
		e2 = (EditText)findViewById(R.id.editTextEmail);
		e3 = (EditText)findViewById(R.id.editTextPassword);
		e4 = (EditText)findViewById(R.id.editTextPasswordConfirm);
				
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonSignup:
			

			if(e2.getText() != null && e2.getText().toString().trim().length() == 0){
				Toast.makeText(SignUp.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
			}else if(e3.getText() != null && e3.getText().toString().trim().length() == 0){
				Toast.makeText(SignUp.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
			}else if(e1.getText() != null && e1.getText().toString().trim().length() == 0){
				Toast.makeText(SignUp.this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
			}else{
				
				ParseUser user = new ParseUser();
				
				if(e3.getText().toString().equals(e4.getText().toString())){
					user.setUsername(e2.getText().toString());
					user.setPassword(e3.getText().toString());
					user.put("name", e1.getText().toString());
					
					
					user.signUpInBackground(new SignUpCallback() {
						  public void done(ParseException e) {
						    if (e == null) {
						    	Toast.makeText(SignUp.this, "Account Created.", Toast.LENGTH_SHORT).show();
								finish();
						    } else {
						    	//Toast.makeText(SignUp.this, "Invalid Email.", Toast.LENGTH_SHORT).show();
						    	Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
						    }
						  }
						});
					
					
					
				}else{
					Toast.makeText(this, "Passwords must match.", Toast.LENGTH_SHORT).show();
				}
				
				// other fields can be set just like with ParseObject
				//user.put("phone", "650-253-0000");
			}
			
			
			
	
			break;
		case R.id.buttonCancel:
			finish();
			break;
		
		}
	}
	
	
	
	
	

}
