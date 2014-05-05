package de.hhu.propra14.team132.gameMechanics.rule;

public abstract class Rule {
	public Rule(){
		messageTypesToReactTo=defineMessageTypesToReactTo();
	}
	
	int[] messageTypesToReactTo; //TODO:change to appropriate Enum when it is implemented!
	
	protected abstract int[] defineMessageTypesToReactTo();//TODO:change to appropriate Enum when it is implemented!
	
	public abstract void applyRule();
}
