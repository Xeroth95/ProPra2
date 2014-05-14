package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.physics.util.Vector2D;

import java.io.IOException;

public class SetUpWormsRule extends StartUpRule {
	int numberOfPlayers;
	int[] wormsToStartWith;
	public SetUpWormsRule(Map gameMap,int[] wormsToStartWith) {
		super(gameMap);
		this.numberOfPlayers=gameMap.getPlayers().length;
		if(wormsToStartWith.length!=numberOfPlayers){
			System.err.println("Unexpected array size!");
			System.exit(-1);
		}
		this.wormsToStartWith = wormsToStartWith;
		
	}
	public SetUpWormsRule(Map gameMap,int wormsToStartWith) {
		super(gameMap);
		this.numberOfPlayers=gameMap.getPlayers().length;
		this.wormsToStartWith = new int[numberOfPlayers];
		for(int i=0;i<this.wormsToStartWith.length;i++){
			this.wormsToStartWith[i]=wormsToStartWith;
		}
	}
	@Override
	public void applyRule() {
//		stupidTestPlacementOfWorms();

		Player p = gameMap.getPlayers()[0];
		Worm w = new Worm(p.getPlayerID(), gameMap, "TestName");
		w.setPosition(new Vector2D(600,600));
		gameMap.addObject(w);
		p.getWorms().add(w);
    }
	private void stupidTestPlacementOfWorms() {
		for(int i=0;i<numberOfPlayers;i++){
			for(int j=0;j<wormsToStartWith[i];j++){
				Player p = gameMap.getPlayers()[i];
				Worm w = new Worm(p.getPlayerID(), gameMap, "TestName");
				w.setPosition(new Vector2D(500*i+50*j,600));
				gameMap.addObject(w);
				p.getWorms().add(w);
			}
		}
	}
}
