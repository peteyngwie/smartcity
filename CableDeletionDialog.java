package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CableDeletionDialog extends Dialog {

    private TextView tvTempValue, tvHumiValue, tvLightValue;

    private Button cfmbtn, cnlbtn;

    public CableDeletionDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.cabledeletiondialog);
        initView();
        addListener(context);   // define all listeners of component

    }                          // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog
        cfmbtn = findViewById(R.id.btn_confirm);
        cnlbtn = findViewById(R.id.btn_cancel);
    }
    private void addListener(Context context)
    {
        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Sure ! you delete the cable . ", Toast.LENGTH_SHORT).show();
                showCustomDialog(v); // Shows delete successfully !

                dismiss();  // close current dialog

            }
        });
        // cancel button click event listener
        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the dialog
                dismiss();
            }
        });
    }

    private void showCustomDialog(View view) {

        // Before inflating the custom alert dialog layout, we will get the current activity viewgroup

        Button okbtn ;
        Button btnsearch ;
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.delcabledialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated

        builder.setView(dialogView);
        okbtn = dialogView.findViewById(R.id.buttonOk);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (true) {   // dialog animation
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setAttributes(layoutParams);
            //设置dialog进入的动画效果
            Window window = alertDialog.getWindow();
            window.setWindowAnimations(R.style.AnimLeft);
        }


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "ok button", Toast.LENGTH_SHORT).show();

                alertDialog.cancel(); // confirmation button
    /*
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rfidtagsearch, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                //setting the view of the builder to our custom view that we already inflated
                Button btnsearch;
                builder.setView(dialogView);
                btnsearch = dialogView.findViewById(R.id.buttonsearch);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
   */
              /*
                AlertDialog.Builder builder = new AlertDialog.Builder(querycable.this);
                builder.setTitle("RFID標籤搜尋");
                builder.setMessage("編號: 0049");
                // add a button
                // builder.setPositiveButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show(); */
                //Intent intent = new Intent(querycable.this, querycable.class);
                // startActivity(intent);
                // alertOneButton(v) ;
                // finish(); //  close current activity

            }
        });

        //finally creating the alert dialog and displaying it
        // AlertDialog alertDialog = builder.create();
        //  alertDialog.show();
        //Now we need an AlertDialog.Builder object

    }
}