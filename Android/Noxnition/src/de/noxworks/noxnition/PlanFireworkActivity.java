package de.noxworks.noxnition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import de.noxworks.noxnition.adapter.FireActionArrayAdapter;
import de.noxworks.noxnition.model.FireAction;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.model.PlannedFirework;
import de.noxworks.noxnition.settings.ModuleConfig;

public class PlanFireworkActivity extends BaseActivity {

	public static final String IGNITION_MODULES = "ignition_modules";
	public static final String PLANNED_FIREWORK = "planned_firework";

	private List<IgnitionModule> ignitionModules = new ArrayList<>();
	private PlannedFirework plannedFirework;

	private ChannelSelectionFragmentSectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private boolean selection = false;

	private MenuItem addMenuItem;

	private FireActionArrayAdapter fireActionArrayAdapter;

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Feuerwerk planen");
		Intent intent = getIntent();
		if (intent != null) {
			Serializable serializable = intent.getSerializableExtra(IGNITION_MODULES);
			if (serializable != null) {
				@SuppressWarnings("unchecked")
				ArrayList<IgnitionModule> list = (ArrayList<IgnitionModule>) serializable;
				ignitionModules.addAll(list);
			}
			String plannedFireworkName = intent.getStringExtra(PLANNED_FIREWORK);
			for (PlannedFirework plannedFirework : settingsManager.getPlannedFireworks()) {
				if (plannedFirework.getName().equals(plannedFireworkName)) {
					this.plannedFirework = plannedFirework;
					break;
				}
			}
		}

		initMainLayout();
	}

	private void initMainLayout() {
		selection = false;
		initMenu();
		setContentView(R.layout.plan_firework_layout);

		TextView name = (TextView) findViewById(R.id.planFirework_name);
		name.setText(plannedFirework.getName());

		ListView plannedFiringList = (ListView) findViewById(R.id.plannedFiringList);
		fireActionArrayAdapter = new FireActionArrayAdapter(this, plannedFirework.getFireActions());
		plannedFiringList.setAdapter(fireActionArrayAdapter);

		plannedFiringList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {

				final FireAction fireAction = fireActionArrayAdapter.getItem(position);

				final Dialog dialog = new Dialog(PlanFireworkActivity.this);
				dialog.setContentView(R.layout.fire_action_edit_dialog);
				dialog.setTitle("Edit");

				final EditText fireActionName = (EditText) dialog.findViewById(R.id.fireaction_name);
				fireActionName.setText(fireAction.getName());
				fireActionName.setSelectAllOnFocus(true);

				final List<Integer> channels = new ArrayList<>();
				for (int i = 1; i <= fireAction.getModule().getModuleConfig().getChannels(); i++) {
					channels.add(i);
				}
				ArrayAdapter<Integer> channelAdapter = new ArrayAdapter<>(PlanFireworkActivity.this,
				    android.R.layout.simple_list_item_1, channels);
				final Spinner channelNumberSpinner = (Spinner) dialog.findViewById(R.id.fireaction_channelSpinner);
				channelNumberSpinner.setAdapter(channelAdapter);
				channelNumberSpinner.setSelection(fireAction.getChannel() - 1);

				final Spinner moduleNameSpinner = (Spinner) dialog.findViewById(R.id.fireaction_moduleSpinner);
				final ArrayAdapter<IgnitionModule> adapter = new ArrayAdapter<>(PlanFireworkActivity.this,
				    android.R.layout.simple_list_item_1, ignitionModules);
				moduleNameSpinner.setAdapter(adapter);
				IgnitionModule module = fireAction.getModule();
				moduleNameSpinner.setSelection(ignitionModules.indexOf(module));

				final EditText fireActionDelay = (EditText) dialog.findViewById(R.id.fireaction_delay);
				fireActionDelay.setText("" + fireAction.getDelay());
				fireActionDelay.setSelectAllOnFocus(true);

				moduleNameSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						Object selectedItem = channelNumberSpinner.getSelectedItem();
						int channelNumber = 1;
						if (selectedItem != null) {
							String channelNumberString = selectedItem.toString();
							if (!channelNumberString.isEmpty()) {
								channelNumber = Integer.parseInt(channelNumberString);
							}
						}
						IgnitionModule module = adapter.getItem(position);
						ModuleConfig moduleConfig = module.getModuleConfig();
						channels.clear();
						for (int i = 1; i <= moduleConfig.getChannels(); i++) {
							channels.add(i);
						}
						if (channelNumber > moduleConfig.getChannels()) {
							channelNumber = 1;
						}
						channelNumber--;
						channelNumberSpinner.setSelection(channelNumber);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

				Button dialogButtonOk = (Button) dialog.findViewById(R.id.fireaction_Ok);
				dialogButtonOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = fireActionName.getText().toString();
						IgnitionModule module = (IgnitionModule) moduleNameSpinner.getSelectedItem();
						String channelNumberString = channelNumberSpinner.getSelectedItem().toString();
						int channelNumber = 0;
						if (!channelNumberString.isEmpty()) {
							channelNumber = Integer.parseInt(channelNumberString);
						}
						String delayText = fireActionDelay.getText().toString();
						int delay = 0;
						if (!delayText.isEmpty()) {
							delay = Integer.parseInt(delayText);
						}

						fireAction.setName(name);
						fireAction.setChannel(channelNumber);
						fireAction.setModule(module);
						fireAction.setDelay(delay);

						fireActionArrayAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});

				Button dialogButtonDel = (Button) dialog.findViewById(R.id.fireaction_Delete);
				dialogButtonDel.setOnClickListener(new OnClickListener() {

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

		Button startButton = (Button) findViewById(R.id.planFirework_start);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(PlanFireworkActivity.this, ExecutePlannedFireworkActivity.class);
				ArrayList<IgnitionModule> nextModules = new ArrayList<>(ignitionModules);
				nextScreen.putExtra(ExecutePlannedFireworkActivity.IGNITION_MODULES, nextModules);
				ArrayList<FireAction> nextFireActions = new ArrayList<>(plannedFirework.getFireActions());
				nextScreen.putExtra(ExecutePlannedFireworkActivity.FIRE_ACTIONS, nextFireActions);
				startActivity(nextScreen);
			}
		});
		fireActionArrayAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.plan_firework_menu, menu);
		addMenuItem = menu.findItem(R.id.planFirework_add);
		addMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				initSelectionLayout();
				return true;
			}
		});
		initMenu();
		return true;
	}

	public void addFireAction(FireAction fireAction) {
		plannedFirework.getFireActions().add(fireAction);
		initMainLayout();
	}

	private void initMenu() {
		if (addMenuItem != null) {
			addMenuItem.setVisible(!selection);
		}
	}

	private void initSelectionLayout() {
		selection = true;
		initMenu();
		setContentView(R.layout.module_fragment_layout);

		mSectionsPagerAdapter = new ChannelSelectionFragmentSectionsPagerAdapter(getSupportFragmentManager(), this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public void onBackPressed() {
		if (selection) {
			initMainLayout();
		} else {
			super.onBackPressed();
		}
	}
}