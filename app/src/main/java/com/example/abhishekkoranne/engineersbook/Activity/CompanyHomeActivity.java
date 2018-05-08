package com.example.abhishekkoranne.engineersbook.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Fragment.PlacedDeptFragment;
import com.example.abhishekkoranne.engineersbook.Fragment.PlacedHistoryFragment;
import com.example.abhishekkoranne.engineersbook.Fragment.PlacedStudentFragment;

public class CompanyHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);


        final ViewPager mypager = (ViewPager) findViewById(R.id.mypager);
        TabLayout mytabs = (TabLayout) findViewById(R.id.mytabs);

        mypager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mytabs.setupWithViewPager(mypager);
        //TASK = This code is to change fragment by tabs on tablayout
        // DOUBT : Commented then also task was completed.
        mytabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mypager.setCurrentItem(tab.getPosition()); //when we have to change frag by clicking tabs
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs = {"History", "Dept", "Student"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new PlacedHistoryFragment();
            else if (position == 1) return new PlacedDeptFragment();
            else if (position == 2) return new PlacedStudentFragment();
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
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
