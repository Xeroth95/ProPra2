package de.hhu.propra14.team132.physics.util;

import java.awt.Polygon;



public class ConvexCollisionShape {
	
//probably really nasty class with a lot of attributes and a lot of algorithms for collision. Think thrice before changing!
	double[] xPoints;
	double[] yPoints;
	double[] originalxPoints;
	double[] originalyPoints;
	Vector2D[] collisionAxes;
	double positionX;
	double positionY;
	double minOnX;
	double maxOnX;
	double minOnY;
	double maxOnY;
	double xSize;
	double ySize;
	public ConvexCollisionShape(){
		
	}
	public ConvexCollisionShape(Vector2D[] p){
		xPoints=new double[p.length];
		yPoints=new double[p.length];
		originalxPoints=new double[p.length];
		originalyPoints=new double[p.length];
		for(int i=0;i<p.length;i++){
			originalxPoints[i]=p[i].getX();
			originalyPoints[i]=p[i].getY();
		}
		positionX=0;
		positionY=0;
		this.recalcY();
		this.recalcX();
		this.computeCollisionAxes();
	}
	public ConvexCollisionShape(double[] x,double[] y){
		minOnX=Double.MAX_VALUE;
		maxOnX=Double.MIN_VALUE;
		minOnY=Double.MAX_VALUE;
		maxOnY=Double.MIN_VALUE;
		this.originalxPoints=x;
		this.originalyPoints=y;
		this.xPoints=new double[x.length];
		this.yPoints=new double[x.length];
		for(int i=0;i<x.length;i++){
			if(x[i]<minOnX)minOnX=x[i];
			if(x[i]>maxOnX)maxOnX=x[i];
			if(y[i]<minOnY)minOnY=y[i];
			if(y[i]>maxOnY)maxOnY=y[i];
		}
		this.xSize=this.maxOnX-this.minOnX;
		this.ySize=this.maxOnY-this.minOnY;
		positionX=0;
		positionY=0;
		this.recalcY();
		this.recalcX();
		this.computeCollisionAxes();
	}
	
	
	
	private Vector2D[] getCollisionAxes() {
		return this.collisionAxes;
	}
	public void computeCollisionAxes(){
		Vector2D[] axes=new Vector2D[xPoints.length];
		for(int i=0;i<this.xPoints.length;i++){
			//obtaining the edge: vectorb-vectora
			// edge is an relative Vector, describing the distance between the two vertices. It does NOT describe a Point on its own!
			//Vector2D edge = new Vector2D(xPoints[(i+1)%xPoints.length]-xPoints[i],yPoints[(i+1)%yPoints.length]-yPoints[i]);
			//Vector2D axis = edge.getLeftHandNormal();
			Vector2D axis = new Vector2D(yPoints[(i+1)%yPoints.length]-yPoints[i],-(xPoints[(i+1)%xPoints.length]-xPoints[i]));
			//again:the axis should be normalized to get accurate projections!!!
			axis.multiplyWith(1/axis.getLength());
			axes[i]=axis;
		}
//		this.collisionAxes = excludeParallelAxes(axes); TODO: implement excludeParallelAxes
	}
	public Vector2D[] getComputedCollisionAxes(){
		Vector2D[] axes=new Vector2D[xPoints.length-1];
		double[] slopes= new double[xPoints.length-1];
		for(int i=0;i<this.xPoints.length-1;i++){
			//obtaining the edge: vectorb-vectora?????
			// edge is an relative Vector, describing the distance between the two vertices. It does NOT describe a Point on its own!
			Vector2D edge = new Vector2D(xPoints[i+1]-xPoints[i],yPoints[i+1]-yPoints[i]);
			
			
			Vector2D axis = edge.getLeftHandNormal();
			axes[i]=axis;
		}
		return axes;
	}
	
	
	public double[] getOnX(){
		double[] minMax={
				this.minOnX+this.positionX,this.maxOnX+this.positionX
		};
		return minMax;
	}
	public double getMaxOnX(){
		return this.maxOnX+this.positionX;
	}
	public double getMinOnX(){
		return this.minOnX+this.positionX;
	}
	public double[] getOnY(){
		double[] minMax={
				this.minOnY+this.positionY,this.maxOnY+this.positionY
		};
		return minMax;
	}
	public double getMaxOnY(){
		return this.maxOnY+this.positionY;
	}
	public double getMinOnY(){
		return this.minOnY+this.positionY;
	}
	
	public Polygon getPolygonToDraw(){
		int[] x=new int[xPoints.length];
		int[] y=new int[yPoints.length];
		for(int i=0;i<xPoints.length;i++){
			x[i]=(int)(xPoints[i]);
		}
		for(int i=0;i<yPoints.length;i++){
			y[i]=(int)(yPoints[i]);
		}
		Polygon p=new Polygon(x,y, x.length);
		return p;
	}
	public double[] getxPoints() {
		double[] x=new double[xPoints.length];
		for(int i=0;i<xPoints.length;i++){
			x[i]=xPoints[i]+this.positionX;
		}
		return x;
	}
	public void setxPoints(double[] xPoints) {
		this.xPoints = xPoints;
	}
	public double[] getyPoints() {
		double[] y=new double[xPoints.length];
		for(int i=0;i<xPoints.length;i++){
			y[i]=xPoints[i]+this.positionX;
		}
		return y;
	}
	public void setyPoints(double[] yPoints) {
		this.yPoints = yPoints;
	}
	public void setCollisionAxes(Vector2D[] newAxes){
		this.collisionAxes=newAxes;
	}
	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
		this.recalcX();
	}
	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
		this.recalcY();
	}
	protected void recalcY() {
		for(int i=0;i<originalyPoints.length;i++){
			this.yPoints[i]=originalyPoints[i]+this.positionY;
		}
	}
	protected void recalcX() {
		for(int i=0;i<originalxPoints.length;i++){
			this.xPoints[i]=originalxPoints[i]+this.positionX;
		}
	}
	public double getxSize() {
		return xSize;
	}
	public void setxSize(double xSize) {
		this.xSize = xSize;
	}
	public double getySize() {
		return ySize;
	}
	public void setySize(double ySize) {
		this.ySize = ySize;
	}
	public void setMinOnX(double minOnX) {
		this.minOnX = minOnX;
	}
	public void setMaxOnX(double maxOnX) {
		this.maxOnX = maxOnX;
	}
	public void setMinOnY(double minOnY) {
		this.minOnY = minOnY;
	}
	public void setMaxOnY(double maxOnY) {
		this.maxOnY = maxOnY;
	}
	
	
}
