package com.tra.loginscreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class IpaddressInputConfirmDialog extends Dialog {

    private Button cfmbtn ;

    public IpaddressInputConfirmDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.ipaddressinputconfirmdialog);

        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // this method which is responsible for handling the dialog's status
        // 判断触摸事件的类型和触摸位置

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect dialogRect = new Rect();
            getWindow().getDecorView().getHitRect(dialogRect);

            if (!dialogRect.contains((int) event.getX(), (int) event.getY())) {
                // 在 Dialog 外部区域单击事件
                // 执行某些操作
                dismiss();  // close the dialog
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {

        // this function which initializes those components in dialog
        cfmbtn = findViewById(R.id.btn_confirminputip);

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener
        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // ChangePassword1.mChangePassword1.finish();   // close before activity , avoid it still exists in stack

                    Toast.makeText(context, "正確 !", Toast.LENGTH_SHORT).show();
                    dismiss();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過兩秒後要做的事情
                        IpaddressInputDialog.mDialog.dismiss();    // close 前一個dialog
                        // Intent intent = new Intent(v.getContext(), SplashActivity.class);
                        // v.getContext().startActivity(intent);

                    }}, 300);

            }
        });

    }
}