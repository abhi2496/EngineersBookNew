package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Doubt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileDoubtsAdapter extends RecyclerView.Adapter<ProfileDoubtsAdapter.ProfileDoubtsViewHolder> {
    Context cont;
    ArrayList<Doubt> doubtList = new ArrayList<>();

    public ProfileDoubtsAdapter(Context cont, ArrayList<Doubt> doubtList) {
        this.doubtList = doubtList;
        this.cont = cont;
    }

    @Override
    public ProfileDoubtsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.profile_doubts_item, parent, false);
        return new ProfileDoubtsViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ProfileDoubtsViewHolder holder, int position) {
        final Date date = new Date(doubtList.get(position).getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        holder.timestamp.setText(strDate);
        holder.no_of_upvotes.setText("" + doubtList.get(position).getUpVote());
        holder.doubt_question.setText(doubtList.get(position).getDoubtHeading());
    }

    @Override
    public int getItemCount() {
        return doubtList.size();
    }

    public class ProfileDoubtsViewHolder extends RecyclerView.ViewHolder {
        TextView doubt_question, no_of_upvotes, timestamp;

        public ProfileDoubtsViewHolder(View itemView) {
            super(itemView);
            doubt_question = (TextView) itemView.findViewById(R.id.doubt_question);
            no_of_upvotes = (TextView) itemView.findViewById(R.id.no_of_upvotes);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }
    }
}
