package de.hhu.propra14.team132.Network;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class ExampleNetworkMessage extends Message {

	private final char[] message;
	
	public ExampleNetworkMessage(String message, Communicable sender) {
		super(MessageType.CHAT, sender);
		this.message = message.toCharArray();
	}

	public char[] getData() {
		return message;
	}

}
