package de.noxworks.noxnition.communication;

import de.noxworks.noxnition.handler.ArmRequestHandler;
import de.noxworks.noxnition.handler.CheckChannelStatesRequestHandler;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.handler.StateCheckRequestHandler;
import de.noxworks.noxnition.model.RequestTask;

public class ModuleConnector {

	private final String ipAdress;
	private final ArmRequestHandler armRequestHandler;
	private final StateCheckRequestHandler stateCheckRequestHandler;
	private final FireChannelRequestHandler<?> fireChannelRequestHandler;
	private final CheckChannelStatesRequestHandler checkChannelStatesRequestHandler;

	public ModuleConnector(String ipAdress, ArmRequestHandler armRequestHandler,
	    StateCheckRequestHandler stateCheckRequestHandler, FireChannelRequestHandler<?> fireChannelRequestHandler,
	    CheckChannelStatesRequestHandler checkChannelStatesRequestHandler) {
		this.ipAdress = ipAdress;
		this.armRequestHandler = armRequestHandler;
		this.stateCheckRequestHandler = stateCheckRequestHandler;
		this.fireChannelRequestHandler = fireChannelRequestHandler;
		this.checkChannelStatesRequestHandler = checkChannelStatesRequestHandler;
	}

	public void sendArmRequest() {
		execute(new RequestTask(ipAdress, "arm", armRequestHandler));
	}

	public void sendDisArmRequest() {
		execute(new RequestTask(ipAdress, "disarm", armRequestHandler));
	}

	public void sendStateCheckRequest() {
		execute(new RequestTask(ipAdress, "state", stateCheckRequestHandler));
	}

	public void fireChannel(int channelNumber) {
		execute(new RequestTask(ipAdress, "fire", fireChannelRequestHandler), "channel=" + channelNumber);
	}

	public void checkChannelStates() {
		execute(new RequestTask(ipAdress, "check", checkChannelStatesRequestHandler));
	}

	private void execute(final RequestTask task, final String... params) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				task.execute(params);
			}
		};
		new Thread(runnable).start();
	}
}