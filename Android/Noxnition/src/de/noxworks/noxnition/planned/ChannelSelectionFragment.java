package de.noxworks.noxnition.planned;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import de.noxworks.noxnition.BaseFragment;
import de.noxworks.noxnition.IntentHelper;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireTrigger;
import de.noxworks.noxnition.persistence.FireTriggerGroup;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class ChannelSelectionFragment extends BaseFragment {

	private FireTriggerGroup fireTriggerGroup;
	private IgnitionModule ignitionModule;
	private PlannedFirework plannedFirework;

	public static ChannelSelectionFragment newInstance(IgnitionModule ignitionModule, FireTriggerGroup fireTriggerGroup,
	    PlannedFirework plannedFirework) {
		ChannelSelectionFragment f = new ChannelSelectionFragment();
		Bundle bundle = new Bundle(3);
		bundle.putString(IntentHelper.FIRE_TRIGGER_GROUP_ID, fireTriggerGroup.getId());
		bundle.putString(IntentHelper.PLANNED_FIREWORK_ID, plannedFirework.getId());
		bundle.putString(IntentHelper.IGNITION_MODULE_ID, ignitionModule.getId());
		IntentHelper.add(fireTriggerGroup);
		IntentHelper.add(plannedFirework);
		IntentHelper.add(ignitionModule);
		f.setArguments(bundle);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String ignitionModuleId = getArguments().getString(IntentHelper.IGNITION_MODULE_ID);
		ignitionModule = (IgnitionModule) IntentHelper.get(ignitionModuleId);

		String fireTriggerGroupId = getArguments().getString(IntentHelper.FIRE_TRIGGER_GROUP_ID);
		fireTriggerGroup = (FireTriggerGroup) IntentHelper.get(fireTriggerGroupId);

		String plannedFireworkId = getArguments().getString(IntentHelper.PLANNED_FIREWORK_ID);
		plannedFirework = (PlannedFirework) IntentHelper.get(plannedFireworkId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.channel_selection_fragment, container, false);

		FireTrigger fireTrigger = getFireTriggerOfModule(fireTriggerGroup);

		Set<Integer> alreadyAssignedChannels = new HashSet<>();
		List<FireTriggerGroup> fireTriggerGroups = plannedFirework.getFireTriggerGroups();
		for (FireTriggerGroup fireTriggerGroup : fireTriggerGroups) {
			if (fireTriggerGroup != this.fireTriggerGroup) {
				FireTrigger fireTriggerOfModule = getFireTriggerOfModule(fireTriggerGroup);
				if (fireTriggerOfModule != null) {
					alreadyAssignedChannels.addAll(fireTriggerOfModule.getChannels());
				}
			}
		}

		List<Integer> channels = new ArrayList<>();
		if (fireTrigger != null) {
			channels.addAll(fireTrigger.getChannels());
		}

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
				final ToggleButton button = new ToggleButton(rootView.getContext());
				button.setLayoutParams(buttonLayoutParams);
				channelRows.addView(button);
				final int channelNumber = i * 8 + y;
				button.setTextOff("Channel " + channelNumber);
				button.setTextOn("Channel " + channelNumber);
				button.setChecked(false);
				if (channels.contains(channelNumber)) {
					button.setChecked(true);
				}
				if (alreadyAssignedChannels.contains(channelNumber)) {
					button.setEnabled(false);
				}
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						FireTrigger fireTrigger = getFireTriggerOfModule(fireTriggerGroup);
						if (button.isChecked()) {
							if (fireTrigger == null) {
								fireTrigger = new FireTrigger(ignitionModule);
								fireTriggerGroup.addFireTrigger(fireTrigger);
							}
							Set<Integer> channels = fireTrigger.getChannels();
							channels.add(channelNumber);
						} else {
							Set<Integer> channels = fireTrigger.getChannels();
							channels.remove(channelNumber);
							if (channels.isEmpty()) {
								fireTriggerGroup.removeFireTrigger(fireTrigger);
							}
						}
					}
				});
			}
		}
		return rootView;
	}

	private FireTrigger getFireTriggerOfModule(FireTriggerGroup fireTriggerGroup) {
		List<FireTrigger> fireTriggers = fireTriggerGroup.getFireTriggers();
		for (FireTrigger fireTrigger : fireTriggers) {
			if (fireTrigger.getModule().equals(ignitionModule)) {
				return fireTrigger;
			}
		}
		return null;
	}
}