package com.example.onlinetyari.storyproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.example.onlinetyari.storyproject.activity.StoryDetailActivity;
import com.example.onlinetyari.storyproject.activity.StoryListActivity;
import com.example.onlinetyari.storyproject.constants.IntentConstants;
import com.example.onlinetyari.storyproject.database.DatabaseHelper;
import com.example.onlinetyari.storyproject.pojo.Story;
import com.example.onlinetyari.storyproject.pojo.User;

import java.util.List;


/**
 * Created by Siddharth Verma on 24/5/16.
 */

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Story> mStory;
    private Context context;
    private Resources resources;
    public OnFollowClickedListener onFollowClickedListener;
    public static final String FOLLOW = "Follow";

    public interface OnFollowClickedListener {
        void onFollowClicked(String userID);
    }

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
        this.onFollowClickedListener = (OnFollowClickedListener) context;
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
        storyViewHolder.posted_by.setText(String.format(resources.getString(R.string.by), story.getUser().getUsername()));

        if (story.user.getIs_following() != null) {
            if (story.user.getIs_following() == 0)
                storyViewHolder.follow.setText(R.string.follow);
            else storyViewHolder.follow.setText(R.string.following);
        }

        else
            storyViewHolder.follow.setText(R.string.follow);

        Glide
                .with(context)
                .load(mStory.get(position).getSi())
                .listener(requestListener)
                .placeholder(R.drawable.images)
                .error(R.drawable.trouble_afoot)
                .into(storyViewHolder.story_image);

        storyViewHolder.story_description.setText(story.getDescription());
        storyViewHolder.likes.setText(String.format(resources.getString(R.string.likes), story.getLikes_count()));
        storyViewHolder.comments.setText(String.format(resources.getString(R.string.comments), story.getComment_count()));

        storyViewHolder.follow.setOnClickListener(v -> {
            if (storyViewHolder.follow.getText().toString().equals(FOLLOW))
                storyViewHolder.follow.setText(R.string.following);
            else storyViewHolder.follow.setText(R.string.follow);

            User user = story.getUser();

            user.setIs_following(user.is_following ^= 1);
            DatabaseHelper.getInstance(context).updateUser(user);
            user.setIs_following(user.is_following ^= 1);
            onFollowClickedListener.onFollowClicked(user.getId());
        });

        storyViewHolder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConstants.TITLE, story.getTitle());
            bundle.putString(IntentConstants.USER_ID, story.getDb());
            bundle.putInt(IntentConstants.IS_FOLLOWING, story.getUser().getIs_following());
            bundle.putString(IntentConstants.USERNAME, story.getUser().getUsername());
            bundle.putString(IntentConstants.IMAGE, story.getSi());
            bundle.putString(IntentConstants.DESCRIPTION, story.getDescription());
            bundle.putInt(IntentConstants.LIKES, story.getLikes_count());
            bundle.putInt(IntentConstants.COMMENTS, story.getComment_count());

            intent.putExtras(bundle);
            context.startActivity(intent);
        });
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
                .placeholder(R.drawable.images)
                .error(R.drawable.trouble_afoot)
                .into(imageView);
    }
}
