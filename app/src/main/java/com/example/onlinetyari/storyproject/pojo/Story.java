package com.example.onlinetyari.storyproject.pojo;

import com.google.gson.annotations.Expose;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class Story {

    @Expose
    public String description;

    @Expose
    public String id;

    @Expose
    public String verb;

    @Expose
    public String db;

    @Expose
    public String url;

    @Expose
    public String si;

    @Expose
    public String type;

    @Expose
    public String title;

    @Expose
    public Integer like_flag;

    @Expose
    public Integer likes_count;

    @Expose
    public Integer comment_count;

    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLike_flag() {
        return like_flag;
    }

    public void setLike_flag(Integer like_flag) {
        this.like_flag = like_flag;
    }

    public Integer getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(Integer likes_count) {
        this.likes_count = likes_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
}
