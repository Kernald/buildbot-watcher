package com.android.buildbotwatcher.worker;

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

	public String toString() {
		String ret = "\"" + _number + "\": { " + "\"builderName\": \""
		+ _builderName + "\", " + "\"number\": " + _number + ", "
		+ "\"reason\": " + _reason + ", " + "\"slave\": " + _slaveName
		+ ", " + "\"text\": " + _text;
		if (_timeStart != null && _timeEnd != null)
			ret += ", \"times\": [" + _timeStart.toLocaleString() + ", " + _timeEnd.toLocaleString() + "] }";
		else
			ret += " }";

		return ret;
	}

	public String getBuilderName() {
		return _builderName;
	}

	public void setBuilderName(String name) {
		_builderName = name;
	}

	public int getNumber() {
		return _number;
	}

	public void setNumber(int number) {
		_number = number;
	}

	public String getReason() {
		return _reason;
	}

	public void setReason(String reason) {
		_reason = reason;
	}

	public String getSlaveName() {
		return _slaveName;
	}

	public void setSlaveName(String slave) {
		_slaveName = slave;
	}

	public int getResults() {
		return _results;
	}

	public void setResults(int results) {
		_results = results;
	}

	public List<Step> getSteps() {
		return _steps;
	}

	public void setSteps(List<Step> steps) {
		_steps = steps;
	}

	public Timestamp getTimeStart() {
		return _timeStart;
	}

	public void setTimeStart(Timestamp timeStart) {
		_timeStart = timeStart;
	}

	public Timestamp getTimeEnd() {
		return _timeEnd;
	}

	public void setTimeEnd(Timestamp timeEnd) {
		_timeEnd = timeEnd;
	}

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}
}
