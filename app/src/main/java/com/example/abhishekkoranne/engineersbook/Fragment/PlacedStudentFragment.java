package com.example.abhishekkoranne.engineersbook.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.APIManager;
import com.example.abhishekkoranne.engineersbook.Adapter.PlacedStudentsAdapter;
import com.example.abhishekkoranne.engineersbook.Constant;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.PlacedStudent;
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
public class PlacedStudentFragment extends Fragment {
    RecyclerView rv_placed_students;


    ArrayList<PlacedStudent> placedStudentList = new ArrayList<>();

    public PlacedStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_placed_student, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placedStudentList.clear();
        rv_placed_students = (RecyclerView) getActivity().findViewById(R.id.rv_placed_students);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put("comp_id",877);
        params.put("year_of_passing",2018);
        params.put("colg_id",41);
        params.put("dept_id",7);

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);
        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getPlacedStudList(params);
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

                    Map<String, Object> map = response.body();

                    // Convert JSONArray to your custom model class list as follow
                    Gson gson =  new Gson();
                    String jsonString = gson.toJson(map);

                    Log.d("error", "jsonString: " + jsonString);

                    JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                    Log.d("error", "content: " + content);

                    JsonArray placedStudArr = content.getAsJsonArray("studentInfoDeptWise");

                    int userId;
                    long enrollmentNo;
                    String fna, lna, proPic;


                    for (int i = 0, m = placedStudArr.size(); i < m; i++) {
                        JsonArray subArray = placedStudArr.get(i).getAsJsonArray();
                        userId = subArray.get(0).getAsInt();
                        fna = subArray.get(1).getAsString();
                        lna = subArray.get(2).getAsString();
                        proPic = "http://kubrickgroup.com/wp-content/uploads/2016/12/Quatum_pic.png";
                        enrollmentNo = subArray.get(4).getAsLong();
                        Log.d("error",enrollmentNo+"");
                        placedStudentList.add(new PlacedStudent(userId, fna, lna, proPic, enrollmentNo));
                    }

                    setAdapter();


                } else {
                    Toast.makeText(getActivity(), "No response available.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "No response available");
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
    }


    private void setAdapter() {
        PlacedStudentsAdapter adapt = new PlacedStudentsAdapter(getActivity(), placedStudentList);
        rv_placed_students.setAdapter(adapt);
        rv_placed_students.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }
}

