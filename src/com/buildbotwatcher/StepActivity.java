package com.buildbotwatcher;

import com.buildbotwatcher.worker.Build;
import com.buildbotwatcher.worker.Builder;
import com.buildbotwatcher.worker.Step;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StepActivity extends Activity {
	private Build			_build;
	private Step			_step;
	private Menu			_menu;

	static final Class<?>	PARENT_ACTIVITY = StepsActivity.class;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		//TODO setContentView

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		Bundle bundle = getIntent().getExtras();
		// if no step, go back to the parent activity
		if (!bundle.containsKey("step") || bundle.get("step") == null) {
			Intent intent = new Intent(this, PARENT_ACTIVITY);
			intent.putExtra("build", (Build) bundle.get("build"));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			//TODO fill view
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		_menu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.step, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, PARENT_ACTIVITY);
			intent.putExtra("builder", (Builder) getIntent().getExtras().get("builder"));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
			
		case R.id.menu_refresh:
			refresh();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void refresh() {
		_menu.findItem(R.id.menu_refresh).setEnabled(false);
		//TODO implement this
	}

}
