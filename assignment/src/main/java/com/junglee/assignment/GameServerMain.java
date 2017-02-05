package com.junglee.assignment;

import java.util.logging.Logger;

import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.server.WebSocketServer;

public class GameServerMain {
	private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());

	public static void main(String[] args) {

//		final ResourceBundle configurationBundle = ResourceBundle.getBundle("configuration");
		int port = Integer.valueOf("8888");
		WebSocketServer pWebSocketServer = new WebSocketServer();
		pWebSocketServer.start(port);
		LOG.info("server started");

	}

}
