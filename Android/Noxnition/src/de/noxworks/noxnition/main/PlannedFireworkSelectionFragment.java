package de.noxworks.noxnition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.persistence.PlannedFirework;
import de.noxworks.noxnition.persistence.SettingsManager;

public class PlanFireworkFragment extends Fragment {

	private static final int DELETE_PLANNED_FIREWORK = 1;
	private static final int RENAME_PLANNED_FIREWORK = 2;

	private final SettingsManager settingsManager;
	private ArrayAdapter<PlannedFirework> plannedFireworksAdapter;
	private Handler updateConversationHandler;

	public PlanFireworkFragment(SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateConversationHandler = new Handler();
		View rootView = inflater.inflate(R.layout.plan_firework_selection_fragment, container, false);

		ListView fireworksList = (ListView) rootView.findViewById(R.id.planFireworkSelection_list);
		registerForContextMenu(fireworksList);
		sortPlannedFireworks();
		List<PlannedFirework> plannedFireworks = settingsManager.getPlannedFireworks();
		final Context context = container.getContext();
		plannedFireworksAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, plannedFireworks);
		fireworksList.setAdapter(plannedFireworksAdapter);
		fireworksList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PlannedFirework plannedFirework = plannedFireworksAdapter.getItem(position);
				startPlannedFireworkIntent(plannedFirework, context);
			}
		});

		Button newButton = (Button) rootView.findViewById(R.id.planFireworkSelection_newButton);

		newButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog nameDialog = new Dialog(context);
				nameDialog.setTitle("Name");
				nameDialog.setContentView(R.layout.plan_firework_name_dialog);
				final EditText nameEdit = (EditText) nameDialog.findViewById(R.id.planFireworkDialog_name);
				Button okButton = (Button) nameDialog.findViewById(R.id.planFireworkDialog_okButton);

				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String name = nameEdit.getText().toString();
						if (name.equals("")) {
							return;
						}
						List<PlannedFirework> plannedFireworks = settingsManager.getPlannedFireworks();
						for (PlannedFirework plannedFirework : plannedFireworks) {
							if (plannedFirework.getName().equals(name)) {
								return;
							}
						}

						PlannedFirework plannedFirework = new PlannedFirework(name);
						plannedFireworks.add(plannedFirework);
						sortPlannedFireworks();
						nameDialog.dismiss();
					}
				});
				nameDialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				nameDialog.show();
			}
		});

		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, RENAME_PLANNED_FIREWORK, Menu.NONE, "rename");
		menu.add(Menu.NONE, DELETE_PLANNED_FIREWORK, Menu.NONE, "delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (!getUserVisibleHint()) {
			return super.onContextItemSelected(item);
		}
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final PlannedFirework plannedFirework = plannedFireworksAdapter.getItem(info.position);
		switch (item.getItemId()) {
		case DELETE_PLANNED_FIREWORK:
			settingsManager.getPlannedFireworks().remove(plannedFirework);
			handleFireworkListChanged();
			return true;
		case RENAME_PLANNED_FIREWORK:
			FragmentActivity activity = PlanFireworkFragment.this.getActivity();
			final Dialog nameDialog = new Dialog(activity);
			nameDialog.setTitle("Name");
			nameDialog.setContentView(R.layout.plan_firework_name_dialog);
			final EditText nameEdit = (EditText) nameDialog.findViewById(R.id.planFireworkDialog_name);
			nameEdit.setText(plannedFirework.getName());
			Button okButton = (Button) nameDialog.findViewById(R.id.planFireworkDialog_okButton);

			okButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String name = nameEdit.getText().toString();
					if (name.equals("")) {
						return;
					}
					List<PlannedFirework> plannedFireworks = settingsManager.getPlannedFireworks();
					for (PlannedFirework plannedFirework : plannedFireworks) {
						if (plannedFirework.getName().equals(name)) {
							return;
						}
					}

					plannedFirework.setName(name);
					sortPlannedFireworks();
					handleFireworkListChanged();
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

	private void sortPlannedFireworks() {
		Collections.sort(settingsManager.getPlannedFireworks(), new Comparator<PlannedFirework>() {

			@Override
			public int compare(PlannedFirework lhs, PlannedFirework rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});
	}

	private void handleFireworkListChanged() {
		updateConversationHandler.post(new Runnable() {

			@Override
			public void run() {
				plannedFireworksAdapter.notifyDataSetChanged();
			}
		});
	}

	private void startPlannedFireworkIntent(PlannedFirework plannedFirework, Context context) {
		Intent nextScreen = new Intent(context, PlanFireworkActivity.class);
		ArrayList<IgnitionModule> planableModules = new ArrayList<>();
		for (IgnitionModule ignitionModule : settingsManager.getIgnitionModules()) {
			if (ignitionModule.getModuleConfig().isConfigured()) {
				planableModules.add(ignitionModule);
			}
		}
		nextScreen.putExtra(PlanFireworkActivity.IGNITION_MODULES, planableModules);
		nextScreen.putExtra(PlanFireworkActivity.PLANNED_FIREWORK, plannedFirework.getName());
		startActivity(nextScreen);
	}
}