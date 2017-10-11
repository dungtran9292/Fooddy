package com.example.hoang.fooddy.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoang.fooddy.Adapter.AdapterItem;
import com.example.hoang.fooddy.DAO.ItemType;
import com.example.hoang.fooddy.DAO.User;
import com.example.hoang.fooddy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    Button btn, btn_confirm;
    EditText edt;
    TextView tv;
    RecyclerView mRecyclerView;
    ArrayList<ItemType> mList;
    AdapterItem adapterItem;
    DatabaseReference database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       database = FirebaseDatabase.getInstance().getReference();
        User user1 = new User("dungtran",24,1);
        writeToFirebase(user1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
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



    public void writeToFirebase(User user){
        database.child("Users").child(String.valueOf(user.getId())).setValue(user);

    }


}
