package de.noxworks.noxnition;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements IMessageable {

	@Override
	public void showMessage(String message) {
		Context context = getActivity();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
}