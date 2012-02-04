package com.buildbotwatcher;

import com.buildbotwatcher.R;
import com.buildbotwatcher.worker.JsonParser;
import com.buildbotwatcher.worker.Project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BuildbotWatcherActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		linkBtns();

		firstTimeWizard();

		JsonParser p = new JsonParser("http://buildbot.buildbot.net", 80);

		new GetProject().execute(p);

	}

	private void firstTimeWizard() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String host = prefs.getString("host", "");
		if (host.equals("")) {
			AlertDialog.Builder popup = new AlertDialog.Builder(this);
			popup.setTitle(R.string.ftw_title);
			popup.setMessage(R.string.ftw_message);
			popup.setPositiveButton(R.string.ftw_now, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					startSettings();
				}
			});
			popup.setNegativeButton(R.string.ftw_later, null);
			popup.create();
			popup.show();
		}

	}

	private void linkBtns() {
		Button bSettings = (Button) findViewById(R.id.btn_settings);
		bSettings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startSettings();
			}
		});
	}

	private void startSettings() {
		Intent i = new Intent();
		i.setClass(BuildbotWatcherActivity.this, SettingsActivity.class);
		startActivity(i);
	}


	private class GetProject extends AsyncTask<JsonParser, Integer, Project> {
		protected Project doInBackground(JsonParser... p) {
			return p[0].getProject();
		}

		protected void onProgressUpdate(Integer... progress) {
			// TODO
		}

		protected void onPostExecute(Project result) {
			Log.d("Project", result.getName());
		}
	}
}
