package de.noxworks.noxnition.persistence;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ModuleConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final int channels;
	private boolean configured;

	public ModuleConfig(String name, int channels, boolean configured) {
		this.name = name;
		this.channels = channels;
		this.configured = configured;
	}

	public int getChannels() {
		return channels;
	}

	@Override
	public String toString() {
		return name + " (" + channels + ")";
	}

	public String getName() {
		return name;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public static ModuleConfig fromJson(String jsonString) throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String name = json.getString("name");
		int channels = json.getInt("channels");
		return new ModuleConfig(name, channels, true);
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("name", getName());
		json.put("channels", getChannels());
		return json.toString();
	}
}