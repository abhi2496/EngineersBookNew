package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddDoubtActivity extends AppCompatActivity {

    EditText doubt_body,doubt_title;
    String tag;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);

        doubt_body= (EditText) findViewById(R.id.doubt_body);
        doubt_title= (EditText) findViewById(R.id.doubt_title);
        doubt_body.setGravity(Gravity.TOP);

        final Spinner spinner = (Spinner) findViewById(R.id.tags_filter_spinner);

        final String[] tags = new String[]{
                "Filter tags",
                "Java",
                "Javascript",
                "C",
                "C++",
                "Php",
                "Android",
                "SQL",
                "Dot Net MVC",
                "Compiler",
                "Operating System",
                "Web Technology"
        };

        final List<String> tagsList = new ArrayList<>(Arrays.asList(tags));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,tagsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    tag=tags[position];
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.DKGRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void add_dbt_img(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Log.d("error", "imageUri: " + imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void addDoubt(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String, RequestBody> partMap = new HashMap<>();
        if (imageUri.getPath() != null && !imageUri.getPath().isEmpty()) {
            File attachment = new File(imageUri.getPath());
            String fileName = attachment.getName();
            partMap.put("image" + "\" filename=\"" + fileName,
                    RequestBody.create(MediaType.parse("file/*"), attachment));
        }
        partMap.put("tag", RequestBody.create(MediaType.parse("text/plain"), doubt_title.getText().toString()));
        partMap.put("heading", RequestBody.create(MediaType.parse("text/plain"), doubt_title.getText().toString()));
        partMap.put("text", RequestBody.create(MediaType.parse("text/plain"), doubt_body.getText().toString()));
        partMap.put("dept_id", RequestBody.create(MediaType.parse("text/plain"), "07"));
        partMap.put("user_id", RequestBody.create(MediaType.parse("text/plain"), "2"));

        APIManager api = retrofit.create(APIManager.class);
        Call<Map<String, Object>> call = api.adddoubt(partMap);

        final ProgressDialog progressDialog = new ProgressDialog(AddDoubtActivity.this);
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
                        Toast.makeText(AddDoubtActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> content = response.body();
                        startActivity(new Intent(AddDoubtActivity.this, LoginActivity.class));
                        Gson gson = new Gson();
                    } else {
                        Toast.makeText(AddDoubtActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(AddDoubtActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(AddDoubtActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });

        }
}
