package de.hhu.propra14.team132.physics;

import de.hhu.propra14.team132.physics.util.Vector2D;


public abstract class CollisionObject {
	static final int TRANSLATION_BEHAVIOUR_NORMAL=1;
	static final int TRANSLATION_BEHAVIOUR_UNMOVING=0;
	static final int TRANSLATION_BEHAVIOUR_YIELDING=2;
	
	Vector2D speed;
	Vector2D position;
	Vector2D acceleration;
	
}
