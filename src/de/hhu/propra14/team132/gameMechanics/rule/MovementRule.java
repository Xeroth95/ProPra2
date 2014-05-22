package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.KeyboardMessage;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.physics.Jump;
import de.hhu.propra14.team132.physics.WormMovementOnGround;

public class MovementRule extends RuntimeRule {
	

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
		System.out.println("I get a Message!!");
		switch(mt){
			case KEYBOARD: {
				KeyboardMessage k = (KeyboardMessage) m;
				switch(k.getCommand()){
					case MOVE_RIGHT:{
						WormMovementOnGround.GLOBAL_MOVE_EFFECT.setLeft(false);
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case MOVE_LEFT:{
						WormMovementOnGround.GLOBAL_MOVE_EFFECT.setLeft(true);
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case JUMP:{
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(Jump.GLOBAL_JUMP_EFFECT);
						break;
					}
					
					case MOVE_RIGHT_STOP:{
						this.gameMap.getCurrentPlayer().getCurrentWorm().removeEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case MOVE_LEFT_STOP:{
						this.gameMap.getCurrentPlayer().getCurrentWorm().removeEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
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
