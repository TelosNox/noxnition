package de.noxworks.noxnition.planned;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class PlanFireworkFragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {

	private PlannedFirework plannedFirework;

	public PlanFireworkFragmentSectionsPagerAdapter(FragmentManager fm, PlannedFirework plannedFirework) {
		super(fm);
		this.plannedFirework = plannedFirework;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new PlanFireActionsFragment(plannedFirework);
		}
		return new PlanFireTriggersFragment(plannedFirework);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "Plan actions";
		}
		return "Plan triggers";
	}
}