package model;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Goloconda on 2016-11-29.
 */
/*
   public class Deserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = je.getAsJsonObject().get("content");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, type);

    }
}*/
