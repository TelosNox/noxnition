package de.noxworks.noxnition.planned.execute;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.noxworks.noxnition.IMessageable;
import de.noxworks.noxnition.IStateCheckResultHandler;
import de.noxworks.noxnition.communication.StateCheckResult;

public class StateCheckResultHandler implements IStateCheckResultHandler, IMessageable {

	private final CountDownLatch latch = new CountDownLatch(1);
	private StateCheckResult result = null;

	@Override
	public void handleStateCheckResult(StateCheckResult result) {
		this.result = result;
		latch.countDown();
	}

	public StateCheckResult getResult() {
		try {
			latch.await(2000, TimeUnit.MILLISECONDS);
			return result;
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public void showMessage(String message) {
	}
}