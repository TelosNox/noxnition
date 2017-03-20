package de.noxworks.noxnition.communication;

public class RequestResult {

	private final boolean success;

	public RequestResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}
}