package de.noxworks.noxnition.communication;

import de.noxworks.noxnition.direct.execute.FireFragment;
import de.noxworks.noxnition.handler.ArmRequestHandler;
import de.noxworks.noxnition.handler.CheckChannelStatesRequestHandler;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.handler.StateCheckRequestHandler;

public class FireFragmentModuleConnector extends ModuleConnector {

	public FireFragmentModuleConnector(FireFragment mainActivity, String ipAdress) {
		super(ipAdress, new ArmRequestHandler(mainActivity), new StateCheckRequestHandler<FireFragment>(mainActivity),
		    new FireChannelRequestHandler<FireFragment>(mainActivity),
		    new CheckChannelStatesRequestHandler<FireFragment>(mainActivity));
	}
}