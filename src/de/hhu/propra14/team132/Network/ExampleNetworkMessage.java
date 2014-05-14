package de.hhu.propra14.team132.Network;

import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class ExampleNetworkMessage extends Message implements NetworkableMessage {

	private final char[] message;
	
	public ExampleNetworkMessage(String message) {
		super(MessageType.CHAT);
		this.message = message.toCharArray();
	}
	
	// do not use this method
	public ExampleNetworkMessage(MessageType type, String message) {
		super(type);
		this.message = null;
		throw new RuntimeException();
	}

	@Override
	public char[] getData() {
		return message;
	}

}
