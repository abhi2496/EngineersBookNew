package com.example.abhishekkoranne.engineersbook.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishekkoranne.engineersbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacedStudentFragment extends Fragment {


    public PlacedStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_placed_student, container, false);
    }

}
