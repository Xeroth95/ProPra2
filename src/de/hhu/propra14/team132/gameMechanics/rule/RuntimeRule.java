package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.Communicable;

public abstract class RuntimeRule extends Rule implements Communicable{
	public RuntimeRule(Map gameMap) {
		super(gameMap);
	}
}
