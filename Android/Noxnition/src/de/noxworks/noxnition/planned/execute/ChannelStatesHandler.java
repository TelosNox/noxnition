package de.noxworks.noxnition.planned.execute;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.direct.execute.IChannelStatesHandler;

public class ChannelStatesHandler implements IChannelStatesHandler, IMessageable {

	private final Collection<Integer> channels;

	private final Map<Integer, Boolean> channelStates = new HashMap<>();

	private final CountDownLatch latch;

	public ChannelStatesHandler(Collection<Integer> channels) {
		this.channels = channels;
		this.latch = new CountDownLatch(channels.size());
	}

	@Override
	public void showMessage(String message) {
	}

	@Override
	public Collection<Integer> getChannels() {
		return channels;
	}

	public Map<Integer, Boolean> getChannelStates() {
		try {
			if (latch.await(2000, TimeUnit.MILLISECONDS)) {
				return channelStates;
			}
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public void handleChannelState(int channel, boolean state) {
		channelStates.put(channel, state);
		latch.countDown();
	}
}