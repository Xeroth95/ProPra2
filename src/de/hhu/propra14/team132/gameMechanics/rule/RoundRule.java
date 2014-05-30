package de.hhu.propra14.team132.gameMechanics.rule;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

public strictfp class RoundRule extends PassiveRule{
	@Expose private int currentRound;
	@Expose private int currentRoundStart;
	@Expose private int numberOfPlayers;
	@Expose private double roundLengthInSeconds;
	@Expose private int roundLengthInTicks;
	
	public RoundRule(Map gameMap, double roundLengthInSeconds, int playerCount) {
		super(gameMap);
		currentRound = 1;
		currentRoundStart=0;
		numberOfPlayers=playerCount;
		this.roundLengthInSeconds = roundLengthInSeconds;
		roundLengthInTicks=(int)Math.round(GameManager.TICKS_PER_SECOND*roundLengthInSeconds);
	}

	@Override
	public void applyRule() {
//		System.out.println(gameMap.getCurrentTick()%roundLengthInTicks);
		int timeLeft=roundLengthInTicks-(gameMap.getCurrentTick()%roundLengthInTicks);
		gameMap.setTimeLeftInTicks(timeLeft);
		if(timeLeft==roundLengthInTicks){//time is up
			//TODO: send round is over message
			currentRound++;
			gameMap.getCurrentPlayer().nextWorm();
			gameMap.setCurrentPlayer(gameMap.getPlayers()[(gameMap.getCurrentPlayer().getPlayerID())%gameMap.getPlayers().length]);
		}
		//do nothing if time is not up
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;//react to predefined 
	}

	@Override
	public void receiveMessage(Message m) {
		// TODO Auto-generated method stub
		
	}
	
}
