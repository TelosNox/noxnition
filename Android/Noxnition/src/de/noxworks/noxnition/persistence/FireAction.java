package de.noxworks.noxnition.model;

import java.io.Serializable;

public class FireAction implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private IgnitionModule module;
	private int channel;
	private int delay = 0;

	public FireAction(String name, IgnitionModule module, int channel) {
		this.name = name;
		this.module = module;
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IgnitionModule getModule() {
		return module;
	}

	public void setModule(IgnitionModule module) {
		this.module = module;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}