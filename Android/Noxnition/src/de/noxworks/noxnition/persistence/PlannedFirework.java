package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.noxworks.noxnition.model.IgnitionModule;

public class PlannedFirework implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "name";
	private static final String ACTIONS = "actions";
	private static final String TRIGGER_GROUPS = "trigger_groups";

	private String name;
	private final List<FireAction> fireActions = new ArrayList<>();
	private final List<FireTriggerGroup> fireTriggerGroups = new ArrayList<>();

	public PlannedFirework(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FireAction> getFireActions() {
		return fireActions;
	}

	public List<FireTriggerGroup> getFireTriggerGroups() {
		return fireTriggerGroups;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(NAME, name);
		JSONArray array = new JSONArray();
		for (FireAction action : fireActions) {
			array.put(action.toJson());
		}
		json.put(ACTIONS, array);
		JSONArray triggerGroupsJson = new JSONArray();
		for (FireTriggerGroup fireTriggerGroup : fireTriggerGroups) {
			triggerGroupsJson.put(fireTriggerGroup.toJson());
		}
		json.put(TRIGGER_GROUPS, triggerGroupsJson);
		return json.toString();
	}

	public static PlannedFirework fromJson(String jsonString, Map<String, IgnitionModule> ignitionModules)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String name = json.getString(NAME);
		PlannedFirework plannedFirework = new PlannedFirework(name);
		Map<String, FireTriggerGroup> fireTriggerGroups = new HashMap<>();
		JSONArray triggerGroupArray = json.getJSONArray(TRIGGER_GROUPS);
		for (int i = 0; i < triggerGroupArray.length(); i++) {
			String triggerGroupJsonString = triggerGroupArray.getString(i);
			FireTriggerGroup fireTriggerGroup = FireTriggerGroup.fromJson(triggerGroupJsonString, ignitionModules);
			fireTriggerGroups.put(fireTriggerGroup.getName(), fireTriggerGroup);
		}
		plannedFirework.getFireTriggerGroups().addAll(fireTriggerGroups.values());
		List<FireAction> fireActions = plannedFirework.getFireActions();
		JSONArray array = json.getJSONArray(ACTIONS);
		for (int i = 0; i < array.length(); i++) {
			String actionJsonString = array.getString(i);
			FireAction fireAction = FireAction.fromJson(actionJsonString, fireTriggerGroups);
			fireActions.add(fireAction);
		}
		return plannedFirework;
	}
}