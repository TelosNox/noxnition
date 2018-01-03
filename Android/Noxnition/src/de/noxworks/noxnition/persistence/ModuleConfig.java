package de.noxworks.noxnition.settings;

import java.io.Serializable;

public class ModuleConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final int channels;
	private boolean configured;

	public ModuleConfig(String name, int channels, boolean configured) {
		this.name = name;
		this.channels = channels;
		this.configured = configured;
	}

	public int getChannels() {
		return channels;
	}

	@Override
	public String toString() {
		return name + " (" + channels + ")";
	}

	public String getName() {
		return name;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}
}