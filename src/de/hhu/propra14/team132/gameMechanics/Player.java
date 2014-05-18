package de.hhu.propra14.team132.gameMechanics;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameObjects.Worm;

public class Player {
	@Expose public static final int WORLD = 0;
	@Expose protected static int playerCount=1;
	@Expose public int playerID;

	private ArrayList<Worm> worms;
	public  Player(){
		this.playerID=getNextPlayerInt();
		this.worms=new ArrayList<Worm>();
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
	
}
