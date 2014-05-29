package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.MessageType;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public abstract class Rule implements Communicable {
	transient Map gameMap;
	
	@Expose MessageType[] messageTypesToReactTo;
	
	public Rule(Map gameMap){
		this.gameMap=gameMap;
		this.messageTypesToReactTo=defineMessageTypesToReactTo();
		if(messageTypesToReactTo!=null){
			gameMap.getManager().register(this, messageTypesToReactTo);
		}
	}
	
	
	protected abstract MessageType[] defineMessageTypesToReactTo();
	
	public abstract void applyRule();

	public Map getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}


	public MessageType[] getMessageTypesToReactTo() {
		return messageTypesToReactTo;
	}
	
}
