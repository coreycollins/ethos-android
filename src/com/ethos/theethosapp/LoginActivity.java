package com.ethos.theethosapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		//track opens
		ParseAnalytics.trackAppOpened(getIntent());
		//Setup push notifications
		PushService.setDefaultPushCallback(this, LoginActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		login();
		
	}
	
	private void login() {
		// TODO Auto-generated method stub
		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ethos.theethosapp", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
		

		ParseFacebookUtils.logIn(LoginActivity.this, new LogInCallback() {
		  @Override
		  public void done(ParseUser user, ParseException err) {
		    if (user == null) {
		      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
		    } else if (user.isNew()) {
		      Log.d("MyApp", "User signed up and logged in through Facebook!");
		      makeMeRequest(ParseFacebookUtils.getSession());
		      
		      Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
		      startActivity(intent);
		      finish();
		    } else {
		      Log.d("MyApp", "User logged in through Facebook!");
		      
		      Parse.Cloud.beforeSave(Parse.User, function(request, response) {

		    	  var newACL = new Parse.ACL();
		    	  newACL.setRoleWriteAccess("Administrator", true);
		    	  request.object.setACL(newACL);
		    	  response.success();  
		    	  });
		      
		      
		      makeMeRequest(ParseFacebookUtils.getSession());
		      
		      Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
		      startActivity(intent);
		      finish();
		    }
		  }
		});
	}
	
	private void makeMeRequest(final Session session) {
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {

	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    String facebookId = user.getId();
	                    ParseApplication app = (ParseApplication) getApplication();
	                    app.setCurrentUserFBId(facebookId);
	                }
	            }
	            if (response.getError() != null) {
	                // Handle error
	            }
	        }
	    });
	    request.executeAsync();
	} 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
}
