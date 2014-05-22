package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.MessageType;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public abstract class Rule implements Communicable {
	transient Map gameMap;
	public Rule(Map gameMap){
		this.gameMap=gameMap;
		messageTypesToReactTo=defineMessageTypesToReactTo();
		if(messageTypesToReactTo!=null)
		for(MessageType m : messageTypesToReactTo){
			gameMap.getManager().register(this, messageTypesToReactTo);
		}
	}
	
	@Expose MessageType[] messageTypesToReactTo;
	
	protected abstract MessageType[] defineMessageTypesToReactTo();
	
	public abstract void applyRule();

	public Map getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}
	
	
}
