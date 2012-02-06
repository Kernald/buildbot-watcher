package com.buildbotwatcher;

import java.util.ArrayList;
import java.util.List;

import com.buildbotwatcher.worker.Builder;
import com.buildbotwatcher.worker.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class BuildersActivity extends ListActivity {
	static final int DIALOG_NET_ISSUE_ID = 0;

	private BuildersAdapter	_adapter;
	private JsonParser		_p;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.builders_list_loading);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		_p = new JsonParser(prefs.getString("host", "http://buildbot.buildbot.net"), Integer.valueOf(prefs.getString("port", "80")), prefs.getBoolean("auth", false), prefs.getString("auth_login", null), prefs.getString("auth_password", null));
		_adapter = new BuildersAdapter(this);
		setListAdapter(_adapter);

		@SuppressWarnings("unchecked")
		final List<Builder> data = (List<Builder>) getLastNonConfigurationInstance();
		if (data == null) {
			new GetBuilders().execute(_p);
		} else {
			setContentView(R.layout.builders_list);
			for (Builder b: data) {
				_adapter.addBuilder(b);
			}
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		final List<Builder> data = _adapter.getBuilders();
		return data;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Builder builder = (Builder) getListAdapter().getItem(position);
		Intent i = new Intent();
		i.setClass(BuildersActivity.this, BuilderActivity.class);
		//TODO: use android.os.Parcelable instead of java.io.Serializable
		i.putExtra("builder", builder);
		startActivity(i);
	}
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
	    switch(id) {
	    case DIALOG_NET_ISSUE_ID:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.dlg_net_issue)
	    	       .setCancelable(false)
	    	       .setNeutralButton(R.string.dlg_net_issue_btn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                BuildersActivity.this.finish();
	    	           }
	    	       });
	    	dialog = builder.create();
	        break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}

	private class BuildersAdapter extends ArrayAdapter<Builder> {
		private final Activity	_context;
		private List<Builder>	_builders;

		public BuildersAdapter(Activity context) {
			super(context, R.layout.builders_row);
			_context = context;
			_builders = new ArrayList<Builder>();
		}

		public List<Builder> getBuilders() {
			return _builders;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = _context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.builders_row, null, true);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			String s = _builders.get(position).getName();
			textView.setText(s);
			if (_builders.get(position).getLastBuild().isSuccessful())
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);
			else
				textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.failure, 0, 0, 0);

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
			setContentView(R.layout.builders_list);
			if (result != null) {
				for (Builder b: result) {
					_adapter.addBuilder(b);
				}
			} else {
				showDialog(DIALOG_NET_ISSUE_ID);
			}
		}
	}
}
