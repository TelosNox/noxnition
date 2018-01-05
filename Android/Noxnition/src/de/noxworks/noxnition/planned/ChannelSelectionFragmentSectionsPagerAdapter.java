package de.noxworks.noxnition.planned;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.FireTriggerGroup;

public class ChannelSelectionFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private FireTriggerGroup fireTriggerGroup;
	private List<IgnitionModule> ignitionModules;

	public ChannelSelectionFragmentSectionsPagerAdapter(FragmentManager supportFragmentManager,
	    FireTriggerGroup fireTriggerGroup, List<IgnitionModule> ignitionModules) {
		super(supportFragmentManager);
		this.fireTriggerGroup = fireTriggerGroup;
		this.ignitionModules = ignitionModules;
	}

	@Override
	public Fragment getItem(int position) {
		return new ChannelSelectionFragment(ignitionModules.get(position), fireTriggerGroup);
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