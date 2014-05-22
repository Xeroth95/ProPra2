package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.MessageType;

public abstract strictfp class StartUpRule extends Rule {
	public StartUpRule(Map gameMap) {
		super(gameMap);
	}

	protected MessageType[] defineMessageTypesToReactTo(){
		return null;//does not react to Messages!
	}
}
