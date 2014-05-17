package de.hhu.propra14.team132.gameMechanics.rule;

import com.google.gson.annotations.Expose;

import de.hhu.propra14.team132.gameMechanics.Map;

public class SetUpTeamsRule extends StartUpRule{
	@Expose SetUpWormsRule setUpWormsRule;
	public SetUpTeamsRule(Map gameMap,SetUpWormsRule setUpWormsRule) {
		super(gameMap);
		
	}

	@Override
	public void applyRule() {
		// TODO Auto-generated method stub
		
	}
	
}
