package de.noxworks.noxnition.handler;

import java.util.Properties;

import android.os.Handler;
import de.noxworks.noxnition.IMessageable;

public class ArmRequestHandler extends RequestHandler {

	public ArmRequestHandler(IMessageable activity, Handler uiHandler) {
		super(activity, uiHandler);
	}

	@Override
	public void requestFailed() {
		showMessage("Scharfschalten fehlgeschlagen");
	}

	@Override
	public void requestSuccess(Properties properties) {
	}
}