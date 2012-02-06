package com.buildbotwatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.buildbotwatcher.worker.Build;
import com.buildbotwatcher.worker.Builder;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BuilderActivity extends ListActivity {
	private Builder			_builder;
	private BuildsAdapter	_adapter;
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
		_adapter = new BuildsAdapter(this, _builder);
		setListAdapter(_adapter);
	}

	private class BuildsAdapter extends ArrayAdapter<Build> {
		private final Activity	_context;
		private List<Build>		_builds;

		public BuildsAdapter(Activity context, Builder b) {
			super(context, R.layout.builders_row);
			_context = context;
			_builds = new ArrayList<Build>(b.getBuilds().values());
			Collections.reverse(_builds);

			Iterator<Build> itr = _builds.iterator();
			while(itr.hasNext())
				add(itr.next());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = _context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.builders_row, null, true);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			String s = String.valueOf(_builds.get(position).getNumber());
			textView.setText(s);
			if (_builds.get(position).isSuccessful())
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);
			else
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.failure, 0, 0, 0);

			return rowView;
		}
	}
}
