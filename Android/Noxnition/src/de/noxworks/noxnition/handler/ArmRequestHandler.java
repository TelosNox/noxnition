package de.noxworks.noxnition.handler;

import java.util.Properties;

import de.noxworks.noxnition.IMessageable;

public class ArmRequestHandler extends RequestHandler<IMessageable> {

	public ArmRequestHandler(IMessageable activity) {
		super(activity);
	}

	@Override
	public void requestFailed() {
		showMessage("Scharfschalten fehlgeschlagen");
	}

	@Override
	public void requestSuccess(Properties properties) {
	}
}