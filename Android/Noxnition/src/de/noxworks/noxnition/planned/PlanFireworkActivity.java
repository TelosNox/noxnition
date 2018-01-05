package de.noxworks.noxnition.planned;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import de.noxworks.noxnition.BaseActivity;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class PlanFireworkActivity extends BaseActivity {

	public static final String PLANNED_FIREWORK = "planned_firework";

	private PlannedFirework plannedFirework;

	private PlanFireworkFragmentSectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Feuerwerk planen");
		Intent intent = getIntent();
		if (intent != null) {
			String plannedFireworkName = intent.getStringExtra(PLANNED_FIREWORK);
			for (PlannedFirework plannedFirework : settingsManager.getPlannedFireworks()) {
				if (plannedFirework.getName().equals(plannedFireworkName)) {
					this.plannedFirework = plannedFirework;
					break;
				}
			}
		}

		setContentView(R.layout.module_fragment_layout);

		mSectionsPagerAdapter = new PlanFireworkFragmentSectionsPagerAdapter(getSupportFragmentManager(), plannedFirework);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}
}