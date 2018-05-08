package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Abhishek Koranne on 22-03-2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Context cont;
    ArrayList<Chat> chatList = new ArrayList<>();

    int myUserId;
    int otherUserId;

    public ChatAdapter(Context cont, ArrayList<Chat> chatList, int myUserId, int otherUserId) {
        this.cont = cont;
        this.chatList = chatList;
        this.myUserId = myUserId;
        this.otherUserId = otherUserId;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.layout_chat, parent, false);
        return new ChatViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Date date = new Date(chatList.get(position).getTimestamp());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm");
        String strDate= formatter.format(date);
        if(myUserId==chatList.get(holder.getAdapterPosition()).getUserId())
        {
            holder.layoutChatMe.setVisibility(View.VISIBLE);
            holder.layoutChatOther.setVisibility(View.GONE);
            holder.tvMyMessage.setText(chatList.get(position).getMessage());
            holder.tvMyTimestamp.setText(strDate);
        }
        else
        {
            holder.layoutChatMe.setVisibility(View.GONE);
            holder.layoutChatOther.setVisibility(View.VISIBLE);
            holder.tvOtherMessage.setText(chatList.get(position).getMessage());
            holder.tvOtherTimestamp.setText(strDate);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvMyMessage, tvMyTimestamp, tvOtherMessage, tvOtherTimestamp;
        View layoutChatMe, layoutChatOther;
        public ChatViewHolder(View itemView) {
            super(itemView);
            tvMyMessage=(TextView)itemView.findViewById(R.id.tvMyMessage);
            tvMyTimestamp=(TextView)itemView.findViewById(R.id.tvMyTimestamp);
            tvOtherMessage=(TextView)itemView.findViewById(R.id.tvOtherMessage);
            tvOtherTimestamp=(TextView)itemView.findViewById(R.id.tvOtherTimestamp);
            layoutChatMe=itemView.findViewById(R.id.layoutChatMe);
            layoutChatOther=itemView.findViewById(R.id.layoutChatOther);
        }
    }
}
