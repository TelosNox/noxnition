package de.noxworks.noxnition.planned.execute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.noxworks.noxnition.BaseActivity;
import de.noxworks.noxnition.IFireResultHandler;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.StringUtil;
import de.noxworks.noxnition.adapter.FireActionArrayAdapter;
import de.noxworks.noxnition.communication.FireChannelResult;
import de.noxworks.noxnition.communication.ModuleConnector;
import de.noxworks.noxnition.communication.StateCheckResult;
import de.noxworks.noxnition.handler.CheckChannelStatesRequestHandler;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.handler.StateCheckRequestHandler;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireAction;
import de.noxworks.noxnition.persistence.FireTrigger;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class ExecutePlannedFireworkActivity extends BaseActivity implements IFireResultHandler {

	public static final String PLANNED_FIREWORK = "planned_firework";

	private List<FireAction> fireActions = new ArrayList<>();
	private List<IgnitionModule> ignitionModules;

	private FireActionArrayAdapter fireActionArrayAdapter;

	private Map<IgnitionModule, ModuleConnector> connectorsByModule = new HashMap<>();
	private Button fireButton;
	private FireAction currentFireAction;
	private ProgressBar progressBar;
	private TextView timeText;
	private TextView nameText;
	private FiredActionVisualization firedAction;
	private CountDownTimer timer;
	private int fireCommandsToConfirm = 0;

	private Set<IgnitionModule> requiredModules;
	private Map<IgnitionModule, ChannelStatesHandler> channelStatesByModule;
	private Map<IgnitionModule, StateCheckResultHandler> moduleStatesByModule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ignitionModules = settingsManager.getIgnitionModules();

		setTitle("Feuerwerk zünden");

		Intent intent = getIntent();
		if (intent != null) {
			String plannedFireworkName = intent.getStringExtra(PLANNED_FIREWORK);
			for (PlannedFirework plannedFirework : settingsManager.getPlannedFireworks()) {
				if (plannedFirework.getName().equals(plannedFireworkName)) {
					fireActions.addAll(plannedFirework.getFireActions());
					break;
				}
			}
		}

		channelStatesByModule = new HashMap<>();
		moduleStatesByModule = new HashMap<>();
		for (IgnitionModule ignitionModule : ignitionModules) {
			String ipAddress = ignitionModule.getIpAddress();
			if (ipAddress != null) {
				List<Integer> channels = new ArrayList<>();
				int numberOfChannels = ignitionModule.getModuleConfig().getChannels();
				for (int i = 1; i <= numberOfChannels; i++) {
					channels.add(i);
				}

				ChannelStatesHandler channelStatesHandler = new ChannelStatesHandler(channels);
				channelStatesByModule.put(ignitionModule, channelStatesHandler);
				StateCheckResultHandler moduleStateHandler = new StateCheckResultHandler();
				moduleStatesByModule.put(ignitionModule, moduleStateHandler);
				ModuleConnector moduleConnector = new ModuleConnector(ipAddress, null,
				    new StateCheckRequestHandler<>(moduleStateHandler), new FireChannelRequestHandler<>(this),
				    new CheckChannelStatesRequestHandler<>(channelStatesHandler));
				connectorsByModule.put(ignitionModule, moduleConnector);
			}
		}

		if (fireActions.isEmpty()) {
			showMessage("No fire actions");
			finish();
			return;
		}

		Map<IgnitionModule, List<Integer>> requiredChannelsByModule = new HashMap<>();
		for (FireAction fireAction : fireActions) {
			List<FireTrigger> fireTriggers = fireAction.getFireTriggerGroup().getFireTriggers();
			if (fireTriggers.isEmpty()) {
				showMessage("Trigger has no assigned channels: " + fireAction.getFireTriggerGroup().getName());
				finish();
				return;
			}
			for (FireTrigger fireTrigger : fireTriggers) {
				IgnitionModule requiredModule = fireTrigger.getModule();
				List<Integer> channels = requiredChannelsByModule.get(requiredModule);
				if (channels == null) {
					channels = new ArrayList<>();
					requiredChannelsByModule.put(requiredModule, channels);
				}
				channels.addAll(fireTrigger.getChannels());
			}
		}

		requiredModules = requiredChannelsByModule.keySet();
		if (!connectorsByModule.keySet().containsAll(requiredModules)) {
			showMessage("Not all required modules are online");
			finish();
			return;
		}

		for (IgnitionModule ignitionModule : requiredModules) {
			ModuleConnector moduleConnector = connectorsByModule.get(ignitionModule);
			moduleConnector.checkChannelStates();
		}

		String message = "Not all required channels are connected:";
		boolean channelsOk = true;
		for (IgnitionModule ignitionModule : requiredModules) {
			String moduleName = ignitionModule.getModuleConfig().getName();
			Map<Integer, Boolean> channelStates = channelStatesByModule.get(ignitionModule).getChannelStates();
			List<Integer> requiredChannels = requiredChannelsByModule.get(ignitionModule);
			if (channelStates == null) {
				showMessage("No channel state answer from module: " + moduleName);
				finish();
				return;
			}
			List<Integer> activeChannels = new ArrayList<>();
			for (Entry<Integer, Boolean> entry : channelStates.entrySet()) {
				if (entry.getValue()) {
					activeChannels.add(entry.getKey());
				}
			}
			if (!activeChannels.containsAll(requiredChannels)) {
				channelsOk = false;
				List<Integer> list = new ArrayList<>(requiredChannels);
				Collections.sort(list);
				list.removeAll(activeChannels);
				String channels = StringUtil.join(list);
				message += "\n" + moduleName + " : " + channels;
			}
		}
		if (!channelsOk) {
			showMessage(message);
			finish();
			return;
		}

		for (IgnitionModule ignitionModule : requiredModules) {
			ModuleConnector moduleConnector = connectorsByModule.get(ignitionModule);
			moduleConnector.sendArmRequest();
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		for (IgnitionModule ignitionModule : requiredModules) {
			ModuleConnector moduleConnector = connectorsByModule.get(ignitionModule);
			moduleConnector.sendStateCheckRequest();
		}

		for (IgnitionModule ignitionModule : requiredModules) {
			String moduleName = ignitionModule.getModuleConfig().getName();
			StateCheckResultHandler stateCheckResultHandler = moduleStatesByModule.get(ignitionModule);
			StateCheckResult result = stateCheckResultHandler.getResult();
			if (result == null) {
				showMessage("No module state answer from module: " + moduleName);
				finish();
				return;
			}
			if (!result.isArmed()) {
				showMessage("Module is not armed: " + moduleName);
				finish();
				return;
			}
		}

		initMainLayout();
	}

	private void initMainLayout() {
		setContentView(R.layout.execute_planned_firework_layout);

		ListView plannedFiringList = (ListView) findViewById(R.id.plannedFireActionsList);
		fireActionArrayAdapter = new FireActionArrayAdapter(this, fireActions);
		plannedFiringList.setAdapter(fireActionArrayAdapter);

		firedAction = initFiredAction();

		nameText = (TextView) findViewById(R.id.fireactionRow_name);
		timeText = (TextView) findViewById(R.id.fireactionRow_time);
		timeText.setTextColor(Color.GRAY);

		nextFireAction();

		fireButton = (Button) findViewById(R.id.fireButton);

		progressBar = (ProgressBar) findViewById(R.id.execute_planned_progressBar);

		fireButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fireCurrentAction();
			}
		});
	}

	private void nextFireAction() {
		currentFireAction = fireActions.get(0);
		nameText.setText(currentFireAction.getFireTriggerGroup().getName());
		String delayText = currentFireAction.getDelay() + " sec";
		timeText.setText(delayText);
		fireActions.remove(currentFireAction);
	}

	private FiredActionVisualization initFiredAction() {
		final TextView runningNameText = (TextView) findViewById(R.id.runningFireaction_name);
		return new FiredActionVisualization(runningNameText);
	}

	private void runProgress() {
		progressBar.setProgress(0);

		final long delay = currentFireAction.getDelay() * 1000;
		timer = new CountDownTimer(delay, delay / 100) {

			@Override
			public void onTick(long millisUntilFinished) {
				long millis = delay - millisUntilFinished;
				long progress = millis * 100 / delay;
				int seconds = (int) (millis / 1000);
				int remainingDelay = currentFireAction.getDelay() - seconds;
				String delayText = remainingDelay + " sec";
				timeText.setText(delayText);
				progressBar.setProgress((int) progress);
			}

			@Override
			public void onFinish() {
				String delayText = 0 + " sec";
				timeText.setText(delayText);
				progressBar.setProgress(100);
				setCurrentActionBackgroundColors(Color.GREEN);
				if (delay == 0) {
					fireCurrentAction();
				} else {
					Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(500);
				}
			}

		};
		timer.start();
	}

	private void setCurrentActionBackgroundColors(int color) {
		timeText.setBackgroundColor(color);
		nameText.setBackgroundColor(color);
	}

	private void fireCurrentAction() {
		if (timer != null) {
			timer.cancel();
		}
		fireCommandsToConfirm = 0;
		List<FireTrigger> fireTriggers = currentFireAction.getFireTriggerGroup().getFireTriggers();
		for (FireTrigger fireTrigger : fireTriggers) {
			fireCommandsToConfirm += fireTrigger.getChannels().size();
		}
		for (FireTrigger fireTrigger : fireTriggers) {
			IgnitionModule module = fireTrigger.getModule();
			ModuleConnector moduleConnector = connectorsByModule.get(module);
			for (Integer channel : fireTrigger.getChannels()) {
				moduleConnector.fireChannel(channel);
			}
		}
		setCurrentActionBackgroundColors(Color.TRANSPARENT);
	}

	private void channelFiredSuccess() {
		fireCommandsToConfirm--;
		if (fireCommandsToConfirm == 0) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					firedAction.setFired(currentFireAction);
					nameText.setText("");
					timeText.setText("");
					progressBar.setProgress(0);
					if (!fireActions.isEmpty()) {
						nextFireAction();
						fireActionArrayAdapter.notifyDataSetChanged();
						runProgress();
					}
				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			fireCurrentAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void handleFireChannelResult(FireChannelResult result) {
		if (result.isSuccess()) {
			channelFiredSuccess();
		} else {
			channelFiredFail();
		}
	}

	private void channelFiredFail() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				setCurrentActionBackgroundColors(Color.RED);
			}
		});
	}

	@Override
	public void onBackPressed() {
		for (IgnitionModule ignitionModule : requiredModules) {
			ModuleConnector moduleConnector = connectorsByModule.get(ignitionModule);
			moduleConnector.sendDisArmRequest();
		}
		super.onBackPressed();
	}
}