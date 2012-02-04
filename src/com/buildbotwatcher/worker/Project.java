package com.buildbotwatcher.worker;

import org.json.JSONObject;

public class Project {
	private String _projectUrl;
	private String _name;
	private String _buildbotUrl;

	public Project(JSONObject jsono) {
		_projectUrl = jsono.optString("titleURL", null);
		_name = jsono.optString("title", null);
		_buildbotUrl = jsono.optString("buildbotURL", null);
	}

	public Project(String projectUrl, String name, String buildbotUrl) {
		_projectUrl = projectUrl;
		_name = name;
		_buildbotUrl = buildbotUrl;
	}

	public String toString() {
		return "\"project\": { "
		+ "\"buildbotURL\": " + _buildbotUrl + ", "
		+ "\"title\": " + _name + ", "
		+ "\"titleURL\": " + _projectUrl 
		+ " }";
	}

	public String getProjectUrl() {
		return _projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		_projectUrl = projectUrl;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getBuildbotUrl() {
		return _buildbotUrl;
	}

	public void setBuildbotUrl(String buildbotUrl) {
		_buildbotUrl = buildbotUrl;
	}
}
