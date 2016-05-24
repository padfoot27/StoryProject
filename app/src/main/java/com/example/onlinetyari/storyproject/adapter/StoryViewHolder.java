package com.example.onlinetyari.storyproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinetyari.storyproject.R;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class StoryViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView posted_by;
    Button follow;
    ImageView story_image;
    TextView story_description;
    TextView likes;
    TextView comments;

    public StoryViewHolder(View view) {
        super(view);

        title = (TextView) view.findViewById(R.id.title);
        posted_by = (TextView) view.findViewById(R.id.posted_by);
        follow = (Button) view.findViewById(R.id.follow);
        story_image = (ImageView) view.findViewById(R.id.story_image);
        story_description = (TextView) view.findViewById(R.id.story_description);
        likes = (TextView) view.findViewById(R.id.likes);
        comments = (TextView) view.findViewById(R.id.comments);
    }
}
