package com.buildbotwatcher.worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONObject;

public class Builder implements Serializable {
	private static final long serialVersionUID = -6734677484879284883L;
	private String					_name;
	private String					_basedir;
	private String					_category;
	private List<Build>				_cachedBuilds, _currentBuilds, _pendingBuilds;
	private TreeMap<Integer, Build>	_builds;
	private List<String>			_slaves;
	private String					_state;

	public Builder (Object key, JSONObject jsono, JsonParser parser) {
		_cachedBuilds = new ArrayList<Build>();
		_currentBuilds = new ArrayList<Build>();
		_pendingBuilds = new ArrayList<Build>();
		_builds = new TreeMap<Integer, Build>();
		List<Integer> cachedBuilds, currentBuilds, pendingBuilds;
		_name = key.toString();
		_basedir = jsono.optString("basedir", null);
		List<Build> builds = parser.getBuilds(_name);
		cachedBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("cachedBuilds"));
		currentBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("currentBuilds"));
		pendingBuilds = JsonParser.arrayToListInteger(jsono.optJSONArray("pendingBuilds"));
		for (Build b: builds) {
			if (cachedBuilds.contains(b.getNumber()))
				_cachedBuilds.add(b);
			else if (currentBuilds.contains(b.getNumber()))
				_currentBuilds.add(b);
			else if (pendingBuilds.contains(b.getNumber()))
				_pendingBuilds.add(b);
			_builds.put(b.getNumber(), b);
		}
		_category = jsono.optString("category", null);
		_slaves = JsonParser.arrayToListString(jsono.optJSONArray("slaves"));
		_state = jsono.optString("state", null);
	}
	
	public Build getLastBuild() {
		return _builds.get(_builds.lastKey());
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

	public List<Build> getCachedBuilds() {
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

	public List<Build> getCurrentBuilds() {
		return _currentBuilds;
	}

	public List<Build> getPendingBuilds() {
		return _pendingBuilds;
	}
}
