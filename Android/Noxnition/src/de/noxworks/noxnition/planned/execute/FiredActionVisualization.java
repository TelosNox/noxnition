package de.noxworks.noxnition.planned.execute;

import android.graphics.Color;
import android.widget.TextView;
import de.noxworks.noxnition.persistence.FireAction;

public class FiredActionVisualization {

	final TextView runningNameText;

	public FiredActionVisualization(TextView runningNameText) {
		this.runningNameText = runningNameText;

		runningNameText.setTextColor(Color.RED);
	}

	public void setFired(FireAction fireAction) {
		runningNameText.setText(fireAction.getFireTriggerGroup().getName());
	}
}