package com.example.onlinetyari.storyproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onlinetyari.storyproject.constants.DebugConstants;
import com.example.onlinetyari.storyproject.debug.DebugHandler;
import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;

import java.util.ArrayList;
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

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }

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
                KEY_STORY_LIKE_FLAG + " INTEGER, " +
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
                KEY_USER_IS_FOLLOWING + " INTEGER, " +
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

        if (storyExists(story))
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(KEY_STORY_DESCRIPTION, story.getDescription());
            contentValues.put(KEY_STORY_ID, story.getId());
            contentValues.put(KEY_STORY_VERB, story.getVerb());
            contentValues.put(KEY_STORY_DB, story.getDb());
            contentValues.put(KEY_STORY_URL, story.getUrl());
            contentValues.put(KEY_STORY_SI, story.getSi());
            contentValues.put(KEY_STORY_TYPE, story.getType());
            contentValues.put(KEY_STORY_TITLE, story.getTitle());
            contentValues.put(KEY_STORY_LIKE_FLAG, story.getLike_flag());
            contentValues.put(KEY_STORY_LIKES_COUNT, story.getLikes_count());
            contentValues.put(KEY_STORY_COMMENT_COUNT, story.getComment_count());


            db.insertOrThrow(TABLE_STORY, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<List<Story>> getAllStories() {

        SQLiteDatabase db = getReadableDatabase();
        final List<Story> storyList = new ArrayList<>();

        // Performing inner join of Messages with Contacts on basis of matching of the foreign key of message and id of contact
        String selectQuery = String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                TABLE_STORY,
                TABLE_USER,
                TABLE_STORY,
                KEY_STORY_DB,
                TABLE_USER,
                KEY_USER_ID);

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {
                Story story = new Story();
                story.setUrl(cursor.getString(cursor.getColumnIndex(KEY_STORY_URL)));
                story.setId(cursor.getString(cursor.getColumnIndex(KEY_STORY_ID)));
                story.setComment_count(cursor.getInt(cursor.getColumnIndex(KEY_STORY_COMMENT_COUNT)));
                story.setDb(cursor.getString(cursor.getColumnIndex(KEY_STORY_DB)));
                story.setDescription(cursor.getString(cursor.getColumnIndex(KEY_STORY_DESCRIPTION)));
                story.setLike_flag(cursor.getInt(cursor.getColumnIndex(KEY_STORY_LIKE_FLAG)));
                story.setLikes_count(cursor.getInt(cursor.getColumnIndex(KEY_STORY_LIKES_COUNT)));
                story.setSi(cursor.getString(cursor.getColumnIndex(KEY_STORY_SI)));
                story.setTitle(cursor.getString(cursor.getColumnIndex(KEY_STORY_TITLE)));
                story.setType(cursor.getString(cursor.getColumnIndex(KEY_STORY_TYPE)));
                story.setVerb(cursor.getString(cursor.getColumnIndex(KEY_STORY_VERB)));

                User user = new User();
                user.setAbout(cursor.getString(cursor.getColumnIndex(KEY_USER_ABOUT)));
                user.setCreatedOn(cursor.getLong(cursor.getColumnIndex(KEY_USER_CREATED_ON)));
                user.setFollowers(cursor.getInt(cursor.getColumnIndex(KEY_USER_FOLLOWERS)));
                user.setFollowing(cursor.getInt(cursor.getColumnIndex(KEY_USER_FOLLOWING)));
                user.setHandle(cursor.getString(cursor.getColumnIndex(KEY_USER_HANDLE)));
                user.setId(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                user.setImage(cursor.getString(cursor.getColumnIndex(KEY_USER_IMAGE)));
                user.setIs_following(cursor.getInt(cursor.getColumnIndex(KEY_USER_IS_FOLLOWING)));
                user.setUrl(cursor.getString(cursor.getColumnIndex(KEY_USER_URL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USER_USERNAME)));

                story.setUser(user);
                storyList.add(story);
            }

        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return Observable.defer(() -> Observable.just(storyList));
    }

    @Override
    public void addUser(User user) {

        if (userExists(user))
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(KEY_USER_ABOUT, user.getAbout());
            contentValues.put(KEY_USER_USERNAME, user.getUsername());
            contentValues.put(KEY_USER_FOLLOWERS, user.getFollowers());
            contentValues.put(KEY_USER_FOLLOWING, user.getFollowing());
            contentValues.put(KEY_USER_IMAGE, user.getImage());
            contentValues.put(KEY_USER_URL, user.getUrl());
            contentValues.put(KEY_USER_HANDLE, user.getHandle());
            contentValues.put(KEY_USER_IS_FOLLOWING, user.getIs_following());
            contentValues.put(KEY_USER_CREATED_ON, user.getCreatedOn());

            db.insertOrThrow(TABLE_USER, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public Observable<User> getUser(String userID) {

        SQLiteDatabase db = getReadableDatabase();

        db.beginTransaction();

        String selectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                TABLE_USER,
                KEY_USER_ID);

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userID)});

        final User user = new User();

        try {

            if (cursor.moveToFirst()) {
                user.setAbout(cursor.getString(cursor.getColumnIndex(KEY_USER_ABOUT)));
                user.setCreatedOn(cursor.getLong(cursor.getColumnIndex(KEY_USER_CREATED_ON)));
                user.setFollowers(cursor.getInt(cursor.getColumnIndex(KEY_USER_FOLLOWERS)));
                user.setFollowing(cursor.getInt(cursor.getColumnIndex(KEY_USER_FOLLOWING)));
                user.setHandle(cursor.getString(cursor.getColumnIndex(KEY_USER_HANDLE)));
                user.setId(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                user.setImage(cursor.getString(cursor.getColumnIndex(KEY_USER_IMAGE)));
                user.setIs_following(cursor.getInt(cursor.getColumnIndex(KEY_USER_IS_FOLLOWING)));
                user.setUrl(cursor.getString(cursor.getColumnIndex(KEY_USER_URL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USER_USERNAME)));
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            db.endTransaction();
        }
        return Observable.defer(() -> Observable.just(user));
    }

    @Override
    public boolean storyExists(Story story) {

        SQLiteDatabase db = getReadableDatabase();

        db.beginTransaction();

        String selectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                KEY_STORY_PRIMARY_ID,
                TABLE_STORY,
                KEY_STORY_ID);

        boolean returnValue = false;

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(story.getId())});

        try {
            if (cursor.getCount() > 0) {
                returnValue = true;
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);

        } finally {
            db.endTransaction();
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return returnValue;
    }

    @Override
    public boolean userExists(User user) {

        SQLiteDatabase db = getReadableDatabase();

        db.beginTransaction();

        String selectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                KEY_USER_PRIMARY_ID,
                TABLE_USER,
                KEY_USER_ID);

        boolean returnValue = false;

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(user.getId())});

        try {
            if (cursor.getCount() > 0) {
                returnValue = true;
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            DebugHandler.LogException(DebugConstants.DATABASE_ERROR_LOG, e);

        } finally {
            db.endTransaction();
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return returnValue;
    }
}
