package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.PlacedStudent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class PlacedStudentsAdapter extends RecyclerView.Adapter<PlacedStudentsAdapter.PlacedStudentsViewHolder> {
    Context context;
    ArrayList<PlacedStudent> placedStudentList = new ArrayList<>();
    DisplayImageOptions options;
    ImageLoader imgloader;

    public PlacedStudentsAdapter(Context context, ArrayList<PlacedStudent> placedStudentList) {
        this.context = context;
        this.placedStudentList = placedStudentList;

        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();
    }

    @Override
    public PlacedStudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View myView = myInflater.inflate(R.layout.placed_student_item, parent, false);
        return new PlacedStudentsViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(PlacedStudentsViewHolder holder, int position) {

        holder.tv_enrollment_number.setText("" + placedStudentList.get(position).getEnrollmentNumber());
        holder.user_name.setText(placedStudentList.get(position).getFna() + " " + placedStudentList.get(position).getLna());
        String userProfilePic = "http://192.168.31.247:8080/" + placedStudentList.get(position).getProPic();

        if (userProfilePic != null) {
            imgloader.displayImage(userProfilePic, holder.profile_pic, new ImageLoadingListener() {
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


    }

    @Override
    public int getItemCount() {
        return placedStudentList.size();
    }

    public class PlacedStudentsViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, tv_enrollment_number;
        ImageView profile_pic;

        public PlacedStudentsViewHolder(View itemView) {
            super(itemView);
            profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            tv_enrollment_number = (TextView) itemView.findViewById(R.id.tv_enrollment_number);
        }
    }
}
