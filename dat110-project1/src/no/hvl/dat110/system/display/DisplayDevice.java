package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");
		
		// implement the operation of the display RPC server
		DisplayImpl display = new DisplayImpl();
		RPCServer displayServer = new RPCServer(Common.DISPLAYPORT);
		
		displayServer.register(1, display);
		
		try {
			displayServer.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		displayServer.stop();
		
		System.out.println("Display server stopping ...");
		
	}
}
