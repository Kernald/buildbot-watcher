package com.buildbotwatcher;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuildersActivity extends ListActivity {
	private BuildersAdapter _adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] values = new String[] {"test"};
		_adapter = new BuildersAdapter(this, values);
		setListAdapter(_adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
	}
	
	private class BuildersAdapter extends ArrayAdapter<String> {
		private final Activity context;
		private final String[] names;

		public BuildersAdapter(Activity context, String[] names) {
			super(context, R.layout.builders_row, names);
			this.context = context;
			this.names = names;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.builders_row, null, true);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			String s = names[position];
			textView.setText(s);
			if (false)
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.failure, 0, 0, 0);
			else
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);

			return rowView;
		}
	}
}
