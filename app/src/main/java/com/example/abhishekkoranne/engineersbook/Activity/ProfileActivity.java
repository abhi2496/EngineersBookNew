package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Adapter.ProfileArticlesAdapter;
import com.example.abhishekkoranne.engineersbook.Adapter.ProfileDoubtsAdapter;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Article;
import com.example.abhishekkoranne.engineersbook.model.Doubt;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView rv_doubts, rv_articles;
    ArrayList<Doubt> doubtList = new ArrayList<>();
    ArrayList<Article> articleList = new ArrayList<>();
    NestedScrollView sv_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv_doubts = (RecyclerView) findViewById(R.id.rv_doubts);
        rv_articles = (RecyclerView) findViewById(R.id.rv_articles);

        final TextView tv_points, tv_articles_shared, tv_doubts_asked;

        tv_points = (TextView) findViewById(R.id.tv_points);
        tv_articles_shared = (TextView) findViewById(R.id.tv_articles_shared);
        tv_doubts_asked = (TextView) findViewById(R.id.tv_doubts_asked);

        sv_profile = (NestedScrollView) findViewById(R.id.sv_profile);
        sv_profile.smoothScrollBy(0, 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, Object>> call = api.getProfileData(0, 20);
        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                        Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

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

                        JsonArray doubtListArr = content.getAsJsonArray("doubtlist");
                        JsonArray articleListArr = content.getAsJsonArray("ArticleList");

                        int doubtAsked, articlesAdded, point;
                        long timestamp;

                        point = content.get("point").getAsInt();
                        doubtAsked = content.get("doubtsAsked").getAsInt();
                        articlesAdded = content.get("articlesAdded").getAsInt();

                        tv_points.setText("" + point);
                        tv_articles_shared.setText("" + articlesAdded);
                        tv_doubts_asked.setText("" + doubtAsked);


                        String articleImageUrl, articleText;
                        int articleId, shares = 0, comments, articleType, likes;
                        for (int i = 0, m = articleListArr.size(); i < m; i++) {
                            articleId = articleListArr.get(i).getAsJsonObject().get("articleId").getAsInt();
                            likes = articleListArr.get(i).getAsJsonObject().get("likes").getAsInt();
                            articleType = articleListArr.get(i).getAsJsonObject().get("articleType").getAsInt();
                            timestamp = articleListArr.get(i).getAsJsonObject().get("createTime").getAsLong();

                            if (articleListArr.get(i).getAsJsonObject().get("articleImage") != null) {
                                articleImageUrl = articleListArr.get(i).getAsJsonObject().get("articleImage").getAsString();
                            } else {
                                articleImageUrl = null;
                            }

                            articleText = articleListArr.get(i).getAsJsonObject().get("articleText").getAsString();
                            articleList.add(new Article(timestamp, articleId, articleText, articleImageUrl, likes, shares, articleType, 0, null));
                        }

                        setArticlesAdapter();


                        String doubtHeading, doubtImageUrl, tags, doubt;
                        int doubtId, downVotes, upVotes;

                        for (int i = 0, n = doubtListArr.size(); i < n; i++) {
                            doubtId = doubtListArr.get(i).getAsJsonObject().get("doubtId").getAsInt();
                            upVotes = doubtListArr.get(i).getAsJsonObject().get("upvote").getAsInt();
                            downVotes = doubtListArr.get(i).getAsJsonObject().get("downvote").getAsInt();
                            timestamp = doubtListArr.get(i).getAsJsonObject().get("createTime").getAsLong();


                            tags = doubtListArr.get(i).getAsJsonObject().get("tag").getAsString();
                            doubt = doubtListArr.get(i).getAsJsonObject().get("text").getAsString();
                            doubtHeading = doubtListArr.get(i).getAsJsonObject().get("heading").getAsString();

                            if (doubtListArr.get(i).getAsJsonObject().get("doubtImage") != null) {
                                doubtImageUrl = doubtListArr.get(i).getAsJsonObject().get("doubtImage").getAsString();
                            } else {
                                doubtImageUrl = null;
                            }

                            doubtList.add(new Doubt(timestamp, doubtId, doubt, doubtHeading, doubtImageUrl, upVotes, downVotes, null, 0, tags));
                        }

                        setDoubtsAdapter();
                    } else {
                        Toast.makeText(ProfileActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });



/*{
    "doubtsAsked": 1,
    "response": "true",
    "noOfCorrespondingAnswers": [
        1
    ],
    "numberOfCommmentsList": [
        0
    ],
    "doubtlist": [
        {
            "doubtId": 1,
            "deptId": 7,
            "downvote": 1,
            "createTime": 0,
            "heading": "Help me",
            "text": "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letra",
            "doubtImage": null,
            "tag": "java",
            "upvote": 1
        }
    ],
    "point": 2,
    "articlesAdded": 1,
    "ArticleList": [
        {
            "deptId": 7,
            "articleId": 1,
            "likes": 1,
            "articleType": 1,
            "articleText": "hello there",
            "articleImage": null,
            "createTime": 0
        }
    ]
}*/


    }

    private void setArticlesAdapter() {
        ProfileArticlesAdapter adapt = new ProfileArticlesAdapter(this, articleList);
        rv_articles.setAdapter(adapt);
        rv_articles.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDoubtsAdapter() {
        ProfileDoubtsAdapter adapt = new ProfileDoubtsAdapter(this, doubtList);
        rv_doubts.setAdapter(adapt);
        rv_doubts.setLayoutManager(new LinearLayoutManager(this));
    }

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

}
