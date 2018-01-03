package de.noxworks.noxnition.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.FireAction;

public class FireActionArrayAdapter extends BaseAdapter {

	private final List<FireAction> fireActions;
	private LayoutInflater inflater = null;

	public FireActionArrayAdapter(Context context, List<FireAction> fireActions) {
		this.fireActions = fireActions;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return fireActions.size();
	}

	@Override
	public FireAction getItem(int position) {
		return fireActions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.fireaction_row_layout, null);
		}
		FireAction fireAction = fireActions.get(position);

		TextView moduleText = (TextView) vi.findViewById(R.id.fireactionRow_module);
		moduleText.setText(fireAction.getModule().getModuleConfig().getName());
		TextView channelText = (TextView) vi.findViewById(R.id.fireactionRow_channel);
		TextView timeText = (TextView) vi.findViewById(R.id.fireactionRow_time);
		TextView nameText = (TextView) vi.findViewById(R.id.fireactionRow_name);
		nameText.setText(fireAction.getName());

		channelText.setText("Channel " + fireAction.getChannel());
		channelText.setTextColor(Color.GRAY);
		String delayText = fireAction.getDelay() + " sec";
		timeText.setText(delayText);
		timeText.setTextColor(Color.GRAY);
		return vi;
	}
}