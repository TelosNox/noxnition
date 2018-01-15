package de.noxworks.noxnition.planned;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import de.noxworks.noxnition.BaseActivity;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.FireTriggerGroup;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class ChannelSelectionActivity extends BaseActivity {

	public static final String PLANNED_FIREWORK = "planned_firework";
	public static final String FIRE_TRIGGER_GROUP_INDEX = "fire_trigger_group_index";

	private FireTriggerGroup fireTriggerGroup;
	private PlannedFirework plannedFirework;

	private ChannelSelectionFragmentSectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Feuerwerk planen");
		Intent intent = getIntent();
		if (intent != null) {
			String plannedFireworkName = intent.getStringExtra(PLANNED_FIREWORK);
			int index = intent.getIntExtra(FIRE_TRIGGER_GROUP_INDEX, -1);
			for (PlannedFirework plannedFirework : settingsManager.getPlannedFireworks()) {
				if (plannedFirework.getName().equals(plannedFireworkName)) {
					List<FireTriggerGroup> fireTriggerGroups = plannedFirework.getFireTriggerGroups();
					FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(index);
					this.fireTriggerGroup = fireTriggerGroup;
					this.plannedFirework = plannedFirework;
					break;
				}
			}
		}

		setContentView(R.layout.module_fragment_layout);

		mSectionsPagerAdapter = new ChannelSelectionFragmentSectionsPagerAdapter(getSupportFragmentManager(),
		    fireTriggerGroup, plannedFirework, settingsManager.getPlanableIgnitionModules());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}
}