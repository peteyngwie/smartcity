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

public class CablequeryFromdelDialog extends  Dialog {

    private Button cfmbtn, cnlbtn;

    private EditText editno, editname , editport ;

    public CablequeryFromdelDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);

        this.setContentView(R.layout.cablequeryfromdeletiondialog);

        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog

        cfmbtn = findViewById(R.id.confirmquerydelbtn);
        cnlbtn = findViewById(R.id.cancelquerydelbtn);

        // the following is edit fields
        editno = findViewById(R.id.editquerydeltagno);
        editname = findViewById(R.id.editquerydelname);
        editport = findViewById(R.id.editquerydelportnum);

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
                showCabledetailsDialog(v); //
                // RFIDTagSearchDialog rfidTagSearchDialog = new RFIDTagSearchDialog(v.getContext());
                // rfidTagSearchDialog.show();


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
    private void showCabledetailsDialog(View v )
    {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.cabledeldetials, viewGroup, false);
        TextView cabletxt ;

        cabletxt= dialogView.findViewById(R.id.cabledeltxt);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        cabletxt.setText(

                        "◉ TAG ID: " +""     + "\n" +
                        "◉ 光纜名稱: " + "山側96C"    + "\n" +
                        "◉ From: "   +  "MPLS01-RX"  + "\n" +
                        "◉ To: "     +  "ODF-第2芯"+ "\n" +
                        "◉ 樓層: "    +  "2"+ "\n" +
                        "◉ 機房名稱: " +  "B"+ "\n" +
                        "◉ 排: "      +  "2"+ "\n" +
                        "◉ 櫃: "      +  "2"+ "\n" +
                        "◉ 箱: "      +  "3"+ "\n" +
                        "◉ 芯: "      +  "2"+ "\n" +
                        "◉ 備註: "     + "\n" +
                        "◉ 纜線廠商: "  + "驣龍"+ "\n" +
                        "◉ 設備名稱: "  + "MPLS"+ "\n" +
                        "◉ 建置廠商: "  + "華電"+ "\n" +
                        "◉ 傳輸送收端: " + "TX"+"\n"+
                        "◉ 傳輸設備PORT位: "+"ODF-第一芯" ) ;

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (true) {   // dialog animation
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setAttributes(layoutParams);
            //设置dialog进入的动画效果
            Window window = alertDialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);
        }


      /*
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("纜線詳細資料");
        progress.setMessage("標籤序號: " +dat.Getf1() + "\n" +
                            "路由編號: " +dat.Getf2() + "\n" +
                            "機房名稱(From): " + dat.Getf3() + "\n" +
                            "機房樓層(From): " + dat.Getf4() + "\n" +
                            "機櫃編號(From): " + dat.Getf5() + "\n" +
                            "接續盒編號(From): " + dat.Getf6() + "\n" +
                            "連接埠編號(From): " + dat.Getf7() + "\n" +
                            "纜線名稱(From): " + dat.Getf8() + "\n" );
        progress.show();


        // The following codes show the action which inform users writing progress is going to progress
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();  //
            }
        };
         */
        // Handler pdCanceller = new Handler();
        // pdCanceller.postDelayed(progressRunnable, 2000);


        Button btn = dialogView.findViewById(R.id.buttondelcable);  // deletion button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 // Toast.makeText(v.getContext(), "yes, you delete the cable ! ", Toast.LENGTH_SHORT).show();
                 CableDeletionDialog cableDeletionDialog= new CableDeletionDialog(v.getContext());
                 cableDeletionDialog.show();  // this is cable deletion dialog


                if (true) {   // dialog animation
                    WindowManager.LayoutParams layoutParams = cableDeletionDialog.getWindow().getAttributes();
                    layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                    cableDeletionDialog.getWindow().setAttributes(layoutParams);
                    //设置dialog进入的动画效果
                    Window window = cableDeletionDialog.getWindow();
                    window.setWindowAnimations(R.style.AnimLeft);
                }


                 alertDialog.dismiss();  // close current dialog
                // alertDialog.dismiss();
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

        if (true) {   // this is  animation for dialog entery

            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

            alertDialog.getWindow().setAttributes(layoutParams);
            //设置dialog进入的动画效果
            Window window = alertDialog.getWindow();
            window.setWindowAnimations(R.style.AnimCenter);

        }



        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(v.getContext(), "ok button", Toast.LENGTH_SHORT).show();


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