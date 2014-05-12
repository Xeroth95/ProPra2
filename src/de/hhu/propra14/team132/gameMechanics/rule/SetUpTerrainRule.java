package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;

public class SetUpTerrainRule extends StartUpRule {
	ArrayList<Terrain> terrainToUse;
	public SetUpTerrainRule(Map gameMap) {
		super(gameMap);
	}
	public SetUpTerrainRule(Map gameMap,ArrayList<Terrain> terrainToUse) {
		super(gameMap);
		this.terrainToUse=terrainToUse;
	}
	

	@Override
	public void applyRule() {
		if(this.terrainToUse==null){
			terrainToUse = randomGenerateTerrain();
		}
		for(Terrain t : terrainToUse){
			gameMap.addObject(t);
		}
	}
	private ArrayList<Terrain> randomGenerateTerrain(){
		ArrayList<Terrain> newTerrain = new ArrayList<>();
		double[] x = {500,6000,6000,500}, y= {4000,4000,500,500};
		ConvexCollisionShape stub =new ConvexCollisionShape(x,y);
		newTerrain.add(new Terrain(stub, gameMap));
		return newTerrain;
	}

}
