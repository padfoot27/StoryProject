package com.example.onlinetyari.storyproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.onlinetyari.storyproject.R;
import com.example.onlinetyari.storyproject.StoryProjectApp;
import com.example.onlinetyari.storyproject.pojo.Story;

import java.util.List;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Story> mStory;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View storyView = inflater.inflate(R.layout.story_item, parent, false);

        // Return a new holder instance
        return new StoryViewHolder(storyView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        StoryViewHolder storyViewHolder = (StoryViewHolder) holder;

        Story story = mStory.get(position);

        storyViewHolder.title.setText(story.getTitle());
        storyViewHolder.posted_by.setText(story.getDb());
        setImage(storyViewHolder.story_image, story.getSi());
        storyViewHolder.story_description.setText(story.getDescription());
        storyViewHolder.likes.setText(story.getLikes_count());
        storyViewHolder.comments.setText(story.getComment_count());
    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public void setImage(ImageView imageView, String imageURL) {
        Glide
                .with(StoryProjectApp.getAppContext())
                .load(imageURL)
                .crossFade()
                .into(imageView);
    }
}
