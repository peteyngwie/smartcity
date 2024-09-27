package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter  on 10-01-2024.
 */

public class ExitAppAnimation extends AppCompatActivity {

    public static Activity mExitAppAnimationActivity;   // this is a static activity

    private ImageView logo;
    private static int splashTimeOut = 2000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Context context;
        boolean networkingstatus;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        context = getApplicationContext();

        logo = (ImageView) findViewById(R.id.logo);
        logo.setScaleType(ImageView.ScaleType.FIT_START);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

        int width = metric.widthPixels;     //  屏幕宽度（像素）
        int height = metric.heightPixels;    //  屏幕高度（像素）
        float density = metric.density;         //  屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;      //  屏幕密度DPI（120 / 160 / 240）

        logo.setAdjustViewBounds(true);   // allow image can be adjusted - h and w

        ViewGroup.LayoutParams layoutParams = logo.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        logo.setLayoutParams(layoutParams);
        mExitAppAnimationActivity = this;   // point to current activity

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

        Log.d(TAG,"mExitAppAnimationActivity");  // exit app animation
        // overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);      // 進場動畫(淡出效果)
        // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_none);  // 進場動畫(從下到上)

        // 取得前一個Activity傳過來的資料
        Bundle bundle = this.getIntent().getExtras();

        // 將取得的Bundle資料設定
        if (bundle != null) {
            Boolean result = bundle.getBoolean("onoffline");   // Getting the content of bundle
            if (result.booleanValue()  == true ) {

                MainSqaurefun.mMainSquarefunActivity.finish();       // close previous page (online 主畫面)
                ExitAppAnimation.this.moveTaskToBack(true);  //  退出 app
            }
            else {

                MainOffLineSqaurefun.mMainOffLineSqaurefunActivity.finish();
                ExitAppAnimation.this.moveTaskToBack(true);  // exit app

            }
        }
            else
                Log.d(TAG,"錯誤!") ;

        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //過兩秒後要做的事情
                Log.d("tag", "我要打掃了");
                finish();

            }
        }, 2000);

         */
        // finish();



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

        if (false) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // ip 位址輸入對話框
                    ipcheckdialog ipdialog = new ipcheckdialog(ExitAppAnimation.this);
                    ipdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));      // 避免邊框空白 消除
                    ipdialog.getWindow().getAttributes().windowAnimations = R.style.emp_dialog_Animation;  // 指派動畫
                    ipdialog.show();                                                                       // 對話框顯示
                    ipdialog.setCancelable(true);  // close the dialog when user click outside of it !
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                }
            }, splashTimeOut);

        }  // end of false


    }   // end of onCreate

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = intent.getStringExtra("EXIT_TAG");
        if (tag != null && TextUtils.isEmpty(tag)) {
            if ("SINGLETASK".equals(tag)) {
                finish();  //  exit app
            }

        }

    }

    @Override
    public void finish() {

        super.finish();
       //  System.exit(0);
        overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);  // 進場動畫(淡出效果)


    }
}


