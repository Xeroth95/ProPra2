package de.hhu.propra14.team132.gameMechanics;

import java.awt.Color;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameObjects.Weapon;
import de.hhu.propra14.team132.gameObjects.Worm;

public class Player {
	@Expose public static final int WORLD = 0;
	@Expose protected static int playerCount=1;
	@Expose public int playerID;
	@Expose private int currentWormArrayListIndex;
	@Expose private Worm currentWorm;
	@Expose private Color color;
	@Expose private ArrayList<Weapon> weapons;
	
	private ArrayList<Worm> worms;
	public  Player(){
		this.playerID=getNextPlayerInt();
		this.worms=new ArrayList<>();
		this.weapons=new ArrayList<>();
		currentWormArrayListIndex=0;
		this.color=Color.BLACK;
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

	public int getCurrentWormArrayListIndex() {
		return currentWormArrayListIndex;
	}

	public void setCurrentWormArrayListIndex(int currentWormArrayListIndex) {
		this.currentWormArrayListIndex = currentWormArrayListIndex;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}
	
}
