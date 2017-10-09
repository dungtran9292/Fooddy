package com.example.hoang.fooddy.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoang.fooddy.CustomItemClickListener;
import com.example.hoang.fooddy.R;

/**
 * Created by VT-99 on 09/10/2017.
 */

public class DialogUtil implements View.OnClickListener {

    CustomItemClickListener listener;
    Context mContext;

    public DialogUtil(Context mContext) {
        this.mContext = mContext;
    }

    public Dialog createDialogInternet() {
        // Internet Dialog
        Dialog dialogConfirm = new Dialog(mContext, R.style.Theme_Dialog);
        dialogConfirm.setContentView(R.layout.dialog_notify_internet);
        TextView tv_title = (TextView) dialogConfirm.findViewById(R.id.title);
        TextView tv_message = (TextView) dialogConfirm.findViewById(R.id.message);
        Button btn_left = (Button) dialogConfirm.findViewById(R.id.btn_dong);
        Button btn_right = (Button) dialogConfirm.findViewById(R.id.btn_ok);
        dialogConfirm.setCanceledOnTouchOutside(false);
        dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        return dialogConfirm;
    }


    public Dialog createDialogConfirm() {
        // Internet Dialog
        Dialog dialogConfirm = new Dialog(mContext, R.style.Theme_Dialog);
        dialogConfirm.setContentView(R.layout.dialog_confirm);
        Button btn_cancel = (Button) dialogConfirm.findViewById(R.id.btn_cancel);
        Button btn_save = (Button) dialogConfirm.findViewById(R.id.btn_save);
        dialogConfirm.setCanceledOnTouchOutside(false);
        dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        return dialogConfirm;
    }

    public void onClickAllDialog(CustomItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dong:
                listener.onItemClick(v);
                break;
            case R.id.btn_ok:
                listener.onItemClickButton(v, 1);
                break;
            case R.id.btn_cancel:
                listener.leftClick(v);
                break;
            case R.id.btn_save:
                listener.rightClick(v,3);
                break;
        }
    }
}
