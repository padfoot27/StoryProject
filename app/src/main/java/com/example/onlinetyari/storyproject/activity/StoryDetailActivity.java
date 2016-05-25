package com.example.onlinetyari.storyproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onlinetyari.storyproject.R;
import com.example.onlinetyari.storyproject.StoryProjectApp;
import com.example.onlinetyari.storyproject.adapter.StoryAdapter;
import com.example.onlinetyari.storyproject.constants.IntentConstants;
import com.example.onlinetyari.storyproject.database.DatabaseHelper;
import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;
import com.jakewharton.rxbinding.view.RxView;

public class StoryDetailActivity extends AppCompatActivity {

    private Boolean followChanged = false;
    private String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title = (TextView) findViewById(R.id.title_detail);
        TextView posted_by = (TextView) findViewById(R.id.posted_by_detail);
        Button follow = (Button) findViewById(R.id.follow_detail);
        ImageView image = (ImageView) findViewById(R.id.story_image_detail);
        TextView description = (TextView) findViewById(R.id.story_description_detail);
        TextView likes = (TextView) findViewById(R.id.likes_detail);
        TextView comments = (TextView) findViewById(R.id.comments_detail);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String titleVal = bundle.getString(IntentConstants.TITLE);
        String username = bundle.getString(IntentConstants.USERNAME);
        String imageURL = bundle.getString(IntentConstants.IMAGE);
        userID = bundle.getString(IntentConstants.USER_ID);
        User user = DatabaseHelper.getInstance(this).getUser(userID);
        String descriptionVal = bundle.getString(IntentConstants.DESCRIPTION);
        Integer isFollowing = user.getIs_following();
        Integer likesVal = bundle.getInt(IntentConstants.LIKES);
        Integer commentsVal = bundle.getInt(IntentConstants.COMMENTS);


        assert title != null;
        title.setText(titleVal);

        assert posted_by != null;
        posted_by.setText(username);


        assert follow != null;
        if (isFollowing != null) {
            if (isFollowing == 0) {
                follow.setText(R.string.follow);
            }
            else follow.setText(R.string.following);
        }

        else
            follow.setText(R.string.follow);

        assert image != null;
        Glide
                .with(this)
                .load(imageURL)
                .crossFade()
                .into(image);


        assert description != null;
        description.setText(descriptionVal);

        assert likes != null;
        likes.setText(String.format(getResources().getString(R.string.likes), likesVal));

        assert comments != null;
        comments.setText(String.format(getResources().getString(R.string.comments), commentsVal));


        follow.setOnClickListener(v -> {
            followChanged = !followChanged;
            if (follow.getText().toString().equals(StoryAdapter.FOLLOW))
                follow.setText(R.string.following);
            else follow.setText(R.string.follow);

        });
    }

    @Override
    public void onBackPressed() {
        if (followChanged) {
            followChanged = false;
            User user = DatabaseHelper.getInstance(this).getUser(userID);
            user.setIs_following(user.is_following ^= 1);
            DatabaseHelper.getInstance(StoryProjectApp.getAppContext()).updateUser(user);
        }
        super.onBackPressed();
    }

}
