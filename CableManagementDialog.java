package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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


public class CableManagementDialog extends  Dialog {

        private TextView tvTempValue, tvHumiValue, tvLightValue;

        private RadioGroup classification_rg;   // classification of radio group
        private RadioButton rb1, rb2;
        private Button cfmbtn, cnlbtn;

        public CableManagementDialog(@NonNull Context context) {
            super(context, R.style.dialog_style);

            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            if (false)
            this.setContentView(R.layout.cablemngdialog);
            else
                this.setContentView(R.layout.cablemngdialog1);


            initView();             // this line which responsible for initializing all components on cable  management dialog
            addListener(context);   // define all listeners of component

        }  // the definition of constructor

        private void initView() {

            // this function which initializes those components in dialog

            cfmbtn = findViewById(R.id.confirmbtn);
            cnlbtn = findViewById(R.id.cancelbtn);

            classification_rg = (RadioGroup) findViewById(R.id.cablerg);
            rb1 = (RadioButton) findViewById(R.id.cablequeryrb);
            rb2 = (RadioButton) findViewById(R.id.cabledelrb);

        }
        private void addListener(Context context)
        {

            rb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+"rb1", Toast.LENGTH_SHORT).show();
                }
            });

            //  the confirmation button click event listener

            cfmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rb1.isChecked() == true && rb2.isChecked() == false) {

                        // find the radiobutton by returned id
                        Toast.makeText(v.getContext(), ">>>" + "纜線查詢", Toast.LENGTH_SHORT).show();

                        //  Intent intent = new Intent(v.getContext(),querycable.class);  // to get current dialog context and jump to another activity
                        //  v.getContext().startActivity(intent);

                        CablequeryDialog cablequeryDialog = new CablequeryDialog(v.getContext());
                        cablequeryDialog.show();  // query cable dialog

                        if (true) {   // dialog animation

                            WindowManager.LayoutParams layoutParams = cablequeryDialog.getWindow().getAttributes();
                            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                            cablequeryDialog.getWindow().setAttributes(layoutParams);
                            //设置dialog进入的动画效果
                            Window window = cablequeryDialog.getWindow();
                            window.setWindowAnimations(R.style.AnimLeft);
                        }

                        dismiss();   // close current dialog
                    }
                    else if ( rb2.isChecked() == true && rb1.isChecked() == false) {

                        Toast.makeText(v.getContext(), ">>>" + "纜線刪除", Toast.LENGTH_SHORT).show();

                        // the following shows two ways to delete cables.
                        // 1. input cable no, port and related fields and then display data of cable
                        // 2.
                        // it needs a popup dialog which owns 2 options

                       DeletecableoptionsDialog deletecableoptionsDialog = new DeletecableoptionsDialog(v.getContext());
                       deletecableoptionsDialog.show();

                        if (true) {   // dialog animation
                            WindowManager.LayoutParams layoutParams = deletecableoptionsDialog.getWindow().getAttributes();
                            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                            deletecableoptionsDialog.getWindow().setAttributes(layoutParams);
                            //设置dialog进入的动画效果
                            Window window = deletecableoptionsDialog.getWindow();
                            window.setWindowAnimations(R.style.AnimLeft);

                        }
                         // Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                         // v.getContext().startActivity(intent);
                        dismiss();   // close current dialog
                    }
                    else if (rb1.isChecked() == true && rb2.isChecked() == true )
                        Log.e(TAG, "有錯誤..." );
                    else ;

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
    }