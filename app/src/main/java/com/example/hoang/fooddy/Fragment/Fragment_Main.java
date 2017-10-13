package com.example.hoang.fooddy.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.fooddy.Adapter.AdapterItem;
import com.example.hoang.fooddy.DAO.ItemType;
import com.example.hoang.fooddy.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Main extends Fragment {


    RecyclerView mRecyclerView;
    ArrayList<ItemType> mList;
    AdapterItem adapterItem;

    public Fragment_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
    }


    private void initUi(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position == 6) {
                    Log.d("dungtran 1", position + "");
                    return 3;
                } else {
                    Log.d("dungtran 2", position + "");
                    return 1;
                }

            }
        });

        mRecyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        ItemType item1 = new ItemType("Part 1", R.drawable.img_01, R.drawable.img_01);
        ItemType item2 = new ItemType("Part 2", R.drawable.download, R.drawable.img_01);
        ItemType item3 = new ItemType("Part 3", R.drawable.download, R.drawable.img_01);
        ItemType item4 = new ItemType("Part 4", R.drawable.download, R.drawable.img_01);
        ItemType item5 = new ItemType("Part 5", R.drawable.download, R.drawable.img_01);
        ItemType item6 = new ItemType("Part 6", R.drawable.download, R.drawable.img_01);
        ItemType item7 = new ItemType("Part 7", R.drawable.img_01, R.drawable.img_01);
        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        mList.add(item4);
        mList.add(item5);
        mList.add(item6);
        mList.add(item7);
        adapterItem = new AdapterItem(mList, getActivity());
        mRecyclerView.setAdapter(adapterItem);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
