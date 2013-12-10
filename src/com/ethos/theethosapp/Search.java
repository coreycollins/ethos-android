package com.ethos.theethosapp;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Search extends FragmentActivity implements OnClickListener{
    private static final int PICK_FRIENDS_ACTIVITY = 1;
    private Button pickFriendsButton;
    private TextView resultsTextView;
    private UiLifecycleHelper lifecycleHelper;
    boolean pickFriendsWhenSessionOpened;
    String recieverName, recieverFBId;
    Button angelPost, devilPost;
    EditText message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_picker_main);
        startPickFriendsActivity();
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        pickFriendsButton = (Button) findViewById(R.id.pickFriendsButton);
        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickFriends();
            }
        });
        Log.d("ParseUser:", ParseUser.getCurrentUser().toString());

        lifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);
            }
        });
        
        angelPost = (Button) findViewById(R.id.angelPost);
        devilPost = (Button) findViewById(R.id.devilPost);
        
        angelPost.setOnClickListener(this);
        devilPost.setOnClickListener(this);
        
        message = (EditText) findViewById(R.id.message);
        
        
        lifecycleHelper.onCreate(savedInstanceState);
        ensureOpenSession();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Update the display every time we are started.
        displaySelectedFriends(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FRIENDS_ACTIVITY:
                displaySelectedFriends(resultCode);
                break;
            default:
                Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
                break;
        }
    }

    private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null || !Session.getActiveSession().isOpened()) {
            Session.openActiveSession(this, true, new Session.StatusCallback() {
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                    onSessionStateChanged(session, state, exception);
                }
            });
            Log.d("false", "false");
            return false;
        }
        Log.d("true", "true");
        return true;
    }

    private void onSessionStateChanged(Session session, SessionState state, Exception exception) {
        if (pickFriendsWhenSessionOpened && state.isOpened()) {
            pickFriendsWhenSessionOpened = false;

            startPickFriendsActivity();
        }
    }

    private void displaySelectedFriends(int resultCode) {
        String results = "";
        ParseApplication application = (ParseApplication) getApplication();
        Collection<GraphUser> selection = application.getSelectedUsers();
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
                names.add(user.getId());
                recieverName = user.getName();
                recieverFBId = user.getId();
            }
            results = TextUtils.join(", ", names);
        } else {
            results = "<No friends selected>";
        }
        resultsTextView.setText(results);
    }

    private void onClickPickFriends() {
        startPickFriendsActivity();
    }

    private void startPickFriendsActivity() {
        if (ensureOpenSession()) {
        	ParseApplication application = (ParseApplication) getApplication();
            application.setSelectedUsers(null);

            Intent intent = new Intent(this, PickFriendsActivity.class);
            // Note: The following line is optional, as multi-select behavior is the default for
            // FriendPickerFragment. It is here to demonstrate how parameters could be passed to the
            // friend picker if single-select functionality was desired, or if a different user ID was
            // desired (for instance, to see friends of a friend).
            PickFriendsActivity.populateParameters(intent, null, false, true);
            startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
        } else {
            pickFriendsWhenSessionOpened = true; 
        }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String comment = message.getText().toString();
		ParseApplication app = (ParseApplication) getApplication();
		switch(v.getId()){
			case R.id.angelPost:
				ParseObject angelPost = new ParseObject("Comments");
				angelPost.put("isAngel", 1);
				angelPost.put("fromUser", app.getCurrentUserFBId());
				angelPost.put("toUser", recieverFBId);
				angelPost.put("comment", comment);
				angelPost.put("isLocked", true);
				//TODO:: update whether or not the comment is locked
				angelPost.put("lockedForever", false);
				angelPost.saveInBackground();	
				finish();
			break;
			case R.id.devilPost:
				ParseObject devilPost = new ParseObject("Comments");
				devilPost.put("isAngel", 0);
				devilPost.put("fromUser", app.getCurrentUserFBId());
				devilPost.put("toUser", recieverFBId);
				devilPost.put("comment", comment);
				devilPost.put("isLocked", true);
				//TODO:: update whether or not the comment is locked
				devilPost.put("lockedForever", false);
				devilPost.saveInBackground();
				finish();
				break;
		}
	}
}
