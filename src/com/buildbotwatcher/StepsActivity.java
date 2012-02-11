package com.buildbotwatcher;

import com.buildbotwatcher.worker.Build;
import com.buildbotwatcher.worker.Builder;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StepsActivity extends ListActivity {
	private Build			_build;
	private Menu			_menu;
	
	static final Class<?>	PARENT_ACTIVITY = BuildActivity.class;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //TODO setContentView
	    
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	
		Bundle bundle = getIntent().getExtras();
		// if no build, go back to the parent activity
		if (!bundle.containsKey("build") || bundle.get("build") == null) {
			Intent intent = new Intent(this, PARENT_ACTIVITY);
			intent.putExtra("builder", (Builder) bundle.get("builder"));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			_build = (Build) bundle.get("build");
			//TODO fill view
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		_menu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.steps, menu);
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
