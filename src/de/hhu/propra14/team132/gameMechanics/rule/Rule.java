package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameSystem.MessageType;

public abstract class Rule {
	public Rule(){
		messageTypesToReactTo=defineMessageTypesToReactTo();
	}
	
	MessageType[] messageTypesToReactTo;
	
	protected abstract MessageType[] defineMessageTypesToReactTo();
	
	public abstract void applyRule();
}
