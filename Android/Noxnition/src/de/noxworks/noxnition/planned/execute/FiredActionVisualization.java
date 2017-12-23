package de.noxworks.noxnition.planned.execute;

import android.graphics.Color;
import android.widget.TextView;
import de.noxworks.noxnition.model.FireAction;

public class FiredActionVisualization {

	final TextView runningModuleText;
	final TextView runningChannelText;
	final TextView runningTimeText;
	final TextView runningNameText;

	public FiredActionVisualization(TextView runningModuleText, TextView runningChannelText, TextView runningTimeText,
	    TextView runningNameText) {
		this.runningModuleText = runningModuleText;
		this.runningChannelText = runningChannelText;
		this.runningTimeText = runningTimeText;
		this.runningNameText = runningNameText;

		runningModuleText.setTextColor(Color.RED);
		runningChannelText.setTextColor(Color.RED);
		runningTimeText.setTextColor(Color.RED);
		runningNameText.setTextColor(Color.RED);
	}

	public void setFired(FireAction fireAction) {
		runningModuleText.setText(fireAction.getModule().getModuleConfig().getName());
		runningNameText.setText(fireAction.getName());
		runningChannelText.setText("Channel " + fireAction.getChannel());
	}
}