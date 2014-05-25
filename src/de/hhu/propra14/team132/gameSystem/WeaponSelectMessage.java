package de.hhu.propra14.team132.gameSystem;

/**
 * Created by fabian on 25.05.14.
 */
public class WeaponSelectMessage extends Message{
	Class<?> weaponClass;

    public WeaponSelectMessage(Class<?> weaponClass) {
        super(MessageType.WEAPONSELECT);
        this.weaponClass=weaponClass;
    }

        public Class<?> getWeaponClass() {
        return weaponClass;
    }
}
