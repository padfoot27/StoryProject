package com.example.onlinetyari.storyproject.pojo;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class User {

    public String about;
    public String id;
    public String username;
    public Integer followers;
    public Integer following;
    public String image;
    public String url;
    public String handle;
    public Boolean is_following;
    public Long createdOn;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Boolean getIs_following() {
        return is_following;
    }

    public void setIs_following(Boolean is_following) {
        this.is_following = is_following;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }
}
