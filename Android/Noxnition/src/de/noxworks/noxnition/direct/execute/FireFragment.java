package de.noxworks.noxnition.direct.execute;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
import de.noxworks.noxnition.BaseFragment;
import de.noxworks.noxnition.IFireResultHandler;
import de.noxworks.noxnition.IStateCheckResultHandler;
import de.noxworks.noxnition.IntentHelper;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.communication.FireChannelResult;
import de.noxworks.noxnition.communication.FireFragmentModuleConnector;
import de.noxworks.noxnition.communication.ModuleConnector;
import de.noxworks.noxnition.communication.StateCheckResult;
import de.noxworks.noxnition.model.IgnitionModule;

public class FireFragment extends BaseFragment
    implements IFireResultHandler, IChannelStatesHandler, IStateCheckResultHandler {

	private Timer timer;
	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private TextView voltage;
	private Switch armedSwitch;
	private TextView connectionState;
	private Map<Integer, ToggleButton> identifierByChannelButton;

	private ModuleConnector moduleConnector = null;
	private Handler uiHandler = new Handler();
	private IgnitionModule ignitionModule;

	public Map<Integer, ToggleButton> getIdentifierByChannelButton() {
		return identifierByChannelButton;
	}

	public static FireFragment newInstance(IgnitionModule ignitionModule) {
		FireFragment fragment = new FireFragment();
		Bundle args = new Bundle();
		args.putString(IntentHelper.IGNITION_MODULE_ID, ignitionModule.getId());
		fragment.setArguments(args);
		IntentHelper.add(ignitionModule);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String ignitionModuleId = getArguments().getString(IntentHelper.IGNITION_MODULE_ID);
		ignitionModule = (IgnitionModule) IntentHelper.get(ignitionModuleId);
		moduleConnector = new FireFragmentModuleConnector(this, ignitionModule.getIpAddress(), uiHandler);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("fragment", "createView");
		View rootView = inflater.inflate(R.layout.fire_fragment, container, false);
		connectionState = (TextView) rootView.findViewById(R.id.configMain);
		setConnectionState(false, 0);

		armedSwitch = (Switch) rootView.findViewById(R.id.scharf);
		armedSwitch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				armedSwitch.setChecked(!armedSwitch.isChecked());
				if (armedSwitch.isChecked()) {
					moduleConnector.sendDisArmRequest();
				} else {
					moduleConnector.sendArmRequest();
				}
			}
		});

		voltage = (TextView) rootView.findViewById(R.id.voltage);
		voltage.setText("");

		setArmed(false);

		identifierByChannelButton = new HashMap<>();
		LinearLayout channelColumns = (LinearLayout) rootView.findViewById(R.id.channelcolumns);
		int columns = ignitionModule.getModuleConfig().getChannels() / 8;
		int rows = 8;
		for (int i = 0; i < columns; i++) {
			LinearLayout channelRows = new LinearLayout(rootView.getContext());
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
			    LinearLayout.LayoutParams.MATCH_PARENT);
			layoutParams.weight = 1;
			layoutParams.gravity = Gravity.FILL_HORIZONTAL;
			channelRows.setOrientation(LinearLayout.VERTICAL);
			channelRows.setLayoutParams(layoutParams);
			channelColumns.addView(channelRows);
			for (int y = 1; y <= rows; y++) {
				LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
				    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				buttonLayoutParams.weight = 1;
				ToggleButton button = new ToggleButton(rootView.getContext());
				button.setLayoutParams(buttonLayoutParams);
				channelRows.addView(button);
				int channelNumber = i * 8 + y;
				button.setTextOff("Channel " + channelNumber);
				button.setTextOn("Channel " + channelNumber);
				button.setChecked(false);
				identifierByChannelButton.put(channelNumber, button);
				button.setOnClickListener(createChannelListener(channelNumber));
			}
		}
		return rootView;
	}

	private View.OnClickListener createChannelListener(final Integer channel) {
		final ToggleButton button = identifierByChannelButton.get(channel);
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button.setChecked(!button.isChecked());
				moduleConnector.fireChannel(channel);
			}
		};
	}

	public void setVoltageText(final String text) {
		voltage.setText(text);
	}

	public void setArmed(final boolean armed) {
		armedSwitch.setChecked(armed);
		int color = Color.GREEN;
		if (armed) {
			color = Color.RED;
		}
		armedSwitch.setTextColor(color);
	}

	public void setConnectionState(final boolean connected, long time) {
		String text = "offline";
		int color = Color.RED;
		if (connected) {
			text = time + "ms";
			color = Color.GREEN;
		}
		connectionState.setText(text);
		connectionState.setTextColor(color);
	}

	@Override
	public void handleChannelState(final int channel, final boolean state) {
		final ToggleButton toggleButton = identifierByChannelButton.get(channel);
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				toggleButton.setChecked(state);
			}
		});
	}

	private void startTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				moduleConnector.sendStateCheckRequest();
			}
		}, 0, 1000);
	}

	@Override
	public void onStart() {
		super.onStart();

		if (moduleConnector != null) {
			startTimer();
		}
	}

	@Override
	public void onStop() {
		stopTimer();
		super.onStop();
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		MenuItem check = menu.findItem(R.id.check);
		check.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				for (ToggleButton toggleButton : identifierByChannelButton.values()) {
					toggleButton.setChecked(false);
				}
				moduleConnector.checkChannelStates();
				return true;
			}
		});
	}

	@Override
	public void handleStateCheckResult(final StateCheckResult result) {
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				if (result.isSuccess()) {
					setArmed(result.isArmed());
					setVoltageText(result.getVoltage().toString() + "v");
					setConnectionState(true, result.getAnswerTime());
				} else {
					setVoltageText("");
					setConnectionState(false, 0);
				}
			}
		});
	}

	@Override
	public void handleFireChannelResult(final FireChannelResult result) {
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				if (result.isSuccess()) {
					int channel = result.getChannel();
					final ToggleButton button = identifierByChannelButton.get(channel);
					button.setTextColor(Color.RED);
					scheduler.schedule(new Runnable() {
						@Override
						public void run() {
							button.setTextColor(Color.BLACK);
						}
					}, 1000, TimeUnit.MILLISECONDS);
				} else {
					showMessage("Zuendung fehlgeschlagen");
				}
			}
		});
	}

	@Override
	public Collection<Integer> getChannels() {
		return identifierByChannelButton.keySet();
	}
}