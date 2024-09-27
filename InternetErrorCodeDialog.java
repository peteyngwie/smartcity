package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;




public class InternetErrorCodeDialog extends Dialog {


    private TextView tverrorip;
    private EditText editip ;

    // private RadioGroup classification_rg;
    // private RadioButton rb1, rb2;
    // private Button cfmbtn, cnlbtn;

    public InternetErrorCodeDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.setContentView(R.layout.interneterrorcodedialog);  // ip address input dialog

        Log.d(TAG,"XXXXXXXXXXXXXXXXXXXXXXXX") ;

        initView();             // view initialization
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

                dismiss();  // close the dialog
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {

        // this function which initializes those components in dialog

       //  cfmbtn     = findViewById(R.id.btn_confirmip);
       //  cnlbtn     = findViewById(R.id.btn_cancelip);
        editip     = findViewById(R.id.editinputip);
        tverrorip  = findViewById(R.id.ipinputerrortxt);

    }
    private void addListener(Context context)    {

        String ipaddress ;
        ipaddress = editip.getText().toString() ;

        //  the confirmation button click event listener

        /*
        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if  ( editip.getText().toString().equals("192.168.100.55") == true )
                {
                    Log.d(TAG,"IP: " + ipaddress.toString()) ;
                    tverrorip.setText("");
                    editip.setText("");  // clear edit contents
                    Toast.makeText(context, "正確 !", Toast.LENGTH_SHORT).show();
                    IpaddressInputConfirmDialog ipaddressInputConfirmDialog = new IpaddressInputConfirmDialog(v.getContext());
                    ipaddressInputConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    ipaddressInputConfirmDialog.show();

                    if (true) {   // dialog animation

                        WindowManager.LayoutParams layoutParams = ipaddressInputConfirmDialog.getWindow().getAttributes();
                        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        ipaddressInputConfirmDialog.getWindow().setAttributes(layoutParams);
                        //设置dialog进入的动画效果
                        Window window = ipaddressInputConfirmDialog.getWindow();
                        window.setWindowAnimations(R.style.AnimLeft);
                    }

                    // dismiss();
                }
                else {

                    Toast.makeText(context, "錯誤!", Toast.LENGTH_SHORT).show();
                    tverrorip.setText("IP位址錯誤，請重新輸入。");  // this is an error message that shows

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){

                        @Override
                        public void run() {

                            //過三秒後要做的事情
                            Log.d(TAG,"訊息消失!");
                            tverrorip.setText("");  // this is an error message that shows

                        }}, 3000);

                    editip.setText("");  // clear edit contents

                }
            }
        });

         */

        // cancel button click event listener
        /*
        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the dialog
                dismiss();

            }
        });

         */
    }
}
