package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {

	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException ex) {

			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Accept an incoming connection from a client
	 * 
	 * @return Connection object
	 * @throws IOException
	 */
	public Connection accept() throws IOException {

		Connection connection = null;
		Socket serverSocket = null;

		try {
			/**
			 * ServerSocket accept() Listens for a connection to be made to this socket and
			 * accepts it.
			 */
			serverSocket = welcomeSocket.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}

		connection = new Connection(serverSocket);

		return connection;

	}

	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException ex) {

				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
