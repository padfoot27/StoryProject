package com.example.onlinetyari.storyproject.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.onlinetyari.storyproject.R;
import com.example.onlinetyari.storyproject.StoryProjectApp;
import com.example.onlinetyari.storyproject.pojo.Story;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Siddharth Verma on 24/5/16.
 */

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Story> mStory;
    private Context context;
    private Resources resources;

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            e.printStackTrace();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    public StoryAdapter(List<Story> mStory, Context context, Resources resources) {
        this.mStory = mStory;
        this.context = context;
        this.resources = resources;
    }

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
        storyViewHolder.posted_by.setText(story.getUser().getUsername());
        storyViewHolder.follow.setText(R.string.follow);
        Glide
                .with(context)
                .load(mStory.get(position).getSi())
                .listener(requestListener)
                .placeholder(R.drawable.drawer_image)
                .error(R.drawable.trouble_afoot)
                .crossFade()
                .into(storyViewHolder.story_image);

        storyViewHolder.story_description.setText(story.getDescription());
        storyViewHolder.likes.setText(String.format(resources.getString(R.string.likes), story.getLikes_count()));
        storyViewHolder.comments.setText(String.format(resources.getString(R.string.comments), story.getComment_count()));

        RxView.clicks(storyViewHolder.follow)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public void setImage(ImageView imageView, String imageURL) {
        Glide
                .with(context)
                .load(imageURL)
                .crossFade()
                .placeholder(R.drawable.drawer_image)
                .error(R.drawable.trouble_afoot)
                .into(imageView);
    }
}
