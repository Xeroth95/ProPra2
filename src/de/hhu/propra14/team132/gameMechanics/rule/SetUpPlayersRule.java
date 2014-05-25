package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Weapons.Bazooka;
import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;
import de.hhu.propra14.team132.gameSystem.ShootMessage;
import de.hhu.propra14.team132.physics.util.Vector2D;

public strictfp class SetUpPlayersRule extends StartUpRule{
	
	@Expose ArrayList<ArrayList<Weapon>> weaponsPerPlayer;
	@Expose int playerCount;
	String[] names;
	
	public SetUpPlayersRule(Map gameMap,ArrayList<ArrayList<Weapon>> weaponsToSet,int playerCount,String[] names) {
		super(gameMap);
		this.weaponsPerPlayer=weaponsToSet;
		this.playerCount=playerCount;
		this.names=names;
	}


	@Override
	public void applyRule() {
		gameMap.setPlayers(new Player[playerCount]);
		for(int i=0;i<gameMap.getPlayers().length;i++){
			gameMap.getPlayers()[i]=new Player();
			gameMap.getPlayers()[i].setWeapons(weaponsPerPlayer.get(i));
		}
	}
	public static ArrayList<ArrayList<Weapon>> standardWeaponsSet(int players){
		ArrayList<ArrayList<Weapon>> weapons = new ArrayList<>();
		for(int i=0;i<players;i++){
			ArrayList<Weapon> weaponsForPlayer = new ArrayList<Weapon>();
			weaponsForPlayer.add(new Bazooka());
			
			weapons.add(weaponsForPlayer);
		}
		return weapons;
	}

	@Override
	public void receiveMessage(Message m) {
	}
	
}
