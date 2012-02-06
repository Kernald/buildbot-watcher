package com.buildbotwatcher;

import com.buildbotwatcher.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class BuildbotWatcherActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		linkBtns();

		firstTimeWizard();
	}

	private void firstTimeWizard() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String host = prefs.getString("host", "");
		if (host.equals("")) {
			AlertDialog.Builder popup = new AlertDialog.Builder(this);
			popup.setTitle(R.string.ftw_title);
			popup.setMessage(R.string.ftw_message);
			popup.setCancelable(false);
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
		Button bWaterfall = (Button) findViewById(R.id.btn_waterfall);
		Button bBuilders = (Button) findViewById(R.id.btn_builders);
		Button bSettings = (Button) findViewById(R.id.btn_settings);
		
		bWaterfall.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startWaterfall();
			}
		});
		
		bBuilders.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startBuilders();
			}
		});
		
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

	private void startBuilders() {
		Intent i = new Intent();
		i.setClass(BuildbotWatcherActivity.this, BuildersActivity.class);
		startActivity(i);
	}

	private void startWaterfall() {
		Intent i = new Intent();
		i.setClass(BuildbotWatcherActivity.this, WaterfallActivity.class);
		startActivity(i);
	}
}
