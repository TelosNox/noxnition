package de.noxworks.noxnition.planned;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.FireTrigger;
import de.noxworks.noxnition.persistence.FireTriggerGroup;

public class FireTriggerGroupArrayAdapter extends BaseExpandableListAdapter {

	private final List<FireTriggerGroup> fireTriggerGroups;
	private LayoutInflater inflater = null;

	public FireTriggerGroupArrayAdapter(Context context, List<FireTriggerGroup> fireTriggerGroups) {
		this.fireTriggerGroups = fireTriggerGroups;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {
		return fireTriggerGroups.size();
	}

	@Override
	public FireTriggerGroup getGroup(int position) {
		return fireTriggerGroups.get(position);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.firetriggergroup_row_layout, null);
		}
		FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(groupPosition);

		TextView triggerGroupText = (TextView) vi.findViewById(R.id.fireTriggerGroupRow_name);
		triggerGroupText.setText(fireTriggerGroup.getName());
		ExpandableListView eLV = (ExpandableListView) parent;
		eLV.expandGroup(groupPosition);
		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(groupPosition);
		return fireTriggerGroup.getFireTriggers().size();
	}

	@Override
	public FireTrigger getChild(int groupPosition, int childPosition) {
		FireTriggerGroup fireTriggerGroup = fireTriggerGroups.get(groupPosition);
		return fireTriggerGroup.getFireTriggers().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
	    ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.firetrigger_row_layout, null);
		}
		FireTrigger child = getChild(groupPosition, childPosition);
		String moduleName = child.getModule().getModuleConfig().getName();
		TextView moduleNameText = (TextView) vi.findViewById(R.id.fireTrigger_module);
		moduleNameText.setText(moduleName);
		List<Integer> channels = new ArrayList<>(child.getChannels());
		Collections.sort(channels);
		StringBuilder s = new StringBuilder();
		for (Iterator<Integer> i = channels.iterator(); i.hasNext();) {
			s.append(i.next());
			if (i.hasNext())
				s.append(",");
		}
		String channelsString = s.toString();
		TextView channelText = (TextView) vi.findViewById(R.id.fireTrigger_channel);
		channelText.setText(channelsString);
		return vi;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}