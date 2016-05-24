package com.example.onlinetyari.storyproject;

import com.example.onlinetyari.storyproject.pojo.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        String about = null;
        if (jsonObject.get("about") != null)
            about = jsonObject.get("about").getAsString();

        String id = null;
        if (jsonObject.get("id") != null)
            id = jsonObject.get("id").getAsString();

        String username = null;
        if (jsonObject.get("username") != null)
            username = jsonObject.get("username").getAsString();

        Integer followers = null;
        if (jsonObject.get("followers") != null)
            followers = jsonObject.get("followers").getAsInt();

        Integer following = null;
        if (jsonObject.get("following") != null)
            following = jsonObject.get("following").getAsInt();

        String image = null;
        if (jsonObject.get("image") != null)
            image = jsonObject.get("image").getAsString();

        String url = null;
        if (jsonObject.get("url") != null)
            url = jsonObject.get("url").getAsString();

        String handle = null;
        if (jsonObject.get("handle") != null)
            handle = jsonObject.get("handle").getAsString();

        Integer is_following = null;
        if (jsonObject.get("is_following") != null)
            if (jsonObject.get("is_following").getAsBoolean())
                is_following = 1;
            else is_following = 0;

        Long created_on = null;
        if (jsonObject.get("created_on") != null)
            created_on = jsonObject.get("created_on").getAsLong();

        User user = new User();
        user.setUsername(username);
        user.setUrl(url);
        user.setAbout(about);
        user.setId(id);
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setImage(image);
        user.setHandle(handle);
        user.setIs_following(is_following);
        user.setCreatedOn(created_on);

        return user;
    }
}
