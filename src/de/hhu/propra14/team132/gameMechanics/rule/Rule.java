package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.MessageType;

import java.io.IOException;

import com.google.gson.annotations.Expose;

public abstract class Rule {
	transient Map gameMap;
	public Rule(Map gameMap){
		this.gameMap=gameMap;
		messageTypesToReactTo=defineMessageTypesToReactTo();
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
