package org.developerworks.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {
	
	public static final String PREFS = "MyEpisodesAndroidPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
		
	

		Button submitButton = (Button)findViewById(R.id.submit_button);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			    SharedPreferences settings = getSharedPreferences(PREFS, 0);
			    SharedPreferences.Editor editor = settings.edit();
			    
			    	EditText username = (EditText)findViewById(R.id.username);
			    	EditText password = (EditText)findViewById(R.id.password);
			    
				editor.putString("username", username.getText().toString());
				editor.putString("password", password.getText().toString());
				
				editor.commit();
			    
				finish();
			}
		});
		
	}

	
}
