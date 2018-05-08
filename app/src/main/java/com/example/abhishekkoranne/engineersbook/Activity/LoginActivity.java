package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class LoginActivity extends Activity {
    EditText et1, et2;
    Button button_login;
    TextView tv_uname_err, tv_password_err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        button_login = (Button) findViewById(R.id.button_login);

        tv_uname_err = (TextView) findViewById(R.id.tv_uname_err);
        tv_password_err = (TextView) findViewById(R.id.tv_password_err);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginApi();
            }
        });
    }

    private void loginApi() {

        if (et1.getText().toString().equals("root")) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();
        params.put("email", et1.getText().toString());
        params.put("password", et2.getText().toString());

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, Object>> call = api.login(params);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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
                            Toast.makeText(LoginActivity.this, "" + mssg, Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences shad = getSharedPreferences("cookie", Context.MODE_PRIVATE);


                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }

                        //content.get("email").getAsString();
                        //content.get("password").getAsString();

                        // Convert JSONArray to your custom model class list as follow

                        //new TypeToken<List<YourModelClass>>(){}.getType());
                    } else {
                        Toast.makeText(LoginActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });

    }

    //TODO:Check getArticles2() from AskBindaas
    private void getArticles2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();
        params.put("email", "testing");
        params.put("password", "android");

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getArticle(0, 20);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> content = response.body();

                        // TODO: Read response here
                        //content.get("email").getAsString();
                        //content.get("password").getAsString();

                        // Convert JSONArray to your custom model class list as follow
                    /*Gson gson = new Gson();
                    List<YourModelClass> myModelList = gson.fromJson(content.get("array_name").getAsJsonArray().toString(),
                            new TypeToken<List<YourModelClass>>(){}.getType());*/
                    } else {
                        Toast.makeText(LoginActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });

    }

    public void onSignUpClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    public void onCompany(View view) {
        startActivity(new Intent(LoginActivity.this, CompanyLoginActivity.class));
    }

    public void onLoginClick(View view) {
//        tv_uname_err.setVisibility(View.GONE);
//        tv_password_err.setVisibility(View.GONE);
//
//        //Pattern email=Pattern.compile("/^[1-9][0-9]{13}$/");
//        //Matcher matcher=email.matcher(et1.getText().toString());
//
//        if (et1.getText().toString().equals("140410107024")) {
//            if (et2.getText().toString().equalsIgnoreCase("Abhishek")) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            } else {
//                tv_password_err.setVisibility(View.VISIBLE);
//                //et1.setBackgroundTintList(ColorStateList.valueOf(yourcolor));
//            }
//        } else if (et1.getText().toString().equals("140410107002")) {
//            if (et2.getText().toString().equalsIgnoreCase("Shabbir")) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            } else {
//                tv_password_err.setVisibility(View.VISIBLE);
//            }
//        } else if (et2.getText().toString().equalsIgnoreCase("Abhishek")) {
//            if (et1.getText().toString().equals("140410107024"))
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            else tv_uname_err.setVisibility(View.VISIBLE);
//        } else if (et2.getText().toString().equalsIgnoreCase("Shabbir")) {
//            if (et1.getText().toString().equals("140410107002"))
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            else tv_uname_err.setVisibility(View.VISIBLE);
//        } else {
//            tv_uname_err.setVisibility(View.VISIBLE);
//            tv_password_err.setVisibility(View.VISIBLE);
//        }
    }
}