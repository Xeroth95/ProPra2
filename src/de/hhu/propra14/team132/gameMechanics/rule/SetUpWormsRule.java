package de.hhu.propra14.team132.gameMechanics.rule;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.physics.util.Vector2D;

import com.google.gson.annotations.Expose;

public strictfp class  SetUpWormsRule extends StartUpRule {
	@Expose int numberOfPlayers;
	@Expose int[] wormsToStartWith;
	String[] pathsToTextures;
	public SetUpWormsRule(Map gameMap,int[] wormsToStartWith, int playerCount,String[] pathsToTextures) {
		super(gameMap);
		this.numberOfPlayers=playerCount;
		this.pathsToTextures=pathsToTextures;
		if(wormsToStartWith.length!=numberOfPlayers){
			System.err.println("Unexpected array size!");
			System.exit(-1);
		}
		this.wormsToStartWith = wormsToStartWith;
		
	}
	@Override
	public void applyRule() {
		stupidTestPlacementOfWorms();
    }
	private void stupidTestPlacementOfWorms() {
		
		for(int i=0;i<numberOfPlayers;i++){
			Player p = gameMap.getPlayers()[i];
			for(int j=0;j<wormsToStartWith[i];j++){
				Worm w = new Worm(p.getPlayerID(), "TestName", pathsToTextures[i]);
				w.setPosition(new Vector2D(10+480*i+50*j,600));
				gameMap.addObject(w);
				p.getWorms().add(w);
			}

			p.setCurrentWorm(p.getWorms().get(p.getCurrentWormArrayListIndex()%p.getWorms().size()));
		}
	}
	@Override
	public void receiveMessage(Message m) {
		// TODO Auto-generated method stub
		
	}
}
