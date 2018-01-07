package de.noxworks.noxnition.direct.execute;

import java.util.Collection;

public interface IChannelStatesHandler {

	Collection<Integer> getChannels();

	void handleChannelState(int channel, boolean state);

}
