package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class Sensor extends RPCStub {

	private byte RPCID = 1;
	
	/**
	 * Marshalling, call and unmarshalling for read RPC method
	 * @return
	 */
	public int read() {
		
		int temp;
		
		byte[] request = RPCUtils.marshallVoid(RPCID);
		byte[] response = rpcclient.call(request);
		temp = RPCUtils.unmarshallInteger(response);
		
		return temp;
	}
	
}
