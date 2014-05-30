package de.hhu.propra14.team132.GUI;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class ChatMessage extends Message {
	private final String message;
	public ChatMessage(String message, Communicable sender) {
		super(MessageType.CHAT, sender);
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
