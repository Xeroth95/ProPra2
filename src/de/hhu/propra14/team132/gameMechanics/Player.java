package de.hhu.propra14.team132.gameMechanics;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameObjects.Worm;

public class Player {
	@Expose public static final int WORLD = 0;
	@Expose protected static int playerCount=1;
	@Expose public int playerID;
	@Expose private int currentWormArrayListIndex;
	@Expose private Worm currentWorm;

	private ArrayList<Worm> worms;
	public  Player(){
		this.playerID=getNextPlayerInt();
		this.worms=new ArrayList<Worm>();
		currentWormArrayListIndex=0;
	}
	
	private static int getNextPlayerInt(){
		return playerCount++;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public ArrayList<Worm> getWorms() {
		return worms;
	}
	public void setWorms(ArrayList<Worm> worms) {
		this.worms = worms;
	}
	public void nextWorm(){
		if(this.worms.size()==0){
			System.out.println("Player "+this.playerID+" has no Worm left!");
			return;
		}
		currentWormArrayListIndex=(currentWormArrayListIndex+1)%this.worms.size();
		currentWorm=worms.get(currentWormArrayListIndex);
	}

	public Worm getCurrentWorm() {
		return currentWorm;
	}

	public void setCurrentWorm(Worm currentWorm) {
		this.currentWorm = currentWorm;
	}
	
}
