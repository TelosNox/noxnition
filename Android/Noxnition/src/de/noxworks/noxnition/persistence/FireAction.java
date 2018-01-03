package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import de.noxworks.noxnition.model.IgnitionModule;

public class FireAction implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "name";
	private static final String MODULE = "module";
	private static final String CHANNEL = "channel";
	private static final String DELAY = "delay";

	private String name;
	private IgnitionModule module;
	private int channel;
	private int delay = 0;

	public FireAction(String name, IgnitionModule module, int channel) {
		this.name = name;
		this.module = module;
		this.channel = channel;
	}

	public FireAction(String name, IgnitionModule module, int channel, int delay) {
		this(name, module, channel);
		this.delay = delay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IgnitionModule getModule() {
		return module;
	}

	public void setModule(IgnitionModule module) {
		this.module = module;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(NAME, name);
		json.put(CHANNEL, channel);
		json.put(DELAY, delay);
		json.put(MODULE, module.getModuleConfig().getName());
		return json.toString();
	}

	public static FireAction fromJson(String jsonString, Map<String, IgnitionModule> ignitionModules)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String name = json.getString(NAME);
		int channel = json.getInt(CHANNEL);
		int delay = json.getInt(DELAY);
		String moduleName = json.getString(MODULE);
		IgnitionModule module = ignitionModules.get(moduleName);
		return new FireAction(name, module, channel, delay);
	}
}