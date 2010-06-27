package org.developerworks.android;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MessageList extends ListActivity {
	
	private List<Message> messages;
	public static final String PREFS = "MyEpisodesAndroidPrefs";
	private String username;
	private String password;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        SharedPreferences settings = getSharedPreferences(PREFS, 0);
        username = settings.getString("username", "");
        password = settings.getString("password", "");
        
        if(username == "" || password == ""){
        	Context context = getApplicationContext();
        	CharSequence text = "You need to setup your username and password for MyEpisodes!";
        	int duration = Toast.LENGTH_SHORT;
        	
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        } else {
        	loadFeed();
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MenuItems.UPDATE_FEED.ordinal(), MenuItems.UPDATE_FEED.ordinal(), R.string.update_feed);
		menu.add(Menu.NONE, MenuItems.SETTINGS.ordinal(), MenuItems.SETTINGS.ordinal(), R.string.settings);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch(item.getItemId()) {
		case 0:
			this.loadFeed();
			return true;
		case 1:
			this.viewSettings();
			return true;
		}
			
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(messages.get(position).getLink().toExternalForm()));
		this.startActivity(viewMessage);
	}

	private void viewSettings() {
			Intent i = new Intent(this, Settings.class);
			startActivity(i);
	}
	
	public static String getMd5Hash(String input) {
        try     {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(input.getBytes());
                BigInteger number = new BigInteger(1,messageDigest);
                String md5 = number.toString(16);
                while (md5.length() < 32)
                        md5 = "0" + md5;
                return md5;
        } catch(NoSuchAlgorithmException e) {
                Log.e("MD5", e.getMessage());
                return null;
        }
}
	
	private void loadFeed(){
    	try{
    		//Get username/password from settings
    		SharedPreferences settings = getSharedPreferences(PREFS, 0);
    		username = settings.getString("username", "");
            password = settings.getString("password", "");
            
            String md5password = getMd5Hash(password.trim());
            
            //http://www.myepisodes.com/rss.php?feed=mylist&uid=bigfoot&pwdmd5=d2b53174d782921941ef5914ccfb21e1
            
    		//Create an instance of the parser and parse feed
    		String url = "http://myepisodes.com/rss.php?feed=mylist&uid="+username.trim()+"&pwdmd5="+md5password;
	    	FeedParser parser = new AndroidSaxFeedParser(url);
	    	messages = parser.parse();
	    	        
	    	//Initialize list of Maps
	    	List<Map<String, String>> list = new ArrayList<Map<String, String>>(messages.size());
	    	
	    	//Iterate through list of messages (RSS Feed)
	    	for (Message msg : messages){
	    		Map<String, String> map = new HashMap<String, String>();
	    		map.put("showName", msg.getShowName());
	    		map.put("episodeTitle", msg.getEpisodeTitle());
	    		map.put("episodeNumber", msg.getEpisodeNumber());
	    		map.put("episodeDate", msg.getepisodeDate());
	    		list.add(map);
	    	}
	    	
	    	//Array that specifies which key is used on the ListView
	    	String[] from = {"showName", "episodeTitle", "episodeNumber", "episodeDate"};
	    	
	    	//Array that specifies the layout styles
	    	int[] to = {R.id.show_name, R.id.episode_title, R.id.episode_number, R.id.episode_date};
	    	
	    	SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.row, from, to);
	    	
	    	this.setListAdapter(adapter);
    	
    	} catch (Throwable t){
    		
	    	//Toast
        	Context context = getApplicationContext();
        	CharSequence text = "Wrong username/password\nChange in settings";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
    		
    		Log.e("AndroidNews",t.getMessage(),t);
    	}
    }
   
}