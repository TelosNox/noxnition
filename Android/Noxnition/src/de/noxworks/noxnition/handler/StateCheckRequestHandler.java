package de.noxworks.noxnition.handler;

import java.util.Properties;

import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.IStateCheckResultHandler;
import de.noxworks.noxnition.communication.StateCheckResult;

public class StateCheckRequestHandler<T extends IStateCheckResultHandler & IMessageable> extends RequestHandler<T> {

	private final T mainActivity;
	private long startMillis;
	private long[] collectedMillis = new long[5];
	private int millisIndex = 0;

	public StateCheckRequestHandler(T mainActivity) {
		super(mainActivity);
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
		collectedMillis[millisIndex] = duration;
		millisIndex++;
		if (millisIndex > 4) {
			millisIndex = 0;
		}

		long averageMillis = 0;
		for (int i = 0; i < 5; i++) {
			long millis = collectedMillis[i];
			averageMillis += millis;
		}
		averageMillis /= 5;

		final Object voltage = properties.get("voltage");

		Object armedString = properties.get("a");
		final boolean armed = armedString.toString().equals("1");

		StateCheckResult result = new StateCheckResult(true, voltage.toString(), armed, averageMillis);
		mainActivity.handleStateCheckResult(result);
	}

	@Override
	public void preRequest() {
		startMillis = System.currentTimeMillis();
	}
}