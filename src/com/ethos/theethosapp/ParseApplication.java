package com.ethos.theethosapp;

import java.util.Collection;

import android.app.Application;

import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;


public class ParseApplication extends Application {

	private Collection<GraphUser> selectedUsers;
	private String currentUserFBId;

    public Collection<GraphUser> getSelectedUsers() {
        return selectedUsers;
    }

	public void setSelectedUsers(Collection<GraphUser> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
	
	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "jNKXY8CLM6xtT2CnpVQtkVEpF0k3k2de6ER7b4DI", "P70AezEBYIMCsWPrVhTut9WhDIm7ntKtQ1xQcbtg");
		//Initialize FB:
		ParseFacebookUtils.initialize("595212907211045");

		//ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		//defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
	}

	public void setCurrentUserFBId(String id) {
		// TODO Auto-generated method stub
		currentUserFBId = id;
	} 
	public String getCurrentUserFBId() {
		// TODO Auto-generated method stub
		return currentUserFBId;
	} 
}
