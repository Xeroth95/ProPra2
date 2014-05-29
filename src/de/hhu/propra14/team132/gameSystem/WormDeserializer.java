package de.hhu.propra14.team132.gameSystem;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import de.hhu.propra14.team132.gameObjects.GameObject;
import de.hhu.propra14.team132.gameObjects.Worm;
import de.hhu.propra14.team132.physics.Effect;

public class WormDeserializer implements JsonDeserializer<Worm> {

	@Override
	public Worm deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
		GsonBuilder gB=new GsonBuilder().setPrettyPrinting();
        gB.registerTypeAdapter(GameObject.class, new JsonAdapter<GameObject>());
    	gB.registerTypeAdapter(Effect.class, new JsonAdapter<Effect>());
    	Gson g = gB.create();
		Worm wormToLoad = g.fromJson(json, typeOfT);
		wormToLoad.reloadTexture();
		return wormToLoad;
	}

}
