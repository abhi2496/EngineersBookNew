package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Adapter.ContactsInNetworkAdapter;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
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

public class ContactsInNetworkActivity extends AppCompatActivity {
    RecyclerView rv_search_contacts;
    ArrayList<User> usersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_in_network);

        usersList.clear();

        rv_search_contacts = (RecyclerView) findViewById(R.id.rv_search_contacts);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "1");
        params.put("dept_id", "7");

        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, Object>> call = api.getnetworklist(params);

        final ProgressDialog progressDialog = new ProgressDialog(ContactsInNetworkActivity.this);
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

                        Log.d("ErrorNWList", "onResponse: body: " + response.body());
                        Gson gson = new Gson();
                        // Read response as follow
                        Map<String, Object> map = response.body();
                        String jsonString = gson.toJson(map);
                        Log.d("error", "jsonString: " + jsonString);

                        JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                        Log.d("error", "content: " + content);

                        JsonArray studentListArr = content.getAsJsonArray("studentList");
                        JsonArray facultyListArr = content.getAsJsonArray("facultyList");

                        String firstName, lastName, profilePic;
                        int userId;


                        Log.d("eror", "TEST: " + facultyListArr);


                        for (int i = 0, m = facultyListArr.size(); i < m; i++) {
                            userId = facultyListArr.get(i).getAsJsonObject().get("userId").getAsInt();
                            Log.d("error", "ID: " + userId);

                            firstName = facultyListArr.get(i).getAsJsonObject().get("fname").getAsString();
                            Log.d("error", "FN: " + firstName);

                            lastName = facultyListArr.get(i).getAsJsonObject().get("lname").getAsString();
                            Log.d("error", "LN: " + lastName);


                            /*if (facultyListArr.get(i).getAsJsonObject().get("profilePic").isJsonNull())
                                profilePic = null;
*/                              profilePic = "http://kubrickgroup.com/wp-content/uploads/2016/12/Quatum_pic.png";

  /*                          else {
                                profilePic = facultyListArr.get(i).getAsJsonObject().get("profilePic").getAsString();
                            }
  */                          Log.d("error", "PP: " + profilePic);
                            usersList.add(new User(userId, profilePic, null, firstName, lastName, null));
                        }

                        for (int i = facultyListArr.size(), m = studentListArr.size(); i < m; i++) {
                            userId = studentListArr.get(i).getAsJsonObject().get("userId").getAsInt();
                            Log.d("error", "ID: " + userId);

                            firstName = studentListArr.get(i).getAsJsonObject().get("fname").getAsString();
                            Log.d("error", "FN: " + firstName);

                            lastName = studentListArr.get(i).getAsJsonObject().get("lname").getAsString();
                            Log.d("error", "LN: " + lastName);
                            if (studentListArr.get(i).getAsJsonObject().get("profilepic").isJsonNull())
                                profilePic = null;

                            else {
                                profilePic = studentListArr.get(i).getAsJsonObject().get("profilepic").getAsString();
                            }

                            Log.d("error", "PP: " + profilePic);
                            usersList.add(new User(userId, profilePic, null, firstName, lastName, null));
                        }

                        setAdapter();

                        // Convert JSONArray to your custom model class list as follow

                        //new TypeToken<List<YourModelClass>>(){}.getType());
                    } else {
                        Toast.makeText(ContactsInNetworkActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(ContactsInNetworkActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(ContactsInNetworkActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });

    /*    User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        usersList.add(user4);
        usersList.add(user5);*/
    }

    private void setAdapter() {
        ContactsInNetworkAdapter adapt = new ContactsInNetworkAdapter(this, usersList);
        rv_search_contacts.setAdapter(adapt);
        rv_search_contacts.setLayoutManager(new LinearLayoutManager(this));
    }
}
