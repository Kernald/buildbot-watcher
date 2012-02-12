package com.buildbotwatcher.worker;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Build implements Serializable {
	private static final long serialVersionUID = 1384249386207628530L;
	private String				_builderName;
	private int					_number;
	private String				_reason;
	private String				_slaveName;
	private int					_results;
	private List<Step>			_steps;
	private Timestamp			_timeStart, _timeEnd;
	private Map<String, String>	_text;

	public Build(JSONObject jsono) {
		_text = new HashMap<String, String>();
		_builderName = jsono.optString("builderName", null);
		_number = jsono.optInt("number", -1);
		_reason = jsono.optString("reason", null);
		_slaveName = jsono.optString("slave", null);
		JSONArray text = null;
		try {
			text = jsono.getJSONArray("text");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (text != null) { 
			for (int i = 0; i < text.length() - 1; i += 2){ 
				try {
					String k = text.get(i).toString();
					String v = text.get(i + 1).toString();
					_text.put(k, v);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} 
		} 

		try {
			String[] timestamps = jsono.getJSONArray("times").toString().replace("[", "").replace("]", "").split(",");
			_timeStart = JsonParser.parseJSONTimeStamp(timestamps[0]);
			if (timestamps.length > 1)
				_timeEnd = JsonParser.parseJSONTimeStamp(timestamps[1]);
			else
				_timeEnd = null;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
	
	public boolean isSuccessful() {
		return (_text.containsKey("build") && _text.get("build").equals("successful"));
	}

	public String getBuilderName() {
		return _builderName;
	}

	public int getNumber() {
		return _number;
	}

	public String getReason() {
		return _reason;
	}

	public String getSlaveName() {
		return _slaveName;
	}

	public int getResults() {
		return _results;
	}

	public List<Step> getSteps() {
		return _steps;
	}

	public Timestamp getTimeStart() {
		return _timeStart;
	}

	public Timestamp getTimeEnd() {
		return _timeEnd;
	}

	public Map<String, String> getText() {
		return _text;
	}
}
