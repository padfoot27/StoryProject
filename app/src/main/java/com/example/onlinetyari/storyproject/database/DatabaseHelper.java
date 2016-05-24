package com.example.onlinetyari.storyproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperInterface {

    private static final String DATABASE_NAME = "database";
    private static final Integer DATABASE_VERSION = 1;

    private static final String TABLE_STORY = "story";
    private static final String TABLE_USER = "user";

    // Story Table Columns
    public static final String KEY_TABLE_PRIMARY_ID = "id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TABLE_ID = "idStory";
    public static final String KEY_VERB = "verb";
    public static final String KEY_DB = "db";
    public static final String KEY_TABLE_URL = "url";
    public static final String KEY_SI = "si";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TITLE = "title";
    public static final String KEY_LIKE_FLAG = "like_flag";
    public static final String KEY_LIKES_COUNT = "likes_count";
    public static final String KEY_COMMENT_COUNT = "comment_count";

    // User Table Columns
    public static final String KEY_USER_PRIMARY_ID = "id";
    public static final String KEY_ABOUT =  "about";
    public static final String KEY_USER_ID = "idUser";
    public static final String KEY_USERNAME = "username";
    public static final String  KEY_FOLLOWERS = "followers";
    public static final String KEY_FOLLOWING =  "following";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER_URL = "url";
    public static final String KEY_HANDLE = "handle";
    public static final String KEY_IS_FOLLOWING = "is_following";
    public static final String KEY_CREATED_ON = "createdOn";
    
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
