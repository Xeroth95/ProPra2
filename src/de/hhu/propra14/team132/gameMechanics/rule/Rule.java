package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.MessageType;

public abstract class Rule {
	Map gameMap;
	public Rule(Map gameMap){
		this.gameMap=gameMap;
		messageTypesToReactTo=defineMessageTypesToReactTo();
	}
	
	MessageType[] messageTypesToReactTo;
	
	protected abstract MessageType[] defineMessageTypesToReactTo();
	
	public abstract void applyRule();
}
