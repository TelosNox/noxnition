package de.noxworks.noxnition.direct.execute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.Toast;
import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.model.IgnitionModule;

public class FireActivity extends FragmentActivity implements IMessageable {

	public static final String IGNITION_MODULES = "ignition_modules";

	private FireFragmentSectionsPagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;

	private List<IgnitionModule> ignitionModules = new ArrayList<>();

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Manuell zünden");

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Intent intent = getIntent();
		if (intent != null) {
			Serializable serializable = intent.getSerializableExtra(IGNITION_MODULES);
			if (serializable != null) {
				@SuppressWarnings("unchecked")
				ArrayList<IgnitionModule> list = (ArrayList<IgnitionModule>) serializable;
				ignitionModules.addAll(list);
			}
		}
		mSectionsPagerAdapter = new FireFragmentSectionsPagerAdapter(getSupportFragmentManager(), this);

		initFireLayout();
	}

	private void initFireLayout() {
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