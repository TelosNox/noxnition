package de.noxworks.noxnition.model;

import java.io.Serializable;

import de.noxworks.noxnition.persistence.ModuleConfig;

public class IgnitionModule implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ModuleConfig moduleConfig;
	private String ipAddress;
	private long lastUpdate;

	public IgnitionModule(ModuleConfig moduleConfig) {
		this.moduleConfig = moduleConfig;
	}

	public ModuleConfig getModuleConfig() {
		return moduleConfig;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		lastUpdate = System.currentTimeMillis();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public String toString() {
		return getModuleConfig().getName();
	}

	public long getLastUpdate() {
		return lastUpdate;
	}
}