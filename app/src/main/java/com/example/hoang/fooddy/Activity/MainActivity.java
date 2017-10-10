package com.example.hoang.fooddy.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoang.fooddy.Adapter.AdapterItem;
import com.example.hoang.fooddy.DAO.ItemType;
import com.example.hoang.fooddy.R;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    Button btn, btn_confirm;
    EditText edt;
    TextView tv;
    RecyclerView mRecyclerView;
    ArrayList<ItemType> mList;
    AdapterItem adapterItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ItemType item1 = new ItemType("Khám Phá", R.drawable.img_04, R.drawable.img_01);
        ItemType item2 = new ItemType("Khám Phá", R.drawable.img_04, R.drawable.img_04);
        ItemType item3 = new ItemType("Khám Phá", R.drawable.img_04, R.drawable.img_03);
        ItemType item4 = new ItemType("Khám Phá", R.drawable.img_04, R.drawable.img_02);
        ItemType item5 = new ItemType("Khám Phá", R.drawable.img_04, R.drawable.img_01);
        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        mList.add(item4);
        mList.add(item5);
        adapterItem.notifyDataSetChanged();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        edt = (EditText) findViewById(R.id.edt);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(MainActivity.this,3);
        mRecyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        adapterItem = new AdapterItem(mList,MainActivity.this);
        mRecyclerView.setAdapter(adapterItem);
//        https://stackoverflow.com/questions/29457712/how-to-set-different-columns-for-rows-in-android-gridview
    }

}
