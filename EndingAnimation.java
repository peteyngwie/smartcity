package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Sumeet Jain on 23-06-2018.
 */

public class EndingAnimation extends AppCompatActivity {

    public static Activity mEndingAnimation;   // this is a static activity

    private ImageView logo;
    private static int splashTimeOut=1000;

    private ipcheckdialog ipdialog ;

    SoundPool soundPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_animation);

        ActivityManager.getInstance().addActivity(this);

        logo=(ImageView)findViewById(R.id.logoending);
        logo.setScaleType(ImageView.ScaleType.FIT_START);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;     // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

        Toast.makeText(this, "width = " + Integer.toString(width), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "height = " + Integer.toString(height), Toast.LENGTH_SHORT).show();

        logo.setAdjustViewBounds(true);   // allow image can be adjusted - h and w

        ViewGroup.LayoutParams layoutParams = logo.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        logo.setLayoutParams(layoutParams);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                // ipcheckdialog ipdialog = new ipcheckdialog(SplashActivity.this);
                // ipdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                // ipdialog.show();
                // ipdialog.setCancelable(true);  // close the dialog when user click outside of it !
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                // ClassificationDialog classificationDialog = new ClassificationDialog(SplashActivity.this);
                // classificationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // classificationDialog.show();
                // Intent i = new Intent(SplashActivity.this,MainActivity.class);
                // startActivity(i);
                // finish();
            }
        },splashTimeOut);

        // this.finish();

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
            /**
             * 第一个参数：int maxStreams：SoundPool对象的最大并发流数
             * 第二个参数：int streamType：AudioManager中描述的音频流类型
             *第三个参数：int srcQuality：采样率转换器的质量。 目前没有效果。 使用0作为默认值。
             */
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        //可以通过四种途径来记载一个音频资源：
        //1.通过一个AssetFileDescriptor对象
        //int load(AssetFileDescriptor afd, int priority)
        //2.通过一个资源ID
        //int load(Context context, int resId, int priority)
        //3.通过指定的路径加载
        //int load(String path, int priority)
        //4.通过FileDescriptor加载
        //int load(FileDescriptor fd, long offset, long length, int priority)
        //声音ID 加载音频资源,这里用的是第二种，第三个参数为priority，声音的优先级*API中指出，priority参数目前没有效果，建议设置为1。
        // final int voiceId = soundPool.load(SplashActivity.this, R.raw.9351, 1);
        //异步需要等待加载完成，音频才能播放成功

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {

                    //第一个参数soundID
                    //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
                    //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
                    //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
                    //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
                    //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
                    // soundPool.play(voiceId, 1, 1, 1, 0, 1);
                }
            }
        });   //  sound pool listener

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        logo.startAnimation(myanim);
        ActivityManager.getInstance().closeAllActivity();


        // finish();

        // ipcheckdialog dialog = new ipcheckdialog(SplashActivity.this);
    }

    @Override
    public void finish() {
        super.finish();

        ActivityManager.getInstance().closeAllActivity();
        overridePendingTransition(R.anim.dialog_bottom_in, R.anim.dialog_bottom_out);
        System.exit(0);
    }

    @Override

    protected void onPause() {
        super.onPause();

        if (isFinishing()){
            overridePendingTransition(R.anim.dialog_bottom_in, R.anim.dialog_bottom_out);
            System.exit(0);

        }

    }
}