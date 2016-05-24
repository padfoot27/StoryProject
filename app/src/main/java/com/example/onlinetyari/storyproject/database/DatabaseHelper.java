package com.example.onlinetyari.storyproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperInterface {

    private static final String DATABASE_NAME = "database";
    private static final Integer DATABASE_VERSION = 1;

    private static final String TABLE_STORY = "story";
    private static final String TABLE_USER = "user";

    // Story Table Columns
    public static final String KEY_STORY_PRIMARY_ID = "id";
    public static final String KEY_STORY_DESCRIPTION = "description";
    public static final String KEY_STORY_ID = "idStory";
    public static final String KEY_STORY_VERB = "verb";
    public static final String KEY_STORY_DB = "db";
    public static final String KEY_STORY_URL = "url";
    public static final String KEY_STORY_SI = "si";
    public static final String KEY_STORY_TYPE = "type";
    public static final String KEY_STORY_TITLE = "title";
    public static final String KEY_STORY_LIKE_FLAG = "like_flag";
    public static final String KEY_STORY_LIKES_COUNT = "likes_count";
    public static final String KEY_STORY_COMMENT_COUNT = "comment_count";

    // User Table Columns
    public static final String KEY_USER_PRIMARY_ID = "id";
    public static final String KEY_USER_ABOUT =  "about";
    public static final String KEY_USER_ID = "idUser";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_FOLLOWERS = "followers";
    public static final String KEY_USER_FOLLOWING =  "following";
    public static final String KEY_USER_IMAGE = "image";
    public static final String KEY_USER_URL = "url";
    public static final String KEY_USER_HANDLE = "handle";
    public static final String KEY_USER_IS_FOLLOWING = "is_following";
    public static final String KEY_USER_CREATED_ON = "createdOn";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STORY_TABLE = "CREATE TABLE " + TABLE_STORY +
                "(" +
                KEY_STORY_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                KEY_STORY_DESCRIPTION + " TEXT, " +
                KEY_STORY_ID + " TEXT, " +
                KEY_STORY_VERB + " TEXT, " +
                KEY_STORY_DB + " TEXT, " +
                KEY_STORY_URL + " TEXT, " +
                KEY_STORY_SI + " TEXT, " +
                KEY_STORY_TYPE + " TEXT, " +
                KEY_STORY_TITLE + " TEXT, " +
                KEY_STORY_LIKE_FLAG + " YES/NO, " +
                KEY_STORY_LIKES_COUNT + " INTEGER, " +
                KEY_STORY_COMMENT_COUNT + " INTEGER" +
                ")";

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                KEY_USER_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                KEY_USER_ABOUT + " TEXT, " +
                KEY_USER_ID + " TEXT, " +
                KEY_USER_USERNAME + " TEXT, " +
                KEY_USER_FOLLOWERS + " INTEGER, " +
                KEY_USER_FOLLOWING + " INTEGER, " +
                KEY_USER_IMAGE + " TEXT, " +
                KEY_USER_URL + " TEXT, " +
                KEY_USER_HANDLE + " TEXT, " +
                KEY_USER_IS_FOLLOWING + " YES/NO, " +
                KEY_USER_CREATED_ON + " LONG" +
                ")";

        db.execSQL(CREATE_STORY_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }
    }

    @Override
    public void addStory(Story story) {

    }

    @Override
    public Observable<List<Story>> getAllStories() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }
}
