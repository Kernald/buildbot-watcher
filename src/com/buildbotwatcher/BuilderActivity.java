package com.buildbotwatcher;

import com.buildbotwatcher.worker.Builder;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class BuilderActivity extends ListActivity {
	private Builder	_builder;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.builder);
	    Bundle bundle = getIntent().getExtras();
	    _builder = (Builder) bundle.get("builder");
	    setTitle(_builder.getName());
	    TextView state = (TextView) findViewById(R.id.state);
	    state.setText(String.format(getResources().getString(R.string.builder_state), _builder.getState()));
	    TextView header = (TextView) getLayoutInflater().inflate(R.layout.builder_list_header, null);
	    int count = _builder.getBuilds().size();
	    header.setText(getResources().getQuantityString(R.plurals.builder_build_number, count, count));
		ListView listView = getListView();
		listView.addHeaderView(header);
	}
}
