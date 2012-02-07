package com.buildbotwatcher;

import java.util.ArrayList;
import java.util.List;

import com.buildbotwatcher.worker.Build;
import com.buildbotwatcher.worker.Builder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BuilderActivity extends ListActivity {
	private Builder			_builder;
	private BuildsAdapter	_adapter;
	private int				_displayed;
	private boolean			_loadingMore;
	private List<Build>		_newBuilds;

	static final int		LOAD_STEP = 15;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		_displayed = 0;
		_loadingMore = false;
		setContentView(R.layout.builder);
		Bundle bundle = getIntent().getExtras();
		_builder = (Builder) bundle.get("builder");
		setTitle(_builder.getName());
		TextView state = (TextView) findViewById(R.id.state);
		state.setText(String.format(getResources().getString(R.string.builder_state), _builder.getState()));
		TextView header = (TextView) getLayoutInflater().inflate(R.layout.builder_list_header, null);
		int count = _builder.getBuildCount();
		header.setText(getResources().getQuantityString(R.plurals.builder_build_number, count, count));
		ListView listView = getListView();
		listView.addHeaderView(header);

		listView.setOnScrollListener(new OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				int lastInScreen = firstVisibleItem + visibleItemCount;				

				//is the bottom item visible & not loading more already ? Load more !
				if((lastInScreen == totalItemCount) && !(_loadingMore)){
					Thread thread =  new Thread(null, loadBuilds);
					thread.start();
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {}
		});

		_adapter = new BuildsAdapter(this);
		setListAdapter(_adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, BuildersActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Runnable loadBuilds = new Runnable() {
		public void run() {
			_loadingMore = true;
			_newBuilds = new ArrayList<Build>();

			int count = LOAD_STEP;
			int start = _builder.getBuildCount() - _displayed;
			int stop = _builder.getBuildCount() - _displayed - count;
			if (stop < 0)
				stop = 0;
			if (count > _builder.getBuildCount() - _displayed)
				count = _builder.getBuildCount() - _displayed;

			for (int i = start - 1; i >= stop; i--)
				_newBuilds.add(_builder.getBuild(i));

			runOnUiThread(returnRes);
		}
	};

	private Runnable returnRes = new Runnable() {
		public void run() {
			if (_newBuilds != null && _newBuilds.size() > 0){
				for (int i = 0; i < _newBuilds.size(); i++) {
					_displayed++;
					_adapter.addBuild(_newBuilds.get(i));
				}
			}
			_adapter.notifyDataSetChanged();
			_loadingMore = false;
		}
	};

	private class BuildsAdapter extends ArrayAdapter<Build> {
		private final Activity	_context;
		private List<Build>		_builds;

		public BuildsAdapter(Activity context) {
			super(context, R.layout.builders_row);
			_context = context;
			_builds = new ArrayList<Build>();
		}
		
		public void addBuild(Build b) {
			_builds.add(b);
			add(b);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater inflater = _context.getLayoutInflater();
				v = inflater.inflate(R.layout.builders_row, null, true);
			}
			TextView textView = (TextView) v.findViewById(R.id.label);
			String s = String.valueOf(_builds.get(position).getNumber());
			textView.setText(s);
			if (_builds.get(position).isSuccessful())
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);
			else
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.failure, 0, 0, 0);

			return v;
		}
	}
}
