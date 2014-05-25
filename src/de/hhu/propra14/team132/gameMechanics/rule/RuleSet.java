package de.hhu.propra14.team132.gameMechanics.rule;

import java.io.IOException;
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
	
	public static RuleSet generateStandardRules(Map gameMap){
		RuleSet r= new RuleSet(gameMap);
		r.startUpRules.add(new SetUpTerrainRule(gameMap,RandomGenerateMode.RAND_V1));
		r.startUpRules.add(new SetUpWormsRule(gameMap,5));
		r.passiveRules.add(new RoundRule(gameMap,5));
		r.runtimeRules.add(new MovementRule(gameMap));
		r.runtimeRules.add(new WeaponSelectRule(gameMap));
		return r;
	}
	
	public static RuleSet generateCustomRuleSet(Map gameMap,
												RandomGenerateMode terrainGenerateMode,
												int[] wormsToStartWith,
												double roundLengthInSeconds,
												ArrayList<ArrayList<Weapon>> weaponsPerPlayer){
		
		RuleSet r= new RuleSet(gameMap);
		
		r.startUpRules.add(new SetUpTerrainRule(gameMap,terrainGenerateMode));
		r.startUpRules.add(new SetUpWormsRule(gameMap,wormsToStartWith));
		r.passiveRules.add(new RoundRule(gameMap,roundLengthInSeconds));
		
		r.runtimeRules.add(new MovementRule(gameMap));
		r.runtimeRules.add(new WeaponSelectRule(gameMap));
		
		return r;
	}
	public static RuleSet generateCustomRuleSet(Map gameMap,
												RandomGenerateMode terrainGenerateMode,
												int[] wormsToStartWith,
												double roundLengthInSeconds){

		RuleSet r= new RuleSet(gameMap);
		
		r.startUpRules.add(new SetUpTerrainRule(gameMap,terrainGenerateMode));
		r.startUpRules.add(new SetUpWormsRule(gameMap,wormsToStartWith));
		r.passiveRules.add(new RoundRule(gameMap,roundLengthInSeconds));
		
		r.runtimeRules.add(new MovementRule(gameMap));
		r.runtimeRules.add(new WeaponSelectRule(gameMap));
		
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

	
	
}
