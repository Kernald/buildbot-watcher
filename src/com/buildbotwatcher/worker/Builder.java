package com.buildbotwatcher.worker;

import java.util.List;

import org.json.JSONObject;

public class Builder {
	private String			_name;
	private String			_basedir;
	private String			_category;
	private List<Integer>	_cachedBuilds, _currentBuilds, _pendingBuilds;
	private List<String>	_slaves;
	private String			_state;

	public Builder(String name, String basedir, String category, List<Integer> cacheBuilds, List<Integer> currentBuilds, List<Integer> pendingBuilds, List<String> slaves, String state) {
		_name = name;
		_basedir = basedir;
		_category = category;
		_cachedBuilds = cacheBuilds;
		_currentBuilds = currentBuilds;
		_pendingBuilds = pendingBuilds;
		_slaves = slaves;
		_state = state;
	}

	public Builder (Object key, JSONObject jsono) {
		_name = key.toString();
		_basedir = jsono.optString("basedir", null);
		_cachedBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("cachedBuilds"));
		_category = jsono.optString("category", null);
		_currentBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("currentBuilds"));
		_pendingBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("pendingBuilds"));
		_slaves = JsonParser.arrayToListString(jsono.optJSONArray("slaves"));
		_state = jsono.optString("state", null);
	}

	public String toString() {
		return "\"" + _name + "\": { " 
		+ "\"basedir\": \"" + _basedir + "\", " 
		+ "\"cachedBuilds\": " + _cachedBuilds + ", "
		+ "\"category\": " + _category + ", "
		+ "\"currentBuilds\": " + _currentBuilds + ", " 
		+ "\"pendingBuilds\": " + _pendingBuilds + ", "
		+ "\"slaves\": " + _slaves + ", "
		+ "\"state\": " + _state 
		+ " }";
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getBasedir() {
		return _basedir;
	}

	public void setBasedir(String basedir) {
		_basedir = basedir;
	}

	public List<Integer> getCachedBuilds() {
		return _cachedBuilds;
	}

	public void setCachedBuilds(List<Integer> cachedBuilds) {
		_cachedBuilds = cachedBuilds;
	}

	public List<String> getSlaves() {
		return _slaves;
	}

	public void setSlaves(List<String> slaves) {
		_slaves = slaves;
	}

	public String getState() {
		return _state;
	}

	public void setState(String state) {
		_state = state;
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String category) {
		_category = category;
	}

	public List<Integer> getCurrentBuilds() {
		return _currentBuilds;
	}

	public void setCurrentBuilds(List<Integer> currentBuilds) {
		_currentBuilds = currentBuilds;
	}

	public List<Integer> getPendingBuilds() {
		return _pendingBuilds;
	}

	public void setPendingBuilds(List<Integer> pendingBuilds) {
		_pendingBuilds = pendingBuilds;
	}
}
