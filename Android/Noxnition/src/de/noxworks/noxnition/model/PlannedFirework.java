package de.noxworks.noxnition.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlannedFirework implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private final List<FireAction> fireActions = new ArrayList<>();

	public PlannedFirework(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FireAction> getFireActions() {
		return fireActions;
	}

	@Override
	public String toString() {
		return getName();
	}
}