package de.noxworks.noxnition.handler;

import java.util.Properties;

import de.noxworks.noxnition.IFireResultHandler;
import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.communication.FireChannelResult;

public class FireChannelRequestHandler<T extends IFireResultHandler & IMessageable> extends RequestHandler<T> {

	private final T mainActivity;

	public FireChannelRequestHandler(T mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity;
	}

	@Override
	public void requestFailed() {
		postResult(false, 0);
	}

	@Override
	public void requestSuccess(Properties properties) {
		Object channelString = properties.get("channel");
		final int channel = Integer.parseInt(channelString.toString());
		postResult(true, channel);
	}

	private void postResult(boolean success, int channel) {
		FireChannelResult result = new FireChannelResult(success, channel);
		mainActivity.handleFireChannelResult(result);
	}
}