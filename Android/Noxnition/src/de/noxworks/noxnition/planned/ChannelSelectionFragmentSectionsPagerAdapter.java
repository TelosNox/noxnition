package de.noxworks.noxnition.planned;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireTriggerGroup;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class ChannelSelectionFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private final FireTriggerGroup fireTriggerGroup;
	private final List<IgnitionModule> ignitionModules;
	private final PlannedFirework plannedFirework;

	public ChannelSelectionFragmentSectionsPagerAdapter(FragmentManager supportFragmentManager,
	    FireTriggerGroup fireTriggerGroup, PlannedFirework plannedFirework, List<IgnitionModule> ignitionModules) {
		super(supportFragmentManager);
		this.fireTriggerGroup = fireTriggerGroup;
		this.plannedFirework = plannedFirework;
		this.ignitionModules = ignitionModules;
	}

	@Override
	public Fragment getItem(int position) {
		return ChannelSelectionFragment.newInstance(ignitionModules.get(position), fireTriggerGroup, plannedFirework);
	}

	@Override
	public int getCount() {
		return ignitionModules.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ignitionModules.get(position).getModuleConfig().getName();
	}
}