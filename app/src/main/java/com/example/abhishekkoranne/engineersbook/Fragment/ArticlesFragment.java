package com.example.abhishekkoranne.engineersbook.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Activity.AddArticleActivity;
import com.example.abhishekkoranne.engineersbook.Adapter.ArticlesAdapter;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Article;
import com.example.abhishekkoranne.engineersbook.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    /*String[] user_name;
    String[] time_stamp;
    String[] text_post;
    String[] no_of_comments;
    String[] no_of_shares;
    int[] images={R.drawable.img,R.drawable.jiggy};*/


    ArrayList<Article> articlesList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();

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
        articlesList.clear();

        rv_article = (RecyclerView) getActivity().findViewById(R.id.rv_article);
        fab_add_article = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_article);
     //   fab_add_article.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        fab_add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddArticleActivity.class));
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> params = new HashMap<>();

        params.put("user_id", "1");
        params.put("dept_id", "7");

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);
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

                    JsonArray articleListArr = content.getAsJsonArray("ArticleList");
                    JsonArray numberOfCommentsArr = content.getAsJsonArray("numberOfCommmentsList");
                    JsonArray userInfoArr = content.getAsJsonArray("UserInfo");


                    String articleImageUrl, articleText, firstName, lastName, profilePic;
                    int articleId, likes, shares = 0, comments, articleType, userId;
                    long timestamp;

                    String[] userData;

                    for (int i = 0, m = articleListArr.size(); i < m; i++) {

                        userData = userInfoArr.get(i).getAsString().split(",");
                        userId = Integer.parseInt(userData[0]);
                        firstName = userData[1];
                        lastName = userData[2];

                        if (userData[3] == null) {
                            profilePic = null;
                        } else {
                            profilePic = userData[3];
                        }
                        articleId = articleListArr.get(i).getAsJsonObject().get("articleId").getAsInt();
                        likes = articleListArr.get(i).getAsJsonObject().get("likes").getAsInt();
                        articleType = articleListArr.get(i).getAsJsonObject().get("articleType").getAsInt();
                        timestamp = articleListArr.get(i).getAsJsonObject().get("createTime").getAsLong();

                        comments = numberOfCommentsArr.get(i).getAsInt();

                        if (articleListArr.get(i).getAsJsonObject().get("articleImage") != null) {
                            articleImageUrl = articleListArr.get(i).getAsJsonObject().get("articleImage").getAsString();
                        } else {
                            articleImageUrl = null;
                        }

                        articleText = articleListArr.get(i).getAsJsonObject().get("articleText").getAsString();

                        usersList.add(new User(userId, profilePic, null, firstName, lastName, null));
                        articlesList.add(new Article(timestamp, articleId, articleText, articleImageUrl, likes, shares, articleType, comments, usersList.get(i)));
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

    /*private void displayArticles(String deptId, int articleId, int likes, String articleType, String articleText, String articleImage, String createTime, int size) {
        if (i >= size) {
            i--;
        }
        articleID[i] = articleId;


        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbir", "Bhaisaheb", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "Himalay", "Patel", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        commentList.add(new Comment(1000, 1, "This is comment 1", user1));
        commentList.add(new Comment(2000, 2, "This is comment 2", user2));
        commentList.add(new Comment(3000, 3, "This is comment 3", user3));
        commentList.add(new Comment(4000, 4, "This is comment 4", user4));
        commentList.add(new Comment(5000, 5, "This is comment 5", user5));

        articleList.add(new Article(1000, articleID[i], articleText, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fpbs.twimg.com%2Fprofile_images%2F1811310904%2Flogo100x100_SM_twitter_400x400.jpg&imgrefurl=https%3A%2F%2Ftwitter.com%2Fsomos100x100&docid=ZK72S9aXTiELUM&tbnid=-1E2q0TplBkcCM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA..i&w=400&h=400&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwg-KAAwAA&iact=mrc&uact=8", likes, 20, user1, commentList));
        i++;
    }*/

    private void setAdapter() {

        ArticlesAdapter adapt = new ArticlesAdapter(getActivity(), articlesList);
        rv_article.setAdapter(adapt);
        rv_article.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }
}