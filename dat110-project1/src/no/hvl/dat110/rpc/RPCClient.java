package no.hvl.dat110.rpc;

import java.io.IOException;

import no.hvl.dat110.messaging.*;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;

	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server, port);
	}

	public void register(RPCStub remote) {
		remote.register(this);
	}

	/**
	 * Connect using the underlying messaging layer connection
	 */
	public void connect() {
		try {
			this.connection = msgclient.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect/close the underlying messaging connection
	 */
	public void disconnect() {
		connection.close();
	}

	/**
	 * Make a remote call on the RPC server by sending the RPC request message and
	 * receive an RPC reply message
	 * 
	 * rpcrequest is the marshalled rpcrequest from the client-stub 
	 * rpcreply is to be unmarshalled by the client-stub
	 * 
	 * @param rpcrequest
	 * @return
	 */
	public byte[] call(byte[] rpcrequest) {

		byte[] rpcreply;
		Message message = null;

		try {
			message = new Message(rpcrequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		connection.send(message);
		message = connection.receive();
		rpcreply = message.getData();

		return rpcreply;

	}

}
