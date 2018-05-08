package com.example.abhishekkoranne.engineersbook.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacedHistoryFragment extends Fragment {

    TextView tv_no_2018, tv_no_2017, tv_no_2016, tv_no_2015, tv_no_2014;


    public PlacedHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_placed_history, container, false);
        tv_no_2014 = (TextView) v.findViewById(R.id.tv_no_2014);
        tv_no_2015 = (TextView) v.findViewById(R.id.tv_no_2015);
        tv_no_2016 = (TextView) v.findViewById(R.id.tv_no_2016);
        tv_no_2017 = (TextView) v.findViewById(R.id.tv_no_2017);
        tv_no_2018 = (TextView) v.findViewById(R.id.tv_no_2018);
        final Spinner col_filter_spin = (Spinner) v.findViewById(R.id.col_filter_spinner);

        String col[] = {"SVIT", "Parul", "GCET"};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(v.getContext(), R.layout.spin_item, col);

      adapt.setDropDownViewResource(R.layout.spin_item);
        col_filter_spin.setAdapter(adapt);

        col_filter_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    tv_no_2014.setText("20");
                    tv_no_2015.setText("25");
                    tv_no_2016.setText("18");
                    tv_no_2017.setText("24");
                    tv_no_2018.setText("30");

                } else if (position == 1) {

                    tv_no_2014.setText("30");
                    tv_no_2015.setText("35");
                    tv_no_2016.setText("28");
                    tv_no_2017.setText("24");
                    tv_no_2018.setText("50");

                } else {

                    tv_no_2014.setText("15");
                    tv_no_2015.setText("20");
                    tv_no_2016.setText("28");
                    tv_no_2017.setText("25");
                    tv_no_2018.setText("20");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position == 0) {
//
//                    tv_no_2014.setText("20");
//                    tv_no_2015.setText("25");
//                    tv_no_2016.setText("18");
//                    tv_no_2017.setText("24");
//                    tv_no_2018.setText("30");
//
//                } else if (position == 1) {
//
//                    tv_no_2014.setText("30");
//                    tv_no_2015.setText("35");
//                    tv_no_2016.setText("28");
//                    tv_no_2017.setText("24");
//                    tv_no_2018.setText("50");
//
//                } else {
//
//                    tv_no_2014.setText("15");
//                    tv_no_2015.setText("20");
//                    tv_no_2016.setText("28");
//                    tv_no_2017.setText("25");
//                    tv_no_2018.setText("20");
//
//                }
//
//            }
//        });

        // Inflate the layout for this fragment
        return v;
    }

}
