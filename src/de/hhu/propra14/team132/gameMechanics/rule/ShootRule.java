package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Projectiles.Projectile;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.Messages.ShootMessage;

public class ShootRule extends RuntimeRule {

	public ShootRule(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveMessage(Message m) {
		ShootMessage sm = (ShootMessage) m;
		Projectile p=gameMap.getCurrentPlayer().getCurrentWeapon().shoot(sm.getMousePosition(), gameMap.getCurrentPlayer().getCurrentWorm().getPosition(), sm.getPower());
		gameMap.addObject(p);

	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return new MessageType[]{MessageType.SHOOT};
	}

	@Override
	public void applyRule() {
		// TODO Auto-generated method stub
		
	}

}
