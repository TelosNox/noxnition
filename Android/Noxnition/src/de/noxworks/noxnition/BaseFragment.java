package de.noxworks.noxnition;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements IMessageable {

	protected Handler uiHandler = new Handler();

	@Override
	public void showMessage(String message) {
		Context context = getActivity();
		int duration = Toast.LENGTH_SHORT;

		final Toast toast = Toast.makeText(context, message, duration);

		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				toast.show();
			}
		});
	}
}