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
import de.noxworks.noxnition.model.IgnitionModule;

public class ModuleArrayAdapter extends BaseAdapter {

	private final List<IgnitionModule> ignitionModules;
	private LayoutInflater inflater = null;

	public ModuleArrayAdapter(Context context, List<IgnitionModule> ignitionModules) {
		this.ignitionModules = ignitionModules;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return ignitionModules.size();
	}

	@Override
	public IgnitionModule getItem(int position) {
		return ignitionModules.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.module_row_layout, null);
		}
		IgnitionModule ignitionModule = ignitionModules.get(position);

		TextView moduleText = (TextView) vi.findViewById(R.id.moduleRow_module);
		moduleText.setText(ignitionModule.getModuleConfig().getName());
		TextView stateText = (TextView) vi.findViewById(R.id.moduleRow_state);
		TextView channelText = (TextView) vi.findViewById(R.id.moduleRow_channels);
		channelText.setText("Channels: " + ignitionModule.getModuleConfig().getChannels());
		channelText.setTextColor(Color.GRAY);
		TextView configuredText = (TextView) vi.findViewById(R.id.moduleRow_configured);
		String configured = "adHoc";
		int configColor = Color.GRAY;
		if (ignitionModule.getModuleConfig().isConfigured()) {
			configured = "configured";
			configColor = parent.getResources().getColor(android.R.color.holo_blue_dark);
		}
		configuredText.setText(configured);
		configuredText.setTextColor(configColor);

		boolean online = ignitionModule.getIpAddress() != null;
		int onlineColor = Color.GRAY;
		int moduleColor = Color.GRAY;
		String stateString = "offline";
		if (online) {
			stateString = "online";
			onlineColor = parent.getResources().getColor(android.R.color.holo_blue_dark);
			moduleColor = Color.BLACK;
		}
		stateText.setText(stateString);
		stateText.setTextColor(onlineColor);
		moduleText.setTextColor(moduleColor);
		return vi;
	}
}