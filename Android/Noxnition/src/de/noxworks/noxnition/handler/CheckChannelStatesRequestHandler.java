package de.noxworks.noxnition.handler;

import java.util.Collection;
import java.util.Properties;

import android.os.Handler;
import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.direct.execute.IChannelStatesHandler;

public class CheckChannelStatesRequestHandler<T extends IChannelStatesHandler & IMessageable>
    extends RequestHandler<T> {

	private static String OPERATION = "operation";

	public CheckChannelStatesRequestHandler(T mainActivity, Handler uiHandler) {
		super(mainActivity, uiHandler);
	}

	@Override
	public void requestFailed() {
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				messageable.showMessage("Check fehlgeschlagen");
			}
		});
	}

	@Override
	public void requestSuccess(final Properties properties) {
		boolean success = properties.get(OPERATION).toString().equals("1");
		if (!success) {
			uiHandler.post(new Runnable() {

				@Override
				public void run() {
					messageable.showMessage("Check fehlgeschlagen");
				}
			});
			return;
		}

		Collection<Integer> channels = messageable.getChannels();
		for (Integer channel : channels) {
			String identifier = "c" + channel;
			final boolean state = properties.get(identifier).toString().equals("1");
			messageable.handleChannelState(channel, state);
		}
	}
}