package com.example.onlinetyari.storyproject;

import com.example.onlinetyari.storyproject.pojo.Story;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class StoryDeserializer implements JsonDeserializer<Story> {
    @Override
    public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        String description = null;
        if (jsonObject.get("description") != null)
            description = jsonObject.get("description").getAsString();

        String id = null;
        if (jsonObject.get("id") != null)
            id = jsonObject.get("id").getAsString();

        String verb = null;
        if (jsonObject.get("verb") != null)
            verb = jsonObject.get("verb").getAsString();

        String db = null;
        if (jsonObject.get("db") != null)
            db = jsonObject.get("db").getAsString();

        String url = null;
        if (jsonObject.get("url") != null)
            url = jsonObject.get("url").getAsString();

        String si = null;
        if (jsonObject.get("si") != null)
            si = jsonObject.get("si").getAsString();

        String type = null;
        if (jsonObject.get("type") != null)
            type = jsonObject.get("type").getAsString();

        String title = null;
        if (jsonObject.get("title") != null)
            title = jsonObject.get("title").getAsString();

        Integer like_flag = null;
        if (jsonObject.get("like_flag") != null)
            if (jsonObject.get("like_flag").getAsBoolean())
                like_flag = 1;
            else like_flag = 0;

        Integer likes_count = null;
        if (jsonObject.get("likes_count") != null)
            likes_count = jsonObject.get("likes_count").getAsInt();

        Integer comment_count = null;
        if (jsonObject.get("comment_count") != null)
            comment_count = jsonObject.get("comment_count").getAsInt();

        Story story = new Story();

        story.setDescription(description);
        story.setTitle(title);
        story.setId(id);
        story.setVerb(verb);
        story.setDb(db);
        story.setUrl(url);
        story.setLike_flag(like_flag);
        story.setLikes_count(likes_count);
        story.setSi(si);
        story.setComment_count(comment_count);
        story.setType(type);

        return story;
    }
}
