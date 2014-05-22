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
		return null;//TODO: react to the input!
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
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case MOVE_LEFT:{
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(WormMovementOnGround.GLOBAL_MOVE_EFFECT);
						break;
					}
					case JUMP:{
						this.gameMap.getCurrentPlayer().getCurrentWorm().addEffect(Jump.GLOBAL_JUMP_EFFECT);
						break;
					}
					
					
					
					default:{
						System.err.println("EMPTY KEYBOARD MESSAGE!");
					}
				}
			}
			default:{
				
			}
		}
		
	}

}
