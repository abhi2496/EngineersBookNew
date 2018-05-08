package com.example.abhishekkoranne.engineersbook.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Activity.AddArticleActivity;
import com.example.abhishekkoranne.engineersbook.Activity.HomeActivity;
import com.example.abhishekkoranne.engineersbook.Activity.LoginActivity;
import com.example.abhishekkoranne.engineersbook.Adapter.ArticlesAdapter;
import com.example.abhishekkoranne.engineersbook.model.Article;
import com.example.abhishekkoranne.engineersbook.model.Comment;

import com.example.abhishekkoranne.engineersbook.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */


public class ArticlesFragment extends Fragment {
    RecyclerView rv_article;
    FloatingActionButton fab_add_article;
    static int j = 0, i = 0;
//    Button button_like;
    int flag=0;
    APIManager api;
    Retrofit retrofit;

    /*String[] user_name;
    String[] time_stamp;
    String[] text_post;
    String[] no_of_comments;
    String[] no_of_shares;
    int[] images={R.drawable.img,R.drawable.jiggy};*/

    JSONArray arr1 = new JSONArray();
    ArrayList<Article> articleList = new ArrayList<>();
//    ArrayList<Comment> commentList = new ArrayList<>();

    // int[] comments;
    int[] articleID;

    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_articles, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleList.clear();
//        button_like = (Button) getActivity().findViewById(R.id.button_like);
        rv_article = (RecyclerView) getActivity().findViewById(R.id.rv_article);
        fab_add_article = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_article);
        fab_add_article.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        fab_add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddArticleActivity.class));
            }
        });


        final Map<String, String> params = new HashMap<>();


