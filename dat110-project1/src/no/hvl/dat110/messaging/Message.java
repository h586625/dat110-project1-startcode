package no.hvl.dat110.messaging;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) throws Exception {
		if (payload.length > (MessageConfig.SEGMENTSIZE - 1)) {
			throw new IllegalArgumentException(
					"Payload kan ikke være større enn " + (MessageConfig.SEGMENTSIZE - 1) + " bytes");
		} else {
			this.payload = payload;
		}
	}

	public Message() {
		super();
	}

	/**
	 * Get the payload data from Message
	 * @return byte[] payload
	 */
	public byte[] getData() {
		return this.payload;
	}

	/**
	 * Encode the payload of the message in the encoded byte array according to
	 * message format
	 * 
	 * @return byte[]
	 */
	public byte[] encapsulate() {

		byte[] encoded = new byte[MessageConfig.SEGMENTSIZE];

		int length = payload.length;
		encoded[0] = (byte) length;

		for (int i = 0; i < length; i++) {
			encoded[i + 1] = payload[i];
		}

		// Padding
		for (int i = length + 1; i < MessageConfig.SEGMENTSIZE; i++) {
			encoded[i] = 0;
		}

		return encoded;

	}

	/**
	 * Decode the data received and store it in the payload of this message
	 * 
	 * @param byte[] array
	 */
	public void decapsulate(byte[] received) {

		int length = received[0];

		byte[] decoded = new byte[length];

		for (int i = 0; i < length; i++) {
			decoded[i] = received[i + 1];
		}

		this.payload = decoded;

	}
}
