package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.physics.util.Vector2D;

public class SetUpWormsRule extends StartUpRule {
	int numberOfPlayers;
	int[] wormsToStartWith;
	public SetUpWormsRule(Map gameMap) {
		super(gameMap);
		this.numberOfPlayers=gameMap.getPlayers().length;
		wormsToStartWith = new int[numberOfPlayers];
	}

	@Override
	public void applyRule() {
		stupidTestPlacementOfWorms();
	}
	private void stupidTestPlacementOfWorms(){
		for(int i=0;i<numberOfPlayers;i++){
			for(int j=0;j<wormsToStartWith[i];j++){
				Player p = gameMap.getPlayers()[i];
				Worm w = new Worm(p.getPlayerID(), gameMap, "TestName");
				w.setPosition(new Vector2D(500*i+50*j,1000));
				gameMap.addObject(w);
				p.getWorms().add(w);
			}
		}
	}
}
