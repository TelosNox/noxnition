package de.noxworks.noxnition;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.Toast;
import de.noxworks.noxnition.persistence.SettingsManager;

public class BaseActivity extends FragmentActivity implements IMessageable {

	protected static final String PREFS_NAME = "prefs_name";

	protected static SettingsManager settingsManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onStop() {
		super.onStop();
		settingsManager.persist(getSharedPreferences(PREFS_NAME, 0));
	}

	@Override
	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
}