package com.tra.loginscreen;

import static android.content.ContentValues.TAG;

import static com.tra.loginscreen.R.layout.activity_main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    Button confirmButton;
    EditText username;  // 使用者名稱
    EditText password;  // 密碼
    Button loginButton;

    Button cancelButton;

    TextView ACCMngTxt;

    private boolean flag = false;   // this is a flag which controls password's appearence or not

    ArrayList<String> myList;       // item data
    AlertDialog.Builder alertDialog;


    Map<String,String> Authmap = new HashMap();  // Given a hash map

    final private int OPEN = 111;

    SharedPreferences sharedPreferences;

    private String acc, pwd ;


    private static boolean online = true ;   // online or offline  (default value : true means online )

    public static String ResToken ;  // declare a token

    URL url;
    HttpURLConnection connection = null;

    private SharedPreferences offonlinesharedPreferences ;


    String TAG = "My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();   // key - value pair
    // The following codes show the coordination of from to
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    String JsonStringData ;

    // private final String authURL = "https://192.168.100.201/rfid/login";  // login URL
    private final String authURL = "https://192.168.0.168/rfid/login";  // login URL

    private int resp = 404 ;

    private String Token ;

    private Button ButtonOffline ;

    private int Authorizationstatus ;  // default value is 1 means that it's failed

    private TextView logoversion ;

    //第一参数为保存的文件名，第二个为保存的模型，当文件存在就读取，如果不存在就创建

    public static Activity mMainActivity;   // this is a static activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        SplashActivity.mSplashActivity.finish();   // close splash activity , this is a very important action
        ipcheckdialog.mipcheckdialog.dismiss();
        overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);   // 動畫

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // 防止螢幕休眠
        setContentView(activity_main);

        mMainActivity = this ;   // point to itself ( context)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        /* you must use following codes before using setContentView . because window transaction must be executed ! */
        /* Otherwise , it causes system crash */
        // overridePendingTransition(R.anim.from_right, R.anim.no_slide);
        // setContentView(activity_main);
        // ActivityManager.getInstance().addActivity(this);  // add all activities to activity manager

        PackageManager packageManager = getPackageManager();

        try {
            // Get the package information
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            // Retrieve the version information
            String versionName = packageInfo.versionName;   // apk 的版本號
            int versionCode = packageInfo.versionCode;

            // Use the version information

            logoversion = (TextView)findViewById(R.id.signupText) ;   // 最下面的聲明
            logoversion.setText(logoversion.getText() + versionName);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        Button loginbutton = findViewById(R.id.loginButton);
        loginbutton.setBackgroundColor(Color.TRANSPARENT);
        loginButton = (Button) findViewById(R.id.loginButton);

        SetOnOffLine(true);   // default value

        // Check if we're running on Android 5.0 or higher
        // ACCMngTxt = findViewById(R.id.ACCMNGtxt);

        // acc = findViewById(R.id.username);
        // pwd = findViewById(R.id.password);

        myList = new ArrayList<String>();   // create a array list for storing those items

        // cancelButton = findViewById(R.id.cancelButton);

        // ACCMngTxt = findViewById(R.id.ACCMNGtxt);

        username = findViewById(R.id.username);  // 使用者名稱
        password = findViewById(R.id.password);  // 密碼

        ButtonOffline = (Button)findViewById(R.id.offlineButton);  // 離線作業
        ButtonOffline.setPaintFlags(ButtonOffline.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Countdown();   // count down

        if (false) {

            // connection of network
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected()) {
                Toast.makeText(this, "網路有效 !", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "網路無效,請先連接網路 !", Toast.LENGTH_SHORT).show();
            }

            String show;
            show = NetworkInfo();
            TextView showtxt;

            // Toast.makeText(this, "" + show.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, ">>>> " + show);
        }

        /*
        ACCMngTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "帳密管理", Toast.LENGTH_SHORT).show();
                if (false) ACCPWDMngDialog();   // call the acc/pwd dialog
                Intent intent = new Intent(MainActivity.this, accountMNG1.class);
                startActivity(intent);   // launch main function
                overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_none);   // 進場動畫(從下到上)

            }
        });

         */

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // To get stored data from shared preferences , account and password
                //
                String accresponse = getSharedPreferences("UserData", MODE_PRIVATE)
                        .getString("valueName1", "");
                String pwdresponse = getSharedPreferences("UserData", MODE_PRIVATE)
                        .getString("valueName2", "");

                // Log.d(TAG,"離線作業!");

                SetOffOnLine(view.getContext(),1);  // online main menu
                Log.d(TAG,"離線作業!");


                // Save user name and password !  (線上作業)
                SetUserName(username.getText().toString());   // setting username
                SetPassword(password.getText().toString());  //  setting password
                // SetToken(ResToken.toString());               // setting token
                SetOnOffLine(true);   // setting online

                // ( username.getText().toString().equals("admin@gmail.com") == true ) &&
                // ( password.getText().toString().equals("123456")  == true

               // Authorization(acc,pwd,"http://192.168.100.201/rfid");
               /*
                if ( acc.equals("admin") == true &&
                        pwd.equals("adminP") == true )

                {

                */
                    //  LaunchMainMenu();  // launch main menu
                    // login successfully !
                    // Toast.makeText(MainActivity.this, "登入成功 !", Toast.LENGTH_SHORT).show(); //  This is a toast for showing log in successfully !
                    // JsonStringData = Authorization(Authmap,"http://192.168.100.201/rfid/login/index");
                    // Toast.makeText(MainActivity.this, JsonStringData.toString(), Toast.LENGTH_SHORT).show();

                    startHttpRequestThread();  // post request for login


                    if (Authorizationstatus == 0 ) {

                       // GetCableDataFromWeb(ResToken);  // Get Cable Data from web server
                       //   LaunchMainMenu();  // launch main menu
                        //  GetCableDataFromWeb();

                    }
                    else {

                        Log.d( TAG,"登入失敗,請檢查帳密是否正確 !");

                    }
                    // Intent intentDashBoard = new Intent(MainActivity.this, MainSqaurefun.class);   // invoke dash board
                    // Intent intentDashBoard = new Intent(MainActivity.this, CircularmMainManu.class);   // invoke dash board
                    // ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    // startActivity(intentDashBoard, activityOptions.toBundle());

                }   //   login successfully !


                    // Intent intentMainSqaure = new Intent(MainActivity.this , MainSqaurefun.class) ;
                    // startActivity(intentMainSqaure);  // launch another activity ( square style )
                    // overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_none);   // 進場動畫(從下到上)

               // } else {

                         /*
                        String accresponse = getSharedPreferences("UserData",MODE_PRIVATE)
                            .getString("valueName1","");
                        String pwdresponse = getSharedPreferences("UserData", MODE_PRIVATE)
                                .getString("valueName2","");
                         // Toast.makeText(MainActivity.this, "登入失敗 !", Toast.LENGTH_SHORT).show();
                         Toast.makeText(MainActivity.this, "account is" + accresponse.toString(), Toast.LENGTH_SHORT).show();
                         Toast.makeText(MainActivity.this, "password is" + pwdresponse.toString(),Toast.LENGTH_SHORT).show();
                         */

            // }
        }); // end of login click event listener

            // 這裡是離線作業,操作流程: 1. 先輸入帳號 及 密碼 (必須正確)
            // 2.
        ButtonOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"離線作業!");

                // 儲存名稱 及 密碼 !

                SetUserName(username.getText().toString());  // setting username
                SetPassword(password.getText().toString());  //  setting password

                SetOnOffLine(false);                    // offline operation

                startHttpRequestThread();  // post request for login

                if (Authorizationstatus == 0 ) {

                    // GetCableDataFromWeb(ResToken);  // Get Cable Data from web server
                    //   LaunchMainMenu();  // launch main menu
                    //  GetCableDataFromWeb();
                    // Log.d(TAG,"登入成功並開啟離線畫面!") ;
                    // LaunchOffineMainMenu();

                }
                else {

                    Log.d( TAG,"登入失敗,請檢查帳密是否正確 !");

                }

                // Toast.makeText(MainActivity.this, "Offline ... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SetOffOnLine(Context context , int flag ) {

        this.offonlinesharedPreferences = context.getSharedPreferences("offonline", Context.MODE_PRIVATE);

        // 取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)

        if ( flag == 0) {

        this.offonlinesharedPreferences.edit().putInt("mainmenu", 0).apply();      // offline main menu

        }
        else {
            this.offonlinesharedPreferences.edit().putInt("mainmenu", 1).apply();  // online main menu
        }

        //存入資料，丟入的參數為(key , value)

        int value = this.offonlinesharedPreferences.getInt("mainmenu", 0);   // default value : 0
        Log.d(TAG,"設定:" + value);

    }

    public  void SetToken(String token) {

        assert token != null ;

        SharedPreferences sharedPreferences = mMainActivity.getSharedPreferences("channel" , Context.MODE_PRIVATE);
        //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
        sharedPreferences.edit().putString("token" , token).apply();  // setting token

        Log.d(TAG,"MainActivity -> token  :" + token.toString()) ;

        this.Token = token ;

    }

    private  void SetOnOffLine(boolean flag) {
        this.online = flag ;

    }

    private  String GetToken () {

        assert this.Token != null ;

        return this.Token ;
    }

    private void SetUserName(String username) {

        assert username != null ;
        SharedPreferences sharedPreferences = mMainActivity.getSharedPreferences("channel" , Context.MODE_PRIVATE);
        //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
        sharedPreferences.edit().putString("username" , username).apply();  // setting username

        Log.d(TAG,"MainActivity -> username :" + username.toString()) ;

        this.acc = username ;

    }
    private void SetPassword(String password){

        assert password != null ;

        SharedPreferences sharedPreferences = mMainActivity.getSharedPreferences("channel" , Context.MODE_PRIVATE);
        //取得SharedPreferences ， 丟入的參數為("名稱" , 存取權限)
        sharedPreferences.edit().putString("username" , password).apply();  // setting password

        Log.d(TAG,"MainActivity -> password :" + password.toString()) ;

        this.pwd = password ;
    }

    private String GetUserName() {

        assert  this.acc != null ;  // assert account name is available
        return this.acc ;
    }
    private String GetPassword() {

       //  assert this.pwd != null ;

       if( this.pwd == null )
           Log.d(TAG,"password is null");

        return this.pwd ;
    }

    public void SetAuthorization(int authorizationstatus){

        this.Authorizationstatus = authorizationstatus ;
    }
    public int GetAuthorization() {
        return this.Authorizationstatus ;
    }
    private void GetCableDataFromWeb( )
    {


         Log.d(TAG, "hello");

         String strUrl = "http://localhost:9080/rfid/api/rfidFormat" ;
        // http://192.168.100.201/rfid
         /*

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(strUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); // 设置请求方式为 GET
                    connection.connect(); // 连接

                    int responseCode = connection.getResponseCode();

                    Log.d(TAG,Integer.toString(responseCode));

                    if (responseCode == HttpURLConnection.HTTP_OK) { // 请求成功

                        InputStream inputStream = connection.getInputStream(); // 得到响应流
                        JSONObject json = streamToJson(inputStream); // 从响应流中提取 JSON
                        Log.i(TAG, "88888888888888888888>>>"+json.toString()); // 打印返回的 JSON 观察处理

                    }

                    connection.disconnect();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

     */

        new Thread(){
            @Override
            public void run() {
                try {

                    //HttpURLConnection
                    //1.一個 URL對象
                    URL url = new URL(strUrl);

                    //2.取得 HttpURLConnection 實體
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //3.設置和請求相關的性質
                    //请求方式
                    connection.setRequestMethod("GET");
                    //请求超时时长 ms
                    connection.setConnectTimeout(6000);
                    resp = connection.getResponseCode();
                    Log.d(TAG,"res"+Integer.toString(resp));

                    //4.获取响应码
                    // 200：成功/404：未请求到指定资源/500：服务器异常

                    if (resp == HttpURLConnection.HTTP_OK) {

                        // Log.d(TAG,Integer.toString(resp));

                        //5.判断響應码並獲得響應數(響應的context)
                        //获取响应的流

                        InputStream is = connection.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        // bis.read(b); 该方法返回值是int类型数据，代表的是实际读到的数据长度
                        byte[] b = new byte[1024];
                        int len = 0;

                        //在循环中读取输入流
                        while( (len = bis.read(b)) != -1) {
                            //将字节数组里面的内容存/写入缓存流
                            //参数1：待写入的数组
                            //参数2：起点
                            //参数3：长度
                            baos.write(b, 0, len);
                        }
                        //关闭资源
                        baos.flush();
                        bis.close();
                        baos.close();

                        //6.输出获取到到数据
                        String msg = new String(baos.toByteArray());
                        Log.d(TAG, msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private JSONObject streamToJson(InputStream inputStream) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String temp = "";

        StringBuilder stringBuilder = new StringBuilder();

        while ((temp = bufferedReader.readLine()) != null) {
            stringBuilder.append(temp);
        }
        JSONObject json = new JSONObject(stringBuilder.toString().trim());
        return json;
    }

    // 連線畫面開啟
    private void LaunchMainMenu()
    {

        // 啟動主功能畫面

        Intent intent = new Intent();

        intent.setClass(MainActivity.this, MainSqaurefun.class);  // 跳到主功能頁面
        // passing authorization code and token to next activity and using token to get data from web

        finish();   // close current activity
        overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);  // 進場動畫( in/out resize)

        Bundle bundle = new Bundle();
        bundle.putInt("status", GetAuthorization());   // add GetAuthorization() - 20240112
        bundle.putString("token", ResToken);
        bundle.putString("username",GetUserName());
        bundle.putString("password",GetPassword());

        intent.putExtras(bundle);
        // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);  // 進場動畫( in/out resize)
        startActivity(intent);

        Log.d(TAG,"CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        // finish();   // close
        // 這裡需要一個轉場動畫
        // overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);  // 進場動畫( in/out resize)
    }

    // 離線畫面開啟
    private void LaunchOffineMainMenu(Context context)
    {

        // 啟動主功能畫面
        Intent intent = new Intent();

        // Intent intentDashBoard = new Intent(MainActivity.this, MainSqaurefun.class);   // invoke dash board
        // Intent intentDashBoard = new Intent(MainActivity.this, CircularmMainManu.class);         // invoke dash board
        // ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        // startActivity(intentDashBoard, activityOptions.toBundle());

        intent.setClass(MainActivity.this, OffLineMainSquare.class);  // 跳到主功能頁面 (offline)
        finish();   // close current activity
        overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);  // 進場動畫( in/out resize)

        Log.d(TAG,"離線畫面進入");

        // passing authorization code and token to next activity and using token to get data from web

        Log.d(TAG,"GetAuthorization():" + GetAuthorization());
        Log.d(TAG,"Token:" + ResToken);
        Log.d(TAG,"GetUserName():" + GetUserName());
        Log.d(TAG,"GetPassword():" + GetPassword());

        Bundle bundle = new Bundle();
        bundle.putInt("status", GetAuthorization());   // add GetAuthorization() - 20240112
        bundle.putString("token", ResToken);
        bundle.putString("username",GetUserName());
        bundle.putString("password",GetPassword());

        intent.putExtras(bundle);
        context.startActivity(intent);

        // 這裡需要一個轉場動畫
        // overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);  // 進場動畫(從下到上)
    }


    private void startHttpRequestThread() {

        Thread HttpRequestThread ;


        HttpRequestThread = new Thread(new Runnable() {
            @Override
            public void run() {

                doHttpRequest();   // do httpurlconnection

            }
        });

        HttpRequestThread.start();   // start the thread to get data

    }   // end of startHttpRequestThread

    private String getJsonContent() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userId", GetUserName().toString());     //  username  : admin
        jsonObject.put("password", GetPassword().toString());   //  password  : depends on input

        return jsonObject.toString();

    }

    private void doHttpRequest() {

        try {

            // URL url = new URL("http://192.168.100.201/rfid/api/login");  // login url
            URL url = new URL("http://192.168.0.168/rfid/api/login");  // login url
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");   // post the user data
            conn.setRequestProperty("Connection","keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");

           // conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
           // conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();

            String json = getJsonContent(); // pass username and password in Json object


            Log.d(TAG, "Json String <<<<<>>>>>>> " + json.toString());

            OutputStream os = conn.getOutputStream();
            // UTF_8 format
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            Log.d(TAG, "Response Code <<>> " + responseCode);

            // Toast.makeText(this, "Response Code:" + Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
            // android.util.Log.e("tag", "responseCode = " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK)
            {

                InputStream input = conn.getInputStream();
                StringBuilder sb = new StringBuilder();

                int ss;

                while ((ss = input.read()) != -1) {
                    sb.append((char) ss);
                }    // get input stream

                JSONObject jsonObj = new JSONObject(sb.toString());  // Convert string to json object type
                Log.d(TAG, "Json Object >> " + jsonObj.toString());

                // String ResToken ;  // declare a token
                ResToken = jsonObj.getString("result");          // Get the token if status code is 0
                Authorizationstatus = jsonObj.getInt("status");  // Get the return status

                SetAuthorization(Authorizationstatus);   // set authorization is available

                // SetToken(ResToken);

                Log.d(TAG, "Status >>"  + Authorizationstatus);
                Log.d(TAG, "Token >>"  + ResToken);
                Log.d(TAG, "Code >> "  + responseCode);
                Log.d(TAG, "Response Json String >> " + sb.toString());

                input.close();

                Log.d(TAG,"Auth <<<<>>>>>" + Integer.toString(GetAuthorization())) ;

                if  (GetAuthorization() == 0 && responseCode == 200 )
                {
                    // authorization is successful and connection is sucessful !
                    // 認證成功且連接回傳成功 !
                    // 選擇開啟的畫面 - online 或是 offline
                    //

                    Log.d(TAG,"登入成功且認證無誤,準備進入畫面") ;

                    if (isOnLine()) {
                        Log.d(TAG,"主畫面開啟") ;

                        SetOffOnLine(MainActivity.this,1);   // online main menu
                        LaunchMainMenu();   // launch main function menu (同步作業)
                    }
                    else {

                        Log.d(TAG,"離線畫面開啟") ;  // Launch offline main function
                        SetOffOnLine(MainActivity.this,0);
                        LaunchOffineMainMenu(MainActivity.this); // 進入離線操作畫面
                        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);  // 進場動畫(從下到上)
                    }

                }
                else  {

                    Log.d(TAG,"Authorization Code :" + Integer.toString(GetAuthorization()));

                    // 這裡是除錯用的 !

                    if (false) {
                        Looper.prepare();
                        Toast.makeText(this, "登入失敗!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }   // end of

                    // 因為dialog 中有 handler , 而 thread 中不能用 handler 故必須用 looper.prepare()/ looper.loop()
                    // 20240118 - peter

                    Looper.prepare();
                       LoginErrDialog();   // 登入錯誤對話框 !
                    Looper.loop();

                }

            }
            else {

                Log.d(TAG,"Code >> " + responseCode) ;

            }
            conn.disconnect();   // disconnect it !

        } catch (Exception e) {

            Log.d(TAG, "Error >> " + e.toString());
            e.printStackTrace();
        }
    }
    private boolean isOnLine() {

        // 判斷是否為線上作業

        return this.online ;
    }

    // 登入錯誤對話框
    // 20240118 - peter
    private void LoginErrDialog()
    {
        Log.d(DataManipulation2.TAG,"LoginErrDialog()");
        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(MainActivity.this).
                inflate(R.layout.loginerrdialog, null);   // 登入失敗 !

        //initialize alert builder.
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MainActivity.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel      =  (ImageView) alertCustomdialog.findViewById(R.id.cancel_button);  // 右上角取消按鈕
        // btn_no   = (Button) alertCustomdialog.findViewById(R.id.btn_no) ;           // 不下載按鈕
        // btn_yes  = (Button) alertCustomdialog.findViewById(R.id.btn_yes) ;        // 下載按鈕

        final android.app.AlertDialog dialog = alert.create();

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

                if (((android.app.AlertDialog) dialog).isShowing()) {

                    dialog.dismiss();
                    // close dialog
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
        /*
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();  // close current dialog

                startCableDataHttpGetRequestThreadNoDB();        // 取得纜線資料 (不寫入database)
                // startCableDataFormatHttpGetRequestThread();   // rfid format
                // JustDownLoadJSONData();   //  just download json data
                Toast.makeText(MainSqaurefun.this, "資料", Toast.LENGTH_SHORT).show();

            }
        });    // 下載但不寫入資料庫  !

         */
        /*

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();  // close current dialog
                progressBar = findViewById(R.id.ProgressBar01);  // progress bar
                progressBar.setVisibility(View.GONE);            // 不顯示 progress bar

                // show the progress bar
                // progressBar.getProgress();

                progressBar.setVisibility(View.VISIBLE);
                startCableDataHttpGetRequestThread();   // 取得纜線資料 (寫入database)

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        // 過兩秒後要做的事情

                        if (progressBar.getVisibility() == View.VISIBLE)
                            progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainSqaurefun.this, "纜線資料同步完成!", Toast.LENGTH_SHORT).show();

                    }}, 5000);

            }
        });   // 下載

         */

        dialog.show();   // 顯示 dialog

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情
                dialog.dismiss();  // 關閉對話框

            }}, 2000);

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

            x2 = event.getX();
            y2 = event.getY();

            if(y1 - y2 > 50) {
                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > 50) {
                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }


    public static String getJSONString(Map<String, String> params)
    {

        JSONObject json = new JSONObject();

        for(String key:params.keySet()) {
            try {
                json.put(key, params.get(key));
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return json.toString();
    }

    public void Countdown()
    {

        new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {

                if ( ( username.getText().toString().equals("admin@gmail.com") == true ) &&
                        ( password.getText().toString().equals("123456")  == true         )  // here is a global default account / password
                )
                {  // login successfully !

                    Toast.makeText(MainActivity.this, "登入成功 !", Toast.LENGTH_SHORT).show(); //  This is a toast for showing log in successfully !
                    Intent intentDashBoard = new Intent(MainActivity.this, MainSqaurefun.class);   // invoke dash board
                    startActivity(intentDashBoard);

                    finish();
                    overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);  // 進場動畫(從下到上)

                }  //   login successfully !
                else {

                    Toast.makeText(MainActivity.this, "登入失敗,系統離開 !", Toast.LENGTH_SHORT).show();

                }

               // Intent intent = new Intent();
               // intent.setClass(MainActivity.this, MainSqaurefun.class);
               // startActivity(intent);

                int VERSION = Integer.parseInt(Build.VERSION.SDK);
                if (VERSION >= 5) {
                    MainActivity.this.overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);
                }

               // finish();
            }
        }.start();

    }

    @Override
    public void finish() {
        super.finish();   // override super method
        overridePendingTransition(R.anim.dialog_center_in, R.anim.dialog_center_out);
        //   overridePendingTransition(0, R.anim.activity_push_bottom_out);   // 上到下動畫退場

    }

    private void setupWindowAnimations() {
        Slide slide = (Slide)TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

    private void catchData() {

          //   String url = "http://192.168.100.201/rfid";
        String url = "http://192.168.0.168/rfid";


// do something with the connection
        ProgressDialog dialog = ProgressDialog.show(this,"資料讀取中"
                ,"請稍候",true);   // progressDialog for showing data download
    }

    public String NetworkInfo() {

        ConnectivityManager CM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();

        if(info!=null)
            return
                    "getTypeName:" + info.getTypeName()+"\n" +
                    "getState:"    + info.getState()   +"\n" +
                    "isAvailable:" + info.isAvailable()+"\n" +
                    "isConnected:" + info.isConnected()+"\n" +
                    "isConnectedOrConnecting:" + info.isConnectedOrConnecting()+"\n" +
                    "isFailover:"  + info.isFailover()+"\n" +
                    "isRoaming:"   + info.isRoaming()+ "\n"
                    ;
        else
            return "{}";
    }   // end of

    @Override
    public void onBackPressed(){
        //onBackPressed() 捕获后退键按钮back的信息
        Log.d(DataManipulation2.TAG,"onBackPressed") ;

        /*
        if(mBackPressed+TIME_EXIT < System.currentTimeMillis()){ //currentTimeMillis,返回毫秒级别的系统时间
         */
        super.onBackPressed();

        Log.d(DataManipulation2.TAG,"按了 Back 鍵");

        finish();  // close current activity
        Intent ii = new Intent(MainActivity.this , SplashActivity.class) ;
        startActivity(ii);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AppExitQueryDialog();   // 離開 app 詢問對話框

            }
        });
        // AppExitQueryDialog();   // 離開 app 詢問對話框

        // 這裡要詢問
        // 詢問一下

        // CloseDBHelper(GetDBHelper1());  // close database !

        // 這裡要詢問是否要關閉 app
        /*
        if (false) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }

         */


        return;
            /*
        }else{
            Toast.makeText(this,"再案一次退出app", Toast.LENGTH_SHORT
            ).show();
            mBackPressed=System.currentTimeMillis();
        }

             */
    }

    private void AppExitQueryDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(MainActivity.this).
                inflate(R.layout.appexitdialog, null);

        //initialize alert builder.
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MainActivity.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel   = (ImageView) alertCustomdialog.findViewById(R.id.exitcancel_button);  // 右上角取消按鈕
        btn_no   = (Button) alertCustomdialog.findViewById(R.id.exitbtn_no) ;           // 不離開按鈕
        btn_yes  = (Button) alertCustomdialog.findViewById(R.id.exitbtn_yes) ;          // 離開按鈕

        final android.app.AlertDialog dialog = alert.create();

        // 下面是動畫的處理
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        // logo.startAnimation(animFadeOut);   // execute fade out animation

        //finally show the dialog box in android all

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((android.app.AlertDialog) dialog).isShowing()) {

                    dialog.dismiss();  // close dialog
                    // 只是將 dialog 關閉 , do nothing  !
                    overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)

                }
            }
        });   // cancel button - onclicklistener

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();  // close current dialog

            }
        });    // 下載但不寫入資料庫  !

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if you exit the app , you must close database !
                // CloseDBHelper(GetDBHelper1());   // close database !
                finish(); // close current activity
                MainActivity.this.moveTaskToBack(true);  // 離開 the app

            }
        });   // 下載


        dialog.show();   // 顯示 dialog

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setAttributes(layoutParams);
        //设置dialog进入的动画效果
        Window exitwindow = dialog.getWindow();
        exitwindow.setWindowAnimations(R.style.AnimCenter);    // 對話框離場效果

    }


    /*
     * Show AlertDialog with some form elements.
     */
    public void ACCPWDMngDialog() {

        /*
         * Inflate the XML view. activity_main is in
         * res/layout/form_elements.xml
         */

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.accpwdmng,
                null, false);  // to get the view of dialog

        // You have to list down your form elements
        /*
        final RadioGroup genderRadioGroup = (RadioGroup) formElementsView
                .findViewById(R.id.);

        // the alert dialog
        new AlertDialog.Builder(MainActivity.this).setView(formElementsView)
                .setTitle("請選擇一項")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        String toastString = "";

                        // get selected radio button from radioGroup
                        int selectedId = genderRadioGroup
                                .getCheckedRadioButtonId();

                        // find the radiobutton by returned id

                        RadioButton selectedRadioButton = (RadioButton) formElementsView
                                .findViewById(selectedId);

                        toastString += "Selected radio button is: "
                                + selectedRadioButton.getText() + "!\n";

                        if ( selectedRadioButton.getText().toString().equals("新增帳號") == true) {
                            Toast.makeText(MainActivity.this, "新增帳號", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this , DashBoardLinearActivity.class);
                            startActivity(intent);
                            CreateACCDialog();  // call the dialog of creating account/password
                        }
                        else if (selectedRadioButton.getText().toString().equals("刪除帳號") == true) {
                            Toast.makeText(MainActivity.this, "刪除帳號", Toast.LENGTH_SHORT).show();
                            DeletACCDialog(); //  call the dialog of deleting account/password
                        }
                        else if ( selectedRadioButton.getText().toString().equals("管理帳號") == true ) {
                            Toast.makeText(MainActivity.this, "管理帳號", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e(TAG, "Error, this title is :  " + selectedRadioButton.getText().toString() );
                        }
                        */

