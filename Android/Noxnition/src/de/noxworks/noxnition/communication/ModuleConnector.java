package de.noxworks.noxnition.communication;

import de.noxworks.noxnition.handler.ArmRequestHandler;
import de.noxworks.noxnition.handler.CheckChannelStatesRequestHandler;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.handler.StateCheckRequestHandler;
import de.noxworks.noxnition.model.RequestTask;

public class ModuleConnector {

	private final String ipAdress;
	private final ArmRequestHandler armRequestHandler;
	private final StateCheckRequestHandler<?> stateCheckRequestHandler;
	private final FireChannelRequestHandler<?> fireChannelRequestHandler;
	private final CheckChannelStatesRequestHandler<?> checkChannelStatesRequestHandler;

	public ModuleConnector(String ipAdress, ArmRequestHandler armRequestHandler,
	    StateCheckRequestHandler<?> stateCheckRequestHandler, FireChannelRequestHandler<?> fireChannelRequestHandler,
	    CheckChannelStatesRequestHandler<?> checkChannelStatesRequestHandler) {
		this.ipAdress = ipAdress;
		this.armRequestHandler = armRequestHandler;
		this.stateCheckRequestHandler = stateCheckRequestHandler;
		this.fireChannelRequestHandler = fireChannelRequestHandler;
		this.checkChannelStatesRequestHandler = checkChannelStatesRequestHandler;
	}

	public void sendArmRequest() {
		new RequestTask(ipAdress, "arm", armRequestHandler).execute();
	}

	public void sendDisArmRequest() {
		new RequestTask(ipAdress, "disarm", armRequestHandler).execute();
	}

	public void sendStateCheckRequest() {
		new RequestTask(ipAdress, "state", stateCheckRequestHandler).execute();
	}

	public void fireChannel(int channelNumber) {
		new RequestTask(ipAdress, "fire", fireChannelRequestHandler).execute("channel=" + channelNumber);
	}

	public void checkChannelStates() {
		new RequestTask(ipAdress, "check", checkChannelStatesRequestHandler).execute();
	}
}