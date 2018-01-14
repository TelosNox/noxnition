package de.noxworks.noxnition.planned;

import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupClickListener;
import de.noxworks.noxnition.BaseFragment;
import de.noxworks.noxnition.IntentHelper;
import de.noxworks.noxnition.NamedElementComparator;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.persistence.FireTriggerGroup;
import de.noxworks.noxnition.persistence.PlannedFirework;

public class PlanFireTriggersFragment extends BaseFragment {

	private static final int DELETE_TRIGGER_GROUP = 1;
	private static final int RENAME_TRIGGER_GROUP = 2;

	private PlannedFirework plannedFirework;
	private MenuItem addMenuItem;
	private List<FireTriggerGroup> triggerGroups;
	private FireTriggerGroupArrayAdapter adapter;

	public static PlanFireTriggersFragment newInstance(PlannedFirework plannedFirework) {
		PlanFireTriggersFragment fragment = new PlanFireTriggersFragment();
		Bundle bundle = new Bundle(1);
		bundle.putString(IntentHelper.PLANNED_FIREWORK_ID, plannedFirework.getId());
		IntentHelper.add(plannedFirework);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String plannedFireworkId = getArguments().getString(IntentHelper.PLANNED_FIREWORK_ID);
		plannedFirework = (PlannedFirework) IntentHelper.get(plannedFireworkId);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.plan_firetriggers_layout, container, false);
		final Context context = container.getContext();
		ExpandableListView plannedTriggerList = (ExpandableListView) rootView.findViewById(R.id.plannedFireTriggersList);
		registerForContextMenu(plannedTriggerList);
		triggerGroups = plannedFirework.getFireTriggerGroups();
		Collections.sort(triggerGroups, NamedElementComparator.getInstance());

		adapter = new FireTriggerGroupArrayAdapter(context, triggerGroups);
		plannedTriggerList.setAdapter(adapter);

		plannedTriggerList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Intent nextScreen = new Intent(context, ChannelSelectionActivity.class);
				nextScreen.putExtra(ChannelSelectionActivity.PLANNED_FIREWORK, plannedFirework.getName());
				nextScreen.putExtra(ChannelSelectionActivity.FIRE_TRIGGER_GROUP_INDEX, groupPosition);
				startActivity(nextScreen);
				return true;
			}
		});
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.plan_firework_menu, menu);
		addMenuItem = menu.findItem(R.id.planFirework_add);
		addMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				final Dialog nameDialog = new Dialog(PlanFireTriggersFragment.this.getActivity());
				nameDialog.setTitle("Name");
				nameDialog.setContentView(R.layout.name_change_dialog);
				final EditText nameEdit = (EditText) nameDialog.findViewById(R.id.nameChangeDialog_name);
				Button okButton = (Button) nameDialog.findViewById(R.id.nameChangeDialog_okButton);

				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String name = nameEdit.getText().toString();
						if (name.equals("")) {
							return;
						}
						FireTriggerGroup triggerGroup = new FireTriggerGroup(name);
						triggerGroups.add(triggerGroup);
						Collections.sort(triggerGroups, NamedElementComparator.getInstance());
						adapter.notifyDataSetChanged();
						nameDialog.dismiss();
					}
				});
				nameDialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				nameDialog.show();
				return true;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, RENAME_TRIGGER_GROUP, Menu.NONE, "rename");
		menu.add(Menu.NONE, DELETE_TRIGGER_GROUP, Menu.NONE, "delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (!getUserVisibleHint()) {
			return super.onContextItemSelected(item);
		}
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
		int position = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		final FireTriggerGroup triggerGroup = adapter.getGroup(position);
		switch (item.getItemId()) {
		case DELETE_TRIGGER_GROUP:
			triggerGroups.remove(triggerGroup);
			adapter.notifyDataSetChanged();
			return true;
		case RENAME_TRIGGER_GROUP:
			FragmentActivity activity = PlanFireTriggersFragment.this.getActivity();
			final Dialog nameDialog = new Dialog(activity);
			nameDialog.setTitle("Name");
			nameDialog.setContentView(R.layout.name_change_dialog);
			final EditText nameEdit = (EditText) nameDialog.findViewById(R.id.nameChangeDialog_name);
			nameEdit.setText(triggerGroup.getName());
			Button okButton = (Button) nameDialog.findViewById(R.id.nameChangeDialog_okButton);

			okButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String name = nameEdit.getText().toString();
					if (name.equals("")) {
						return;
					}
					for (FireTriggerGroup triggerGroup : triggerGroups) {
						if (triggerGroup.getName().equals(name)) {
							return;
						}
					}

					triggerGroup.setName(name);
					Collections.sort(triggerGroups, NamedElementComparator.getInstance());
					adapter.notifyDataSetChanged();
					nameDialog.dismiss();
				}
			});
			nameDialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			nameDialog.show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}