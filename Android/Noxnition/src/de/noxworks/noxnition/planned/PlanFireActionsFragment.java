package de.noxworks.noxnition.planned;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import de.noxworks.noxnition.R;
import de.noxworks.noxnition.adapter.FireActionArrayAdapter;
import de.noxworks.noxnition.persistence.FireAction;
import de.noxworks.noxnition.persistence.FireTriggerGroup;
import de.noxworks.noxnition.persistence.PlannedFirework;
import de.noxworks.noxnition.planned.execute.ExecutePlannedFireworkActivity;

public class PlanFireActionsFragment extends Fragment {

	public static final String IGNITION_MODULES = "ignition_modules";
	public static final String PLANNED_FIREWORK = "planned_firework";
	private static final int DELETE_FIRE_ACTION = 1;

	private PlannedFirework plannedFirework;

	private FireActionArrayAdapter fireActionArrayAdapter;

	public PlanFireActionsFragment(PlannedFirework plannedFirework) {
		this.plannedFirework = plannedFirework;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.plan_firework_layout, container, false);
		setHasOptionsMenu(true);

		final Context context = container.getContext();

		TextView name = (TextView) rootView.findViewById(R.id.planFirework_name);
		name.setText(plannedFirework.getName());

		ListView plannedFiringList = (ListView) rootView.findViewById(R.id.plannedFireActionsList);
		fireActionArrayAdapter = new FireActionArrayAdapter(container.getContext(), plannedFirework.getFireActions());
		plannedFiringList.setAdapter(fireActionArrayAdapter);

		plannedFiringList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final FireAction fireAction = fireActionArrayAdapter.getItem(position);
				FragmentActivity activity = PlanFireActionsFragment.this.getActivity();
				final Dialog dialog = new Dialog(activity);
				dialog.setTitle("Edit fire action");
				dialog.setContentView(R.layout.fire_action_edit_dialog);
				TextView fireActionNameText = (TextView) dialog.findViewById(R.id.fireaction_name);
				fireActionNameText.setText(fireAction.getFireTriggerGroup().getName());
				final TextView delayText = (TextView) dialog.findViewById(R.id.fireaction_delay);
				delayText.setText(fireAction.getDelay() + "");
				delayText.setSelectAllOnFocus(true);

				Button okButton = (Button) dialog.findViewById(R.id.fireaction_Ok);
				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CharSequence text = delayText.getText();
						int delay = Integer.parseInt(text.toString());
						fireAction.setDelay(delay);
						fireActionArrayAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});

				Button upButton = (Button) dialog.findViewById(R.id.fireaction_Up);
				upButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<FireAction> fireActions = plannedFirework.getFireActions();
						int currentIndex = fireActions.indexOf(fireAction);
						if (currentIndex > 0) {
							fireActions.remove(fireAction);
							fireActions.add(currentIndex - 1, fireAction);
							fireActionArrayAdapter.notifyDataSetChanged();
						}
					}
				});

				Button downButton = (Button) dialog.findViewById(R.id.fireaction_Down);
				downButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<FireAction> fireActions = plannedFirework.getFireActions();
						int currentIndex = fireActions.indexOf(fireAction);
						if (currentIndex < fireActions.size() - 1) {
							fireActions.remove(fireAction);
							fireActions.add(currentIndex + 1, fireAction);
							fireActionArrayAdapter.notifyDataSetChanged();
						}
					}
				});

				Button deleteButton = (Button) dialog.findViewById(R.id.fireaction_Delete);
				deleteButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						plannedFirework.getFireActions().remove(fireAction);
						fireActionArrayAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});

		registerForContextMenu(plannedFiringList);

		Button startButton = (Button) rootView.findViewById(R.id.planFirework_start);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(context, ExecutePlannedFireworkActivity.class);
				nextScreen.putExtra(ExecutePlannedFireworkActivity.PLANNED_FIREWORK, plannedFirework.getName());
				startActivity(nextScreen);
			}
		});
		fireActionArrayAdapter.notifyDataSetChanged();
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.plan_firework_menu, menu);
		MenuItem addMenuItem = menu.findItem(R.id.planFirework_add);
		addMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				FragmentActivity activity = PlanFireActionsFragment.this.getActivity();
				final Dialog dialog = new Dialog(activity);
				dialog.setTitle("Add fire trigger");
				dialog.setContentView(R.layout.add_firetrigger_layout);
				ListView listView = (ListView) dialog.findViewById(R.id.addFireTriggersList);
				final AddFireTriggerGroupArrayAdapter adapter = new AddFireTriggerGroupArrayAdapter(activity,
				    plannedFirework.getFireTriggerGroups());
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						FireTriggerGroup item = adapter.getItem(position);
						FireAction fireAction = new FireAction(item);
						plannedFirework.getFireActions().add(fireAction);
						fireActionArrayAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});
				dialog.show();
				return true;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, DELETE_FIRE_ACTION, Menu.NONE, "delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (!getUserVisibleHint()) {
			return super.onContextItemSelected(item);
		}
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final FireAction fireAction = fireActionArrayAdapter.getItem(info.position);
		switch (item.getItemId()) {
		case DELETE_FIRE_ACTION:
			plannedFirework.getFireActions().remove(fireAction);
			fireActionArrayAdapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}