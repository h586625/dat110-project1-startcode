package no.hvl.dat110.rpc;

import java.io.IOException;
import java.util.HashMap;

import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;

	// hashmap to register RPC methods which are required to implement RPCImpl
	private HashMap<Integer, RPCImpl> services;

	public RPCServer(int port) {

		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer, RPCImpl>();

		// the stop RPC methods is built into the server
		services.put((int) RPCCommon.RPIDSTOP, new RPCServerStopImpl());
	}

	public void run() throws Exception {

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		try {
			connection = msgserver.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("RPC SERVER ACCEPTED");

		boolean stop = false;

		while (!stop) {

			int rpcid;

			// - receive message containing RPC request
			Message message = connection.receive();
			byte[] request = message.getData();

			// - find the identifier for the RPC methods to invoke
			rpcid = request[0];

			// - lookup the method to be invoked
			RPCImpl rpcimpl = services.get(rpcid);

			// - invoke the method
			byte[] reply = rpcimpl.invoke(request);

			// - send back message containing RPC reply
			Message replyMessage = new Message(reply);
			connection.send(replyMessage);

			if (rpcid == RPCCommon.RPIDSTOP) {
				stop = true;
			}
		}

	}

	/**
	 * Puts an RPC Implementation method in the HashMap with RPCID key
	 * @param int rpcid
	 * @param RPCImpl impl
	 */
	public void register(int rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}

	public void stop() {
		connection.close();
		msgserver.stop();

	}
}
