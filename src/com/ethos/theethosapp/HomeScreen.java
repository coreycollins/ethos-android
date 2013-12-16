package com.ethos.theethosapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeScreen extends Activity {
	
	CustomAdapter adapter;
	private ArrayList<Comment> fetch = new ArrayList<Comment>();
	ListView lv;
	ParseApplication app;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		app = (ParseApplication) getApplication();
		Log.d("userid", "a"+app.getCurrentUserFBId());
		initList();
        lv = (ListView) findViewById(R.id.listview);
        
     // Set a listener to be invoked when the list should be refreshed.
        ((PullToRefreshListView) lv).setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        
        adapter = new CustomAdapter(HomeScreen.this,
                R.id.listview,
                fetch);
        lv.setAdapter(adapter);
	}

	private void initList() {
		 Log.d("starting", "init");
		 Log.d("userid", app.getCurrentUserFBId());
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
		 query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		 
		 query.whereEqualTo("fromUser", app.getCurrentUserFBId());
		 query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null) {
			            Log.d("score", "Retrieved " + scoreList.size() + " scores");
			            for (int i = 0; i < scoreList.size(); i++){
			            	Log.d("date object", "a" + scoreList.get(i).getDate("dateCreated"));
			            	Log.d("date formatted", String.valueOf(scoreList.get(i).getDate("dateCreated")));
			            	
			            	Comment comment = new Comment(
			            			scoreList.get(i).getObjectId(), 
			            			scoreList.get(i).getString("message"),
			            			scoreList.get(i).getCreatedAt(),
			            			scoreList.get(i).getUpdatedAt(),
			            			scoreList.get(i).getString("fromUser"),
			            			scoreList.get(i).getString("toUser"),
			            			scoreList.get(i).getBoolean("isHidden"),
			            			scoreList.get(i).getBoolean("isLocked"),
			            			scoreList.get(i).getBoolean("isAngel"),
			            			scoreList.get(i).getBoolean("isSpam"),
			            			scoreList.get(i).getString("fromUserName"),
			            			scoreList.get(i).getString("toName")
			            			);
			            	fetch.add(comment);
			            	
			            }
			            adapter.notifyDataSetChanged();
			        } else {
			            Log.d("score", "Error: " + e.getMessage());
			        }
			    }
			});
		}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
	    
	    @Override
	    protected void onPostExecute(String[] result) {
	    	Comment comment = new Comment(
        			"test", 
        			"test",
        			new Date(),
        			new Date(),
        			"test",
        			"test",
        			false,
        			false,
        			true,
        			false,
        			"test",
        			"test"
        			);
	        fetch.add(0, comment);
	        // Call onRefreshComplete when the list has been refreshed.
	        ((PullToRefreshListView) lv).onRefreshComplete();
	        super.onPostExecute(result);
	    }

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public void openSearchActivity() {
		Intent intent = new Intent(HomeScreen.this, Search.class);
		startActivity(intent);
	}

}
