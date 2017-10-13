package com.example.hoang.fooddy.Fragment;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.fooddy.Adapter.ViewPagerAdapter;
import com.example.hoang.fooddy.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Listent extends Fragment implements MaterialTabListener{
    ViewPager viewPager;
    MaterialTabHost tabHost;
    Fragment_Listent_Part1 fragment_listent_part1;
    FragmentPart2 fragment_listent_part2;

    public Fragment_Listent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__listent, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        fragment_listent_part1 = new Fragment_Listent_Part1();
        fragment_listent_part2 = new FragmentPart2();


        adapter.addFragment(fragment_listent_part1);
        adapter.addFragment(fragment_listent_part2);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(getIcon(i))
                            .setTabListener(this)
            );
        }



    }

    private Drawable getIcon(int position) {
        switch(position) {
            case 0:
                return getActivity().getDrawable(R.drawable.doc);
            case 1:
                return getActivity().getDrawable(R.drawable.nghe);
            case 2:
                return getActivity().getDrawable(R.drawable.doc);
        }
        return null;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
