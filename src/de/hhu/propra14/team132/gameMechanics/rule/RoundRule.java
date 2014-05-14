package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class RoundRule extends PassiveRule{
	private int currentRound;
	private int currentRoundStart;
	private int numberOfPlayers;
	private double roundLengthInSeconds;
	
	public RoundRule(Map gameMap, double roundLengthInSeconds) {
		super(gameMap);
		currentRound = 1;
		currentRoundStart=0;
		numberOfPlayers=gameMap.getPlayers().length;
		this.roundLengthInSeconds = roundLengthInSeconds;
	}

	@Override
	public void applyRule() {
		if((GameManager.currentTick+currentRoundStart)%this.roundLengthInSeconds==0){//time is up
			//TODO: send round is over message
			if(currentRound%numberOfPlayers==0){
				currentRound++;
			}
		}
		//do nothing if time is not up
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;//react to predefined 
	}
	
}
