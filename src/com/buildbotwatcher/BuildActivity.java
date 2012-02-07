package com.buildbotwatcher;

import com.buildbotwatcher.worker.Build;
import com.buildbotwatcher.worker.Builder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class BuildActivity extends Activity {
	private Build	_build;
	private Builder	_builder;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		Bundle bundle = getIntent().getExtras();
		_build = (Build) bundle.get("build");
		_builder = (Builder) bundle.get("builder");
		setTitle(String.valueOf(_build.getNumber()));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, BuilderActivity.class);
			intent.putExtra("builder", _builder);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
