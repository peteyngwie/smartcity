package com.tra.loginscreen;


import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ClassificationDialog extends Dialog {

    private TextView tvTempValue, tvHumiValue, tvLightValue;

    private RadioGroup classification_rg;
    private RadioButton rb1, rb2;
    private Button cfmbtn, cnlbtn;

    public ClassificationDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.classificationdialog1);

        initView();
        addListener(context);   // define all listeners of component

        }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog
        cfmbtn = findViewById(R.id.btn_confirm);
        cnlbtn = findViewById(R.id.btn_cancel);
        classification_rg = (RadioGroup) findViewById(R.id.classrg);
        rb1 = (RadioButton) findViewById(R.id.transrb);
        rb2 = (RadioButton) findViewById(R.id.opmonrb);

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
                   Toast.makeText(v.getContext(), ">>>" + "傳輸單位", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(v.getContext(),transmissionunit.class);  // to get current dialog context and jump to another activity
                      v.getContext().startActivity(intent);

                   dismiss();   // close current dialog
                }
                  else if ( rb2.isChecked() == true && rb1.isChecked() == false) {
                      Toast.makeText(v.getContext(), ">>>" + "光纖監測", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(v.getContext(),opmonitor.class);  // to get current dialog context and jump to another activity
                      v.getContext().startActivity(intent);
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