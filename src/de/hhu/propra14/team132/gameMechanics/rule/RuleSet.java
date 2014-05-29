package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;
import de.hhu.propra14.team132.gameObjects.Weapons.Weapon;

public class RuleSet {
	transient Map gameMap;
	@Expose ArrayList<StartUpRule> startUpRules;
	@Expose ArrayList<RuntimeRule> runtimeRules;
	@Expose ArrayList<PassiveRule> passiveRules;
	protected RuleSet(Map gameMap){
		this.gameMap=gameMap;
		startUpRules=new ArrayList<StartUpRule>();
		runtimeRules=new ArrayList<RuntimeRule>();
		passiveRules=new ArrayList<PassiveRule>();
	}
	
	public static RuleSet generateCustomRuleSet(Map gameMap,
												RandomGenerateMode terrainGenerateMode,
												int playerCount,
												int[] wormsToStartWith,
												String[] pathsToTextures,
												String[] teamNames,
												double roundLengthInSeconds,
												ArrayList<ArrayList<Weapon>> weaponsPerPlayer){
		
		RuleSet r= new RuleSet(gameMap);
		r.startUpRules.add(new SetUpPlayersRule(gameMap,weaponsPerPlayer,playerCount,teamNames));
		r.startUpRules.add(new SetUpTerrainRule(gameMap,terrainGenerateMode));
		r.startUpRules.add(new SetUpWormsRule(gameMap,wormsToStartWith,playerCount,pathsToTextures));
		
		r.passiveRules.add(new RoundRule(gameMap,roundLengthInSeconds,playerCount));
		r.passiveRules.add(new WinRule(gameMap));
		
		r.runtimeRules.add(new MovementRule(gameMap));
		r.runtimeRules.add(new WeaponSelectRule(gameMap));
		r.runtimeRules.add(new ShootRule(gameMap));
		
		return r;
	}

	
	public void applyRuntimeRules() {
		for(RuntimeRule s : runtimeRules){
			s.applyRule();
		}
	}
	public void applyStartUpRules() {
		for(StartUpRule s : startUpRules){
			s.applyRule();
		}
	}
	public void applyPassiveRules() {
		for(PassiveRule s : passiveRules){
			s.applyRule();
		}
	}

	public ArrayList<StartUpRule> getStartUpRules() {
		return startUpRules;
	}

	public void setStartUpRules(ArrayList<StartUpRule> startUpRules) {
		this.startUpRules = startUpRules;
	}

	public ArrayList<RuntimeRule> getRuntimeRules() {
		return runtimeRules;
	}

	public void setRuntimeRules(ArrayList<RuntimeRule> runtimeRules) {
		this.runtimeRules = runtimeRules;
	}

	public ArrayList<PassiveRule> getPassiveRules() {
		return passiveRules;
	}

	public void setPassiveRules(ArrayList<PassiveRule> passiveRules) {
		this.passiveRules = passiveRules;
	}

	public Map getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}

	
	
}
