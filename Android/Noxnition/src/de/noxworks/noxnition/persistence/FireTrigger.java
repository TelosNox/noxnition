package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.noxworks.noxnition.model.IgnitionModule;

public class FireTrigger implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MODULE = "module";
	private static final String CHANNELS = "channels";

	private IgnitionModule module;
	private Set<Integer> channels = new HashSet<>();

	public FireTrigger(IgnitionModule module) {
		this.module = module;
	}

	public IgnitionModule getModule() {
		return module;
	}

	public void setModule(IgnitionModule module) {
		this.module = module;
	}

	public Set<Integer> getChannels() {
		return channels;
	}

	public void addChannel(int channel) {
		channels.add(channel);
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(MODULE, module.getModuleConfig().getName());
		json.put(CHANNELS, new JSONArray(channels));
		return json.toString();
	}

	public static FireTrigger fromJson(String jsonString, Map<String, IgnitionModule> ignitionModules)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		JSONArray channelsJson = json.getJSONArray(CHANNELS);
		String moduleName = json.getString(MODULE);
		IgnitionModule module = ignitionModules.get(moduleName);
		FireTrigger fireTrigger = new FireTrigger(module);
		for (int i = 0; i < channelsJson.length(); i++) {
			int channel = channelsJson.getInt(i);
			fireTrigger.addChannel(channel);
		}
		return fireTrigger;
	}
}