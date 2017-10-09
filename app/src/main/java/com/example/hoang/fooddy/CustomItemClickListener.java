package com.example.hoang.fooddy;

import android.view.View;

/**
 * Created by VT-99 on 09/10/2017.
 */

public interface CustomItemClickListener {

     void onItemClick(View v);
     void onItemClickButton(View v,int position);
     void leftClick(View view);
     void rightClick(View view,int i);
}
