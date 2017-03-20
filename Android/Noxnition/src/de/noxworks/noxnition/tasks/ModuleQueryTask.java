package de.noxworks.noxnition.tasks;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.AsyncTask;

public class ModuleQueryTask extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {
		String messageStr = params[0];
		int server_port = 2390; // port that I’m using
		try (DatagramSocket s = new DatagramSocket()) {
			InetAddress local = InetAddress.getByName("255.255.255.255");
			int msg_length = messageStr.length();
			byte[] message = messageStr.getBytes();
			DatagramPacket p = new DatagramPacket(message, msg_length, local, server_port);
			s.send(p);
		} catch (Exception e) {
		}
		return null;
	}
}