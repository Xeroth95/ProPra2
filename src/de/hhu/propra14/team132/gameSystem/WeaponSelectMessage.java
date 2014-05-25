package de.hhu.propra14.team132.gameSystem;

import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;

/**
 * Created by fabian on 25.05.14.
 */
public class WeaponSelectMessage extends Message{
    Weapon weapon;
    public WeaponSelectMessage(MessageType type) {
        super(MessageType.WEAPONSELECT);

        public Weapon getWeapon() {
        return weapon;
    }
}
