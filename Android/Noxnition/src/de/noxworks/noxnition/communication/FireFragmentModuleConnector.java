package de.noxworks.noxnition.communication;

import android.os.Handler;
import de.noxworks.noxnition.FireFragment;
import de.noxworks.noxnition.handler.ArmRequestHandler;
import de.noxworks.noxnition.handler.CheckChannelStatesRequestHandler;
import de.noxworks.noxnition.handler.FireChannelRequestHandler;
import de.noxworks.noxnition.handler.StateCheckRequestHandler;

public class FireFragmentModuleConnector extends ModuleConnector {

	public FireFragmentModuleConnector(FireFragment mainActivity, String ipAdress, Handler uiHandler) {
		super(ipAdress, new ArmRequestHandler(mainActivity, uiHandler),
		    new StateCheckRequestHandler(mainActivity, uiHandler),
		    new FireChannelRequestHandler<FireFragment>(mainActivity, uiHandler),
		    new CheckChannelStatesRequestHandler(mainActivity, uiHandler));
	}
}