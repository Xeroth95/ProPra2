package de.hhu.propra14.team132.physics;

import java.util.ArrayList;
import java.util.List;

import de.hhu.propra14.team132.gameMechanics.Map;

public class BadCollisionSystem extends CollisionSystem{
	
	CollisionObject[] objects;
	List<Integer> IDs;

	public BadCollisionSystem(Map m){
		this.IDs=m.getObjectIds();
		this.objects=m.getMapObjects();
	}

	@Override
	public synchronized void calcCollision() {
		synchronized(objects) {
			synchronized(IDs) {
				for(int i:IDs){
					for(int j:IDs){
						if(i==j)continue;
						objects[i].collideWithCheckTeam(objects[j]);
					}
					if(objects[i].getPosition().getX()<0||objects[i].getPosition().getX()>8192){
						objects[i].getPosition().setX(-1.01*objects[i].getPosition().getX());
					}
					if(objects[i].getPosition().getY()<0||objects[i].getPosition().getY()>8192){
						objects[i].getPosition().setY(-1.01*objects[i].getPosition().getY());
					}
				}
			}
		}
	}
}
