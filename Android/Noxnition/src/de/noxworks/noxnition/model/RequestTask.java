package de.noxworks.noxnition.model;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import android.os.AsyncTask;
import de.noxworks.noxnition.handler.IRequestHandler;

public class RequestTask extends AsyncTask<String, Integer, Properties> {

	private final String ipAddress;

	private final IRequestHandler requestHandler;
	private final String action;

	public RequestTask(String ipAddress, String action, IRequestHandler requestHandler) {
		this.ipAddress = ipAddress;
		this.action = action;
		this.requestHandler = requestHandler;
	}

	@Override
	protected Properties doInBackground(String... params) {
		int TIMEOUT_VALUE = 1000;
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
			if (requestHandler != null) {
				requestHandler.preRequest();
			}
			long send = System.currentTimeMillis();
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(TIMEOUT_VALUE);
			urlConnection.setReadTimeout(TIMEOUT_VALUE);
			InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
			props.load(inputStream);
			inputStream.close();
			long duration = System.currentTimeMillis() - send;
			System.out.println(duration + "ms - " + urlString);
		} catch (Exception e) {
			e.printStackTrace();
			if (requestHandler != null) {
				requestHandler.requestFailed();
			}
			return null;
		} finally {
			urlConnection.disconnect();
		}
		if (requestHandler != null && props != null) {
			requestHandler.requestSuccess(props);
		}
		return props;
	}
}