package de.noxworks.noxnition;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.noxworks.noxnition.persistence.SettingsManager;

public class MainFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private SettingsManager settingsManager;

	public MainFragmentSectionsPagerAdapter(FragmentManager fm, SettingsManager settingsManager) {
		super(fm);
		this.settingsManager = settingsManager;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return ModuleFragment.newInstance(settingsManager);
		}
		return new PlanFireworkFragment(settingsManager);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "Modules";
		}
		return "Plan firework";
	}
}