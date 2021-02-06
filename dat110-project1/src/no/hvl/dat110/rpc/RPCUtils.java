package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;

/**
 * Utility methods for marshalling and unmarshalling of parameters which return
 * values in RPC request and RPC responses
 * 
 * byte[] data is according to the RPC msg syntax [rpcid,parameter/return value]
 * 
 * @author frkmj
 */
public class RPCUtils {

	/**
	 * Marshalls RPC identifier and string into byte array
	 * 
	 * @param byte   rpcid
	 * @param String str
	 * @return byte[] encoded
	 */
	public static byte[] marshallString(byte rpcid, String str) {

		byte[] strByte = str.getBytes();
		int strSize = strByte.length;
		byte[] encoded = new byte[strSize + 1];

		encoded[0] = rpcid;

		for (int i = 0; i < strSize; i++) {
			encoded[i + 1] = strByte[i];
		}

		return encoded;
	}

	/**
	 * Unmarshall String contained in byte array
	 * 
	 * @param byte[] data
	 * @return String
	 */
	public static String unmarshallString(byte[] data) {
		/**
		 * @param bytes  - The bytes to be decoded into characters
		 * @param offset - The index of the first byte to decode
		 * @param length - The number of bytes to decode
		 */
		return new String(data, 1, data.length - 1);
	}

	/**
	 * Marshalls RPC identifier in case of void type (no payload data)
	 * 
	 * @param rpcid
	 * @return
	 */
	public static byte[] marshallVoid(byte rpcid) {

		byte[] encoded = new byte[1];
		encoded[0] = rpcid;

		return encoded;

	}

	/**
	 * Unmarshall without parameters (void type) does not have a function
	 * 
	 * @param data
	 */
	public static void unmarshallVoid(byte[] data) {
	}

	/**
	 * Marshalls RPC ID and boolean value into byte array
	 * 
	 * @param rpcid
	 * @param b
	 * @return byte[] encoded
	 */
	public static byte[] marshallBoolean(byte rpcid, boolean b) {

		byte[] encoded = new byte[2];

		encoded[0] = rpcid;

		if (b) {
			encoded[1] = 1;
		} else {
			encoded[1] = 0;
		}

		return encoded;
	}

	/**
	 * Unmarshall Boolean value contained in byte array
	 * 
	 * @param byte[] data
	 * @return boolean
	 */
	public static boolean unmarshallBoolean(byte[] data) {
		return (data[1] > 0);
	}

	/**
	 * Marshall RPC identifier and integer into byte array
	 * 
	 * @param rpcid
	 * @param x
	 * @return byte[] encoded
	 */
	public static byte[] marshallInteger(byte rpcid, int x) {

		int byteSize = 4;

		byte[] encoded = new byte[byteSize + 1];
		ByteBuffer bb = ByteBuffer.allocate(byteSize);
		bb.putInt(x);
		byte[] intByte = bb.array();

		for (int i = 0; i < byteSize; i++) {
			encoded[i + 1] = intByte[i];
		}

		encoded[0] = rpcid;

		return encoded;
	}

	/**
	 * Unmarshall integer contained in data
	 * 
	 * @param byte[] data
	 * @return int
	 */
	public static int unmarshallInteger(byte[] data) {

		int decoded = ByteBuffer.wrap(data, 1, 4).getInt();

		return decoded;

	}
}
