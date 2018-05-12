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

import com.example.abhishekkoranne.engineersbook.Activity.ProfileActivity;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Answer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class DoubtAnswerAdapter extends RecyclerView.Adapter<DoubtAnswerAdapter.DoubtAnswerViewHolder> {
    Context cont;
    ArrayList<Answer> answerList;
    DisplayImageOptions options;
    ImageLoader imgloader;

    public DoubtAnswerAdapter(Context cont, ArrayList<Answer> answerList) {
        this.cont = cont;
        this.answerList = answerList;
        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();
    }

    @Override
    public DoubtAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.doubts_question_answers_item, parent, false);
        return new DoubtAnswerViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(DoubtAnswerViewHolder holder, int position) {

        String userProfilePic=answerList.get(position).getUser().getUserImageUrl();

        if (userProfilePic != null) {
            imgloader.displayImage("https://goo.gl/images/4BDHri", holder.profile_pic, new ImageLoadingListener() {
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
        Date date = new Date(answerList.get(position).getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        holder.user_name.setText(answerList.get(position).getUser().getFirstName().toString() + " " + answerList.get(position).getUser().getLastName().toString());
        holder.timestamp.setText(strDate);
        holder.no_of_upvotes.setText("" + answerList.get(position).getUpVote());
        holder.tv_answer.setText(""+answerList.get(position).getAnswer());

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
        return answerList.size();
    }

    public class DoubtAnswerViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_pic;
        TextView user_name, timestamp, no_of_upvotes, tv_answer;

        public DoubtAnswerViewHolder(View itemView) {
            super(itemView);
            profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            no_of_upvotes = (TextView) itemView.findViewById(R.id.no_of_upvotes);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
        }
    }
}
