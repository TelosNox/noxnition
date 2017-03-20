package de.noxworks.noxnition.handler;

import java.util.Map.Entry;
import java.util.Properties;

import android.os.Handler;
import android.widget.ToggleButton;
import de.noxworks.noxnition.FireFragment;

public class CheckChannelStatesRequestHandler extends RequestHandler<FireFragment> {

	private static String OPERATION = "operation";

	public CheckChannelStatesRequestHandler(FireFragment mainActivity, Handler uiHandler) {
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

		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				for (Entry<Integer, ToggleButton> entry : messageable.getIdentifierByChannelButton().entrySet()) {
					ToggleButton channel = entry.getValue();
					String identifier = "c" + entry.getKey();
					final boolean state = properties.get(identifier).toString().equals("1");
					messageable.setChannelState(channel, state);
				}
			}
		});
	}
}