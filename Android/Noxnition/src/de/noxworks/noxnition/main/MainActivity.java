package de.noxworks.noxnition.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import de.noxworks.noxnition.BaseActivity;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.SettingsManager;

public class MainActivity extends BaseActivity {

	private ViewPager mViewPager;
	private MainFragmentSectionsPagerAdapter mSectionsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Noxnition");
		settingsManager = new SettingsManager(getSharedPreferences(PREFS_NAME, 0));
		mSectionsPagerAdapter = new MainFragmentSectionsPagerAdapter(getSupportFragmentManager(), settingsManager);
		initStartLayout();
	}

	private void initStartLayout() {
		setContentView(R.layout.module_fragment_layout);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
}