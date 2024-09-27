package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class EndingAnimationActivity extends AppCompatActivity {

    public static Activity mEndingAnimationActivity;   // this is a static activity

    private ImageView logo;
    private static int splashTimeOut = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Context context ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        context = getApplicationContext() ;

        logo = (ImageView)findViewById(R.id.logo);   // 離場 logo
       //  logo.setScaleType(ImageView.ScaleType.FIT_START);
         /*
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏



        int width      = metric.widthPixels;    // 屏幕宽度（像素）
        int height     = metric.heightPixels;   // 屏幕高度（像素）
        float density  = metric.density;        // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;     // 屏幕密度DPI（120 / 160 / 240）

        logo.setAdjustViewBounds(true);   // allow image can be adjusted - h and w

        ViewGroup.LayoutParams layoutParams = logo.getLayoutParams();
        layoutParams.width  = width;
        layoutParams.height = height;
        logo.setLayoutParams(layoutParams);

          */


        mEndingAnimationActivity = this;   // point to current activity

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

         /*
        if (userid.toString().isEmpty() == true ) {
             Toast.makeText(mSplashActivity, "This is first time !" + userid.toString(), Toast.LENGTH_SHORT).show();
             Intent i = new Intent(SplashActivity.this,MainActivity.class);
             startActivity(i);
              // finish();

         }
         else
             Toast.makeText(mSplashActivity, "IP Address:" + userid.toString(), Toast.LENGTH_SHORT).show();
         */

       /*
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {

                // TODO Auto-generated method stub
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                Animation myanim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splashanimation);
                logo.startAnimation(myanim);  // play animation ( 2 seconds )
                handler.postDelayed(this, 3000);
            }
        };

        handler.postDelayed(runnable, 3000);
        handler.removeCallbacks(runnable);    // terminate the handler

        Log.d(TAG,"userid"+userid.toString());
        // clear(this);  // This line which is responsible for clearing the data in shared preference

        if (userid.toString().isEmpty() == true )   // if shared data is empty
        {

            SharedPreferences sharedPreferences=getSharedPreferences("IPADD", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("IP","192.168.100.202");
            editor.commit();

             // Toast.makeText(mSplashActivity, "This is first time !" + userid.toString(), Toast.LENGTH_SHORT).show();

            ipcheckdialog ipdialog = new ipcheckdialog(SplashActivity.this);
            ipdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
            ipdialog.show();

            ipdialog.setCancelable(true);  // close the dialog when user click outside of it !
            // Intent i = new Intent(SplashActivity.this,MainActivity.class);
            // startActivity(i);
             // finish();
        }
        else if ( userid.toString().equals("192.168.100.201") == true )
        {

            // if default IP address has been stored , then move to login activity
            Intent i = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        */

        Log.d(TAG,"哈哈哈");
        // Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);  //
        // logo.startAnimation(myanim);  // play animation ( 2 seconds )

        // logo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.splashanimation));

        // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        // logo.startAnimation(animFadeOut);


        /*
        SoundPool soundPool;
        //实例化SoundPool
        //sdk版本21是SoundPool 的一个分水岭
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入最多播放音频数量,
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();

        } else {

            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
       */

        // Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        // logo.startAnimation(myanim);  // play animation ( 2 seconds )
        // DH  = new DBHelper(this);   //  create a db helper
        // db  = DH.getWritableDatabase();    // create a writable database




    }


    public void finish(){

        super.finish();

    }


    public boolean isNetworkConnected(Context context) {

        if (context != null) {

            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
