package de.hhu.propra14.team132.gameSystem.Messages;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

/**
 * Created by fabian on 25.05.14.
 */
public class WeaponSelectMessage extends Message {
	Class<?> weaponClass;

    public WeaponSelectMessage(Class<?> weaponClass, Communicable sender) {
        super(MessageType.WEAPONSELECT, sender);
        this.weaponClass=weaponClass;
    }

        public Class<?> getWeaponClass() {
        return weaponClass;
    }
}
