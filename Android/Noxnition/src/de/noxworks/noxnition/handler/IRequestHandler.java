package de.noxworks.noxnition.handler;

import java.util.Properties;

public interface IRequestHandler {

	void requestFailed();

	void requestSuccess(Properties properties);

	void preRequest();

}