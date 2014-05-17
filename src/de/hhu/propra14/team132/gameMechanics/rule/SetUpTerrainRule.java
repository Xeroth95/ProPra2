package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;



import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Terrain;
import de.hhu.propra14.team132.physics.util.ConvexCollisionShape;
import de.hhu.propra14.team132.physics.util.Vector2D;

public strictfp class  SetUpTerrainRule extends StartUpRule {
	@Expose ArrayList<Terrain> terrainToUse;
	
	static private final double[][] noshape;
	static private final double[][] square;
	static private final double[][] triangleLeft;
	static private final double[][] triangleRight;
	
	static private final double[][][]shapeverts;
	static{
		noshape=new double[0][0];
		
		square=new double[4][2];
		square[0]=new double[]{0,0};
		square[1]=new double[]{10,0};
		square[2]=new double[]{10,10};
		square[3]=new double[]{0,10};
		
		triangleLeft=new double[3][2];
		triangleLeft[0]=new double[]{0,0};
		triangleLeft[1]=new double[]{10,0};
		triangleLeft[2]=new double[]{0,10};
		
		triangleRight=new double[3][2];
		triangleRight[0]=new double[]{0,0};
		triangleRight[1]=new double[]{10,0};
		triangleRight[2]=new double[]{10,10};
		
		shapeverts=new double[4][][];
		shapeverts[0]=square;
		shapeverts[1]=triangleLeft;
		shapeverts[2]=triangleRight;
		shapeverts[3]=noshape;
	}
	
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
			terrainToUse = randomGenerateTerrainv1();
		}
		for(Terrain t : terrainToUse){
			gameMap.addObject(t);
		}
	}
	private ArrayList<Terrain> randomGenerateTerrain() {
		ArrayList<Terrain> newTerrain = new ArrayList<>();
		double[] x = {500,6000,6000,500}, y= {100,100,500,500};
		ConvexCollisionShape stub =new ConvexCollisionShape(x,y);
		newTerrain.add(new Terrain(stub));
		return newTerrain;
	}
	private ArrayList<Terrain> randomGenerateTerrainv1(){
		ArrayList<Terrain> newTerrain = new ArrayList<>();
		for(int i=0;i<3;i++){
			for(int j=0;j<10;j++){
				Vector2D[] mysquare = new Vector2D[4];
				for(int a=0;a<square.length;a++){
					mysquare[a]=new Vector2D(square[a][0]*10,square[a][1]*10);
				}
				Terrain t = new Terrain(new ConvexCollisionShape(mysquare));
				t.setPosition(new Vector2D((double)j*100,(double)i*100));
				newTerrain.add(t);
			}
		}
		for(int j=0;j<10;j++){
			double[][] randomshape;
			int shapevert=(int)Math.round(Math.random()*(shapeverts.length-1));
			randomshape=shapeverts[shapevert];
			if(randomshape.length==0)continue;
				Vector2D[] shape = new Vector2D[randomshape.length];
				for(int a=0;a<randomshape.length;a++){
					shape[a]=new Vector2D(randomshape[a][0]*10,randomshape[a][1]*10);
				}
				Terrain t = new Terrain(new ConvexCollisionShape(shape));
				t.setPosition(new Vector2D((double)j*100,300));
				newTerrain.add(t);
			
		}
		return newTerrain;
	}

}
