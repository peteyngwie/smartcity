package com.tra.loginscreen;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChangePassword1 extends AppCompatActivity {

    private TextView Accounttxt;
    private EditText OrgPWDEdit;
    private EditText NewPWDEdit;
    private EditText ConfNewPWDEdit;
    private TextView OrgPwdCnfText;
    private TextView NewPwdCnfText;
    private String Token;     // Token
    private String UserName;  // UserName
    private String Password;  // Password

    private boolean sf ;      // change password verification

    private int Status;  // return status

    private String OrgPwd;    // original password

    private  String orgpwd ; // original password from bundle

    private String NewPwd;    // new password
    private String CnfNewPwd; // confirm new password

    private EditText InputNewPwdEdit;
    private EditText ConfirmNewPwdEdit;

    private String OrgAccount;

    private Button ConfirmBtn;

    private ImageView previouspage ;

    private Handler mhandler = new Handler();  // This handler is for getting data

    private  Runnable mRunnable ;    // this is
    private  Runnable runnable ;

    private int offonline ;
    public static Activity mChangePassword1;   // this is a static activity for closing in the future


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password1);

        Bundle bundle = this.getIntent().getExtras();   // new a bundle

        // Toast.makeText(this, "hello world !", Toast.LENGTH_SHORT).show();
        // 將取得的 Bundle資料設定

        mChangePassword1 = this;   // point to itself

        // MainSqaurefun.mMainSquarefunActivity.finish();   // close main function activity

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        Log.d(TAG,"Changepassword1");

        if (bundle != null ) {

        String token    = bundle.getString("token");        // token
        String username = bundle.getString("username");  // username
        String password = bundle.getString("password");  // password
        int offonline   = bundle.getInt("offonline") ;   // off / on line check

            SetOffOnLine(offonline);   // 儲存 on/off line main function


            SetOrgPWDFromBundle(password);    // 儲存原始的密碼
            InitVerification_ChangePWD() ;    // 密碼變更的旗號設定 - 預設為 false

            Log.d(TAG,"ChangePassword - token "    + token.toString());
            Log.d(TAG,"ChangePassword - username " + username.toString());
            Log.d(TAG,"ChangePassword - password " + password.toString());

            // Set following parameters for checking - they must be available !
            // 不要忘記存起 token !
                SetToken(token);                 // Save the available token for change password
            //  SetUserName(username);           // Save username
            //  SetPassword(password);           // Save original password

            previouspage = (ImageView) findViewById(R.id.imgebackarrow) ;    // 前一頁

            Accounttxt = (TextView) findViewById(R.id.accountnametxt);
            Accounttxt.setText(username);

            // 下面的是輸入的部分

            OrgPWDEdit = (EditText) findViewById(R.id.orgpwdedit);
            InputNewPwdEdit = (EditText) findViewById(R.id.newpwdedit);
            ConfirmNewPwdEdit = (EditText) findViewById(R.id.cnfnewpwdedit);

        // NewPwdCnfText = (TextView)findViewById(R.id.cnfnewpwdmsgtxt);
        // OrgPWDEdit.setText(password);

        ConfirmBtn    = (Button) findViewById(R.id.cfmbtn);                // 確認按鈕
        OrgPwdCnfText = (TextView) findViewById(R.id.orgpwdmsgtxt);        // check input password is available or not
        NewPwdCnfText = (TextView) findViewById(R.id.cnfnewpwdmsgtxt);     // confirm input password is matched

        OrgPWDEdit.setTextSize(20);
        OrgPWDEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());  // hint password (dot sign)

            OrgPWDEdit.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                   // OrgPWDEdit.getText().toString() ;

                    SetOrgPWD(OrgPWDEdit.getText().toString());  // 儲存輸入密碼 (及時)
                    Log.d(TAG,"real time password :" + GetOrgPWD()) ;

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // To listen the result after text input
                    //
                    String ss = OrgPWDEdit.getText().toString();   // 輸入的原始密碼
                    Log.d(TAG,"原密碼  " + GetOrgPWD().toString());

                    if (GetOrgPWD().equals(GetOrgPWDFromBundle()) == true)  {

                        Log.d(TAG,"原密碼輸入正確");
                        OrgPwdCnfText.setText("原密碼輸入正確");
                        SetOrgPWD(ss);   // 儲存輸入的原始密碼
                        OrgPwdCnfText.setTextColor(Color.rgb(46,150,75));   // Green Color

                    }
                    else {

                        if (OrgPWDEdit.getText().toString().length() == 0 )
                            OrgPwdCnfText.setText("");   // to avoid the default content is empty

                        else {

                            Log.d(TAG, "原密碼輸入錯誤 !");
                            OrgPwdCnfText.setText("原密碼輸入錯誤 !");
                            OrgPwdCnfText.setTextColor(Color.rgb(255, 1, 1));   // Red Color

                        }

                    }

                }
            });   // end of original password input TextWatcher Listener

            // 新密碼輸入

                InputNewPwdEdit.setTextSize(20);
                InputNewPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());  // hint password (dot sign)
                InputNewPwdEdit.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        SetNewPassword(InputNewPwdEdit.getText().toString());    // 儲存新密碼 (及時)
                        Log.d(TAG,"目前輸入新密碼: " + GetNewPassword().toString());  // 檢查目前輸入的新密碼

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        SetNewPassword(InputNewPwdEdit.getText().toString());  // save latest edit string
                        Log.d(TAG,"afterTextChanged" + InputNewPwdEdit.getText().toString()) ;

                    }
                });  // end of addTextChangedListener

            ConfirmNewPwdEdit.setTextSize(20);
            ConfirmNewPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());  // hint password (dot sign)

            ConfirmNewPwdEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // do nothing !

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Log.d(TAG,"目前輸入確認新密碼: " + ConfirmNewPwdEdit.getText().toString());  // 檢查目前輸入的新密碼
                    SetCfnNewPassword(ConfirmNewPwdEdit.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Log.d(TAG,"afterTextChanged 確認新密碼: " + GetCfnNewPassword().toString());  // 檢查目前輸入的新密碼
                    String newpwd1 , newpwd2 ;

                    newpwd1 = GetNewPassword();
                    newpwd2 = GetCfnNewPassword() ;

                    assert newpwd1!= null && newpwd2 != null ;

                    if ( newpwd1.equals(newpwd2) ){

                        NewPwdCnfText.setText("新密碼相同");
                        NewPwdCnfText.setTextColor(Color.rgb(46,150,75));   // Green  Color
                    }
                    else {

                        NewPwdCnfText.setText("新密碼不相同,請重新輸入!");
                        NewPwdCnfText.setTextColor(Color.rgb(255,1,1));   // Red Color

                    }


                }
            });

            // 左上角 - 回上一頁

            previouspage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(ChangePassword1.this, "前一頁", Toast.LENGTH_SHORT).show();
                    // 這裡必須判斷回到的畫面

                    int offon = GetOffOnLine();  // check where it comes from

                    if (offon == 0 ) {
                        finish();   // offline
                        Intent intent = new Intent(ChangePassword1.this, OffLineMainSquare.class);
                        v.getContext().startActivity(intent);
                        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_none);  // 進場動畫(從下到上)
                    }
                    else if (offon == 1 ) {
                        finish();   // online
                        Intent intent = new Intent(ChangePassword1.this, MainSqaurefun.class);
                        v.getContext().startActivity(intent);
                        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_none);

                    }
                }
            });

            //  將所有的密碼都送出 !
            ConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String orgpwd1, orgpwd2, newpwd1, newpwd2;
                    int orgpwdlen, inputnewpwdlen, confirmnewpwdlen;

                    orgpwdlen        = OrgPWDEdit.getText().toString().length();
                    inputnewpwdlen   = InputNewPwdEdit.getText().toString().length();
                    confirmnewpwdlen = ConfirmNewPwdEdit.getText().toString().length();

                    if (orgpwdlen == 0 || inputnewpwdlen == 0 || confirmnewpwdlen == 0) {

                        Log.d(TAG, "欄位為空");
                        EmptyFiledsPromptDialog();        // 空欄位提示對話框

                    } else {

                        orgpwd1 = GetOrgPWDFromBundle();   // Getting original password from bundle
                        orgpwd2 = GetOrgPWD();
                        newpwd1 = GetNewPassword();
                        newpwd2 = GetCfnNewPassword();

                        Log.d(TAG, "欄位非空");

                        if (orgpwd1.equals("") || orgpwd2.equals("") || newpwd1.equals("") || newpwd2.equals("")) {
                            Log.d(TAG, "欄位不能為空!");

                        }
                        // 有網路 !
                        else {

                            if (haveInternet()) {

                                // 先要確定網路存在,再將密碼變更 !

                                if (orgpwd1.equals(orgpwd2) && newpwd1.equals(newpwd2)) {   // 輸入全部正確
                                    Log.d(TAG, "全部相同!");

                                    //////////////////////////////////
                                    // 20240109 - peter
                                    // mhandler.post(mRunnable);   // Starting a thread to change password

                                    startHttpRequestThread();  // 啟動一個thread 將密碼變更

                                    // finish();  // close current activity
                                    // mChangePassword1.finish();   // close current activity

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {

                                        @Override
                                        public void run() {

                                            Intent intent;
                                            if (GetOffOnLine() == 0) {
                                                finish();
                                                intent = new Intent(ChangePassword1.this, OffLineMainSquare.class);

                                            } else {
                                                finish();
                                                intent = new Intent(ChangePassword1.this, MainSqaurefun.class);

                                            }

                                            startActivity(intent);

                                        }
                                    }, 2000);

                                } else {

                                    ImageView cancel;  // 右上角取消按鈕
                                    Button confirmbtn ;

                                    //will create a view of our custom dialog layout
                                    View alertCustomdialog = LayoutInflater.from(ChangePassword1.this).inflate(R.layout.custom_dialog, null);
                                    //initialize alert builder.
                                    AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword1.this);

                                    //set our custom alert dialog to tha alertdialog builder
                                    alert.setView(alertCustomdialog);   // 設定dialog的 view
                                    cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);
                                    confirmbtn = (Button) alertCustomdialog.findViewById(R.id.confirmBtn) ;

                                    final AlertDialog dialog = alert.create();

                                    // 下面是動畫的處理  ()
                                    Window window = dialog.getWindow();
                                    window.setGravity(Gravity.CENTER_VERTICAL);  //  這裡可以調整 dialog 顯示的位置 (目前是中間)
                                    window.setWindowAnimations(R.style.mystyle);  // 動畫

                                    //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    //finally show the dialog box in android all

                                    dialog.show();   // 顯示 dialog

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            dialog.dismiss();   // 對話框離開
                                        }
                                    });  // cancel button

                                    confirmbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            }
                            else {

                                // 這裡是要顯示無網路的處裡 !

                                NoNetworkPromptDialog();   // no network prompt dialog

                            }

                            // Log.d(TAG,"GetVerification_ChangePWD() : " + GetVerification_ChangePWD());

                        }   // 有網路　!
                    }
                }

            });

        }
        else  {

            // bundle 有誤 !

        }

}   // end of  onCreate


    private void EmptyFiledsPromptDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(ChangePassword1.this).
                inflate(R.layout.emptyfieldspromptdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword1.this);

        assert alert != null;

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);

        cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);
        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理  ()
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);  //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((AlertDialog) dialog).isShowing()) {
                    //  dialog.dismiss();  // close dialog

                    //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    //logo.startAnimation(animFadeOut);   // execute fade out animation

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                            // logo.startAnimation(animFadeOut);   // execute fade out animation
                            dialog.dismiss();  // close dialog
                        }
                    }, 1000);

                    Log.d(DataManipulation2.TAG, "Ending Animation ");

                }

            }
        });   // cancel button - onclicklistener

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 2000;  // 3 seconds

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

                            //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            //logo.startAnimation(animFadeOut);   // execute fade out animation

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                }
                            }, 200);

                            Log.d(DataManipulation2.TAG, "Ending Animation ");

                        }
                    }
                }.start();
            }
        });

        dialog.show();   // 顯示 dialog


        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setAttributes(layoutParams);
        //设置dialog进入的动画效果
        Window nodbwindow = dialog.getWindow();
        nodbwindow.setWindowAnimations(R.style.AnimBottom);    // 對話框 (中間摺疊)

    }

    private void SetOffOnLine(int offonline) {
        this.offonline = offonline ;

    }

    private void NoNetworkPromptDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(ChangePassword1.this).
                inflate(R.layout.nonetworkingdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword1.this);

        assert alert != null;

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);

        cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);
        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理  ()
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);  //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((AlertDialog) dialog).isShowing()) {
                    //  dialog.dismiss();  // close dialog

                    //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    //logo.startAnimation(animFadeOut);   // execute fade out animation

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                            // logo.startAnimation(animFadeOut);   // execute fade out animation
                            dialog.dismiss();  // close dialog
                        }
                    }, 1000);

                    Log.d(DataManipulation2.TAG, "Ending Animation ");

                }

            }
        });   // cancel button - onclicklistener

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 2000;  // 3 seconds

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

                            //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            //logo.startAnimation(animFadeOut);   // execute fade out animation

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                }
                            }, 200);

                            Log.d(DataManipulation2.TAG, "Ending Animation ");

                        }
                    }
                }.start();
            }
        });

        dialog.show();   // 顯示 dialog


        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setAttributes(layoutParams);

        // dialog animation
        Window nodbwindow = dialog.getWindow();
        nodbwindow.setWindowAnimations(R.style.AnimBottom);    // 對話框 (中間摺疊)

    }

    private int GetOffOnLine () {
        return this.offonline ;
    }


    @Override
    protected void onStop() {
        // Call the superclass method first.
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        // 這個 method 在activity 離開時會呼叫, 因為有thread 執行 所以必須摧毀
         mhandler.removeCallbacks(mRunnable);
         super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d(DataManipulation2.TAG,"onKeyDown");

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (true) {

                Intent intent ;

                if  (GetOffOnLine() == 1 ) {
                    finish();
                    intent = new Intent(ChangePassword1.this, MainSqaurefun.class);
                } else {
                    finish();
                    intent = new Intent(ChangePassword1.this, OffLineMainSquare.class) ;
                }

                startActivity(intent);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


private void InitVerification_ChangePWD(){
        // 初始化密碼變更的旗號
        this.sf = false ;
}
private boolean GetVerification_ChangePWD() {
        // 取得密碼變更的旗號
        Log.d(TAG,"GetVerification_ChangePWD : " + this.sf) ;
        return this.sf ;
}

private void SetVerification_ChangePWD(boolean sf) {

        Log.d(TAG,"SetVerification_ChangePWD : " + sf) ;
        // 取得密碼變更的旗號
         this.sf = sf ;
    }


private void ChangePWDialog()
{

    ImageView cancel;  // 右上角取消按鈕

    //will create a view of our custom dialog layout

    View alertCustomdialog = LayoutInflater.from(ChangePassword1.this).inflate(R.layout.custom_dialog1, null);
    //initialize alert builder.
    AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword1.this);

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

    //finally show the dialog box in android all

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });

    dialog.show();   // 顯示 dialog

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {

        @Override
        public void run() {

            //過兩秒後要做的事情
            if (true) {
                dialog.dismiss();    // 關閉對話框

                // ChangePassword1.mChangePassword1.finish();
                // ChangePassword1.mChangePassword1.finish();
                // Intent intent = new Intent(ChangePassword1.this, MainSqaurefun.class);
                // startActivity(intent);   // 回到主畫面
            }

        }
    }, 2000);

}   // end of change password successfully dialog


    @Override
    public void finish() {

        super.finish();
        overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);  // fade in/out effect

    }

    private void FailedChangePWDialog(View view)
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout
        TextView failedpwdtxt ;

        // failedpwdtxt =  view.findViewById(R.id.pwdfailedtxt);
        // failedpwdtxt.setText("密碼變更失敗");

        View alertCustomdialog = LayoutInflater.from(ChangePassword1.this).inflate(R.layout.custom_dialog1, null);
        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword1.this);
        failedpwdtxt = alertCustomdialog.findViewById(R.id.pwdfailedtxt);
        failedpwdtxt.setText("密碼變更失敗");

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view
        cancel = (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);
        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理  ()
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //finally show the dialog box in android all

        dialog.show();   // 顯示 dialog

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                // 過兩秒後要做的事情

                if (true) {

                    dialog.dismiss();        // 關閉對話框

                    // Intent intent = new Intent(ChangePassword1.this, ChangePassword1.class);
                    // startActivity(intent);   // 回到主畫面
                }

            }
        }, 2000);

    }   // end of change password failed dialog

    // 下面是檢查網路是否存在  - 20240109 peter
    private boolean haveInternet()
    {

        boolean result = false;   //  networking available checking
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();

        if (info == null || !info.isConnected())
        {
            result = false;
        }
        else
        {

            if (!info.isAvailable())
            {
                result =false;
            }
            else
            {
                result = true;
            }
        }

        return result;

    }  // end of haveInternet

    private void SetStatus(int status) {

        if (status != 0)
            Toast.makeText(mChangePassword1, "錯誤!", Toast.LENGTH_SHORT).show();
        else
            this.Status = status;
        return;
    }

    private int GetStatus() {

        return this.Status;
    }

    private void SetOrgPWDFromBundle(String pwd) {

        assert pwd!=null ;
        this.orgpwd = pwd ;
    }

    private String GetOrgPWDFromBundle() {
        assert this.orgpwd!= null ;
        return this.orgpwd ;
    }

    private void SetOrgPWD(String orgpwd) {

        this.OrgAccount = orgpwd;
    }

    private String GetOrgPWD() {
        assert this.OrgAccount != null ;
        return this.OrgAccount;
    }

    private void SetToken(String token) {
        this.Token = token;
    }

    private String GetToken() {

        assert this.Token != null ; // assert token is available
        return this.Token;
    }

    private void SetUserName(String username) {

        assert this.UserName != null ;
        this.UserName = username;
    }

    private String GetUserName() {
        assert this.UserName != null ;
        return this.UserName;
    }

    private void SetPassword(String password) {
        assert this.Password != null ;
        this.Password = password;
    }

    private String GetPassword() {

        assert this.Password!=null;

        return this.Password;
    }

    private void SetOrgPwd(String orgpassword) {
        assert orgpassword!= null ;
        this.OrgPwd = orgpassword;
    }

    private void SetNewPassword(String newpassword) {

            assert newpassword != null ;

            SharedPreferences sharedPreferences = mChangePassword1.getSharedPreferences("channel" , Context.MODE_PRIVATE);
            //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
            sharedPreferences.edit().putString("password" , newpassword).apply();  // setting password

            Log.d(TAG,"SetPassword()" + newpassword ) ;

            this.Password = newpassword ;

    }

    private String GetNewPassword() {

        assert this.Password != null ;

        SharedPreferences sharedPreferences = mChangePassword1.getSharedPreferences("channel" , Context.MODE_PRIVATE);
        String Password = sharedPreferences.getString("password" , "");

        Log.d(DataManipulation2.TAG,"GetNewPassword() " + this.Password) ;
        this.NewPwd = Password ;

        return this.NewPwd;
    }

    private void SetCfnNewPassword(String cfnnewpassword) {

        assert cfnnewpassword!= null ;

        this.CnfNewPwd = cfnnewpassword;
    }

    private String GetCfnNewPassword() {
        assert this.CnfNewPwd != null ;
        return this.CnfNewPwd;
    }

    private void startHttpRequestThread() {

        // 這裡必須注意 thread 必須在activity 離開時把她移除;否則,會有問題(崩潰)

       new Thread (new Runnable() {
             @Override
             public void run() {

                 Looper.prepare();  //  Notice ! Here must be added
                 doHttpRequest();   // do httpurlconnection
                 Looper.loop();

                 /////////////////////////////////////////////
                 // 下面的片段是用來檢查json傳遞內容是否正確
                 // 目前是正確的 - 20240109

                 if (false) {
                     try {

                         String jsonstr = getJsonContent();
                         Log.d(TAG, "JsonString :" + jsonstr);
                     } catch (Exception e) {

                         Log.d(TAG, "Error >> " + e.toString());
                         e.printStackTrace();
                     }
                 }
             }    // end of false

         }).start();   // start a thread for connecting to


        /*
        if (GetVerification_ChangePWD() == true) {
            // Toast.makeText(this, "yes , it is perfect !", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"GetVerification_ChangePWD() == true");
            Intent intent= new Intent(ChangePassword1.this, MainSqaurefun.class);
            startActivity(intent);
        }
        else
        {

        }
         */

    }   // end of startHttpRequestThread

    public String getJsonContent() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        Log.d(TAG,"orginial password: " + GetOrgPWDFromBundle().toString()) ;
        Log.d(TAG,"new password:" + GetCfnNewPassword().toString()) ;
        assert GetCfnNewPassword()!=null && GetOrgPWDFromBundle()!=null  ;

        jsonObject.put("password", GetOrgPWDFromBundle().toString() );  // 原始密碼
        jsonObject.put("newPassword", GetCfnNewPassword().toString());  // 新密碼

        return jsonObject.toString();

    }

    public void doHttpRequest() {

        Log.d(TAG,"doHttpRequest") ;

        try {

            // URL url = new URL("http://192.168.100.201/rfid/api/changePwd");  // login url
            URL url = new URL("http://192.168.0.168/rfid/api/changePwd");  // new login url

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Toast.makeText(mChangePassword1, "Token------>>>>>>"+GetToken(), Toast.LENGTH_SHORT).show();
            conn.setRequestProperty("Authorization","Bearer " + GetToken());
            conn.setRequestMethod("POST");   // post the user data
            conn.setRequestProperty("Connection", "keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");

            // conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();

           String json = getJsonContent(); // pass  and password in Json object

           assert json != null ;   // assert json is available
            // Log.d(TAG, "Json String >> " + json.toString());

            OutputStream os = conn.getOutputStream();
            // UTF_8 format
            os.write(json.getBytes(StandardCharsets.UTF_8));  // pass password and new password to server
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            Log.d(TAG, "Response Code >> " + responseCode);

            // Toast.makeText(this, "Response Code:" + Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
            // android.util.Log.e("tag", "responseCode = " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream input = conn.getInputStream();
                StringBuilder sb  = new StringBuilder();

                int ss;

                while ((ss = input.read()) != -1) {
                    sb.append((char) ss);
                }    // get input stream

                JSONObject jsonObj = new JSONObject(sb.toString());  // Convert string to json object type
                Log.d(TAG, "Json Object *****>> " + jsonObj.toString());

                //  String ResToken ;  // declare a token
                //  ResToken = jsonObj.getString("result");                // Get the token if status code is 0

                int Authorizationstatus = jsonObj.getInt("status");  // Get the return status

                SetStatus(Authorizationstatus);   // set authorization is available

                // SetToken(ResToken);

                Log.d(TAG, "Status *****>>" + Authorizationstatus);
                // Log.d(TAG, "Token >>"  + ResToken);
                Log.d(TAG, "Code *****>> " + responseCode);
                Log.d(TAG, "Response Json String *****>> " + sb.toString());

                input.close();

                if  (GetStatus() == 0 && responseCode == 200 )
                {
                    // authorization is successful and connection is sucessful !

                    SetVerification_ChangePWD(true);   // 設定密碼確認成功旗號 !

                    Log.d(TAG,"密碼變更成功!") ;

                    // ChangePWDialog();    // 顯是更改密碼成功的對話框 !
                    // LaunchMainMenu();   // launch main function menu
                }
                else
                {
                    SetVerification_ChangePWD(false);   // 設定密碼確認失敗旗號 !
                    Log.d(TAG,"Authorization Code :" + Integer.toString(GetStatus())); }
                    Log.d(TAG,""+ GetVerification_ChangePWD()) ;

                    View v = this.getWindow().getDecorView();

                if (GetVerification_ChangePWD() == true) {

                    ChangePWDialog() ;   // 顯示對話框
                    Log.d(TAG,"ChangePWDialog().......");

                }
                else {

                    FailedChangePWDialog(v) ;   // 顯示對話框  (因為需要用到同一個dialog view中的 textview 改名稱 )

                    Log.d(TAG,"FailedChangePWDialog().......");

                }

            }  // http is available !
            else {
                Log.d(TAG,"Code >> " + responseCode) ;
            }

                conn.disconnect();   // disconnect it !

            } catch(Exception e){

                Log.d(TAG, "Error >> " + e.toString());
                e.printStackTrace();
            }
        }   // end of doHttpRequest()

    }   // end of activity
