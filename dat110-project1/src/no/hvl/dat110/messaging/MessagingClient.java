package no.hvl.dat110.messaging;

import java.io.IOException;

import java.net.Socket;

public class MessagingClient {

	private String server;
	private int port;

	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	/**
	 * Connect to messaging server
	 * @return Connection object
	 * @throws IOException
	 */
	public Connection connect() throws IOException {

		Socket clientSocket = null;
		Connection connection = null;

		try {
			clientSocket = new Socket(server, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		connection = new Connection(clientSocket);

		return connection;
	}
}
