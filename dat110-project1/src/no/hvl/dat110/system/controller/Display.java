package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class Display extends RPCStub {

	private byte RPCID = 1;

	/**
	 * Marshalling, call and unmarshalling for write RPC method
	 * @param message
	 */
	public void write(String message) {

		byte[] request = RPCUtils.marshallString(RPCID, message);		
		byte[] response = rpcclient.call(request);
		RPCUtils.unmarshallVoid(response);
	}
}
