package de.hhu.propra14.team132.gameMechanics.rule;

import java.util.ArrayList;

import de.hhu.propra14.team132.gameMechanics.Map;

public class RuleSet {
	Map gameMap;
	ArrayList<StartUpRule> startUpRules;
	ArrayList<RuntimeRule> runtimeRules;
	
	public RuleSet(Map gameMap){
		this.gameMap=gameMap;
		startUpRules=new ArrayList<StartUpRule>();
		runtimeRules=new ArrayList<RuntimeRule>();
	}
	
	public static RuleSet generateStandardRules(Map gameMap){
		RuleSet r= new RuleSet(gameMap);
		r.startUpRules.add(new SetUpTerrainRule(gameMap));
		r.startUpRules.add(new SetUpWormsRule(gameMap));
		return r;
	}
	
	public void applyRuntimeRules(){
		for(RuntimeRule s : runtimeRules){
			s.applyRule();
		}
	}
	public void applyStartUpRules(){
		for(StartUpRule s : startUpRules){
			s.applyRule();
		}
	}


	
	
}
