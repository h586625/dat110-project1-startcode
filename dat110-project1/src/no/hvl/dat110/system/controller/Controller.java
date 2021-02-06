package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCServerStopStub;

public class Controller {

	private static int N = 5;

	public static void main(String[] args) {

		Display display;
		Sensor sensor;

		RPCClient displayclient, sensorclient;

		System.out.println("Controller starting ...");

		RPCServerStopStub stopdisplay = new RPCServerStopStub();
		RPCServerStopStub stopsensor = new RPCServerStopStub();

		displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

		// connect to sensor and display RPC servers
		displayclient.connect();
		sensorclient.connect();
		
		// create local display and sensor objects
		display = new Display();
		sensor = new Sensor();
		
		// register display and sensor objects in the RPC layer
		display.register(displayclient);
		sensor.register(sensorclient);
		
		// register stop methods in the RPC layer
		displayclient.register(stopdisplay);
		sensorclient.register(stopsensor);

		
		// loop while reading from sensor and write to display via RPC
		for (int i = 0; i < N; i++) {

			int temp = sensor.read();
			display.write(Integer.toString(temp));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		stopdisplay.stop();
		stopsensor.stop();

		displayclient.disconnect();
		sensorclient.disconnect();

		System.out.println("Controller stopping ...");

	}
}
