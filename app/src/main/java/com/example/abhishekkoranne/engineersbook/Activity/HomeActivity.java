package com.example.abhishekkoranne.engineersbook.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.abhishekkoranne.engineersbook.Fragment.ChatFragment;
import com.example.abhishekkoranne.engineersbook.Fragment.MiscellaneousFragment;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.Fragment.ArticlesFragment;
import com.example.abhishekkoranne.engineersbook.Fragment.DoubtsFragment;
import com.example.abhishekkoranne.engineersbook.Fragment.NotificationFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class HomeActivity extends AppCompatActivity {

    ViewPager my_pager;
    TabLayout my_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        my_pager = (ViewPager) findViewById(R.id.my_pager);
        my_tabs = (TabLayout) findViewById(R.id.my_tabs);

        my_pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        my_tabs.setupWithViewPager(my_pager);

        setUpTabIcons();
    }

    private void setUpTabIcons() {
        my_tabs.getTabAt(0).setIcon(R.drawable.ic_description_white_24dp);
        my_tabs.getTabAt(1).setIcon(R.drawable.ic_live_help_white_24dp);
        my_tabs.getTabAt(2).setIcon(R.drawable.ic_chat_bubble_outline_white_24dp);
        my_tabs.getTabAt(3).setIcon(R.drawable.ic_dehaze_white_24dp);

        my_tabs.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        my_tabs.getTabAt(1).getIcon().setColorFilter(Color.rgb(216,216,216), PorterDuff.Mode.SRC_IN);
        my_tabs.getTabAt(2).getIcon().setColorFilter(Color.rgb(216,216,216), PorterDuff.Mode.SRC_IN);
        my_tabs.getTabAt(3).getIcon().setColorFilter(Color.rgb(216,216,216), PorterDuff.Mode.SRC_IN);

        my_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                my_pager.setCurrentItem(tab.getPosition()); //when we have to change frag by clicking tabs
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.rgb(216,216,216), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs = {"", "", "", ""};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new ArticlesFragment();
            else if (position == 1) return new DoubtsFragment();
            else if (position == 2) return new ChatFragment();
            else if(position == 3) return new MiscellaneousFragment();
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

}