package de.hhu.propra14.team132.gameSystem;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class JsonAdapter <T> implements JsonDeserializer<T>, JsonSerializer<T> {
	
	private static final String CLASS_META_KEY = "CLASS_META_KEY";

    @Override
    public T deserialize(JsonElement jsonElement, Type type,JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
	    JsonObject jsonObj = jsonElement.getAsJsonObject();
	    String className = jsonObj.get(CLASS_META_KEY).getAsString();
	    try {
		    Class<?> clz = Class.forName(className);
		    return jsonDeserializationContext.deserialize(jsonElement, clz);
	    }catch (ClassNotFoundException e) {
	    	throw new JsonParseException(e);
	    }
    }

	@Override
	public JsonElement serialize(T src, Type typeOfSrc,JsonSerializationContext context) {
        JsonElement jsonEle = context.serialize(src, src.getClass());
        jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY,src.getClass().getCanonicalName());
        return jsonEle;
	}



}


