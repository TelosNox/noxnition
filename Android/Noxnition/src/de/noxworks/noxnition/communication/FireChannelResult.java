package de.noxworks.noxnition.communication;

public class FireChannelResult extends RequestResult {

	private final int channel;

	public FireChannelResult(boolean success, int channel) {
		super(success);
		this.channel = channel;
	}

	public int getChannel() {
		return channel;
	}
}