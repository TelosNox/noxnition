package de.noxworks.noxnition.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import de.noxworks.noxnition.handler.IRequestHandler;

public class RequestTask {

	private final String ipAddress;

	private final IRequestHandler requestHandler;
	private final String action;

	public RequestTask(String ipAddress, String action, IRequestHandler requestHandler) {
		this.ipAddress = ipAddress;
		this.action = action;
		this.requestHandler = requestHandler;
	}

	public void execute(String... params) {
		if (requestHandler != null) {
			requestHandler.preRequest();
		}

		int TIMEOUT_VALUE = 500;
		Properties props = new Properties();
		HttpURLConnection urlConnection = null;
		try {
			String urlString = "http://" + ipAddress + "/" + action;
			StringBuilder paramBuilder = new StringBuilder();
			for (String param : params) {
				paramBuilder.append("&").append(param);
			}
			if (paramBuilder.length() > 0) {
				paramBuilder.replace(0, 1, "?");
				urlString += paramBuilder.toString();
			}
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(TIMEOUT_VALUE);
			urlConnection.setReadTimeout(TIMEOUT_VALUE);
			InputStream inputStream = urlConnection.getInputStream();
			props.load(inputStream);
		} catch (Exception e) {
			if (requestHandler != null) {
				requestHandler.requestFailed();
			}
			return;
		} finally {
			urlConnection.disconnect();
		}
		if (requestHandler != null) {
			requestHandler.requestSuccess(props);
		}
	}
}