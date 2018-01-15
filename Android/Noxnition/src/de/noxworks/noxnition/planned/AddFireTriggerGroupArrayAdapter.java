package de.noxworks.noxnition.planned;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.FireTriggerGroup;

public class AddFireTriggerGroupArrayAdapter extends BaseAdapter {

	private final List<FireTriggerGroup> fireTriggerGroups;
	private LayoutInflater inflater = null;

	public AddFireTriggerGroupArrayAdapter(Context context, List<FireTriggerGroup> fireTriggerGroups) {
		this.fireTriggerGroups = fireTriggerGroups;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return fireTriggerGroups.size();
	}

	@Override
	public FireTriggerGroup getItem(int position) {
		return fireTriggerGroups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.firetriggergroup_row_layout, null);
		}
		FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(position);

		TextView triggerGroupText = (TextView) vi.findViewById(R.id.fireTriggerGroupRow_name);
		triggerGroupText.setText(fireTriggerGroup.getName());
		return vi;
	}
}