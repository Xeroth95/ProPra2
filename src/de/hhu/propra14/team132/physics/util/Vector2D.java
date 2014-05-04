package de.hhu.propra14.team132.physics.util;

public strictfp class Vector2D {
	double x;
	double y;
	double length;
	Vector2D rightHandNormal;
	Vector2D leftHandNormal;
	//constructors
	public Vector2D(){
		this.x=0;
		this.y=0;
	}
	public Vector2D(Vector2D a,Vector2D b){
		setVectorFromTo(a,b);
	}
	public Vector2D(double x,double y){
		this.x=x;
		this.y=y;
	}
	//methods
	public void setVectorFromTo(Vector2D a,Vector2D b){
		setVectorFromTo(a.getX(),a.getY(),b.getX(),b.getY());
	}
	public void setVectorFromTo(double xa,double ya,double xb, double yb){
		this.x=xb-xa;
		this.y=yb-ya;
	}
	//need the methods: computeUnitVector(),computeLength,computeRightHandNormal,computeLeftHandNormal
	//streamline: probably  updateXXX() methods are better than computeXXX() returning an Object everytime they are called. Should make the computeXXX() methods static.
	//streamline: preprocess all the values for one frame if it is needed more than once! If it is needed only once just compute it directly!
	public void computeLength(){
		//preprocess length for one frame. No multiple computations! (if possible. We will see..)
		this.length=Math.sqrt(x*x+y*y);
	}
	public double getLength(){
		return Math.sqrt(x*x+y*y);
	}
	public Vector2D computeUnitVector(){
		//preprocess the UnitVector per frame. No multiple computations in one frame. Call the computation method once and return the computed value in further calls.
		return new Vector2D(this.x/this.getLength(),this.y/this.getLength());
	}
	public void makeUnitVector() throws Exception{
		if(x==0 & y==0) throw new Exception("divide by zero!");
		double length=Math.sqrt(x*x+y*y);
		this.x=this.x/length;
		this.y=this.y/length;
	}
	public Vector2D getRightHandNormal(){
		this.computeRightHandNormal();
		return this.rightHandNormal;
	}
	public Vector2D getLeftHandNormal(){
		this.computeLeftHandNormal();
		return this.leftHandNormal;
	}
	public void computeRightHandNormal(){
		//x=-y
		//y=x
		this.rightHandNormal=new Vector2D(-this.y,this.x);
	}
	public void computeLeftHandNormal(){
		//x=y
		//y=-x
		this.leftHandNormal=new Vector2D(this.y,-this.x);
	}
	public void addVector(Vector2D v){
		this.x=this.x+v.getX();
		this.y=this.y+v.getY();
	}
	public void substractVector(Vector2D v) {
		this.x-=v.getX();
		this.y-=v.getY();
	}
	public void multiplyWith(double factor){
		this.x=this.x*factor;
		this.y=this.y*factor;
	}
	public double dot(Vector2D vector){
		return this.x*vector.getX()+this.y*vector.getY();
	}
	public boolean isParallel(Vector2D vector){
		if(vector.getSlope()==this.getSlope()^vector.getSlope()==this.getSlope()*(double)(-1)) return true;
		else if (vector.getY() == 0 && this.getY() == 0) return true;
		return false;
	}
	public double getSlope(){
		return this.y/this.x;
	} 
	//static methods
	public static boolean isCDcrossingAB(Vector2D a,Vector2D b,Vector2D c,Vector2D d){
		//best explained with a graphic. This is probably not very efficient, but it works. This method is hopefully not needed performant.
		if(isPointOnRightHandOfVector(a,b,c)^isPointOnRightHandOfVector(a,b,d)){
			//one point on left side, one on right side
			//maybe return true
			Vector2D pointOnTheLeftOfAB;
			Vector2D pointOnTheRightOfAB;
			if(isPointOnLeftHandOfVector(a,b,c)){
				pointOnTheLeftOfAB=c;
				pointOnTheRightOfAB=d;
			}
			else{
				pointOnTheLeftOfAB=d;
				pointOnTheRightOfAB=c;
			}
			if(isPointOnRightHandOfVector(pointOnTheLeftOfAB,b,pointOnTheRightOfAB)){
				return true;
			}
		}
		//if the above is not right
		return false;
	}
	public static double computeDotProduct(Vector2D a,Vector2D b){
		return a.getX()*b.getX()+a.getY()*b.getY();
	}
	public static boolean isPointOnRightHandOfVector(Vector2D a,Vector2D b,Vector2D pointToTest){
		return (0<((b.getY()-a.getY())*(pointToTest.getX()-a.getX())-(b.getX()-a.getX())*(pointToTest.getY()-a.getY())));
	}
	public static boolean isPointOnLeftHandOfVector(Vector2D a,Vector2D b,Vector2D pointToTest){
		return (0>((b.getY()-a.getY())*(pointToTest.getX()-a.getX())-(b.getX()-a.getX())*(pointToTest.getY()-a.getY())));
	}
	
	public static double distanceToRightHandOfVector(Vector2D a,Vector2D b,Vector2D pointToTest){
		return ((b.getY()-a.getY())*(pointToTest.getX()-a.getX())-(b.getX()-a.getX())*(pointToTest.getY()-a.getY()));
	}
	//regular getter/setter
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
}
	