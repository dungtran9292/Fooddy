package com.example.hoang.fooddy.Activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoang.fooddy.R;
import com.example.hoang.fooddy.Util.DialogUtil;
import com.example.hoang.fooddy.Util.SharedPrefsUtil;


public class MainActivity extends BaseActivity {

    Button btn, btn_confirm;
    EditText edt;
    TextView tv;
    public static final String KEY = "text";
    DialogUtil mDialogUtil;
    Dialog dialogInternet  , dialogConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SharedPrefsUtil.getStringPreference(MainActivity.this, KEY, "").isEmpty()) {
            tv.setText(SharedPrefsUtil.getStringPreference(MainActivity.this, KEY, ""));
        }
        mDialogUtil = new DialogUtil(MainActivity.this);
        dialogInternet = mDialogUtil.createDialogInternet();
        dialogConfirm = mDialogUtil.createDialogConfirm();

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                dialogInternet.show();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.show();
            }
        });

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
    }
}
