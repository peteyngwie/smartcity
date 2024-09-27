package com.tra.loginscreen;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class CableDataModificationDialog extends Dialog {


    private EditText editcablename ,
            editroomname  ,
            editcore      ,
            editequname    ;

    // private RadioGroup classification_rg;
    // private RadioButton rb1, rb2;
    private Button cfmbtn ;

    public CableDataModificationDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.cabledatamodificationdialog);

        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog

        cfmbtn = findViewById(R.id.btn_confirm);

        editcablename = findViewById(R.id.editcablename);
        editroomname  = findViewById(R.id.editroomname) ;
        editcore      = findViewById(R.id.editcore) ;
        editequname   = findViewById(R.id.editequname) ;

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener
        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // cancel button click event listener

    }
}