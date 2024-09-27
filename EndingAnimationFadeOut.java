package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class EndingAnimationFadeOut extends AppCompatActivity {

    private ImageView img;  // ending animation (fade out)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_animation_fade_out);

        SplashActivity.mSplashActivity.finish();   // it must be closed !

        img = (ImageView)findViewById(R.id.ending);
        img.setScaleType(ImageView.ScaleType.FIT_START);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

        MainSqaurefun.mMainSquarefunActivity.finish();  // close mainfunsqaure

        SayGoodBye() ;  // exit mainsqaure function


    }

    public void SayGoodBye() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                img.startAnimation(animFadeOut);   // execute fade out animation

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        android.os.Process.killProcess(android.os.Process.myPid());  // exit app
                        System.exit(0);

                    }}, 1500);  // 1.5 secs 可以看起來比較順暢

            }}, 200);
    }
}