package de.noxworks.noxnition;

import java.util.Hashtable;

public class IntentHelper {

	public static final String FIRE_TRIGGER_GROUP_ID = "fireTriggerGroupId";
	public static final String IGNITION_MODULE_ID = "ignitionModuleId";
	public static final String PLANNED_FIREWORK_ID = "plannedFireworkId";

	private static IntentHelper instance;
	private Hashtable<String, Object> hash;

	private IntentHelper() {
		hash = new Hashtable<String, Object>();
	}

	private static IntentHelper getInstance() {
		if (instance == null) {
			instance = new IntentHelper();
		}
		return instance;
	}

	public static void put(String key, Object object) {
		getInstance().hash.put(key, object);
	}

	public static Object get(String key) {
		return getInstance().hash.get(key);
	}

	public static void add(IIdentifiableElement element) {
		put(element.getId(), element);
	}
}