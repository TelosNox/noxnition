package de.noxworks.noxnition.planned.execute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import de.noxworks.noxnition.adapter.FireActionArrayAdapter;
import de.noxworks.noxnition.communication.FireChannelResult;
import de.noxworks.noxnition.communication.ModuleConnector;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireAction;

public class ExecutePlannedFireworkActivity extends BaseActivity implements IFireResultHandler {

	public static final String IGNITION_MODULES = "ignition_modules";
	public static final String FIRE_ACTIONS = "fireActions";

	private List<FireAction> fireActions = new ArrayList<>();
	private List<IgnitionModule> ignitionModules = new ArrayList<>();

	private FireActionArrayAdapter fireActionArrayAdapter;

	private Handler handler;

	private Map<String, ModuleConnector> connectorsByIp = new HashMap<>();
	private Button fireButton;
	private FireAction currentFireAction;
	private ProgressBar progressBar;
	private TextView moduleText;
	private TextView channelText;
	private TextView timeText;
	private TextView nameText;
	private FiredActionVisualization firedAction;
	private CountDownTimer timer;

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Feuerwerk zünden");

		handler = new Handler();

		Intent intent = getIntent();
		if (intent != null) {
			Serializable modulesSerializable = intent.getSerializableExtra(IGNITION_MODULES);
			if (modulesSerializable != null) {
				@SuppressWarnings("unchecked")
				ArrayList<IgnitionModule> list = (ArrayList<IgnitionModule>) modulesSerializable;
				ignitionModules.addAll(list);
			}
			Serializable actionsSerializable = intent.getSerializableExtra(FIRE_ACTIONS);
			if (actionsSerializable != null) {
				@SuppressWarnings("unchecked")
				ArrayList<FireAction> list = (ArrayList<FireAction>) actionsSerializable;
				fireActions.addAll(list);
			}
		}

		for (IgnitionModule ignitionModule : ignitionModules) {
			String ipAddress = ignitionModule.getIpAddress();
			ModuleConnector moduleConnector = new ModuleConnector(ipAddress, null, null,
			    new FireChannelRequestHandler<ExecutePlannedFireworkActivity>(this, null), null);
			connectorsByIp.put(ipAddress, moduleConnector);
		}

		initMainLayout();
	}

	private void initMainLayout() {
		setContentView(R.layout.execute_planned_firework_layout);

		ListView plannedFiringList = (ListView) findViewById(R.id.plannedFireActionsList);
		fireActionArrayAdapter = new FireActionArrayAdapter(this, fireActions);
		plannedFiringList.setAdapter(fireActionArrayAdapter);

		firedAction = initFiredAction();

		moduleText = (TextView) findViewById(R.id.fireactionRow_module);
		channelText = (TextView) findViewById(R.id.fireactionRow_channel);
		timeText = (TextView) findViewById(R.id.fireactionRow_time);
		nameText = (TextView) findViewById(R.id.fireactionRow_name);
		channelText.setTextColor(Color.GRAY);
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
		moduleText.setText(currentFireAction.getModule().getModuleConfig().getName());
		nameText.setText(currentFireAction.getName());
		channelText.setText("Channel " + currentFireAction.getChannel());
		String delayText = currentFireAction.getDelay() + " sec";
		timeText.setText(delayText);
		fireActions.remove(currentFireAction);
	}

	private FiredActionVisualization initFiredAction() {
		final TextView runningModuleText = (TextView) findViewById(R.id.runningFireaction_module);
		final TextView runningChannelText = (TextView) findViewById(R.id.runningFireaction_channel);
		final TextView runningTimeText = (TextView) findViewById(R.id.runningFireaction_time);
		final TextView runningNameText = (TextView) findViewById(R.id.runningFireaction_name);
		return new FiredActionVisualization(runningModuleText, runningChannelText, runningTimeText, runningNameText);
	}

	private void runProgress() {
		progressBar.setProgress(0);

		final long delay = currentFireAction.getDelay() * 1000;
		timer = new CountDownTimer(delay, delay / 100) {

			@Override
			public void onTick(long millisUntilFinished) {
				long progress = (delay - millisUntilFinished) * 100 / delay;
				progressBar.setProgress((int) progress);
			}

			@Override
			public void onFinish() {
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
		moduleText.setBackgroundColor(color);
		channelText.setBackgroundColor(color);
		timeText.setBackgroundColor(color);
		nameText.setBackgroundColor(color);
	}

	private void fireCurrentAction() {
		if (timer != null) {
			timer.cancel();
		}
		ModuleConnector moduleConnector = connectorsByIp.get(currentFireAction.getModule().getIpAddress());
		moduleConnector.fireChannel(currentFireAction.getChannel());
		setCurrentActionBackgroundColors(Color.TRANSPARENT);
	}

	private void channelFiredSuccess() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				firedAction.setFired(currentFireAction);
				moduleText.setText("");
				nameText.setText("");
				channelText.setText("");
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
}