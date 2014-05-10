package de.hhu.propra14.team132.Network;

import java.io.Serializable;

import de.hhu.propra14.team132.gameSystem.Message;

class NetworkMessage implements Serializable{
	final int sentFrom;
	final Message content;
	public NetworkMessage(Message mes, int sentFrom) {
		this.sentFrom = sentFrom;
		this.content = mes;
	}
	
	public Message getMessage() {
		return this.content;
	}
	
	public int getID() {
		return this.sentFrom;
	}
}
