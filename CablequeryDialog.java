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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class CablequeryDialog extends  Dialog {

    private Button cfmbtn, cnlbtn;

    private EditText editno, editname , editport ;

    public CablequeryDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);

        this.setContentView(R.layout.cablequerydialog);

        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog

        cfmbtn = findViewById(R.id.confirmbtn);
        cnlbtn = findViewById(R.id.cancelbtn);
        // the following is edit fields
        editno = findViewById(R.id.edittagno);
        editname = findViewById(R.id.editname);
        editport = findViewById(R.id.editportnum);

    }
    private void addListener(Context context)
    {

        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "編號: "+editno.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "名稱: "+editname.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Port:"+editport.getText().toString(), Toast.LENGTH_SHORT).show();
                // showQueryDialog(v);

                RFIDTagSearchDialog rfidTagSearchDialog = new RFIDTagSearchDialog(v.getContext());
                rfidTagSearchDialog.show();

                WindowManager.LayoutParams layoutParams = rfidTagSearchDialog.getWindow().getAttributes();
                layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                rfidTagSearchDialog.getWindow().setAttributes(layoutParams);

                //设置dialog进入的动画效果
                Window window = rfidTagSearchDialog.getWindow();
                window.setWindowAnimations(R.style.AnimLeft);


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

    private void showQueryDialog(View view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        Button confirmbtn ;
        Button cancelbtn ;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.iperrordialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated

        builder.setView(dialogView);
        confirmbtn = dialogView.findViewById(R.id.confirmbtn);
        cancelbtn = dialogView.findViewById(R.id.cancelbtn) ;

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

        confirmbtn.setOnClickListener(new View.OnClickListener() {
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

               //  alertOneButton(v) ;


                // finish(); //  close current activity

            }
        });

        //finally creating the alert dialog and displaying it
        // AlertDialog alertDialog = builder.create();
        //  alertDialog.show();
        //Now we need an AlertDialog.Builder object
    }
}
