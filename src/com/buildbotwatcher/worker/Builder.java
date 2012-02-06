package com.buildbotwatcher.worker;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONObject;

public class Builder implements Serializable {
	private static final long serialVersionUID = -6734677484879284883L;
	private String					_name;
	private String					_basedir;
	private String					_category;
	private List<Integer>			_cachedBuilds, _currentBuilds, _pendingBuilds;
	private TreeMap<Integer, Build>	_builds;
	private List<String>			_slaves;
	private String					_state;
	private int						_lastBuild;

	public Builder (Object key, JSONObject jsono, JsonParser parser) {
		_builds = new TreeMap<Integer, Build>();
		_name = key.toString();
		_basedir = jsono.optString("basedir", null);
		//List<Build> builds = parser.getBuilds(_name);
		_cachedBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("cachedBuilds"));
		_currentBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("currentBuilds"));
		_pendingBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("pendingBuilds"));
		_category = jsono.optString("category", null);
		_slaves = JsonParser.arrayToListString(jsono.optJSONArray("slaves"));
		_state = jsono.optString("state", null);
		if (_cachedBuilds.size() > 0) {
			_lastBuild = _cachedBuilds.get(0);
			for (int i = 0; i < _lastBuild; i++)
				_builds.put(i, null);
			_builds.put(_lastBuild, parser.getBuild(_name, -1));
		} else
			_lastBuild = -1;
	}
	
	public Build getLastBuild() {
		if (_lastBuild >= 0)
			return _builds.get(_lastBuild);
		else
			return null;
	}

	public String getName() {
		return _name;
	}

	public String getBasedir() {
		return _basedir;
	}
	
	public TreeMap<Integer, Build> getBuilds() {
		return _builds;
	}

	public List<Integer> getCachedBuilds() {
		return _cachedBuilds;
	}

	public List<String> getSlaves() {
		return _slaves;
	}

	public String getState() {
		return _state;
	}

	public String getCategory() {
		return _category;
	}

	public List<Integer> getCurrentBuilds() {
		return _currentBuilds;
	}

	public List<Integer> getPendingBuilds() {
		return _pendingBuilds;
	}
}
