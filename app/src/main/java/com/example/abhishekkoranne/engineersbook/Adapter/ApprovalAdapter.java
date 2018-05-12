package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Activity.ProfileActivity;
import com.example.abhishekkoranne.engineersbook.model.Student;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;



public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder> {
    Context cont;
    ArrayList<Student> studentsList = new ArrayList<>();
    DisplayImageOptions options;
    ImageLoader imgloader;

    public ApprovalAdapter(Context cont, ArrayList<Student> studentsList) {
        this.cont = cont;
        this.studentsList = studentsList;
        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();
    }

    @Override
    public ApprovalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.approval_item, parent, false);
        return new ApprovalViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ApprovalViewHolder holder, int position) {
        imgloader.displayImage("http://dl.glitter-graphics.com/pub/844/844251efzrltedz0.gif", holder.profile_pic, new ImageLoadingListener() {
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

        holder.user_name.setText(studentsList.get(position).getUser().getFirstName().toString() + " " + studentsList.get(position).getUser().getLastName().toString());
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
        holder.user_enrollment_number.setText(""+Long.parseLong(studentsList.get(position).getEnrollmentNumber()));
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ApprovalViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_pic;
        TextView user_name, user_enrollment_number;
        CheckBox cb_approve;

        public ApprovalViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_enrollment_number = (TextView) itemView.findViewById(R.id.user_enrollment_number);
            profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);

            cb_approve = (CheckBox) itemView.findViewById(R.id.cb_approve);
        }
    }
}