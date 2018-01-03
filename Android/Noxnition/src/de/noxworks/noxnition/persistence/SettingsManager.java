package de.noxworks.noxnition.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.content.SharedPreferences;
import de.noxworks.noxnition.model.IgnitionModule;

public class SettingsManager {

	private static final String FIREWORKS = "fireworks";
	private static final String MODULE_CONFIGS = "moduleConfigs";

	private List<IgnitionModule> ignitionModules = new ArrayList<>();
	private List<PlannedFirework> plannedFireworks = new ArrayList<>();

	public SettingsManager(SharedPreferences settings) {
		load(settings);
	}

	private void load(SharedPreferences settings) {
		Map<String, IgnitionModule> ignitionModulesByName = loadIgnitionModules(settings);
		ignitionModules.addAll(ignitionModulesByName.values());

		loadPlannedFireworks(settings, ignitionModulesByName);
	}

	private void loadPlannedFireworks(SharedPreferences settings, Map<String, IgnitionModule> ignitionModulesByName) {
		Set<String> fireworksJson = settings.getStringSet(FIREWORKS, new HashSet<String>());
		for (String string : fireworksJson) {
			try {
				PlannedFirework plannedFirework = PlannedFirework.fromJson(string, ignitionModulesByName);
				plannedFireworks.add(plannedFirework);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, IgnitionModule> loadIgnitionModules(SharedPreferences settings) {
		Map<String, IgnitionModule> ignitionModulesByName = new HashMap<>();
		Set<String> stringSet = settings.getStringSet(MODULE_CONFIGS, new HashSet<String>());
		for (String string : stringSet) {
			try {
				ModuleConfig moduleConfig = ModuleConfig.fromJson(string);
				IgnitionModule ignitionModule = new IgnitionModule(moduleConfig);
				ignitionModulesByName.put(moduleConfig.getName(), ignitionModule);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ignitionModulesByName;
	}

	public void persist(SharedPreferences settings) {
		SharedPreferences.Editor editor = settings.edit();

		persistModuleConfigs(editor);

		persistPlannedFireworks(editor);

		editor.commit();
	}

	private void persistPlannedFireworks(SharedPreferences.Editor editor) {
		Set<String> fireworks = new HashSet<>();
		for (PlannedFirework firework : plannedFireworks) {
			try {
				String json = firework.toJson();
				fireworks.add(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		editor.putStringSet(FIREWORKS, fireworks);
	}

	private void persistModuleConfigs(SharedPreferences.Editor editor) {
		Set<String> moduleConfigsJson = new HashSet<>();
		for (IgnitionModule ignitionModule : ignitionModules) {
			ModuleConfig config = ignitionModule.getModuleConfig();
			if (config.isConfigured()) {
				try {
					moduleConfigsJson.add(config.toJson());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		editor.putStringSet(MODULE_CONFIGS, moduleConfigsJson);
	}

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	public List<PlannedFirework> getPlannedFireworks() {
		return plannedFireworks;
	}
}