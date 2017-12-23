package de.noxworks.noxnition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.noxworks.noxnition.adapter.FireActionArrayAdapter;
import de.noxworks.noxnition.communication.FireChannelResult;
import de.noxworks.noxnition.communication.ModuleConnector;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.model.FireAction;
import de.noxworks.noxnition.model.IgnitionModule;

public class ExecutePlannedFireworkActivity extends BaseActivity implements IFireResultHandler {

	public static final String IGNITION_MODULES = "ignition_modules";
	public static final String FIRE_ACTIONS = "fireActions";

	private List<FireAction> fireActions = new ArrayList<>();
	private List<IgnitionModule> ignitionModules = new ArrayList<>();

	private FireActionArrayAdapter fireActionArrayAdapter;

	private Handler handler;

	private Map<String, ModuleConnector> connectorsByIp = new HashMap<>();

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

		ListView plannedFiringList = (ListView) findViewById(R.id.plannedFiringList);
		fireActionArrayAdapter = new FireActionArrayAdapter(this, fireActions);
		plannedFiringList.setAdapter(fireActionArrayAdapter);

		final TextView runningModuleText = (TextView) findViewById(R.id.runningFireaction_module);
		final TextView runningChannelText = (TextView) findViewById(R.id.runningFireaction_channel);
		final TextView runningTimeText = (TextView) findViewById(R.id.runningFireaction_time);
		final TextView runningNameText = (TextView) findViewById(R.id.runningFireaction_name);
		runningModuleText.setTextColor(Color.RED);
		runningChannelText.setTextColor(Color.RED);
		runningTimeText.setTextColor(Color.RED);
		runningNameText.setTextColor(Color.RED);

		final TextView moduleText = (TextView) findViewById(R.id.fireactionRow_module);
		final TextView channelText = (TextView) findViewById(R.id.fireactionRow_channel);
		final TextView timeText = (TextView) findViewById(R.id.fireactionRow_time);
		final TextView nameText = (TextView) findViewById(R.id.fireactionRow_name);
		channelText.setTextColor(Color.GRAY);
		timeText.setTextColor(Color.GRAY);

		final FireAction currentFireAction = fireActions.get(0);
		moduleText.setText(currentFireAction.getModule().getModuleConfig().getName());
		nameText.setText(currentFireAction.getName());
		channelText.setText("Channel " + currentFireAction.getChannel());
		String delayText = currentFireAction.getDelay() + " sec";
		timeText.setText(delayText);

		final Button fireButton = (Button) findViewById(R.id.fireButton);

		fireActions.remove(currentFireAction);

		fireButton.setOnClickListener(new OnClickListener() {

			final ProgressBar progressBar = (ProgressBar) findViewById(R.id.execute_planned_progressBar);

			private FireAction runProgress(final FireAction fireAction) {
				moduleText.setText(fireAction.getModule().getModuleConfig().getName());
				nameText.setText(fireAction.getName());
				channelText.setText("Channel " + fireAction.getChannel());
				String delayText = fireAction.getDelay() + " sec";
				timeText.setText(delayText);
				progressBar.setProgress(0);

				final long delay = fireAction.getDelay() * 1000;
				CountDownTimer timer = new CountDownTimer(delay, delay / 100) {

					@Override
					public void onTick(long millisUntilFinished) {
						long progress = (delay - millisUntilFinished) * 100 / delay;
						progressBar.setProgress((int) progress);
					}

					@Override
					public void onFinish() {
						ModuleConnector moduleConnector = connectorsByIp.get(fireAction.getModule().getIpAddress());
						moduleConnector.fireChannel(fireAction.getChannel());
						handler.post(new Runnable() {

							@Override
							public void run() {
								runningModuleText.setText(fireAction.getModule().getModuleConfig().getName());
								runningNameText.setText(fireAction.getName());
								runningChannelText.setText("Channel " + fireAction.getChannel());

								moduleText.setText("");
								nameText.setText("");
								channelText.setText("");
								timeText.setText("");
								progressBar.setProgress(0);
								if (!fireActions.isEmpty()) {
									FireAction nextFireAction = fireActions.get(0);
									fireActions.remove(nextFireAction);
									fireActionArrayAdapter.notifyDataSetChanged();
									runProgress(nextFireAction);
								}
							}
						});
					}
				};

				timer.start();
				return fireAction;
			}

			@Override
			public void onClick(View v) {
				fireButton.setEnabled(false);
				if (!fireActions.isEmpty()) {
					runProgress(currentFireAction);
				}
			}
		});
	}

	@Override
	public void handleFireChannelResult(FireChannelResult result) {
		// TODO Auto-generated method stub

	}
}