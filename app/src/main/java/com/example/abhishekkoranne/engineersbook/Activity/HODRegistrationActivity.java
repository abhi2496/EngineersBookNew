package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HODRegistrationActivity extends AppCompatActivity {

    EditText et_enrollment_no, et_first_name, et_last_name, et_date_of_birth, et_address,
            et_contact_no, et_email, et_dept_id, et_dept_name, et_password, et_confirm_password;

    DisplayImageOptions options;
    ImageLoader imgloader;

    ImageView img_preview;
    Button btn_browse;
    RadioGroup rg_gender;
    RadioButton rb_male, rb_female;

    Spinner col_spin, univ_spin;
    String gender = "0", c, u;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_registration);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        img_preview = (ImageView) findViewById(R.id.img_preview);
        btn_browse = (Button) findViewById(R.id.btn_browse);

        et_enrollment_no = (EditText) findViewById(R.id.et_enrollment_no);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_date_of_birth = (EditText) findViewById(R.id.et_date_of_birth);
        et_address = (EditText) findViewById(R.id.et_address);
        et_contact_no = (EditText) findViewById(R.id.et_contact_no);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        et_dept_id=(EditText)findViewById(R.id.et_dept_id);
        et_dept_name=(EditText)findViewById(R.id.et_dept_name);

        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);

        col_spin = (Spinner) findViewById(R.id.col_spin);
        univ_spin = (Spinner) findViewById(R.id.univ_spin);

        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();

        btn_browse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


/*
        String dept[] = {"Computer", "IT", "Mechanical", "Electrical",
                "Civil", "MCA", "IC", "Aeronautical", "EC"};
        ArrayAdapter<String> adap = new ArrayAdapter<String>(HODRegistrationActivity.this, R.layout.spin_item, dept);
        dept_spin.setAdapter(adap);
*/

        String col[] = {"SVIT", "Parul", "GCET"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(HODRegistrationActivity.this, R.layout.spin_item, col);
        col_spin.setAdapter(adapt);

        String univ[] = {"GTU", "MHU", "DU"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>(HODRegistrationActivity.this, R.layout.spin_item, univ);
        univ_spin.setAdapter(ad);

 /*       dept_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d = dept_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        col_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c = col_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        univ_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                u = univ_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.rb_male) gender = "0";
                else gender = "1";
            }
        });

    }

    public void openGallery() {
        //invoke image galery using implcit intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

        startActivityForResult(intent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if reached here, all processed successfully
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imgloader.displayImage(imageUri.getPath(), img_preview, new ImageLoadingListener() {
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
                // cropImageView.setImageUriAsync(imageUri);
                //cropImageView.getCroppedImageAsync();
                Log.d("error", "imageUri: " + imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void hodreg(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, RequestBody> partMap = new HashMap<>();
        if (imageUri.getPath() != null && !imageUri.getPath().isEmpty()) {
            File attachment = new File(imageUri.getPath());
            String fileName = attachment.getName();
            partMap.put("profile_pic" + "\" filename=\"" + fileName,
                    RequestBody.create(MediaType.parse("file/*"), attachment));
        }
        partMap.put("enroll", RequestBody.create(MediaType.parse("text/plain"), et_enrollment_no.getText().toString()));
        partMap.put("fname", RequestBody.create(MediaType.parse("text/plain"), et_first_name.getText().toString()));
        partMap.put("lname", RequestBody.create(MediaType.parse("text/plain"), et_last_name.getText().toString()));
        partMap.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        partMap.put("dob", RequestBody.create(MediaType.parse("text/plain"), et_date_of_birth.getText().toString()));
        partMap.put("addr", RequestBody.create(MediaType.parse("text/plain"), et_address.getText().toString()));
        partMap.put("contact", RequestBody.create(MediaType.parse("text/plain"), et_contact_no.getText().toString()));
        partMap.put("email", RequestBody.create(MediaType.parse("text/plain"), et_email.getText().toString()));
        partMap.put("dept_id", RequestBody.create(MediaType.parse("text/plain"), et_dept_id.getText().toString()));
        partMap.put("dept_name", RequestBody.create(MediaType.parse("text/plain"), et_dept_name.getText().toString()));
        partMap.put("colg_name", RequestBody.create(MediaType.parse("text/plain"), c));
        partMap.put("univ_name", RequestBody.create(MediaType.parse("text/plain"), u));
        partMap.put("pass", RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString()));
        partMap.put("user_type", RequestBody.create(MediaType.parse("text/plain"), "hod"));

        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, Object>> call = api.registerHOD(partMap);
        final ProgressDialog progressDialog = new ProgressDialog(HODRegistrationActivity.this);
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
                        Toast.makeText(HODRegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> content = response.body();
                        startActivity(new Intent(HODRegistrationActivity.this, LoginActivity.class));

                        //content.get("email").getAsString();
                        //content.get("password").getAsString();

                        // Convert JSONArray to your custom model class list as follow
                        Gson gson = new Gson();
                        //new TypeToken<List<YourModelClass>>(){}.getType());
                    } else {
                        Toast.makeText(HODRegistrationActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(HODRegistrationActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(HODRegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
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