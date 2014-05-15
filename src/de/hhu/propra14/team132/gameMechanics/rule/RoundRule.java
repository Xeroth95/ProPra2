package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class RoundRule extends PassiveRule{
	private int currentRound;
	private int currentRoundStart;
	private int numberOfPlayers;
	private Player currentPlayer;
	private double roundLengthInSeconds;
	
	public RoundRule(Map gameMap, double roundLengthInSeconds) {
		super(gameMap);
		currentRound = 1;
		currentRoundStart=0;
		numberOfPlayers=gameMap.getPlayers().length;
		this.roundLengthInSeconds = roundLengthInSeconds;
		this.currentPlayer=gameMap.getPlayers()[0];
	}

	@Override
	public void applyRule() {
		if((GameManager.currentTick+currentRoundStart)%this.roundLengthInSeconds==0){//time is up
			//TODO: send round is over message
			if(currentRound%numberOfPlayers==0){
				currentRound++;
				gameMap.setCurrentPlayer(gameMap.getPlayers()[(gameMap.getCurrentPlayer().getPlayerID()+1)%gameMap.getPlayers().length]);
			}
		}
		//do nothing if time is not up
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;//react to predefined 
	}
	
}