/*
                        dialog.cancel();
                    }

                }).show();
    }

    public void CreateACCDialog() {


        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);  // info.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.createacc,null, false);
        // You have to list down your form elements
        final EditText accEditText = (EditText) formElementsView.findViewById(R.id.accedit);
        final  EditText pwdEditText = ( EditText) formElementsView.findViewById(R.id.pwdedit);

        // the alert dialog
        new AlertDialog.Builder(MainActivity.this).setView(formElementsView)
                .setTitle("請輸入新帳號及密碼")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        String acc = "" , pwd ="" ;

                        acc = accEditText.getText().toString().trim() ;
                        pwd = pwdEditText.getText().toString().trim() ;


                        Toast.makeText(MainActivity.this, "帳號 :" + accEditText.getText().toString() + "\n"
                                                                    + "密碼  :" + pwdEditText.getText().toString() , Toast.LENGTH_SHORT).show();

                        if (acc.isEmpty() || pwd.isEmpty()){
                            Toast.makeText(getApplicationContext(),"帳密不能為空",Toast.LENGTH_SHORT).show();
                        }else {
                            // store account and password
                            //获取Editor
                            sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                            sharedPreferences.edit()
                                    .putString("valueName1",acc)
                                    .putString("valueName2",pwd)
                                    .commit();

                            Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                        }

                        dialog.cancel();
                    }

                }).show();

    }

    public void DeletACCDialog() {
        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE); //

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.deleteacc,
                null, false);

        // You have to list down your form elements

        final EditText accEditText = (EditText) formElementsView.findViewById(R.id.accedit1);
        final  EditText pwdEditText = ( EditText) formElementsView.findViewById(R.id.pwdedit1);

        // the alert dialog
        new AlertDialog.Builder(MainActivity.this).setView(formElementsView)
                .setTitle("請輸入欲刪除的帳號及密碼")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        String acc = "" , pwd ="" ;

                        acc = accEditText.getText().toString().trim() ;
                        pwd = pwdEditText.getText().toString().trim() ;

                        Toast.makeText(MainActivity.this, "帳號 :" + accEditText.getText().toString() + "\n"
                                + "密碼  :" + pwdEditText.getText().toString() , Toast.LENGTH_SHORT).show();

                        if (acc.isEmpty() || pwd.isEmpty()){
                            Toast.makeText(getApplicationContext(),"帳密不能為空",Toast.LENGTH_SHORT).show();
                        }else {

                            clear(MainActivity.this) ;
                            Toast.makeText(MainActivity.this, "清除資料完成", Toast.LENGTH_SHORT).show();
                            // store account and password
                            //获取Editor

                            sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                            sharedPreferences.edit()
                                    .putString("valueName1",acc)
                                    .putString("valueName2",pwd)
                                    .commit();

                            Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();

                        }
                        dialog.cancel();
                    }
                }).show();

    }

    public static void clear(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();   // clear all data in shared preference
        editor.commit();
    }

}

 */
    }
}