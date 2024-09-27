package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;

public class Ending extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        // Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        //退出时使用
        // getWindow().setExitTransition(explode);
        //第一次进入时使用
        // getWindow().setEnterTransition(explode);
        //再次进入时使用
        // getWindow().setReenterTransition(explode);


        System.exit(0);  // exit app


    }
}