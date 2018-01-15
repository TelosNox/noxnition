package de.noxworks.noxnition.direct.execute;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FireFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private FireActivity mainActivity;

	public FireFragmentSectionsPagerAdapter(FragmentManager fm, FireActivity fireActivity) {
		super(fm);
		this.mainActivity = fireActivity;
	}

	@Override
	public Fragment getItem(int position) {
		return FireFragment.newInstance(mainActivity.getIgnitionModules().get(position));
	}

	@Override
	public int getCount() {
		return mainActivity.getIgnitionModules().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mainActivity.getIgnitionModules().get(position).getModuleConfig().getName();
	}
}