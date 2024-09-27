package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpaddressInputDialog extends Dialog {

    private TextView tverrorip;
    private EditText editip ;

    private Button cfmbtn, cnlbtn;

    private String  ipaddress ;

    private DBOpenHelper1 mDBHelper1 ;   // dbopenhelper1 for setting database helper
    private Cursor mCursor;              // database cursor

    public static Dialog mDialog;   // this is a static dialog


    public IpaddressInputDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.setContentView(R.layout.ipaddressinputdialog);  // ip address input dialog

        initView();             // view initialization
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // this method which is responsible for handling the dialog's status

        // 判断触摸事件的类型和触摸位置

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect dialogRect = new Rect();
            getWindow().getDecorView().getHitRect(dialogRect);

            if (!dialogRect.contains((int) event.getX(), (int) event.getY())) {

                dismiss();  // close the dialog
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {

        // this function which initializes those components in dialog

        mDialog = this ;

        cfmbtn     = findViewById(R.id.btn_confirmip);
        cnlbtn     = findViewById(R.id.btn_cancelip);
        editip     = findViewById(R.id.editinputip);
        tverrorip  = findViewById(R.id.ipinputerrortxt);

       // SystemSetting.mSystemSettingActivity.finish();   // close before activity

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ipaddress = editip.getText().toString();

                ////////////////////////////////////////////////
                //  這裡是檢查 IP address format 是否整確與否
                //  格式: xxxx.xxxx.xxxx.xxxx (using regular expression )
                // 20240111 - peter
                //

                if (Validate_It(ipaddress) == 0 || Validate_It(ipaddress) == 0) {

                    Toast.makeText(context, "格式正確!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "IP位址格式正確 !");
                    // if IP address format is available , do nothing and close dialog !

                    dismiss();    // close current dialog

                    SaveIPAddressAndShow(v, context);    //  Save Ip address and show the dialog

                } else {

                    Toast.makeText(context, "格式錯誤!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "IP位址格式錯誤 !");

                    ErrIPFormatDialog(v);   // error IP Format alert dialog !

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            //過三秒後要做的事情
                            Log.d(TAG, "訊息消失!");
                            tverrorip.setText("");  // this is an error message that shows

                        }
                    }, 3000);

                    editip.setText("");  // clear edit contents

                }

            }
                    // dismiss();
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

    private void SaveIPAddressAndShow(View view,Context context) {

        Log.d(TAG, "ipaddress >>>>" + ipaddress.toString());

        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
        sharedPreferences.edit().putString("ipaddress", ipaddress.toString()).apply();
        // sharedPreferences.edit().putString("score" , editip.getText().toString()).apply();
        //存入資料，丟入的參數為(key , value)
        String value = sharedPreferences.getString("ipaddress", "");
        //取出資料，丟入的參數為(key , 若是沒值，預設為"")

        Log.d(TAG, "ip address :" + value);

            Log.d(TAG, "IP: " + ipaddress.toString());
            tverrorip.setText("");
            editip.setText("");  // clear edit contents
            Toast.makeText(context, "設定完成 !", Toast.LENGTH_SHORT).show();
            IpaddressInputConfirmDialog ipaddressInputConfirmDialog = new IpaddressInputConfirmDialog(view.getContext());
            ipaddressInputConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
            ipaddressInputConfirmDialog.show();

            if (true) {   // dialog animation - 顯示 IP位址設定完成 (animation effect )

                WindowManager.LayoutParams layoutParams = ipaddressInputConfirmDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                ipaddressInputConfirmDialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = ipaddressInputConfirmDialog.getWindow();

                // 下面是動畫的處理
                window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
                window.setWindowAnimations(R.style.mystyle);  // 動畫
                //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
                ipaddressInputConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }
            else { }


    }

    // 錯誤的ip address format dialog alert
    private void ErrIPFormatDialog(View view)
    {

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;
        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(view.getContext()).
                inflate(R.layout.erroripformatdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel   = (ImageView) alertCustomdialog.findViewById(R.id.errcancel_button);  // 右上角取消按鈕
        // btn_no   = (Button) alertCustomdialog.findViewById(R.id.errbtn_no) ;           // 不載按鈕
        btn_yes  = (Button) alertCustomdialog.findViewById(R.id.errbtn_yes) ;         // 下載按鈕

        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
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
                    // 只是將 dialog 關閉 , do nothing  !
                     Animation animFadeOut = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out);

                }
            }
        });   // cancel button - onclicklistener

       if (true) {


           btn_yes.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   dialog.dismiss();   // close current dialog

               }
           });   // 下載
       }

        dialog.show();   // 顯示 dialog

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情

                dialog.dismiss();   // close the dialog if it shows over 2 secs !

            }}, 20000);

    }

    public void SetDBHelper(DBOpenHelper1 dbOpenHelper) {
        this.mDBHelper1 = dbOpenHelper ;
    }   //
    public void CloseDBHelper (DBOpenHelper1 dbOpenHelper) {
        dbOpenHelper.close();  // close db
    }

    public DBOpenHelper1 GetDBHelper1() {

        assert this.mDBHelper1!=null ;
        return this.mDBHelper1 ;
    }



    // 下面的 method 是用來檢查 IP address 格式是否正確
    // 20240111 - peter
    //
    public int Validate_It(String IP)
    {
        // this is a regular expression checking format

        // Regex expression for validating IPv4 format
        String regex="(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";

        // Regex expression for validating IPv6 format
        String regex1="((([0-9a-fA-F]){1,4})\\:){7}([0-9a-fA-F]){1,4}";

        Pattern p = Pattern.compile(regex);
        Pattern p1 = Pattern.compile(regex1);

        // Checking if it is a valid IPv4 addresses

        if (p.matcher(IP).matches())
            return 0 ;
            // Checking if it is a valid IPv6 addresses
        else if (p1.matcher(IP).matches())
            return 1 ;
            // Return Invalid
        else
            return 2 ;

    }

    public static boolean isValidIPAddress(String ip)
    {

        // Regex for digit from 0 to 255.
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false

        if (ip == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.

        Matcher m = p.matcher(ip);

        // Return if the IP address
        // matched the ReGex

        return m.matches();

    }    // end of  isValidIPAddress

}