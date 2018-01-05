package de.noxworks.noxnition.planned;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireTrigger;
import de.noxworks.noxnition.persistence.FireTriggerGroup;

public class ChannelSelectionFragment extends Fragment {

	private final FireTriggerGroup fireTriggerGroup;
	private final IgnitionModule ignitionModule;

	public ChannelSelectionFragment(IgnitionModule ignitionModule, FireTriggerGroup fireTriggerGroup) {
		this.ignitionModule = ignitionModule;
		this.fireTriggerGroup = fireTriggerGroup;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.channel_selection_fragment, container, false);

		FireTrigger fireTrigger = getFireTriggerOfModule(ignitionModule);

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
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						FireTrigger fireTrigger = getFireTriggerOfModule(ignitionModule);
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

	private FireTrigger getFireTriggerOfModule(final IgnitionModule ignitionModule) {
		List<FireTrigger> fireTriggers = fireTriggerGroup.getFireTriggers();
		for (FireTrigger fireTrigger : fireTriggers) {
			if (fireTrigger.getModule().equals(ignitionModule)) {
				return fireTrigger;
			}
		}
		return null;
	}
}