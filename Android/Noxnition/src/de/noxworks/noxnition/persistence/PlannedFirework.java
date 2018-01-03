package de.noxworks.noxnition.persistence;

import java.io.Serializable;
import java.util.ArrayList;
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

	private String name;
	private final List<FireAction> fireActions = new ArrayList<>();

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
		return json.toString();
	}

	public static PlannedFirework fromJson(String jsonString, Map<String, IgnitionModule> ignitionModules)
	    throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		String name = json.getString(NAME);
		PlannedFirework plannedFirework = new PlannedFirework(name);
		List<FireAction> fireActions = plannedFirework.getFireActions();
		JSONArray array = json.getJSONArray(ACTIONS);
		for (int i = 0; i < array.length(); i++) {
			String actionJsonString = array.getString(i);
			FireAction fireAction = FireAction.fromJson(actionJsonString, ignitionModules);
			fireActions.add(fireAction);
		}
		return plannedFirework;
	}
}