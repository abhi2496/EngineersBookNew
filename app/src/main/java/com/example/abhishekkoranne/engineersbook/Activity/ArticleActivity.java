package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Adapter.ArticleCommentsAdapter;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Article;
import com.example.abhishekkoranne.engineersbook.model.Comment;
import com.example.abhishekkoranne.engineersbook.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {
    RecyclerView rv_article_comments;
    NestedScrollView sv_article;
    ImageView profile_pic, image_post;
    TextView user_name, timestamp, text_post, no_of_comments, no_of_likes;
    EditText et_add_comment;
    ArrayList<Comment> commentsList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    ArticleCommentsAdapter adapt;
    DisplayImageOptions options;
    ImageLoader imgloader;
    APIManager api;
    Retrofit retrofit;
    Article article_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        et_add_comment = (EditText) findViewById(R.id.et_add_comment);
        user_name = (TextView) findViewById(R.id.user_name);
        timestamp = (TextView) findViewById(R.id.timestamp);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        image_post = (ImageView) findViewById(R.id.image_post);
        text_post = (TextView) findViewById(R.id.text_post);
        no_of_comments = (TextView) findViewById(R.id.no_of_comments);
        no_of_likes = (TextView) findViewById(R.id.no_of_likes);
        rv_article_comments = (RecyclerView) findViewById(R.id.rv_article_comments);

        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();
        sv_article = (NestedScrollView) findViewById(R.id.sv_article);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent ii = getIntent();
        article_object = ii.getParcelableExtra("article_object");
        final long timeStamp = article_object.getTime();
        String userName = article_object.getUser().getFirstName() + " " + article_object.getUser().getLastName();
        String textPost = article_object.getArticle_text_post();
        int noOfLikes = article_object.getLikes();
        int noOfComments=article_object.getComments();

        int articleID = article_object.getArticleid();
        // Constant.articleID=""+articleID;

        String userProfilePic = article_object.getUser().getUserImageUrl();

        if (userProfilePic != null) {

            imgloader.displayImage(userProfilePic, profile_pic, new ImageLoadingListener() {
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

        profile_pic.setImageResource(R.drawable.ic_person_blue_700_18dp);

/*        https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRB_tFJKizaWI-ABoGZmkAIU6x3IEJqAu-Tve8JaUF3mq1gIwTOQw*/
        imgloader.displayImage("http://kubrickgroup.com/wp-content/uploads/2016/12/Quatum_pic.png", image_post, new ImageLoadingListener() {
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIManager api = retrofit.create(APIManager.class);
        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getComment(articleID, 0, 10);
        final ProgressDialog progressDialog = new ProgressDialog(ArticleActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow4
                    if (response != null && response.body() != null) {
                        Toast.makeText(ArticleActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> map = response.body();

                        // Convert JSONArray to your custom model class list as follow
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(map);

                        Log.d("error", "jsonString: " + jsonString);

                        JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                        Log.d("error", "content: " + content);
                        //content: {"response":"true","CommentList":[{"commentId":1.0,"articleId":1.0,"text":"My comment",
                        // "createTime":0.0},{"commentId":2.0,"articleId":1.0,"text":"this is my comment","createTime":0.0},
                        // {"commentId":3.0,"articleId":1.0,"text":"my comment","createTime":1.521964146798E12}]}

                        JsonArray commentsListArr = content.getAsJsonArray("CommentList");
                        JsonArray userInfoArr = content.getAsJsonArray("UserInfo");

                        String comment, firstName, lastName, profilePic;
                        long timestamp;
                        int commentId, articleId, userId;
                        String[] userData;

                        for (int i = 0, n = userInfoArr.size(); i < n; i++) {
                            userData = userInfoArr.get(i).getAsString().split(",");
                            userId = Integer.parseInt(userData[0]);
                            firstName = userData[1];
                            lastName = userData[2];
                            profilePic = userData[3];

                            timestamp = commentsListArr.get(i).getAsJsonObject().get("createTime").getAsLong();
                            commentId = commentsListArr.get(i).getAsJsonObject().get("commentId").getAsInt();
                            articleId = commentsListArr.get(i).getAsJsonObject().get("articleId").getAsInt();

                            comment = commentsListArr.get(i).getAsJsonObject().get("text").getAsString();
                            usersList.add(new User(userId, profilePic, null, firstName, lastName, null));
                            commentsList.add(new Comment(timestamp, commentId, articleId, comment, usersList.get(i)));
                        }
                        displayComments();

                        ArrayList<Article> myArticlesList = gson.fromJson(content.get("ArticleList").getAsJsonArray().toString(),
                                new TypeToken<ArrayList<Article>>(){}.getType());

                        Log.d("error", "arrList: " + myArticlesList.get(0));
                        //displayArticles(myArticlesList);

                    } else {
                        Toast.makeText(ArticleActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(ArticleActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(ArticleActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });


        /*sv_doubts_question.fullScroll(ScrollView.FOCUS_UP);*/
        sv_article.smoothScrollTo(0, 0);


        Date date = new Date(timeStamp);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        user_name.setText(userName);
        timestamp.setText("" + strDate);
        text_post.setText(textPost);
        no_of_likes.setText("" + noOfLikes + " Likes");
        no_of_comments.setText("" + noOfComments + " Comments");
/*

        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");

        articleCommentsList.add(new Comment(1000, 1, "This is comment 1...This is a great article to read...Thanks for sharing !!", user1));
        articleCommentsList.add(new Comment(2000, 2, "This is comment 2", user2));
        articleCommentsList.add(new Comment(3000, 3, "This is comment 3", user3));
        articleCommentsList.add(new Comment(4000, 4, "This is comment 4", user4));
        articleCommentsList.add(new Comment(5000, 5, "This is comment 5", user5));


        adapt = new ArticleCommentsAdapter(this, articleCommentsList);
        rv_article_comments.setAdapter(adapt);
        rv_article_comments.setLayoutManager(new LinearLayoutManager(this));
*/


        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, ProfileActivity.class));
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, ProfileActivity.class));
            }
        });
    }

    private void displayComments() {
        adapt = new ArticleCommentsAdapter(this, commentsList);
        rv_article_comments.setAdapter(adapt);
        rv_article_comments.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addComment(View view) {
        String comment = et_add_comment.getText().toString().trim();
        if (comment.length() != 0) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL) // Bas URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Map<String, String> params = new HashMap<>();

            params.put("text", et_add_comment.getText().toString());
            params.put("article_id", ""+article_object.getArticleid());
            params.put("user_id", "2");

            APIManager api = retrofit.create(APIManager.class);

            Call<Map<String, Object>> call = api.addcomment(params);

            final ProgressDialog progressDialog = new ProgressDialog(ArticleActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    try {
                        // Read response as follow
                        if (response != null && response.body() != null) {

                            Log.d("Error", "onResponse: body: " + response.body());
                            Gson gson = new Gson();
                            // Read response as follow
                            Map<String, Object> map = response.body();
                            String jsonString = gson.toJson(map);
                            Log.d("error", "jsonString: " + jsonString);

                            JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                            Log.d("error", "content: " + content);
                            JsonElement res = content.get("response");
                            JsonElement mes = content.get("message");
                            Boolean resp = res.getAsJsonObject().get("response").getAsBoolean();
                            String mssg = "" + res.getAsJsonObject().get("message");
                            Log.d("error", "resp:" + resp);
                            Log.d("error", "mes:" + mes);

                            if (resp==false) {
                                Toast.makeText(ArticleActivity.this, "" + mssg, Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences shad = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                                String userId=shad.getString("userId","0");
                                String userType=shad.getString("userType","notype");
                                String deptId=shad.getString("deptId","0");

                                String Type=""+res.getAsJsonObject().get("userType");
                                String Id=""+res.getAsJsonObject().get("userId");


                                if(userType.equals("notype") || userId.equals("nouser"))
                                {
                                    SharedPreferences.Editor edit=shad.edit();
                                    edit.putString("userType",Type);
                                    edit.putString("userId",Id);
                                    edit.commit();
                                }

                                startActivity(new Intent(ArticleActivity.this, HomeActivity.class));
                            }

                            //content.get("email").getAsString();
                            //content.get("password").getAsString();

                            // Convert JSONArray to your custom model class list as follow

                            //new TypeToken<List<YourModelClass>>(){}.getType());
                        } else {
                            Toast.makeText(ArticleActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                            Log.d("Error", "No response available");
                        }
                    } catch (Exception e) {
                        Toast.makeText(ArticleActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "Error in reading response: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Toast.makeText(ArticleActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "onFailure: " + t.getMessage());
                }
            });

            commentsList.clear();

            call = api.getComment(article_object.getArticleid(), 0, 10);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();


            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    try {
                        // Read response as follow4
                        if (response != null && response.body() != null) {
                            Toast.makeText(ArticleActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            Log.d("Error", "onResponse: body: " + response.body());

                            // Read response as follow
                            Map<String, Object> map = response.body();

                            // Convert JSONArray to your custom model class list as follow
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(map);

                            Log.d("error", "jsonString: " + jsonString);

                            JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                            Log.d("error", "content: " + content);
                            //content: {"response":"true","CommentList":[{"commentId":1.0,"articleId":1.0,"text":"My comment",
                            // "createTime":0.0},{"commentId":2.0,"articleId":1.0,"text":"this is my comment","createTime":0.0},
                            // {"commentId":3.0,"articleId":1.0,"text":"my comment","createTime":1.521964146798E12}]}

                            JsonArray commentsListArr = content.getAsJsonArray("CommentList");
                            JsonArray userInfoArr = content.getAsJsonArray("UserInfo");

                            String comment, firstName, lastName, profilePic;
                            long timestamp;
                            int commentId, articleId, userId;
                            String[] userData;

                            for (int i = 0, n = userInfoArr.size(); i < n; i++) {
                                userData = userInfoArr.get(i).getAsString().split(",");
                                userId = Integer.parseInt(userData[0]);
                                firstName = userData[1];
                                lastName = userData[2];
                                profilePic = userData[3];

                                timestamp = commentsListArr.get(i).getAsJsonObject().get("createTime").getAsLong();
                                commentId = commentsListArr.get(i).getAsJsonObject().get("commentId").getAsInt();
                                articleId = commentsListArr.get(i).getAsJsonObject().get("articleId").getAsInt();

                                comment = commentsListArr.get(i).getAsJsonObject().get("text").getAsString();
                                usersList.add(new User(userId, profilePic, null, firstName, lastName, null));
                                commentsList.add(new Comment(timestamp, commentId, articleId, comment, usersList.get(i)));
                            }
                            displayComments();

                            ArrayList<Article> myArticlesList = gson.fromJson(content.get("ArticleList").getAsJsonArray().toString(),
                                    new TypeToken<ArrayList<Article>>(){}.getType());

                            Log.d("error", "arrList: " + myArticlesList.get(0));
                            //displayArticles(myArticlesList);

                        } else {
                            Toast.makeText(ArticleActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                            Log.d("Error", "No response available");
                        }
                    } catch (Exception e) {
                        Toast.makeText(ArticleActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "Error in reading response: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Toast.makeText(ArticleActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "onFailure: " + t.getMessage());
                }
            });


            //  User user6 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
          //  commentsList.add(new Comment(5000, 5, 2, comment, user6));
            if (adapt != null) {
                adapt.notifyDataSetChanged();
            }
            et_add_comment.setText("");
        } else {
            Toast.makeText(ArticleActivity.this, "Comment field cannot be blank...", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void displayComment(String commentId, String articleId, String text, String createTime) {
        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbir", "Bhaisaheb", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "Himalay", "Patel", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");

        ArrayList<User> x = new ArrayList<User>();
        x.add(user2);
        x.add(user3);
        x.add(user4);

        int n = 1, p = 0;
        //  int comment=Integer.parseInt(commentId);
        articleCommentsList.add(new Comment(1000, n, text, x.get(p)));
        n++;
        p++;
        adapt = new ArticleCommentsAdapter(this, articleCommentsList);
        rv_article_comments.setAdapter(adapt);
        rv_article_comments.setLayoutManager(new LinearLayoutManager(this));
        no_of_comments.setText("" + articleCommentsList.size() + " Comments");
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onLikeClick(View view) {
        int flag = 0;
        final Map<String, Object> params = new HashMap<>();
        String[] words=no_of_likes.getText().toString().split(" ");
        int upLike=Integer.parseInt(words[0])+1;
        no_of_likes.setText(""+upLike+" likes");
        if (flag == 0) {
            params.put("user_id", "1");
            params.put("type", "up");
            params.put("article_id",article_object.getArticleid());
            flag = 1;
        } else {
            flag = 0;
            params.put("user_id", "1");
            params.put("type", "down");
            params.put("article_id",article_object.getArticleid());
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(APIManager.class);
        Call<Map<String, Object>> call = api.updateLikes(params);
        final ProgressDialog progressDialog = new ProgressDialog(ArticleActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                // try {
                // Read response as follow4
                if (response != null && response.body() != null) {
                    Toast.makeText(ArticleActivity.this, "Success", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(ArticleActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}