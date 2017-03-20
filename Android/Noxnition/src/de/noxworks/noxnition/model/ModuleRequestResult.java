package de.noxworks.noxnition.model;

public class ModuleRequestResult {

	private final String name;
	private final String ipAddress;
	private final int channels;

	public ModuleRequestResult(String name, String ipAddress, int channels) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.channels = channels;
	}

	public String getName() {
		return name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getChannels() {
		return channels;
	}
}