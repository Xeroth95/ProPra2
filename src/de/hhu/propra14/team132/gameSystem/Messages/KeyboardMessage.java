package de.hhu.propra14.team132.gameSystem.Messages;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

/**
 * Created by isabel on 07.05.14.
 */
//further Message-Classes will be added soon
public class KeyboardMessage extends Message {
    public enum Command {
        JUMP,
        MOVE_RIGHT,
        MOVE_RIGHT_STOP,
        MOVE_LEFT,
        MOVE_LEFT_STOP,
    }
    MessageType messageType;
    Command command;
    public KeyboardMessage(Command command, Communicable sender) {
        super(MessageType.KEYBOARD, sender);
        this.command=command;
    }

    public Command getCommand() {
        return command;
    }
}