/*
        button_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0)
                {
                    params.put("user_id", "1");
                    params.put("type","up");
                    flag=1;
                }
                else
                {
                    flag=0;
                    params.put("user_id","1");
                    params.put("type","down");
                }
                retrofit= new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)//base URL
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api=  retrofit.create(APIManager.class);
                Call<Map<String, Object>> call=api.updatelikes(params);
            }
        });
*/
        retrofit= new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=  retrofit.create(APIManager.class);
        params.put("user_id", "1");
        params.put("dept_id", "7");

        // Initializing APIManager

        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getArticle(0, 10);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "onResponse: body: " + response.body());
                    //ArticleList=[{deptId=7.0, articleId=2.0, likes=0.0, articleType=1.0, articleText=second art,
                    // articleImage=null, createTime=0.0}, {deptId=7.0, articleId=3.0, likes=0.0, articleType=1.0,
                    // articleText=another user, articleImage=null, createTime=0.0}]

                    // Read response as follow
                    Map<String, Object> map = response.body();

                    // TODO: Read response here
                    //content.get("email").getAsString();
                    //content.get("password").getAsString();

                    // Convert JSONArray to your custom model class list as follow
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(map);

                    Log.d("error", "jsonString: " + jsonString);

                    JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                    Log.d("error", "content: " + content);

                    JsonArray arr = content.getAsJsonArray("ArticleList");
                    JsonArray numberOfCommmentsArr = content.getAsJsonArray("numberOfCommmentsList");
                    int[] comments = new int[numberOfCommmentsArr.size()];
                    articleID = new int[arr.size()];


                    //Log.d("errorJSarr1", "arr1: " + arr1.get(0).getAsInt());

                    JsonElement[] elements = new JsonElement[arr.size()];
                    //int[] commentElements = new int[arr1.size()];
                    //  Log.d("errors", "cmmnt: " + arr1.get(0));

                    String articleImage, articleText, deptId, articleType, createTime;
                    int articleId, likes;

                    /*for (int j = 0, n = arr1.size(); j < n; j++) {
                        commentElements[j] = arr1.get(j).getAsInt();
                        Log.d("error", "cmmntsji: " + commentElements[j]);
                    }*/

                    for (int i = 0, m = arr.size(); i < m; i++) {
                        comments[i] = numberOfCommmentsArr.get(i).getAsInt();
                        Log.d("error", "comments: " + comments[i]);
                        elements[i] = arr.get(i);
                        deptId = "" + elements[i].getAsJsonObject().get("deptId");
                        articleId = elements[i].getAsJsonObject().get("articleId").getAsInt();
                        likes = elements[i].getAsJsonObject().get("likes").getAsInt();
                        articleType = "" + elements[i].getAsJsonObject().get("articleType");
                        articleText = elements[i].getAsJsonObject().get("articleText").getAsString();
                        articleImage = "" + elements[i].getAsJsonObject().get("articleImage");
                        createTime = "" + elements[i].getAsJsonObject().get("createTime");

                        //      Log.d("errorID", "OBJ: " + elements[i].getAsJsonObject().get("articleId"));


                        displayArticles(deptId, comments[i], articleId, likes, articleType, articleText, articleImage, createTime, m);
                    }

                    setAdapter();

                    //   ArrayList<Article> myArticlesList = gson.fromJson(content.get("ArticleList").getAsJsonArray().toString(),
                    //   new TypeToken<ArrayList<Article>>(){}.getType());

                    //  Log.d("error", "arrList: " + myArticlesList.get(0).);
                    //     displayArticles(myArticlesList);

                } else {
                    Toast.makeText(getActivity(), "No response available.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "No response available");
                }
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
//
//                    Log.d("Error", "Error in reading response: " + e.getMessage());
//                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });

        /*call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                try
                {
                    Toast.makeText(getContext(),"Inside onResponse",Toast.LENGTH_SHORT).show();
                    List<Article> articleList = response.body();
                    Log.d("error","response : "+response.body() );
                    String[] articles=new String[articleList.size()];
                    for (int i=0; i<articleList.size(); i++)
                    {
                        articles[i]=articleList.get(i).getArticle_text_post();
                        Log.d("error","Texts : "+articles);
                    }
                }
                catch (Exception e)
                {
                    Log.d("error", "Error in reading response: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
*/




     /*   user_name=getResources().getStringArray(R.array.user_name);
        time_stamp=getResources().getStringArray(R.array.time_stamp);
        text_post=getResources().getStringArray(R.array.text_post);
        no_of_comments=getResources().getStringArray(R.array.no_of_comments);
        no_of_shares= getResources().getStringArray(R.array.no_of_shares);

        ArticlesAdapter adapt=new ArticlesAdapter(this,images,user_name,time_stamp,text_post,no_of_comments,no_of_shares);

        rv_article.setAdapter(adapt);
        rv_article.setLayoutManager(new LinearLayoutManager(this.getActivity()));*/

      /*  rv_article = (RecyclerView) getActivity().findViewById(R.id.rv_article);

        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");


        articleList.add(new Article(5555, 1, "This is post1 who you are? Hope you are doing well! Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", 0, 0, 0, user1, new ArrayList<Comment>()));


        articleList.add(new Article(5000, 2, "This is post2...How are you? Good to see after long time" +
                "Good to see after long time" + "Good to see after long time\n" +
                "        Good to see after long time\n" +
                "        Good to see after long time\n" +
                "        Good to see after long time\n" +
                "        Good to see after long time\n" +
                "        Good to see after long time."
                , "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", 5, 6, 9, user2, new ArrayList<Comment>()));


        articleList.add(new Article(1000, 3, "This is post1 who you are? Hope you are doing well! Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", 2, 10, 20, user3, new ArrayList<Comment>()));


        articleList.add(new Article(5555, 4, "This is post1 who you are? Hope you are doing well! Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", 3, 30, 40, user4, new ArrayList<Comment>()));


        articleList.add(new Article(2000, 5, "This is post1 who you are? Hope you are doing well! Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!" +
                "Hope you are doing well!", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", 10, 20, 30, user5, new ArrayList<Comment>()));
*/
    }

    private void displayArticles(String deptId, int comment, int articleId, int likes, String articleType, String articleText, String articleImage, String createTime, int size) {
        if (i >= size) {
            i--;
        }
        articleID[i] = articleId;


        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbir", "Bhaisaheb", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "Himalay", "Patel", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        /*commentList.add(new Comment(1000, 1, "This is comment 1", user1));
        commentList.add(new Comment(2000, 2, "This is comment 2", user2));
        commentList.add(new Comment(3000, 3, "This is comment 3", user3));
        commentList.add(new Comment(4000, 4, "This is comment 4", user4));
        commentList.add(new Comment(5000, 5, "This is comment 5", user5));*/

        articleList.add(new Article(1000, articleID[i], articleText, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", likes, 20, user1, comment));
        i++;
    }

    private void setAdapter() {
        try {/*
            for (int i = 0; i < comments.length; i++) {
                Log.d("error", "CMMNT ARR" + comments[i]);
            }*/
            ArticlesAdapter adapt = new ArticlesAdapter(getActivity(), articleList);
            rv_article.setAdapter(adapt);
            rv_article.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}