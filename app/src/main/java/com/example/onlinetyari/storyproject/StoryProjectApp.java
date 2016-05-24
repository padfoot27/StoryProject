package com.example.onlinetyari.storyproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class StoryProjectApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
