package com.buildbotwatcher.worker;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Build {
	private String		_builderName;
	private int			_number;
	private String		_reason;
	private String		_slaveName;
	private int			_results;
	private List<Step>	_steps;
	private Timestamp	_timeStart, _timeEnd;
	private String		_text;

	public Build(String name, int number, String reason, String slave, int results, List<Step> steps, Timestamp timeStart, Timestamp timeEnd, String text) {
		_builderName = name;
		_number = number;
		_reason = reason;
		_slaveName = slave;
		_results = results;
		_steps = steps;
		_timeStart = timeStart;
		_timeEnd = timeEnd;
		_text = text;
	}

	public Build(JSONObject jsono) {
		_builderName = jsono.optString("builderName", null);
		_number = jsono.optInt("number", -1);
		_reason = jsono.optString("reason", null);
		_slaveName = jsono.optString("slave", null);
		try {
			_text = jsono.getJSONArray("text").toString().replace("[", "").replace("]", "").replace(",", " ");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			String[] timestamps = jsono.getJSONArray("times").toString().replace("[", "").replace("]", "").split(",");
			_timeStart = JsonParser.parseJSONTimeStamp(timestamps[0]);
			_timeEnd = JsonParser.parseJSONTimeStamp(timestamps[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
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

	public String getText() {
		return _text;
	}
}
