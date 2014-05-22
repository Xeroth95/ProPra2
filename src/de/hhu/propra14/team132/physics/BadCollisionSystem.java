package de.hhu.propra14.team132.physics;

import java.util.ArrayList;

import de.hhu.propra14.team132.gameMechanics.Map;

public class BadCollisionSystem extends CollisionSystem{
	
	CollisionObject[] objects;
	ArrayList<Integer> IDs;

	public BadCollisionSystem(Map m){
		this.IDs=m.getObjectIds();
		this.objects=m.getMapObjects();
	}

	@Override
	public void calcCollision() {
		for(int i:IDs){
			for(int j:IDs){
				if(i==j)continue;
				objects[i].collideWithCheckTeam(objects[j]);
			}
		}
	}
}
