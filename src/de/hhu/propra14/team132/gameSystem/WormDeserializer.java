package de.hhu.propra14.team132.gameSystem;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import de.hhu.propra14.team132.gameObjects.Worm;

public class WormDeserializer implements JsonDeserializer<Worm> {

	@Override
	public Worm deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
		JsonObject j = json.getAsJsonObject();
		Gson g=new Gson();
		Worm wormToLoad = context.deserialize(json, typeOfT);
		wormToLoad.reloadTexture();
		return wormToLoad;
	}

}
