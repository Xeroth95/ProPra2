package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.Messages.StopMessage;

public class WinRule extends PassiveRule{

	public WinRule(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveMessage(Message m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected MessageType[] defineMessageTypesToReactTo() {
		return null;
	}

	@Override
	public void applyRule() {
		for(Player p:gameMap.getPlayers()){
			boolean lost=true;
			for(Worm w:p.getWorms()){
				if(!w.isMarkedForDeletion())lost=false;
			}
			if(lost){
				System.out.format("Player %s lost the game! Everyone else is a winner!\nHave a nice day ;);););)",p.playerID);
				gameMap.getManager().sendMessage(new StopMessage());
				gameMap.getManager().mainFrame.mainPanel.showPanel("1");
			}
		}
	}

}
