package org.developerworks.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageList extends ListActivity {
	
	private List<Message> messages;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        loadFeed();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MenuItems.UPDATE_FEED.ordinal(), 
				MenuItems.UPDATE_FEED.ordinal(), R.string.update_feed);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		this.loadFeed();
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(messages.get(position).getLink().toExternalForm()));
		this.startActivity(viewMessage);
	}

	private void loadFeed(){
    	try{
    		//Create an instance of the parser and parse feed
	    	FeedParser parser = new AndroidSaxFeedParser();
	    	messages = parser.parse();
	    		    	
	    	//Initialize list of Maps
	    	List<Map<String, String>> list = new ArrayList<Map<String, String>>(messages.size());
	    	
	    	//Iterate through list of messages (RSS Feed)
	    	for (Message msg : messages){
	    		Map<String, String> map = new HashMap<String, String>();
	    		map.put("title", msg.getTitle());
	    		map.put("description", msg.getDescription());
	    		list.add(map);
	    	}
	    	
	    	//Array that specifies which key is used on the ListView
	    	String[] from = {"title", "description"};
	    	
	    	//Array that specifies the layout styles
	    	int[] to = {R.id.show_name, R.id.episode_title};
	    	
	    	SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.row, from, to);
	    	
	    	this.setListAdapter(adapter);
	    	
	    	//	    	List<String> titles = new ArrayList<String>(messages.size());
//	    	for (Message msg : messages){
//	    		titles.add(msg.getTitle());
//	    	}
//	    	ArrayAdapter<String> adapter = 
//	    		new ArrayAdapter<String>(this, R.layout.row,titles);
//	    	this.setListAdapter(adapter);
	    	
	    	
	    	
    	} catch (Throwable t){
    		Log.e("AndroidNews",t.getMessage(),t);
    	}
    }
   
}