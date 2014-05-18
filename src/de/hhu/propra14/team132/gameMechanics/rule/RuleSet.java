package de.hhu.propra14.team132.gameMechanics.rule;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;

public class RuleSet {
	transient Map gameMap;
	@Expose ArrayList<StartUpRule> startUpRules;
	@Expose ArrayList<RuntimeRule> runtimeRules;
	@Expose ArrayList<PassiveRule> passiveRules;
	public RuleSet(Map gameMap){
		this.gameMap=gameMap;
		startUpRules=new ArrayList<StartUpRule>();
		runtimeRules=new ArrayList<RuntimeRule>();
		passiveRules=new ArrayList<PassiveRule>();
	}
	
	public static RuleSet generateStandardRules(Map gameMap){
		RuleSet r= new RuleSet(gameMap);
		r.startUpRules.add(new SetUpTerrainRule(gameMap));
		r.startUpRules.add(new SetUpWormsRule(gameMap,5));
		r.passiveRules.add(new RoundRule(gameMap,5));
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

	
	
}
