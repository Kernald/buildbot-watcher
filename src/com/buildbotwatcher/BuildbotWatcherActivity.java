package com.buildbotwatcher;

import com.buildbotwatcher.R;
import com.buildbotwatcher.worker.JsonParser;
import com.buildbotwatcher.worker.Project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BuildbotWatcherActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button bSettings = (Button) findViewById(R.id.btn_settings);

		bSettings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(BuildbotWatcherActivity.this, SettingsActivity.class);
				startActivity(i);
			}
		});


		JsonParser p = new JsonParser("http://buildbot.buildbot.net", 80);
		//Log.d("Project", p.getProject().getName());

		new GetProject().execute(p);

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
