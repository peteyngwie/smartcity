package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class bottomsheetmainmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheetmainmenu);

        AddPhotoBottomDialogFragment addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment.newInstance();

        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                "add_photo_dialog_fragment");


        // BottomSheetBehavior behavior= BottomSheetBehavior.from(findViewById(R.id.design_bottom_sheet));
        // behavior.setHideable(false);//此处设置表示禁止BottomSheetBehavior的执行


    }
}