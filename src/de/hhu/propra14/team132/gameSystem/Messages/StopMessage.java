package de.hhu.propra14.team132.gameSystem.Messages;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

/**
 * Created by isabel on 07.05.14.
 */
public class StopMessage extends Message {
    public StopMessage(Communicable sender) {
        super(MessageType.STOP, sender);
    }

}
