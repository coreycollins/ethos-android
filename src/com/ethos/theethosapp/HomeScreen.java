package com.ethos.theethosapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeScreen extends Activity{
	
	TextView tv;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		tv =(TextView)findViewById(R.id.welcome);
		/*
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.getInBackground("zc8pBoM1ea", new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		    	tv.setText(object.getInt("score"));
		    } else {
		    	tv.setText("fuck");
		    }
		  }
		});
		
		
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Comments");
		query2.whereEqualTo("fromUser", "1318080380");
		query2.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + scoreList.size() + " scores");
		            tv.setText(scoreList.get(0).getString("comment"));
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		            tv.setText("fuck2");
		        }
		    }
		});
		
		*/
		
		ParseQuery<ParseObject> query3 = ParseQuery.getQuery("User");
		query3.whereEqualTo("username", "8ijpv6x0wmqzm63t6l8f3y0pd");
		query3.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + scoreList.toString() + " scores");
		            //tv.setText(scoreList.get(0).getInt("score"));
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		            tv.setText("fuck2");
		        }
		    }
		});
		
		
		//tv.setText(ParseFacebookUtils.getSession().toString());
		Log.d("Session info: ", ParseFacebookUtils.getSession().toString());
		Button search = (Button)findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSearchActivity();
			}
		});
	 
	}
	
	public void openSearchActivity(){
		  Intent intent = new Intent(HomeScreen.this, Search.class);
	      startActivity(intent);
	}
	
}
