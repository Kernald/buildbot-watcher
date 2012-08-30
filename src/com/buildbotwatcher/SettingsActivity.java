package com.buildbotwatcher;

import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsActivity extends PreferenceActivity {
	static final Class<?>	PARENT_ACTIVITY = BuildersActivity.class;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if (prefs.getBoolean("light_theme", false)) {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				setTheme(R.style.HoloLight);
			else
				setTheme(R.style.Light);
		} else {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				setTheme(R.style.HoloDark);
			else
				setTheme(R.style.Dark);
		}
		
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, PARENT_ACTIVITY);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
