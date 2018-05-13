package com.example.abhishekkoranne.engineersbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileArticlesAdapter extends RecyclerView.Adapter<ProfileArticlesAdapter.ProfileArticlesViewHolder> {
    Context cont;
    ArrayList<Article> articleList = new ArrayList<>();

    public ProfileArticlesAdapter(Context cont, ArrayList<Article> articleList) {
        this.cont = cont;
        this.articleList=articleList;
    }

    @Override
    public ProfileArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(cont);
        View myView = myInflater.inflate(R.layout.profile_articles_item, parent, false);
        return new ProfileArticlesViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(ProfileArticlesViewHolder holder, int position) {
        Date date = new Date(articleList.get(position).getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        holder.no_of_likes.setText("" + articleList.get(position).getLikes());
        holder.timestamp.setText(strDate);
        int midOfTextPost = articleList.get(position).getArticle_text_post().length() / 2;
        holder.text_post.setText("" + articleList.get(position).getArticle_text_post().substring(0, midOfTextPost) + " ...see more");
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ProfileArticlesViewHolder extends RecyclerView.ViewHolder {
        TextView text_post, no_of_likes, timestamp;

        public ProfileArticlesViewHolder(View itemView) {
            super(itemView);
            text_post = (TextView) itemView.findViewById(R.id.text_post);
            no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }
    }
}
