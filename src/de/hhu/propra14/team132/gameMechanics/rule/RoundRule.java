package de.hhu.propra14.team132.gameMechanics.rule;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.MessageType;

public class RoundRule extends PassiveRule{
	@Expose private int currentRound;
	@Expose private int currentRoundStart;
	@Expose private int numberOfPlayers;
	@Expose private Player currentPlayer;
	@Expose private double roundLengthInSeconds;
	@Expose private int roundLengthInTicks;
	
	public RoundRule(Map gameMap, double roundLengthInSeconds) {
		super(gameMap);
		currentRound = 1;
		currentRoundStart=0;
		numberOfPlayers=gameMap.getPlayers().length;
		this.roundLengthInSeconds = roundLengthInSeconds;
		this.currentPlayer=gameMap.getPlayers()[0];
		roundLengthInTicks=(int)Math.round(GameManager.ticksPerSecond*roundLengthInSeconds);
	}

	@Override
	public void applyRule() {
//		System.out.println(gameMap.getCurrentTick()%roundLengthInTicks);
		if(gameMap.getCurrentTick()%roundLengthInTicks==0){//time is up
			//TODO: send round is over message
			currentRound++;
			gameMap.setCurrentPlayer(gameMap.getPlayers()[(gameMap.getCurrentPlayer().getPlayerID())%gameMap.getPlayers().length]);
		}
		//do nothing if time is not up
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;//react to predefined 
	}
	
}
