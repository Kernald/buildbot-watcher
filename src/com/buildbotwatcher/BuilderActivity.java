package com.buildbotwatcher;

import com.buildbotwatcher.worker.Builder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BuilderActivity extends Activity {
	private Builder	_builder;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.builder);
	    Bundle bundle = this.getIntent().getExtras();
	    Log.d("bundle content", "" + bundle);
	    _builder = (Builder) bundle.get("builder");
	    setTitle(_builder.getName());
	}

}
