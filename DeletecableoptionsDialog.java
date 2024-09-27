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


public class DeletecableoptionsDialog extends  Dialog {

    private TextView tvTempValue, tvHumiValue, tvLightValue;

    private RadioGroup classification_rg;
    private RadioButton rb1, rb2;  // radio buttons
    private Button cfmbtn, cnlbtn;

    public DeletecableoptionsDialog(@NonNull Context context) {

        super(context, R.style.dialog_style);
        this.setContentView(R.layout.deletecableoptionsdialog);
        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog
        cfmbtn = findViewById(R.id.confirmdelbtn);
        cnlbtn = findViewById(R.id.canceldelbtn);

        classification_rg= (RadioGroup) findViewById(R.id.cabledelrg);

        rb1 = (RadioButton) findViewById(R.id.cabledelrb1);
        rb2 = (RadioButton) findViewById(R.id.cabledelrb2);

    }
    private void addListener(Context context)
    {
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+"方式 1", Toast.LENGTH_SHORT).show();
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+"方式 2", Toast.LENGTH_SHORT).show();
            }
        });
        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked() == true ) {

                    // find the radiobutton by returned id
                    Toast.makeText(v.getContext(), ">>>" + "方式 1", Toast.LENGTH_SHORT).show();
                    //  Intent intent = new Intent(v.getContext(),querycable.class);  // to get current dialog context and jump to another activity
                    //  v.getContext().startActivity(intent);

                    // CablequeryDialog cablequeryDialog = new CablequeryDialog(v.getContext());
                   //  cablequeryDialog.show();  // query cable dialog

                    CablequeryFromdelDialog cablequeryFromdelDialog = new CablequeryFromdelDialog(v.getContext());
                    cablequeryFromdelDialog.show();

                    if (true) {   // dialog animation
                        WindowManager.LayoutParams layoutParams = cablequeryFromdelDialog.getWindow().getAttributes();
                        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        cablequeryFromdelDialog.getWindow().setAttributes(layoutParams);
                        //设置dialog进入的动画效果
                        Window window = cablequeryFromdelDialog.getWindow();
                        window.setWindowAnimations(R.style.AnimLeft);
                    }


                    dismiss();   // close current dialog
                }
                else if ( rb2.isChecked() == true ) {

                    Toast.makeText(v.getContext(), ">>>" + "方式 2", Toast.LENGTH_SHORT).show();

                    // the following shows two ways to delete cables.
                    // 1. input cable no, port and related fields and then display data of cable
                    // 2.
                    // it needs a popup dialog which owns 2 options. one is delete cable via input data and the other is
                    // delete it from cable data list

                    // Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                   //  v.getContext().startActivity(intent);
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
