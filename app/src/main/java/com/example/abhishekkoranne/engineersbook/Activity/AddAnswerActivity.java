package com.example.abhishekkoranne.engineersbook.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAnswerActivity extends AppCompatActivity {

    EditText et_answer;
    TextView addimg;
    Uri imageUri;
    DisplayImageOptions options;
    ImageLoader imgloader;

    ImageView img_preview;
    byte[] imgbyte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        options = new DisplayImageOptions.Builder().build();
        imgloader = ImageLoader.getInstance();

        et_answer = (EditText) findViewById(R.id.et_answer);
        addimg = (TextView) findViewById(R.id.addimg);
        img_preview = (ImageView) findViewById(R.id.img_preview);

        et_answer.setGravity(Gravity.TOP);
    }

    public void addimage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        //where to find the data
      /*  File pic_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pic_path = pic_dir.getPath();

        //get URI representation
        Uri data = Uri.parse(pic_path);
        intent.setDataAndType(data, "image*//*");
*/
        //we will invoke this activity and get something back
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                Log.d("error", "imageUri: " + imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        /*        if (resultCode == RESULT_OK) {
            //if reached here, all processed successfully
            if (requestCode == 20) {
                //address of image on SD card
                imageUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    img_preview.setImageBitmap(image);
                    img_preview.setVisibility(View.VISIBLE);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    imgbyte = byteArrayOutputStream.toByteArray();
                    *//*imageToString(image);*//*

                } catch (Exception e) {
                    Log.d("error", "File error: " + e.getStackTrace());
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
    }

    public void addanswer(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, RequestBody> partMap = new HashMap<>();
        if (imageUri.getPath() != null && !imageUri.getPath().isEmpty()) {
            File attachment = new File(imageUri.getPath());
            String fileName = attachment.getName();
            partMap.put("article_image" + "\"; filename=\"" + fileName,
                    RequestBody.create(MediaType.parse("file/*"), attachment));
        }
        partMap.put("text", RequestBody.create(MediaType.parse("text/plain"), et_answer.getText().toString()));
        partMap.put("user_id", RequestBody.create(MediaType.parse("text/plain"), "1"));
        partMap.put("dept_id", RequestBody.create(MediaType.parse("text/plain"), "7"));

        APIManager api = retrofit.create(APIManager.class);

        Call<Map<String, RequestBody>> call = api.addanswer(partMap);

        final ProgressDialog progressDialog = new ProgressDialog(AddAnswerActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String, RequestBody>>() {
            @Override
            public void onResponse(Call<Map<String, RequestBody>> call, Response<Map<String, RequestBody>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow
                    if (response != null && response.body() != null) {
                        Toast.makeText(AddAnswerActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, RequestBody> content = response.body();
                        startActivity(new Intent(AddAnswerActivity.this, LoginActivity.class));

                        //content.get("email").getAsString();
                        //content.get("password").getAsString();

                        // Convert JSONArray to your custom model class list as follow
                        Gson gson = new Gson();
                        //new TypeToken<List<YourModelClass>>(){}.getType());
                    } else {
                        Toast.makeText(AddAnswerActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(AddAnswerActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, RequestBody>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(AddAnswerActivity.this, "Failed", Toast.LENGTH_SHORT).show();

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
