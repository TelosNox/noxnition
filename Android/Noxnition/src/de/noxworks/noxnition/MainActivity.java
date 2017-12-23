package de.noxworks.noxnition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import de.noxworks.noxnition.adapter.ModuleArrayAdapter;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.model.ModuleRequestResult;
import de.noxworks.noxnition.model.PlannedFirework;
import de.noxworks.noxnition.settings.ModuleConfig;
import de.noxworks.noxnition.settings.SettingsManager;
import de.noxworks.noxnition.tasks.ModuleQueryTask;

public class MainActivity extends BaseActivity {

	private static final int SERVERPORT = 2410;

	private static final int REMOVE_FROM_CONFIG = 1;
	private static final int ADD_TO_CONFIG = 2;

	private Handler updateConversationHandler;

	private ModuleArrayAdapter modulesAdapter;
	private List<IgnitionModule> ignitionModules;

	private Map<String, List<ModuleRequestResult>> moduleRequestResults = new HashMap<>();

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> moduleQuerySchedule;

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	private Button fireModulesButton;
	private Button customFireModulesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Noxnition");
		updateConversationHandler = new Handler();
		settingsManager = new SettingsManager(getSharedPreferences(PREFS_NAME, 0));

		ignitionModules = settingsManager.getIgnitionModules();

