package com.android.buildbotwatcher.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser {
	private String _host;

	private static final String PATH_BUILDERS = "/json/builders";
	private static final String PATH_SLAVES = "/json/slaves";
	private static final String PATH_PROJECT = "/json?select=project";
	private static final String PATH_BUILDS = "/json/builders/#{builder}/builds/_all";


	public JsonParser(String url, int port) {
		_host = url + ":" + port;
	}

	private static JSONObject getJson(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String json = reader.readLine();
				JSONObject object = new JSONObject(json);
				return object;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Builder> getBuilders() {
		List<Builder> builders = new ArrayList<Builder>();
		JSONObject objects = getJson(_host + PATH_BUILDERS);
		Iterator<?> keys = objects.keys(); 

		while (keys.hasNext()) {
			Object o = keys.next();
			try {
				JSONObject jsono = (JSONObject)objects.get(o.toString());
				Builder b = new Builder(o, jsono);
				builders.add(b);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return builders;
	}

	public List<Slave> getSlaves() {
		List<Slave> slaves = new ArrayList<Slave>();
		JSONObject objects = getJson(_host + PATH_SLAVES);
		Iterator<?> keys = objects.keys(); 

		while (keys.hasNext()) {
			Object o = keys.next();
			try {
				JSONObject jsono = (JSONObject)objects.get(o.toString());
				Slave s = new Slave(jsono);
				slaves.add(s);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return slaves;
	}

	public List<Build> getBuilds(String builderName) {
		List<Build> builds = new ArrayList<Build>();
		String url = _host + PATH_BUILDS;
		url = url.replace("#{builder}", builderName);
		JSONObject objects = getJson(url);
		Iterator<?> keys = objects.keys(); 

		while (keys.hasNext()) {
			Object o = keys.next();
			try {
				JSONObject jsono = (JSONObject)objects.get(o.toString());
				Build b = new Build(jsono);
				builds.add(b);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return builds;
	}

	public Project getProject() {
		Project project = null;
		JSONObject jsono = getJson(_host + PATH_PROJECT);
		try {
			jsono = jsono.getJSONObject("project");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		project = new Project(jsono);

		return project;
	}

	public static Timestamp parseJSONTimeStamp(String ts) {
		int mult = Integer.parseInt(ts.split("E")[1]);
		long time = Long.parseLong(ts.split("E")[0].replace(".", "").substring(0, mult + 1));
		Timestamp timestamp = new Timestamp(time * 1000);
		return timestamp;
	}

	public static List<Integer> arrayToListInteger(JSONArray jsonArray) {
		if (jsonArray == null)
			return Collections.emptyList();

		String array = jsonArray.toString();
		array = array.replace("[", "").replace("]", "");

		if (array.equals(""))
			return Collections.emptyList();

		List<Integer> list = new ArrayList<Integer>();
		for (String s : array.split(",")) {
			Integer i = Integer.parseInt(s);
			list.add(i);
		}

		return list;
	}

	public static List<String> arrayToListString(JSONArray jsonArray) {
		if (jsonArray == null)
			return Collections.emptyList();

		String array = jsonArray.toString();
		array = array.replace("[", "").replace("]", "");

		if (array.equals(""))
			return Collections.emptyList();

		List<String> list = new ArrayList<String>();
		for (String s : array.split(","))
			list.add(s);

		return list;
	}
}
