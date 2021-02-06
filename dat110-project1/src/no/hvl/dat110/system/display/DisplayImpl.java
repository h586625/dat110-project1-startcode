package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCImpl;
import no.hvl.dat110.rpc.RPCUtils;

public class DisplayImpl implements RPCImpl {

	public void write(String message) {
		System.out.println("DISPLAY:" + message);
	}
	
	/**
	 * Unmarshalling, call and marshall for write RPC method
	 * Look at how this is done in the SensorImpl for the read method
	 */
	public byte[] invoke(byte[] request) {
		
		byte[] reply;
		byte rpcid;
		
		String message = RPCUtils.unmarshallString(request);
		write(message);
		rpcid = request[0];
		reply = RPCUtils.marshallVoid(rpcid);
		
		return reply;
	}
}
