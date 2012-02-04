package com.android.buildbotwatcher;

import com.android.buildbotwatcher.worker.JsonParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BuildbotWatcherActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        JsonParser p = new JsonParser("http://buildbot.buildbot.net", 80);
        Log.d("Project", p.getProject().getName());
    }
}