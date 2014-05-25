package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.Messages.WeaponSelectMessage;

public class WeaponSelectRule extends RuntimeRule {

	public WeaponSelectRule(Map gameMap) {
		super(gameMap);
	}

	@Override
	public void receiveMessage(Message m) {
		WeaponSelectMessage wm = (WeaponSelectMessage) m;
		Class<?> weaponclass=wm.getWeaponClass();
		for(int i=0;i<gameMap.getCurrentPlayer().getWeapons().size();i++){
			if(gameMap.getCurrentPlayer().getWeapons().get(i).getClass()==weaponclass){
				gameMap.getCurrentPlayer().setCurrentWeapon(gameMap.getCurrentPlayer().getWeapons().get(i));
			}
		}
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return new MessageType[]{MessageType.WEAPONSELECT};
	}

	@Override
	public void applyRule() {
		
	}
	
}
