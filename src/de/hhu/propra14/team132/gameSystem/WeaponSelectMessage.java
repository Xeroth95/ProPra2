package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;

/**
 * Created by fabian on 25.05.14.
 */
public class WeaponSelectMessage extends Message{
	Class<?> weaponClass;
    
    public WeaponSelectMessage(MessageType type,Class<?> weaponClass) {
        super(MessageType.WEAPONSELECT);
        this.weaponClass=weaponClass;
    }

        public Class<?> getWeaponClass() {
        return weaponClass;
    }
}
