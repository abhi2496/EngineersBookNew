package com.example.abhishekkoranne.engineersbook.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Activity.AddArticleActivity;
import com.example.abhishekkoranne.engineersbook.Activity.AddDoubtActivity;
import com.example.abhishekkoranne.engineersbook.Adapter.DoubtsAdapter;
import com.example.abhishekkoranne.engineersbook.model.Answer;
import com.example.abhishekkoranne.engineersbook.model.Article;
import com.example.abhishekkoranne.engineersbook.model.Doubt;
import com.example.abhishekkoranne.engineersbook.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
public class DoubtsFragment extends Fragment {
    RecyclerView rv_doubts;
    FloatingActionButton fab_ask_doubt;
    ArrayList<User> usersList = new ArrayList<>();
//    SearchView sv_doubts;

    ArrayList<Doubt> doubtList = new ArrayList<>();


    public DoubtsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doubts, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIManager api = retrofit.create(APIManager.class);
        rv_doubts = (RecyclerView) getActivity().findViewById(R.id.rv_doubts);
        fab_ask_doubt = (FloatingActionButton) getActivity().findViewById(R.id.fab_ask_doubt);
        fab_ask_doubt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        fab_ask_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDoubtActivity.class));
            }
        });

        Call<Map<String, Object>> call = api.getdoubtlist(0, 10);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

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

                        JsonArray doubtListArr = content.getAsJsonArray("Doubtlist");
                        JsonArray userInfoArr = content.getAsJsonArray("correspondingUserInfo");

                        JsonArray numOfAnswersArr = content.getAsJsonArray("noOfCorrespondingAnswers");
                        JsonElement[] elements = new JsonElement[doubtListArr.size()];


                        int doubtId[] = new int[doubtListArr.size()];
                        String[] doubt = new String[doubtListArr.size()];
                        String[] doubtHeading = new String[doubtListArr.size()];
                        String[] doubtImageUrl = new String[doubtListArr.size()];
                        int upVotes[] = new int[doubtListArr.size()];
                        int downVotes[] = new int[doubtListArr.size()];
                        long timestamp[] = new long[doubtListArr.size()];
                        String[] tags = new String[doubtListArr.size()];


                        int numberOfAnswers[] = new int[numOfAnswersArr.size()];


                        String[] user_info = new String[userInfoArr.size()];
                        String[] userId = new String[userInfoArr.size()];
                        String[] firstName = new String[userInfoArr.size()];
                        String[] lastName = new String[userInfoArr.size()];
                        String[] profilePic = new String[userInfoArr.size()];
                        String[] userData = new String[3];


                        for (int i = 0, n = doubtListArr.size(); i < n; i++) {
                            user_info[i] = userInfoArr.get(i).getAsString();
                            userData = user_info[i].split(",");
                            userId[i] = userData[0];
                            firstName[i] = userData[1];
                            lastName[i] = userData[2];
                            profilePic[i] = userData[3];


                            elements[i] = doubtListArr.get(i);
                            doubtId[i] = elements[i].getAsJsonObject().get("doubtId").getAsInt();
                            Log.d("errorDF", "DID:" + doubtId[i]);
                            upVotes[i] = elements[i].getAsJsonObject().get("upvote").getAsInt();
                            Log.d("errorDF", "DUV:" + upVotes[i]);
                            downVotes[i] = elements[i].getAsJsonObject().get("downvote").getAsInt();
                            Log.d("errorDF", "DDV:" + downVotes[i]);
                            timestamp[i] = elements[i].getAsJsonObject().get("createTime").getAsLong();

                            numberOfAnswers[i] = numOfAnswersArr.get(i).getAsInt();

                            Log.d("errorDF", "NOA:" + numberOfAnswers[i]);
                            tags[i] = elements[i].getAsJsonObject().get("tag").getAsString();
                            doubt[i] = elements[i].getAsJsonObject().get("text").getAsString();
                            doubtHeading[i] = elements[i].getAsJsonObject().get("heading").getAsString();

                            if (elements[i].getAsJsonObject().get("doubtImage") != null) {
                                doubtImageUrl[i] = elements[i].getAsJsonObject().get("doubtImage").getAsString();
                            }

                            doubtImageUrl[i] = null;
                            usersList.add(new User(Integer.parseInt(userId[i]), profilePic[i], null, firstName[i], lastName[i], doubtImageUrl[i]));
                            doubtList.add(new Doubt(timestamp[i], doubtId[i], doubt[i], doubtHeading[i], doubtImageUrl[i], upVotes[i], downVotes[i], usersList.get(i), numberOfAnswers[i], tags[i]));
                        }

                        setAdapter();
                       /* Gson gson = new Gson();
                        String List = gson.toJson(content);
                        ArrayList<Article> articleList = gson.fromJson(List,
                                new TypeToken<java.util.List<Article>>() {
                                }.getType());

                        Log.d("error", "ArrayList:" + articleList);
*/
                        //displayArticles(articleList);

                    } else {
                        Toast.makeText(getActivity(), "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
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

//        sv_doubts= (SearchView) getActivity().findViewById(R.id.sv_doubts);
//        sv_doubts.setQueryHint("Search Questions");

/*

        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");

        ArrayList<String> doubtTagsList=new ArrayList<>();
        doubtTagsList.add("PHP");
        doubtTagsList.add("Coding");
        doubtTagsList.add("Web Techology");

        ArrayList<Answer> answerList=new ArrayList<>();
        answerList.add(new Answer(123124,1,00,"This is comment 1",13,1,"This is answer 1",user1));
        answerList.add(new Answer(21445,2,01,"This is comment 2",23,2,"This is answer 2",user2));


        doubtList.add(new Doubt(1000,1,"How to perform lambda implementation in php? ","doubt-imag-url/abc.com",12,2,user1,answerList,doubtTagsList));
        doubtList.add(new Doubt(2000,2,"Form Submission Error in PHP... ","doubt-imag-url/abc.com",15,1,user2,new ArrayList<Answer>(),doubtTagsList));
        doubtList.add(new Doubt(3000,3,"Database Connection Issue in php... ","doubt-imag-url/abc.com",100,41,user3,new ArrayList<Answer>(),doubtTagsList));
        doubtList.add(new Doubt(4000,4,"Driver not found ===> PHP ","doubt-imag-url/abc.com",19,6,user4,new ArrayList<Answer>(),doubtTagsList));
        doubtList.add(new Doubt(55000,5,"How to get started with PHP ?","doubt-imag-url/abc.com",82,0,user5,new ArrayList<Answer>(),doubtTagsList));
*/


    }

    private void setAdapter() {
        DoubtsAdapter adapt = new DoubtsAdapter(getActivity(), doubtList);
        rv_doubts.setAdapter(adapt);
        rv_doubts.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

}