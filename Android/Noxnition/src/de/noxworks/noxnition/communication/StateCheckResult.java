package de.noxworks.noxnition.communication;

public class StateCheckResult extends RequestResult {

	private final String voltage;
	private final boolean armed;
	private final long answerTime;

	public StateCheckResult(boolean success, String voltage, boolean armed, long answerTime) {
		super(success);
		this.voltage = voltage;
		this.armed = armed;
		this.answerTime = answerTime;
	}

	public String getVoltage() {
		return voltage;
	}

	public boolean isArmed() {
		return armed;
	}

	public long getAnswerTime() {
		return answerTime;
	}
}