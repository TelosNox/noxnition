package de.noxworks.noxnition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireAction;

public class ChannelSelectionFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String ARG_IGNITION_MODULE = "ignition_module";

	private final PlanFireworkActivity planFireworkActivity;

	public ChannelSelectionFragment(PlanFireworkActivity planFireworkActivity) {
		this.planFireworkActivity = planFireworkActivity;
	}

	public static ChannelSelectionFragment newInstance(PlanFireworkActivity planFirworkActivity, int sectionNumber) {
		ChannelSelectionFragment fragment = new ChannelSelectionFragment(planFirworkActivity);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putSerializable(ARG_IGNITION_MODULE, planFirworkActivity.getIgnitionModules().get(sectionNumber));
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.channel_selection_fragment, container, false);

		final IgnitionModule ignitionModule = (IgnitionModule) getArguments().getSerializable(ARG_IGNITION_MODULE);

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
				Button button = new Button(rootView.getContext());
				button.setLayoutParams(buttonLayoutParams);
				channelRows.addView(button);
				final int channelNumber = i * 8 + y;
				button.setText("Channel " + channelNumber);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String name = ignitionModule.getModuleConfig().getName() + " - " + channelNumber;
						FireAction fireAction = new FireAction(name, ignitionModule, channelNumber);
						planFireworkActivity.addFireAction(fireAction);
					}
				});
			}
		}
		return rootView;
	}
}