package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.Activity.ChatActivity;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    Context cont;

    ArrayList<User> usersList = new ArrayList<>();
    DisplayImageOptions options;
    ImageLoader imgloader = ImageLoader.getInstance();

    public ContactsAdapter(Context cont, ArrayList<User> usersList) {
        this.cont = cont;
        this.usersList = usersList;
        options = new DisplayImageOptions.Builder().build();
    }

    @Override
    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.chat_contact_item, parent, false);
        return new ContactsViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(final ContactsAdapter.ContactsViewHolder holder, final int position) {
        String userName=usersList.get(position).getFirstName().toString()+" "+usersList.get(position).getLastName().toString();
        holder.user_name.setText(userName);
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
        holder.layout_contacts_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(cont, ChatActivity.class);
                // TODO: 10-05-2018 send user id here
                if(holder.getAdapterPosition()==0)
                {
                    ii.putExtra("otheruser",2);
                }
                else if (holder.getAdapterPosition()==1)
                {
                    ii.putExtra("otheruser",3);
                }
                else if (holder.getAdapterPosition()==2)
                {
                    ii.putExtra("otheruser",4);
                }
                //holder.getAdapterPosition()
                cont.startActivity(ii);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_contacts_item;
        ImageView profile_pic;
        TextView user_name;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);
            layout_contacts_item= (LinearLayout) itemView.findViewById(R.id.layout_contacts_item);
        }
    }
}
