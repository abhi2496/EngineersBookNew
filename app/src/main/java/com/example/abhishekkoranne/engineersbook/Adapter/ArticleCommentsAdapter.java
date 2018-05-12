package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Activity.ProfileActivity;
import com.example.abhishekkoranne.engineersbook.model.Comment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ArticleCommentsAdapter extends RecyclerView.Adapter<ArticleCommentsAdapter.ArticleCommentsViewHolder> {
    Context cont;
    ArrayList<Comment> commentsList;
    DisplayImageOptions options;
    ImageLoader imgloader;

    public ArticleCommentsAdapter(Context cont, ArrayList<Comment> commentsList) {
        this.cont = cont;
        this.commentsList = commentsList;
        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();
    }

    @Override
    public ArticleCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.comment_item, parent, false);
        return new ArticleCommentsViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ArticleCommentsViewHolder holder, int position) {

        String userProfilePic = commentsList.get(position).getUser().getUserImageUrl();

        if (userProfilePic != null) {

            imgloader.displayImage("https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", holder.profile_pic, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }

        holder.profile_pic.setImageResource(R.drawable.ic_person_blue_700_18dp);


        Date date = new Date(commentsList.get(position).getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        String strDate = formatter.format(date);

        holder.timestamp.setText(strDate);
        holder.user_name.setText(commentsList.get(position).getUser().getFirstName().toString() + " " + commentsList.get(position).getUser().getLastName().toString());
        holder.tv_comment.setText(commentsList.get(position).getComment());

        holder.profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont.startActivity(new Intent(cont, ProfileActivity.class));
            }
        });

        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont.startActivity(new Intent(cont, ProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ArticleCommentsViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, timestamp, tv_comment;
        ImageView profile_pic;

        public ArticleCommentsViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);

            profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);
        }
    }
}
