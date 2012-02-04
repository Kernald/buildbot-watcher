package com.android.buildbotwatcher;

import com.android.buildbotwatcher.worker.JsonParser;
import com.android.buildbotwatcher.worker.Project;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class BuildbotWatcherActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
