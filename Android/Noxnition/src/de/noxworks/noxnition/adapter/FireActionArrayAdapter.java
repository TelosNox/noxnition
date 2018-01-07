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

		TextView triggerText = (TextView) vi.findViewById(R.id.fireactionTrigger_name);
		triggerText.setText(fireAction.getFireTriggerGroup().getName());
		TextView timeText = (TextView) vi.findViewById(R.id.fireactionRow_time);
		String delayText = fireAction.getDelay() + " sec";
		timeText.setText(delayText);
		timeText.setTextColor(Color.GRAY);
		return vi;
	}
}