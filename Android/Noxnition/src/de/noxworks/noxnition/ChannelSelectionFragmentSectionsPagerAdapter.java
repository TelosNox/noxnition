package de.noxworks.noxnition;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ChannelSelectionFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private PlanFireworkActivity activity;

	public ChannelSelectionFragmentSectionsPagerAdapter(FragmentManager fm, PlanFireworkActivity activity) {
		super(fm);
		this.activity = activity;
	}

	@Override
	public Fragment getItem(int position) {
		return ChannelSelectionFragment.newInstance(activity, position);
	}

	@Override
	public int getCount() {
		return activity.getIgnitionModules().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return activity.getIgnitionModules().get(position).getModuleConfig().getName();
	}
}