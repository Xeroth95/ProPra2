package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameMechanics.Player;
import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;
import de.hhu.propra14.team132.gameSystem.Message;

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
			gameMap.getPlayers()[i].setName(names[i]);
			gameMap.getPlayers()[i].setWeapons(weaponsPerPlayer.get(i));
			gameMap.getPlayers()[i].setCurrentWeapon(weaponsPerPlayer.get(i).get(0));
		}
		gameMap.setCurrentPlayer(gameMap.getPlayers()[0]);
	}

	@Override
	public void receiveMessage(Message m) {
	}
	
}
