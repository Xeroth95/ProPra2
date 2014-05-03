package de.hhu.propra14.team132.physics.util;

import java.util.ArrayList;

//this class might be unnecessary

public class CollisionShape {
	double[] xPoints;
	double[] yPoints;
	Vector2D position;
	ArrayList<ConvexCollisionShape> subPolygons;
	
	
	public CollisionShape(ArrayList<ConvexCollisionShape> shapes){
		this.subPolygons=shapes;
		this.position=new Vector2D();
	}
	public Vector2D getPosition() {
		return position;
	}




	public void setPosition(Vector2D position) {
		this.position = position;
		for(ConvexCollisionShape s:this.subPolygons){
			s.setPositionX(this.position.getX());
			s.setPositionY(this.position.getY());
		}
	}




	public ArrayList<ConvexCollisionShape> getSubPolygons() {
		return subPolygons;
	}




	public void setSubPolygons(ArrayList<ConvexCollisionShape> subPolygons) {
		this.subPolygons = subPolygons;
	}
}
