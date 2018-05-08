package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        etemail=(EditText)findViewById(R.id.etemail);
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);
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

    public void onSearchAccount(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("email", etemail.getText().toString());

        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, Object>> call = api.forgotPass(params);

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
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
                        startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordTokenActivity.class));

                        /*JsonElement res = content.get("response");
                        JsonElement mes = content.get("message");
                        Boolean resp = res.getAsJsonObject().get("response").getAsBoolean();
                        String mssg = "" + res.getAsJsonObject().get("message");
                        Log.d("error", "resp:" + resp);
                        Log.d("error", "mes:" + mes);
                        if (resp==false) {
                            Toast.makeText(LoginActivity.this, "" + mssg, Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences shad = getSharedPreferences("cookie", Context.MODE_PRIVATE);


                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
*/
                        //content.get("email").getAsString();
                        //content.get("password").getAsString();

                        // Convert JSONArray to your custom model class list as follow

                        //new TypeToken<List<YourModelClass>>(){}.getType());
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(ForgotPasswordActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(ForgotPasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
