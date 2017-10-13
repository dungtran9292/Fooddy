package com.example.hoang.fooddy.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.hoang.fooddy.Adapter.AdapterItem;
import com.example.hoang.fooddy.Adapter.ViewPagerAdapter;
import com.example.hoang.fooddy.DAO.ItemType;
import com.example.hoang.fooddy.Fragment.Fragment_History;
import com.example.hoang.fooddy.Fragment.Fragment_Listent;
import com.example.hoang.fooddy.Fragment.Fragment_Main;
import com.example.hoang.fooddy.Fragment.Fragment_Read;
import com.example.hoang.fooddy.Fragment.Fragment_Test;
import com.example.hoang.fooddy.R;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    RecyclerView mRecyclerView;
    ArrayList<ItemType> mList;
    AdapterItem adapterItem;
    Fragment_History fragment_history;
    Fragment_Listent fragment_listent;
    Fragment_Main fragment_main;
    Fragment_Read fragment_read;
    Fragment_Test fragment_test;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_speed:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_history:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_contact:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_contact1:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.action_bluetooth:
                                viewPager.setCurrentItem(4);
                                break;

                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initUI() {
        //setupRecyclerView();
        setupNavigationBottom();
        
    }

    private void setupNavigationBottom() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        fragment_main = new Fragment_Main();
        fragment_listent = new Fragment_Listent();
        fragment_read = new Fragment_Read();
        fragment_history = new Fragment_History();
        fragment_test = new Fragment_Test();

        adapter.addFragment(fragment_main);
        adapter.addFragment(fragment_history);
        adapter.addFragment(fragment_listent);
        adapter.addFragment(fragment_read);
        adapter.addFragment(fragment_test);

        viewPager.setAdapter(adapter);

    }
}
