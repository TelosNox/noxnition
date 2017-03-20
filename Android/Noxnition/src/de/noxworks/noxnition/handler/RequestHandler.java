package de.noxworks.noxnition.handler;

import android.os.Handler;
import de.noxworks.noxnition.IMessageable;

public abstract class RequestHandler<T extends IMessageable> implements IRequestHandler {

	protected final T messageable;
	protected final Handler uiHandler;

	public RequestHandler(T messageable, Handler uiHandler) {
		this.messageable = messageable;
		this.uiHandler = uiHandler;
	}

	@Override
	public void preRequest() {
	}

	protected void showMessage(final String message) {
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				messageable.showMessage(message);
			}
		});
	}
}