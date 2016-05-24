package com.example.onlinetyari.storyproject.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.onlinetyari.storyproject.R;
import com.example.onlinetyari.storyproject.StoryDeserializer;
import com.example.onlinetyari.storyproject.StoryProjectApp;
import com.example.onlinetyari.storyproject.UserDeserializer;
import com.example.onlinetyari.storyproject.adapter.StoryAdapter;
import com.example.onlinetyari.storyproject.database.DatabaseHelper;
import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding.view.RxView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryListActivity extends AppCompatActivity {

    public StoryAdapter storyAdapter;
    public RecyclerView storyRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.v("cde","123");
        storyAdapter = new StoryAdapter(new ArrayList<>(), this, getResources());
        storyRecylerView = (RecyclerView) findViewById(R.id.story_list);

        assert storyRecylerView != null;

        storyRecylerView.setLayoutManager(new LinearLayoutManager(this));
        storyRecylerView.setAdapter(storyAdapter);


        Observable<List<Story>> databaseObservable = DatabaseHelper.getInstance(StoryProjectApp.getAppContext()).getAllStories();
        Observable<List<Story>> jsonObservable = getStoryListFromJson();

        Observable.concat(databaseObservable, jsonObservable)
                .filter(stories -> !stories.isEmpty())
                .first()
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(story -> {
                    Log.v("abc", story.getTitle());
                    DatabaseHelper.getInstance(StoryProjectApp.getAppContext()).addStory(story);

                    if (story.user == null) {
                        story.user = DatabaseHelper.getInstance(StoryProjectApp.getAppContext()).getUser(story.getDb());
                    }

                    if (!storyAdapter.mStory.contains(story)) {
                        storyAdapter.mStory.add(story);
                        storyAdapter.notifyItemChanged(storyAdapter.getItemCount() - 1);
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;

        RxView.clicks(fab)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(aVoid -> {
                    addUserToDatabase();
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getJSON(Integer jsonFile) {

        Writer writer = null;

        try {
            InputStream inputStream = getResources().openRawResource(jsonFile);
            writer = new StringWriter();
            char[] buffer = new char[1024];

            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (writer != null)
            return writer.toString();

        else return null;
    }

    public Observable<List<Story>> getStoryListFromJson() {

        String storyJson = getJSON(R.raw.story);

        if (storyJson == null)
            return null;

        Type collectionType = new TypeToken<List<Story>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Story.class, new StoryDeserializer());
        Gson gson = gsonBuilder.create();
        final List<Story> storyList = gson.fromJson(storyJson, collectionType);

        return Observable.defer(() -> Observable.just(storyList));
    }

    public void addUserToDatabase() {

        String userJson = getJSON(R.raw.user);

        if (userJson == null)
            return;

        Type collectionType = new TypeToken<List<User>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer());
        Gson gson = gsonBuilder.create();
        final List<User> userList = gson.fromJson(userJson, collectionType);

        for (User user : userList) {
            DatabaseHelper.getInstance(this).addUser(user);
        }
    }
}
