package de.noxworks.noxnition.handler;

import de.noxworks.noxnition.IMessageable;

public abstract class RequestHandler<T extends IMessageable> implements IRequestHandler {

	protected final T messageable;

	public RequestHandler(T messageable) {
		this.messageable = messageable;
	}

	@Override
	public void preRequest() {
	}

	protected void showMessage(final String message) {
		messageable.showMessage(message);
	}
}