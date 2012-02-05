package com.buildbotwatcher;

import java.util.ArrayList;
import java.util.List;

import com.buildbotwatcher.worker.Builder;
import com.buildbotwatcher.worker.JsonParser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuildersActivity extends ListActivity {
	private BuildersAdapter	_adapter;
	private JsonParser		_p;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		_p = new JsonParser(prefs.getString("host", "http://buildbot.buildbot.net"), Integer.valueOf(prefs.getString("port", "80")));
		_adapter = new BuildersAdapter(this);
		setListAdapter(_adapter);
		new GetBuilders().execute(_p);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Builder item = (Builder) getListAdapter().getItem(position);
		Toast.makeText(this, item.getName() + " is " + item.getState() + ".", Toast.LENGTH_SHORT).show();
	}
	
	private class BuildersAdapter extends ArrayAdapter<Builder> {
		private final Activity	_context;
		private List<Builder>	_builders;

		public BuildersAdapter(Activity context) {
			super(context, R.layout.builders_row);
			_context = context;
			_builders = new ArrayList<Builder>();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = _context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.builders_row, null, true);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			String s = _builders.get(position).getName();
			textView.setText(s);
			if (/*_builders.get(position).getLastBuild().getResults() == 0*/false)
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.failure, 0, 0, 0);
			else
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);

			return rowView;
		}
		
		public void addBuilder(Builder b) {
			_builders.add(b);
			add(b);
		}
	}
	
	private class GetBuilders extends AsyncTask<JsonParser, Integer, List<Builder>> {
		protected List<Builder> doInBackground(JsonParser... p) {
			return p[0].getBuilders();
		}

		protected void onProgressUpdate(Integer... progress) {
			// TODO
		}

		protected void onPostExecute(List<Builder> result) {
			for (Builder b: result) {
				_adapter.addBuilder(b);
			}
		}
	}
}
