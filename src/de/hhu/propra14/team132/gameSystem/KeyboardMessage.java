package de.hhu.propra14.team132.gameSystem;

/**
 * Created by isabel on 07.05.14.
 */
//further Message-Classes will be added soon
public class KeyboardMessage extends Message {
    public enum Command {
        JUMP,
        MOVE_RIGHT,
        MOVE_LEFT,
        SHOOT,
        WHATEVER
    }
    MessageType messageType;
    Command command;
    public KeyboardMessage(int tick, Command command) {
        super(MessageType.KEYBOARD,tick);
        this.command=command;
    }

    public Command getCommand() {
        return command;
    }
}
