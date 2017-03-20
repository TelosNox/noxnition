package de.noxworks.noxnition.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import de.noxworks.noxnition.model.FireAction;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.model.PlannedFirework;

public class SettingsManager {

	private static final String MODULE_CONFIG_PREFIX = "moduleconfig.";
	private static final String NAMES = "names";
	private static final String FIREWORK_PREFIX = "firework.";
	private static final String ACTION_PREFIX = "action.";
	private static final String CHANNEL = "channel";
	private static final String DELAY = "delay";
	private static final String MODULE = "module";

	private List<IgnitionModule> ignitionModules = new ArrayList<>();
	private List<PlannedFirework> plannedFireworks = new ArrayList<>();

	public SettingsManager(SharedPreferences settings) {
		load(settings);
	}

	private void load(SharedPreferences settings) {
		Set<String> moduleNames = settings.getStringSet(MODULE_CONFIG_PREFIX + NAMES, new HashSet<String>());
		for (String moduleName : moduleNames) {
			int channels = settings.getInt(MODULE_CONFIG_PREFIX + moduleName, 0);
			ModuleConfig config = new ModuleConfig(moduleName, channels, true);
			IgnitionModule ignitionModule = new IgnitionModule(config);
			ignitionModules.add(ignitionModule);
		}
		Set<String> fireworkNames = settings.getStringSet(FIREWORK_PREFIX + NAMES, new HashSet<String>());
		for (String fireworkName : fireworkNames) {
			PlannedFirework plannedFirework = new PlannedFirework(fireworkName);
			List<String> actionNames = new ArrayList<>(
			    settings.getStringSet(FIREWORK_PREFIX + fireworkName + "." + ACTION_PREFIX + NAMES, new HashSet<String>()));
			Collections.sort(actionNames);
			String propPrefix = FIREWORK_PREFIX + fireworkName + ".";
			for (String actionName : actionNames) {
				int channel = settings.getInt(propPrefix + ACTION_PREFIX + actionName + "." + CHANNEL, 0);
				int delay = settings.getInt(propPrefix + ACTION_PREFIX + actionName + "." + DELAY, 0);
				String moduleName = settings.getString(propPrefix + ACTION_PREFIX + actionName + "." + MODULE, "");
				IgnitionModule actionModule = null;
				for (IgnitionModule ignitionModule : ignitionModules) {
					if (ignitionModule.getModuleConfig().getName().equals(moduleName)) {
						actionModule = ignitionModule;
					}
				}
				if (actionModule != null) {
					FireAction action = new FireAction(actionName, actionModule, channel);
					action.setDelay(delay);
					plannedFirework.getFireActions().add(action);
				}
			}
			plannedFireworks.add(plannedFirework);
		}
	}

	public void save(SharedPreferences settings) {
		SharedPreferences.Editor editor = settings.edit();
		Set<String> moduleConfigNames = new HashSet<>();
		for (IgnitionModule ignitionModule : ignitionModules) {
			ModuleConfig config = ignitionModule.getModuleConfig();
			if (config.isConfigured()) {
				moduleConfigNames.add(config.getName());
				editor.putInt(MODULE_CONFIG_PREFIX + config.getName(), config.getChannels());
			}
		}
		editor.putStringSet(MODULE_CONFIG_PREFIX + NAMES, moduleConfigNames);

		Set<String> fireworkNames = new HashSet<>();
		for (PlannedFirework firework : plannedFireworks) {
			Set<String> actionNames = new HashSet<>();
			String fireworkName = firework.getName();
			fireworkNames.add(fireworkName);
			String propPrefix = FIREWORK_PREFIX + fireworkName + ".";
			for (FireAction fireAction : firework.getFireActions()) {
				saveFireAction(editor, fireAction, propPrefix);
				actionNames.add(fireAction.getName());
			}
			editor.putStringSet(propPrefix + ACTION_PREFIX + NAMES, actionNames);
		}
		editor.putStringSet(FIREWORK_PREFIX + NAMES, fireworkNames);

		editor.commit();
	}

	private void saveFireAction(Editor editor, FireAction fireAction, String propPrefix) {
		String name = fireAction.getName();
		editor.putInt(propPrefix + ACTION_PREFIX + name + "." + CHANNEL, fireAction.getChannel());
		editor.putInt(propPrefix + ACTION_PREFIX + name + "." + DELAY, fireAction.getDelay());
		editor.putString(propPrefix + ACTION_PREFIX + name + "." + MODULE,
		    fireAction.getModule().getModuleConfig().getName());
	}

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	public List<PlannedFirework> getPlannedFireworks() {
		return plannedFireworks;
	}
}