package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FireAction implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String FIRE_TRIGGER_GROUP = "fireTriggerGroup";
	private static final String DELAY = "delay";

	private FireTriggerGroup fireTriggerGroup;
	private int delay = 0;

	public FireAction(FireTriggerGroup fireTriggerGroup) {
		this.fireTriggerGroup = fireTriggerGroup;
	}

	public FireAction(FireTriggerGroup fireTriggerGroup, int delay) {
		this(fireTriggerGroup);
		this.delay = delay;
	}

	public FireTriggerGroup getFireTriggerGroup() {
		return fireTriggerGroup;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(FIRE_TRIGGER_GROUP, fireTriggerGroup.getName());
		json.put(DELAY, delay);
		return json.toString();
	}

	public static FireAction fromJson(String jsonString, Map<String, FireTriggerGroup> fireTriggerGroups)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String fireTriggerGroupName = json.getString(FIRE_TRIGGER_GROUP);
		FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(fireTriggerGroupName);
		int delay = json.getInt(DELAY);
		return new FireAction(fireTriggerGroup, delay);
	}
}