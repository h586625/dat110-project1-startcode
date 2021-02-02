package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Encapsulate the data contained in the message and write to the output stream
	 * @param message
	 */
	public void send(Message message) {

		byte[] encodedMessage = message.encapsulate();
		
		try {
			outStream.write(encodedMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read a segment (128 bytes) from the input stream and decapsulate into message
	 * @return Message object
	 */
	public Message receive() {

		Message message = new Message();
		byte[] recvbuf = new byte[MessageConfig.SEGMENTSIZE];

		try {
			inStream.read(recvbuf, 0, MessageConfig.SEGMENTSIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		message.decapsulate(recvbuf);
		
		return message;

	}

	/** 
	 * Close the connection by closing streams and the underlying socket
	 */
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}