package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.physics.WormMovementOnGround;

public class MovementRule extends RuntimeRule {
	
	

	public MovementRule(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;//TODO: react to the input!
	}

	@Override
	public void applyRule() {
		//TODO: put this somewhere appropriate
		this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
	}
	
}
