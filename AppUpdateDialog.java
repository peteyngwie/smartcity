package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class AppUpdateDialog extends Dialog {


    private Button cfmbtn, cnlbtn;

    public static Dialog mDialog;   // this is a static dialog


    public AppUpdateDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.setContentView(R.layout.appverupdatedialog);  // ip address input dialog

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

        mDialog = this ;

        cfmbtn     = findViewById(R.id.btn_confirmip);
        cnlbtn     = findViewById(R.id.btn_cancelip);

        // SystemSetting.mSystemSettingActivity.finish();   // close before activity

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             dismiss();   // close current dialog

                UpdateManager updateManager = new UpdateManager(v.getContext());
                updateManager.checkUpdateInfo(v);
        }
        });


        // cancel button click event listener
        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the dialog
                dismiss();   // disappear current dialog
            }
        });
    }


}
