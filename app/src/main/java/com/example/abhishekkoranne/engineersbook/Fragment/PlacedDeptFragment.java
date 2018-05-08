package com.example.abhishekkoranne.engineersbook.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.abhishekkoranne.engineersbook.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class PlacedDeptFragment extends Fragment {

    TextView no_of_mech, no_of_elec, no_of_civil, no_of_comp, no_of_it, no_of_mca, no_of_ic, no_of_aero, no_of_ec;
    Spinner col_filter_spin, year_filter_spin;


    public PlacedDeptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_placed_dept, container, false);
        col_filter_spin = v.findViewById(R.id.col_filter_spin);
        year_filter_spin = v.findViewById(R.id.year_filter_spin);
        no_of_aero = (TextView) v.findViewById(R.id.no_of_aero);
        no_of_civil = (TextView) v.findViewById(R.id.no_of_civil);
        no_of_comp = (TextView) v.findViewById(R.id.no_of_comp);
        no_of_ec = (TextView) v.findViewById(R.id.no_of_ec);
        no_of_elec = (TextView) v.findViewById(R.id.no_of_elec);
        no_of_ic = (TextView) v.findViewById(R.id.no_of_ic);
        no_of_it = (TextView) v.findViewById(R.id.no_of_it);
        no_of_mca = (TextView) v.findViewById(R.id.no_of_mca);
        no_of_mech = (TextView) v.findViewById(R.id.no_of_mech);

        String col[] = {"SVIT", "Parul", "GCET"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), R.layout.spin_item, col);
        col_filter_spin.setAdapter(adapt);

        String year[] = {"2018", "2017", "2016", "2015", "2014"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getContext(), R.layout.spin_item, year);
        year_filter_spin.setAdapter(ad);
        ad.setDropDownViewResource(R.layout.spin_item);


        col_filter_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                year_filter_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                        if (position == 0 && pos == 0) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("50");
                            no_of_ec.setText("5");
                            no_of_elec.setText("15");
                            no_of_ic.setText("9");
                            no_of_it.setText("45");
                            no_of_mca.setText("20");
                            no_of_mech.setText("5");

                        }
                        if (position == 0 && pos == 1) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("40");
                            no_of_ec.setText("4");
                            no_of_elec.setText("14");
                            no_of_ic.setText("5");
                            no_of_it.setText("40");
                            no_of_mca.setText("10");
                            no_of_mech.setText("3");

                        }
                        if (position == 0 && pos == 2) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("45");
                            no_of_ec.setText("5");
                            no_of_elec.setText("15");
                            no_of_ic.setText("8");
                            no_of_it.setText("42");
                            no_of_mca.setText("15");
                            no_of_mech.setText("4");

                        }
                        if (position == 0 && pos == 3) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("46");
                            no_of_ec.setText("6");
                            no_of_elec.setText("13");
                            no_of_ic.setText("11");
                            no_of_it.setText("41");
                            no_of_mca.setText("17");
                            no_of_mech.setText("5");

                        }
                        if (position == 0 && pos == 4) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("41");
                            no_of_ec.setText("8");
                            no_of_elec.setText("10");
                            no_of_ic.setText("15");
                            no_of_it.setText("35");
                            no_of_mca.setText("15");
                            no_of_mech.setText("4");

                        } else if (position == 1 && pos == 0) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("40");
                            no_of_ec.setText("10");
                            no_of_elec.setText("10");
                            no_of_ic.setText("0");
                            no_of_it.setText("40");
                            no_of_mca.setText("0");
                            no_of_mech.setText("100");

                        } else if (position == 1 && pos == 1) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("35");
                            no_of_ec.setText("12");
                            no_of_elec.setText("8");
                            no_of_ic.setText("0");
                            no_of_it.setText("35");
                            no_of_mca.setText("0");
                            no_of_mech.setText("90");

                        } else if (position == 1 && pos == 2) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("38");
                            no_of_ec.setText("14");
                            no_of_elec.setText("6");
                            no_of_ic.setText("0");
                            no_of_it.setText("45");
                            no_of_mca.setText("0");
                            no_of_mech.setText("80");

                        } else if (position == 1 && pos == 3) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("41");
                            no_of_ec.setText("12");
                            no_of_elec.setText("8");
                            no_of_ic.setText("0");
                            no_of_it.setText("41");
                            no_of_mca.setText("0");
                            no_of_mech.setText("70");

                        } else if (position == 1 && pos == 4) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("42");
                            no_of_ec.setText("6");
                            no_of_elec.setText("4");
                            no_of_ic.setText("0");
                            no_of_it.setText("45");
                            no_of_mca.setText("0");
                            no_of_mech.setText("60");

                        } else if (position == 2 && pos == 0) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("52");
                            no_of_ec.setText("8");
                            no_of_elec.setText("10");
                            no_of_ic.setText("0");
                            no_of_it.setText("50");
                            no_of_mca.setText("0");
                            no_of_mech.setText("8");

                        } else if (position == 2 && pos == 1) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("50");
                            no_of_ec.setText("7");
                            no_of_elec.setText("8");
                            no_of_ic.setText("0");
                            no_of_it.setText("52");
                            no_of_mca.setText("0");
                            no_of_mech.setText("4");

                        } else if (position == 2 && pos == 2) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("51");
                            no_of_ec.setText("6");
                            no_of_elec.setText("7");
                            no_of_ic.setText("0");
                            no_of_it.setText("53");
                            no_of_mca.setText("0");
                            no_of_mech.setText("6");

                        } else if (position == 2 && pos == 3) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("54");
                            no_of_ec.setText("4");
                            no_of_elec.setText("5");
                            no_of_ic.setText("0");
                            no_of_it.setText("55");
                            no_of_mca.setText("0");
                            no_of_mech.setText("7");

                        } else if (position == 2 && pos == 4) {

                            no_of_aero.setText("0");
                            no_of_civil.setText("0");
                            no_of_comp.setText("56");
                            no_of_ec.setText("4");
                            no_of_elec.setText("8");
                            no_of_ic.setText("0");
                            no_of_it.setText("55");
                            no_of_mca.setText("0");
                            no_of_mech.setText("3");

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        col_filter_spin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                year_filter_spin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {



                    }
                });

            }
        });
*/
        // Inflate the layout for this fragment
        return v;

    }
}