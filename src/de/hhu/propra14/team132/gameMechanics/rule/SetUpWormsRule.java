package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;

public class SetUpWormsRule extends StartUpRule {
	int numberOfPlayers;
	int[] wormsToStartWith;
	public SetUpWormsRule(Map gameMap) {
		this(gameMap,2);
	}
	public SetUpWormsRule(Map gameMap,int numberOfPlayers) {
		super(gameMap);
		this.numberOfPlayers=numberOfPlayers;
		wormsToStartWith = new int[numberOfPlayers];
	}

	@Override
	public void applyRule() {
		
	}

}
