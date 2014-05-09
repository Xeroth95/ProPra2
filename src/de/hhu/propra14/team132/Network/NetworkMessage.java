package de.hhu.propra14.team132.Network;

class NetworkMessage implements NetworkableMessage {
	final int sentFrom;
	final NetworkableMessage content;
	final int contentSize;
	public NetworkMessage(NetworkableMessage mes, int sentFrom) {
		this.sentFrom = sentFrom;
		this.content = mes;
		this.contentSize = mes.getData().length;
	}
	
	public int getSize() {
		return	  this.contentSize	//size of the message
			+ 4;			//size of the senderID
	}
	
	public char[] getData() {
		char[] contentBuffer		= this.content.getData();
		char[] messageLengthBuffer	=  {	(char) (sentFrom),
							(char) (sentFrom << 8),
							(char) (sentFrom << 16),
							(char) (sentFrom << 24)
							};
		char[] data = new char[contentBuffer.length + messageLengthBuffer.length];
		for (int i = 0; i < messageLengthBuffer.length; ++i) {
			data[i] = messageLengthBuffer[i];
		}
		for (int i = 0; i < contentBuffer.length; ++i) {
			data[i + messageLengthBuffer.length] = contentBuffer[i];
		}
		
		return data;
	}
}
