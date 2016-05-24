package com.example.onlinetyari.storyproject.database;

import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public interface DatabaseHelperInterface {

    void addStory(Story story);

    Observable<List<Story>> getAllStories();

    void addUser(User user);

    Observable<User> getUser(String userID);

    boolean storyExists(Story story);

    boolean userExists(User user);
}
