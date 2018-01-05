package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.noxworks.noxnition.model.IgnitionModule;

public class FireTriggerGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "name";
	private static final String TRIGGERS = "triggers";

	private String name;

	private List<FireTrigger> fireTriggers = new ArrayList<>();

	public FireTriggerGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FireTrigger> getFireTriggers() {
		return fireTriggers;
	}

	public void addFireTrigger(FireTrigger fireTrigger) {
		fireTriggers.add(fireTrigger);
	}

	public void removeFireTrigger(FireTrigger fireTrigger) {
		fireTriggers.remove(fireTrigger);
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(NAME, name);
		JSONArray array = new JSONArray();
		for (FireTrigger trigger : fireTriggers) {
			array.put(trigger.toJson());
		}
		json.put(TRIGGERS, array);
		return json.toString();
	}

	public static FireTriggerGroup fromJson(String jsonString, Map<String, IgnitionModule> ignitionModules)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String name = json.getString(NAME);
		FireTriggerGroup fireTriggerGroup = new FireTriggerGroup(name);
		JSONArray array = json.getJSONArray(TRIGGERS);
		for (int i = 0; i < array.length(); i++) {
			String triggerJsonString = array.getString(i);
			FireTrigger fireTrigger = FireTrigger.fromJson(triggerJsonString, ignitionModules);
			fireTriggerGroup.addFireTrigger(fireTrigger);
		}
		return fireTriggerGroup;
	}
}