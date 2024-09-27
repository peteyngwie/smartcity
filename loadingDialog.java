package com.tra.loginscreen;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;

public class loadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    loadingDialog(Activity myactivity)
    {
        activity= myactivity;
    }

    void startLoadingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog_loading,null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 400); //Controlling width and height.


        /*
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = 150;
        lp.height = 50;
        lp.x=170;
        lp.y=100;

        alertDialog.getWindow().setAttributes(lp);

         */



    }


    void dismisDialog()
    {
        alertDialog.dismiss();
    }
}