		initStartLayout();
	}

	@Override
	protected void onStart() {
		this.serverThread = new Thread(new ServerThread());
		this.serverThread.start();

		try {
			final InetAddress broadcast = getBroadcast();
			moduleQuerySchedule = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					queryModules(broadcast);
				}
			}, 0, 1000, TimeUnit.MILLISECONDS);
		} catch (SocketException e) {
			showMessage("can not determine broadcast ip");
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (serverThread != null) {
			serverThread.interrupt();
			serverThread = null;
		}
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (moduleQuerySchedule != null) {
			moduleQuerySchedule.cancel(false);
		}
		super.onStop();
	}

	private void initStartLayout() {
		setContentView(R.layout.start_layout);

		ListView configuredModulesList = (ListView) findViewById(R.id.moduleList);
		modulesAdapter = new ModuleArrayAdapter(this, ignitionModules);
		configuredModulesList.setAdapter(modulesAdapter);

		registerForContextMenu(configuredModulesList);

		fireModulesButton = (Button) findViewById(R.id.fireModulesButton);
		fireModulesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(MainActivity.this, FireActivity.class);
				ArrayList<IgnitionModule> onlineModules = new ArrayList<>();
				for (IgnitionModule ignitionModule : ignitionModules) {
					if (ignitionModule.getIpAddress() != null) {
						onlineModules.add(ignitionModule);
					}
				}
				nextScreen.putExtra(FireActivity.IGNITION_MODULES, onlineModules);
				startActivity(nextScreen);
			}
		});

		customFireModulesButton = (Button) findViewById(R.id.planFireworkButton);
		customFireModulesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.plan_firework_selection_dialog);
				dialog.setTitle("Feuerwerk auswählen");

				Button newButton = (Button) dialog.findViewById(R.id.planFireworkDialog_newButton);

				newButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						final Dialog nameDialog = new Dialog(MainActivity.this);
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

								startPlannedFireworkIntent(plannedFirework);
								nameDialog.dismiss();

							}
						});
						nameDialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
						nameDialog.show();
					}
				});

				ListView fireworksList = (ListView) dialog.findViewById(R.id.planFireworkDialog_list);
				List<PlannedFirework> plannedFireworks = settingsManager.getPlannedFireworks();
				final ArrayAdapter<PlannedFirework> adapter = new ArrayAdapter<>(MainActivity.this,
				    android.R.layout.simple_list_item_1, plannedFireworks);
				fireworksList.setAdapter(adapter);
				fireworksList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						PlannedFirework plannedFirework = adapter.getItem(position);
						startPlannedFireworkIntent(plannedFirework);
					}
				});

				dialog.show();
			}
		});

		initFireButton();
	}

	private void startPlannedFireworkIntent(PlannedFirework plannedFirework) {
		Intent nextScreen = new Intent(MainActivity.this, PlanFireworkActivity.class);
		ArrayList<IgnitionModule> planableModules = new ArrayList<>();
		for (IgnitionModule ignitionModule : ignitionModules) {
			if (ignitionModule.getModuleConfig().isConfigured()) {
				planableModules.add(ignitionModule);
			}
		}
		nextScreen.putExtra(PlanFireworkActivity.IGNITION_MODULES, planableModules);
		nextScreen.putExtra(PlanFireworkActivity.PLANNED_FIREWORK, plannedFirework.getName());
		startActivity(nextScreen);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		IgnitionModule ignitionModule = modulesAdapter.getItem(info.position);
		ModuleConfig moduleConfig = ignitionModule.getModuleConfig();
		if (moduleConfig.isConfigured()) {
			menu.add(Menu.NONE, REMOVE_FROM_CONFIG, Menu.NONE, "remove from config");
		} else {
			menu.add(Menu.NONE, ADD_TO_CONFIG, Menu.NONE, "add to config");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		IgnitionModule ignitionModule = modulesAdapter.getItem(info.position);
		ModuleConfig moduleConfig = ignitionModule.getModuleConfig();
		switch (item.getItemId()) {
		case REMOVE_FROM_CONFIG:
			moduleConfig.setConfigured(false);
			handleModuleListChanged();
			return true;
		case ADD_TO_CONFIG:
			moduleConfig.setConfigured(true);
			handleModuleListChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void queryModules(InetAddress broadcast) {
		final String requestId = UUID.randomUUID().toString();
		moduleRequestResults.put(requestId, new ArrayList<ModuleRequestResult>());

		scheduler.schedule(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				List<ModuleRequestResult> requestResults = moduleRequestResults.get(requestId);
				moduleRequestResults.remove(requestId);

				Map<String, ModuleRequestResult> resultMap = new HashMap<>();
				for (ModuleRequestResult moduleRequestResult : requestResults) {
					resultMap.put(moduleRequestResult.getName(), moduleRequestResult);
				}

				for (Iterator<IgnitionModule> iterator = ignitionModules.iterator(); iterator.hasNext();) {
					IgnitionModule ignitionModule = iterator.next();
					String moduleName = ignitionModule.getModuleConfig().getName();
					ModuleRequestResult moduleRequestResult = resultMap.get(moduleName);
					if (moduleRequestResult != null) {
						requestResults.remove(moduleRequestResult);
						String ipAddress = moduleRequestResult.getIpAddress();
						ignitionModule.setIpAddress(ipAddress);
					} else {
						if (System.currentTimeMillis() - ignitionModule.getLastUpdate() > 5000) {
							if (ignitionModule.getModuleConfig().isConfigured()) {
								ignitionModule.setIpAddress(null);
							} else {
								iterator.remove();
							}
						}
					}
				}

				for (ModuleRequestResult result : requestResults) {
					ModuleConfig moduleConfig = new ModuleConfig(result.getName(), result.getChannels(), false);
					IgnitionModule module = new IgnitionModule(moduleConfig);
					module.setIpAddress(result.getIpAddress());
					ignitionModules.add(module);
				}
				handleModuleListChanged();

				return null;
			}
		}, 500, TimeUnit.MILLISECONDS);
		final ModuleQueryTask moduleQueryTask = new ModuleQueryTask(broadcast);
		moduleQueryTask.execute(requestId);
	}

	private static InetAddress getBroadcast() throws SocketException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements();) {
			NetworkInterface ni = niEnum.nextElement();
			if (!ni.isLoopback() && ni.getDisplayName().contains("wlan0")) {
				for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {
					return interfaceAddress.getBroadcast();
				}
			}
		}
		return null;
	}

	private void handleModuleListChanged() {
		updateConversationHandler.post(new Runnable() {

			@Override
			public void run() {
				modulesAdapter.notifyDataSetChanged();
				initFireButton();
			}
		});
	}

	@Override
	public void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	void initFireButton() {
		boolean onlineModule = false;
		for (IgnitionModule ignitionModule : ignitionModules) {
			if (ignitionModule.getIpAddress() != null) {
				onlineModule = true;
				break;
			}
		}
		fireModulesButton.setEnabled(onlineModule);
	}

	private ServerSocket serverSocket;

	private Thread serverThread = null;

	private class ServerThread implements Runnable {

		@Override
		public void run() {
			Socket socket = null;
			try {
				serverSocket = new ServerSocket(SERVERPORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!Thread.currentThread().isInterrupted()) {
				try {
					socket = serverSocket.accept();

					CommunicationThread commThread = new CommunicationThread(socket);
					new Thread(commThread).start();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class CommunicationThread implements Runnable {

		private final Socket clientSocket;
		private BufferedReader input;

		public CommunicationThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
			try {
				this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			InetAddress inetAddress = clientSocket.getInetAddress();
			final String ipAddress = inetAddress.getHostAddress();
			Properties props = new Properties();
			try {
				props.load(input);
				final String name = props.getProperty("name");
				final String channels = props.getProperty("channels");
				final String request = props.getProperty("request");

				List<ModuleRequestResult> list = moduleRequestResults.get(request);
				if (list != null) {
					ModuleRequestResult result = new ModuleRequestResult(name, ipAddress, Integer.parseInt(channels));
					list.add(result);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}