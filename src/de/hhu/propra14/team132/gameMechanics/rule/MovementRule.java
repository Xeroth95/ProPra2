package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.gameSystem.KeyboardMessage;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.physics.Jump;
import de.hhu.propra14.team132.physics.WormMovementOnGround;

public class MovementRule extends RuntimeRule {
	
	Worm wormLastMoved;

	public MovementRule(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return new MessageType[]{MessageType.KEYBOARD};
	}

	@Override
	public void applyRule() {
		//TODO: put this somewhere appropriate
		
	}

	@Override
	public void receiveMessage(Message m) {
		MessageType mt = m.getMessageType();
		switch(mt){
			case KEYBOARD: {
				KeyboardMessage k = (KeyboardMessage) m;
				switch(k.getCommand()){
					case MOVE_RIGHT:{
						WormMovementOnGround.GLOBAL_MOVE_EFFECT.setLeft(false);
						wormLastMoved=this.gameMap.getCurrentPlayer().getCurrentWorm();
						wormLastMoved.addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case MOVE_LEFT:{
						WormMovementOnGround.GLOBAL_MOVE_EFFECT.setLeft(true);
						wormLastMoved=this.gameMap.getCurrentPlayer().getCurrentWorm();
						wormLastMoved.addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case JUMP:{
						wormLastMoved=this.gameMap.getCurrentPlayer().getCurrentWorm();
						wormLastMoved.addEffect(Jump.GLOBAL_JUMP_EFFECT);
						break;
					}
					
					case MOVE_RIGHT_STOP:{
						wormLastMoved.removeEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case MOVE_LEFT_STOP:{
						wormLastMoved.removeEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					
					
					
					default:{
						System.err.println("EMPTY KEYBOARD MESSAGE!");
					}
				}
				break;
			}
			default:{
				
			}
		}
		
	}

}
