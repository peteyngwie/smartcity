package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CircularmMainManu extends AppCompatActivity {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    ImageView iv;	//  定义imageView
    boolean isChanged = false;

    ImageView findcableimg  ,
              ipsettingimg  ,
              logoutimg     ,
              tagwritingimp ,
              cablemodimg  ,
              pwdimg ,
              pwimg  ,
              dbsyncimg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Stetho.initializeWithDefaults(this);

        // SplashActivity.mSplashActivity.finish();   // close splash activity , this is a very important action
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_circularm_main_manu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        findcableimg  = (ImageView) findViewById(R.id.findcable) ;
        ipsettingimg  = (ImageView) findViewById(R.id.ipimg) ;
        logoutimg     = (ImageView) findViewById(R.id.logoutimg) ;
        tagwritingimp = (ImageView) findViewById(R.id.tagwritigimg) ;
        cablemodimg   = (ImageView) findViewById(R.id.cableimg) ;
        pwimg         = (ImageView) findViewById(R.id.pwimg) ;
        dbsyncimg     = (ImageView) findViewById(R.id.dbsyncimg) ;
        pwdimg        = (ImageView) findViewById(R.id.pwdimg) ;

        findcableimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CircularmMainManu.this, "纜線尋找", Toast.LENGTH_SHORT).show();
            }
        });

        ipsettingimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "ip位址設定", Toast.LENGTH_SHORT).show();
            }
        });

        logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "登出", Toast.LENGTH_SHORT).show();
            }

        });

        tagwritingimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "標籤寫入", Toast.LENGTH_SHORT).show();
            }
        });

        cablemodimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "纜線修改", Toast.LENGTH_SHORT).show();

            }
        });

        pwimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "功率設定", Toast.LENGTH_SHORT).show();

            }
        });

        dbsyncimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "資料庫同步", Toast.LENGTH_SHORT).show();


            }
        });

        pwdimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularmMainManu.this, "密碼修改", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //继承了Activity的onTouchEvent方法，直接监听点击事件

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候

            x2 = event.getX();
            y2 = event.getY();


            if(y1 - y2 > 50) {

                Toast.makeText(CircularmMainManu.this, "向上滑", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CircularmMainManu.this , MainSqaurefun.class) ;
                startActivity(intent);  // get back to default page
                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);


            } else if(y2 - y1 > 50) {
                Toast.makeText(CircularmMainManu.this, "向下滑", Toast.LENGTH_SHORT).show();

               // Intent intent = new Intent(CircularmMainManu.this , StaggeredMainMenu.class);
                // startActivity(intent);
                 //Intent  intent = new Intent(CircularmMainManu.this , bottomsheetmainmenu.class);
                 //startActivity(intent);
                // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);


            } else if(x1 - x2 > 50) {
                Toast.makeText(CircularmMainManu.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Toast.makeText(CircularmMainManu.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画
        overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
    }

}


