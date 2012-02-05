package com.buildbotwatcher.worker;

import java.util.List;
import org.json.JSONObject;

public class Slave {
	private String			_name;
	private String			_admin;
	private String			_accessUri;
	private List<String>	_buildersName;
	private boolean			_connected;
	private String			_host;
	private String			_version;

	public Slave(String name, String admin, String accessUri, List<String> buildersName, boolean connected, String host, String version) {
		_name = name;
		_admin = admin;
		_accessUri = accessUri;
		_buildersName = buildersName;
		_connected = connected;
		_host = host;
		_version = version;
	}

	public Slave(JSONObject jsono) {
		_name = jsono.optString("name", null);
		_accessUri = jsono.optString("access_uri", null);
		_admin = jsono.optString("admin", null).replace("\n", "");
		_connected = jsono.optBoolean("connected", false);
		_host = jsono.optString("host", null).replace("\n", "");
		_version = jsono.optString("version", null);
	}

	public String getName() {
		return _name;
	}

	public String getAdmin() {
		return _admin;
	}

	public List<String> getBuildersName() {
		return _buildersName;
	}

	public boolean isConnected() {
		return _connected;
	}

	public String getHost() {
		return _host;
	}

	public String getVersion() {
		return _version;
	}

	public String getAccessUri() {
		return _accessUri;
	}
}
