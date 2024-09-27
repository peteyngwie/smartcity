package com.tra.loginscreen;

import static android.content.Context.MODE_PRIVATE;
import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tra.loginscreen.R;
import com.tra.loginscreen.opmonitor;
import com.tra.loginscreen.transmissionunit;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ipcheckdialog extends Dialog {

    private TextView tverrorip;
    private EditText editip ;


    private boolean isExit;

    public int StatusCode  = -1 ;

    private TextView txtoffline ;

    public static Dialog mipcheckdialog;   // this is a static dialog instance

    // private RadioGroup classification_rg;
    // private RadioButton rb1, rb2;
    private Button cfmbtn, cnlbtn;
    public ipcheckdialog (@NonNull Context context) {

        super(context, R.style.dialog_style);
        this.setContentView(R.layout.ipcheckdialog);



        mipcheckdialog = this ;

        initView();             // initialization of views
        addListener(context);   // define all listeners of component
                                // 下面是要先檢查網路是否正常

    }  // the definition of constructor

       // 檢查是否有網路連接

    public void DisableSplashAnimation() {

        dismiss();    // close current dialog
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                SplashActivity.mSplashActivity.finish();  // exit app
                SplashActivity.mSplashActivity.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
            }
        }, 300);

        // SplashActivity.mSplashActivity.finish();   // close splash activity (是為了效果)

    }

    public void SetStatus(int statusCode) {

        this.StatusCode = statusCode ;
    }
    public int  GetStatus() {

        return this.StatusCode  ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // this method which is responsible for handling the dialog's status
        // 判断触摸事件的类型和触摸位置

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect dialogRect = new Rect();
            getWindow().getDecorView().getHitRect(dialogRect);


            if (!dialogRect.contains((int) event.getX(), (int) event.getY())) {
                // 在 Dialog 外部區域操作
                // 执行某些操作

                dismiss();  // close the dialog
                // System.exit(0) ;   // exit the app

                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {

        // this function which initializes those components in dialog

        cfmbtn     = findViewById(R.id.btn_confirm);
        cnlbtn     = findViewById(R.id.btn_cancel);
        editip     = findViewById(R.id.editip);
        tverrorip  = findViewById(R.id.errormsgtxt);
        //  txtoffline = findViewById(R.id.txt_offline);  // 這裡無須離線作業 (因為尚未認證)
        //  SplashActivity.mSplashActivity.finish();   // close splash activity


    }

    private void addListener(Context context)    {

        // off line manipulation  (離線作業)
        if (false) {
            txtoffline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 這裡是要切換到離線畫面的功能主畫面, 但是只有查詢功能
                    // 目前只跳到 MainSquare Activity

                    Intent intent = new Intent(context, MainSqaurefun.class);      // Jump to Main square functions activity
                    v.getContext().startActivity(intent);                              //  change to mainsquare functions
                    Toast.makeText(context, "離線作業 !", Toast.LENGTH_SHORT).show();

                }
            });

        }  // 將這個監聽器關閉

        //  the confirmation button click event listener (確定)

        cfmbtn.setOnClickListener(new View.OnClickListener() {

            String ipaddress ;

            @Override
            public void onClick(View v) {
               Context c ;
               c = getContext();
               Boolean isavailable ;

                isavailable = isAvailableOfNetworking(c);

                if (isavailable == true )
                {
                    Log.d(TAG,"yes");
                }
                else
                {
                    Log.d(TAG,"no");
                }

                String inputipaddress = editip.getText().toString() ;

                Log.d(TAG,"inputipaddress : " + inputipaddress.toString());

                // Database Name :  data
                // Table Name : score - 存放 ipaddress
                // 先保留 -
                /*
                SharedPreferences sharedPreferences = context.getSharedPreferences("data" , Context.MODE_PRIVATE);
                //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
                String address = sharedPreferences.getString("score", "");

                if ( address.toString() == null)
                    Log.d(TAG,"null ip");
                else
                    Log.d(TAG,"ip :" + address.toString());

                if (inputipaddress.equals(address.toString().trim()) == true )
                    Log.d(TAG,"與存放的ip位址相同") ;
                else {
                    Log.d(TAG,"不同");

                    IPAddressErrDialog();  // show ip address err message !

                }

                 */

                // Toast.makeText(context, "Click me !", Toast.LENGTH_SHORT).show();
                if  ( editip.getText().toString().equals(/*"192.168.100.201"*/ "192.168.0.168") == true )
                {

                    Log.d(TAG, "IP 位址: " + inputipaddress.toString()) ;

                    ipaddress = "http://"+ inputipaddress + "/rfid/api/test" ;

                    // GetRequest("http://192.168.100.201/rfid/api/test" /* ipaddress  */ , v );
                    GetRequest("http://192.168.0.168/rfid/api/test" /* ipaddress  */ , v );

                      tverrorip.setText("");
                      editip.setText("");  // clear edit contents

                     // 網路連線正確 !　
                     // 20240112 - peter

                    int StatusCode ;
                    StatusCode = GetStatus() ;

                    if ( StatusCode == 200  ) {

                        Toast.makeText(context, "Code :" + Integer.toString(GetStatus()), Toast.LENGTH_SHORT).show();
                        dismiss();  // close current dialog

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(intent);
                        Toast.makeText(context, "網路連線成功 !"  , Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"下一頁") ;

                        SplashActivity.mSplashActivity.finish();  //  close the splash Activity
                       // SplashActivity.mSplashActivity.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);   // 轉場動畫 (淡出)

                    }
                    else {
                        // 網路連線錯誤 !
                        Log.d(TAG,"網路連線錯誤 , 錯誤碼 : " + StatusCode) ;
                        Toast.makeText(context, "網路連線錯誤 !" + "狀態:" + StatusCode , Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                       Toast.makeText(context, "錯誤!", Toast.LENGTH_SHORT).show();
                       tverrorip.setText("IP位址未輸入，請輸入。");

                       Handler handler = new Handler();
                       handler.postDelayed(new Runnable(){

                        @Override
                        public void run() {

                            //過三秒後要做的事情

                            Log.d(TAG,"訊息消失!");
                            tverrorip.setText("");  // this is an error message that shows

                        }}, 3000);

                    editip.setText("");  // clear edit contents

                }
                // 直接跳 !
                // Intent intent = new Intent(v.getContext(), MainActivity.class);

            }
        });

        // cancel button click event listener
        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the dialog
                DisableSplashAnimation();   // exit app
            }
        });
    }    // end of addlistener


            private boolean isAvailableOfNetworking(Context context) {

                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        return true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to mobile data
                        return true;
                    }
                } else {
                    // not connected to the internet

                }
                return false;
            }

            private void GetRequest(String str, View view) {

                Context context;

                context = getContext();

                // Toast.makeText(getContext(), "Get json from web ", Toast.LENGTH_SHORT).show();

                boolean isavailable = isAvailableOfNetworking(context);

                if (isavailable == true)
                    Log.d(TAG, "Networking is available");
                else {
                    Log.d(TAG, "Networking is not available");


                    mipcheckdialog.dismiss();  // close current dialog

                    NoNetworkingDialog();  // 無網路對話框提示 !

                    // SplashActivity.mSplashActivity.finish();

                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String strUrl = str;  // url address

                            URL url = new URL(strUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");   //  GET
                            connection.connect();                 //  connect to web page

                            int responseCode = connection.getResponseCode();
                            SetStatus(responseCode);

                            if (GetStatus() == 200) {  // 登入成功 !

                                Log.d(TAG, "Code >>" + " 正確 : " + Integer.toString(responseCode));


                                if (true) {

                                    dismiss();   // close current dialog
                                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                                    view.getContext().startActivity(intent);
                                    // 這裡需要一個動畫 !

                                }

                            } else {    // 登入失敗 !

                                Log.d(TAG, "Code >>" + " 錯誤 : " + Integer.toString(responseCode));

                                // InternetErrorCodeDialog internetErrorCodeDialog = new InternetErrorCodeDialog(view.getContext());
                                // internetErrorCodeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                                // internetErrorCodeDialog.show();
                                // internetErrorCodeDialog.setCancelable(true);  // close the dialog when user click outside of it !

                            }

                            if (responseCode == HttpURLConnection.HTTP_OK) {

                                //  request successfully !
                                //  登入成功後取出 json 字串 !
                                InputStream inputStream = connection.getInputStream(); //  get stream data
                                JSONObject json = streamToJson(inputStream);           //  JSON String
                                Log.d(TAG, json.toString());                           //  JSON response
                                // 正確的 json string - {"status":0,"message":null,"result":"OK","ok":true}

                            }

                            connection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

            private JSONObject streamToJson(InputStream inputStream) throws Exception {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String temp = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                JSONObject json = new JSONObject(stringBuilder.toString().trim());
                return json;
            }


            private void NoNetworkingDialog() {

                ImageView cancel;  // 右上角取消按鈕

                //will create a view of our custom dialog layout

                Context context = getContext();

                View alertCustomdialog = LayoutInflater.
                        from(context).
                        inflate(R.layout.nonetworkingdialog, null);

                //initialize alert builder.
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                //set our custom alert dialog to tha alertdialog builder
                alert.setView(alertCustomdialog);   // 設定dialog的 view

                cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);
                final AlertDialog dialog = alert.create();

                // 下面是動畫的處理  ()
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER_VERTICAL);  //  這裡可以調整 dialog 顯示的位置 (目前是中間)
                window.setWindowAnimations(R.style.mystyle);  // 動畫

                //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                // logo.startAnimation(animFadeOut);   // execute fade out animation

                //finally show the dialog box in android all

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (((AlertDialog) dialog).isShowing()) {
                            dialog.dismiss();  // close dialog

                            //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            //logo.startAnimation(animFadeOut);   // execute fade out animation
                        }

                    }
                });   // cancel button - onclicklistener

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    private static final int AUTO_DISMISS_MILLIS = 3000;  // 3 seconds

                    @Override
                    public void onShow(final DialogInterface dialog) {

                        final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        final CharSequence negativeButtonText = defaultButton.getText();

                        new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                defaultButton.setText(String.format(
                                        Locale.getDefault(), "%s (%d)",
                                        negativeButtonText,
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                                ));
                            }

                            @Override
                            public void onFinish() {

                                if (((AlertDialog) dialog).isShowing()) {
                                    dialog.dismiss();  // close dialog
                                    SplashActivity.mSplashActivity.finish();  // close splash activity

                                    //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                                    //logo.startAnimation(animFadeOut);   // execute fade out animation

                                }
                            }
                        }.start();
                    }
                });

                dialog.show();   // 顯示 dialog

            }

    private void IPAddressErrDialog() {

        // IP 位置錯誤的對話框

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no, btn_yes;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(getContext()).
                inflate(R.layout.ipaddresserrdialog, null);

        //initialize alert builder.

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        // cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);  // 右上角取消按鈕

        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理  ()
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  //  動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        // logo.startAnimation(animFadeOut);   // execute fade out animation

        //finally show the dialog box in android all
         /*
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((AlertDialog) dialog).isShowing()) {

                    dialog.dismiss();  // close dialog
                    // 只是將 dialog 關閉 , do nothing

                    // startCableDataHttpGetRequestThread();   // 取得纜線資料 (寫入database)

                    // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    // logo.startAnimation(animFadeOut);   // execute fade out animation
                    //        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    //        logo.startAnimation(animFadeOut);   // execute fade out animation
                    //        Log.d(TAG, "Ending Animation "); 若動畫開啟,會crash !
                }
            }
        });   // cancel button - onclicklistener

          */

        dialog.show();   // 顯示 dialog

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情
                dialog.dismiss();   // 關閉 dialog

            }}, 2000);

    }

}   // end of ipcheckialog



