package de.noxworks.noxnition.handler;

import java.util.Properties;

import android.os.Handler;
import de.noxworks.noxnition.FireFragment;
import de.noxworks.noxnition.communication.StateCheckResult;

public class StateCheckRequestHandler extends RequestHandler<FireFragment> {

	private final FireFragment mainActivity;
	private long startMillis;

	public StateCheckRequestHandler(FireFragment mainActivity, Handler uiHandler) {
		super(mainActivity, uiHandler);
		this.mainActivity = mainActivity;
	}

	@Override
	public void requestFailed() {
		StateCheckResult result = new StateCheckResult(false, "", false, 0);
		mainActivity.handleStateCheckResult(result);
	}

	@Override
	public void requestSuccess(final Properties properties) {
		final long duration = System.currentTimeMillis() - startMillis;
		final Object voltage = properties.get("voltage");

		Object armedString = properties.get("a");
		final boolean armed = armedString.toString().equals("1");

		StateCheckResult result = new StateCheckResult(true, voltage.toString(), armed, duration);
		mainActivity.handleStateCheckResult(result);
	}

	@Override
	public void preRequest() {
		startMillis = System.currentTimeMillis();
	}
}