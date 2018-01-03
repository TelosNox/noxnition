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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.noxworks.noxnition.adapter.ModuleArrayAdapter;
import de.noxworks.noxnition.model.IgnitionModule;
import de.noxworks.noxnition.model.ModuleRequestResult;
import de.noxworks.noxnition.persistence.ModuleConfig;
import de.noxworks.noxnition.persistence.SettingsManager;
import de.noxworks.noxnition.tasks.ModuleQueryTask;

public class ModuleFragment extends Fragment {

	private static final int SERVERPORT = 2410;

	private static final int REMOVE_FROM_CONFIG = 1;
	private static final int ADD_TO_CONFIG = 2;

	private Handler updateConversationHandler;

	private ModuleArrayAdapter modulesAdapter;
	private List<IgnitionModule> ignitionModules;

	private Map<String, List<ModuleRequestResult>> moduleRequestResults = new HashMap<>();

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> moduleQuerySchedule;

	private final SettingsManager settingsManager;

	public ModuleFragment(SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
	}

	public static ModuleFragment newInstance(SettingsManager settingsManager) {
		ModuleFragment fragment = new ModuleFragment(settingsManager);
		return fragment;
	}

	public List<IgnitionModule> getIgnitionModules() {
		return ignitionModules;
	}

	private Button fireModulesButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateConversationHandler = new Handler();

		ignitionModules = settingsManager.getIgnitionModules();

		return initStartLayout(inflater, container);
	}

	@Override
	public void onStart() {
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
	public void onStop() {
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

	private View initStartLayout(LayoutInflater inflater, final ViewGroup container) {
		View rootView = inflater.inflate(R.layout.start_layout, container, false);

		configuredModulesList = (ListView) rootView.findViewById(R.id.moduleList);
		modulesAdapter = new ModuleArrayAdapter(inflater, ignitionModules);
		configuredModulesList.setAdapter(modulesAdapter);

		registerForContextMenu(configuredModulesList);

		fireModulesButton = (Button) rootView.findViewById(R.id.fireModulesButton);
		fireModulesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(container.getContext(), FireActivity.class);
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

		initFireButton();

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unregisterForContextMenu(configuredModulesList);
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
		if (!getUserVisibleHint()) {
			return super.onContextItemSelected(item);
		}
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

	private void showMessage(String message) {
		Context context = getActivity().getApplicationContext();
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

	private ListView configuredModulesList;

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
					if (!serverSocket.isClosed()) {
						e.printStackTrace();
					}
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