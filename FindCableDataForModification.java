package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.app.Activity;
import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.graphics.Rect;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListPopupWindow;
        import android.widget.MultiAutoCompleteTextView;
        import android.widget.PopupWindow;
        import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FindCableDataForModification  extends AppCompatActivity  {

    ImageView homeMainimg , BackPreviousimg ;

    Button SearchBtn ;

    // public static Activity mFindCableDataActivity;   // this is a static activity


    private AutoCompleteTextView atv_content  ;    // idc name
    private AutoCompleteTextView atv_content1  ;   // cable name
    private AutoCompleteTextView atv_content2  ;   // port no
    private AutoCompleteTextView atv_content3  ;   // device name

    private MultiAutoCompleteTextView matv_content ;   // this is autocomplete list

    private DBOpenHelper1 mDBHelper1 ;   // dbopenhelper1

    private Cursor mCursor;

    private TextView idcnametxt , cablenametxt  ;   // 機房名稱 , 纜線名稱
    private TextView porttxt , devicenametxt    ;   // 埠號    , 裝置名稱

    private ImageView idcimg , cableimg , portimg, deviceimg ;


    private ArrayList<String> idcListRoomNameFilterArrayList   ;  // 機房名稱初始化
    private String[] idcname ;       // 機房名稱

    private ArrayList<String> rfidListCableNameFilterArrayList   ;  // 纜線名稱初始化
    private String[] cablename ;     // 纜線名稱

    private ArrayList<String> rfidListPortFilterArrayList   ;      // 埠號初始化
    private String[] portno ;        // 埠號

    private ArrayList<String> rfidListDeviceNameFilterArrayList   ;      // 裝置名稱初始化
    private String[] devicename ;    //  裝置名稱


    private String idcnameconfirm ;       // idc  name (confirmation)
    private String cablenameconfirm ;     // cable  name (confirmation)
    private String portconfirm ;          // port (confirmation)
    private String devicenameconfirm ;    // device  name (confirmation)


    public static ProgressDialog dataSearchingPromptDialog ;

    public static ProgressDialog mdataSearchingPromptDialog ;

    public static Activity mFindCableDataForModificationActivity;   // this is a static activity

    private static final String[] data = new String[]{
            "hello", "hell", "helix", "hat", "hight"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // 防止螢幕休眠
        setContentView(R.layout.activity_find_cable_data_for_modification);

        Toast.makeText(this, "纜線修改 - 纜線資料", Toast.LENGTH_SHORT).show();

        homeMainimg = (ImageView) findViewById(R.id.imghome);
        BackPreviousimg = (ImageView) findViewById(R.id.imgback);
        SearchBtn = (Button)findViewById(R.id.btn_search);

        mFindCableDataForModificationActivity = this ;  // point to current activity
        mdataSearchingPromptDialog = dataSearchingPromptDialog ;

        idcnametxt = (TextView) findViewById(R.id.idctxt) ;
        idcimg = (ImageView) findViewById(R.id.idcimg);

        cablenametxt = (TextView) findViewById(R.id.cablenametxt);
        cableimg = (ImageView) findViewById(R.id.cablenameimg);

        porttxt = (TextView) findViewById(R.id.portnotxt);
        portimg = (ImageView) findViewById(R.id.portimg);


        devicenametxt = (TextView)findViewById(R.id.devicenametxt);
        deviceimg = (ImageView)findViewById(R.id.devicenameimg) ;


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        AddListener(); // 監聽器初始化

        // 設定回到搜尋畫面的判斷

        SharedPreferences pref = getSharedPreferences("backtoSearchCableDatarfidforModification",MODE_PRIVATE);
         //讓pref處於編輯狀態
        SharedPreferences.Editor editor = pref.edit();
        //存放資料，put基本資料型態(key, value)，基本資料型態:boolean, float, int, long, String
        editor.putInt("back",0);  // back field
        // 這裡是先設定為 null 字串 - 即初值

        editor.putString("idcname","null");
        editor.putString("cablename","null");
        editor.putString("port","null");
        editor.putString("devicename","null");


        // 直接將修改的結果同步寫入檔案
        editor.commit();
       // 修改記憶體中的暫存資料，並以非同步式寫入檔案
        editor.apply();

        int back = pref.getInt("back : ",0);
        String icdname = pref.getString("idcname","");
        String cablename = pref.getString("cablename","");
        String port = pref.getString("port","");
        String devicenme = pref.getString("devicename","");

        Log.d(TAG,"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

        Log.d(TAG,"back :" + back) ;
        Log.d(TAG,"icdname :" + icdname) ;
        Log.d(TAG,"cablename :" + cablename) ;
        Log.d(TAG,"port :" + port) ;
        Log.d(TAG,"devicename :" + devicename) ;

        /*
        atv_content = (AutoCompleteTextView) findViewById(R.id.atv_content);
        // matv_content = (MultiAutoCompleteTextView) findViewById(R.id.matv_content);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindCableDataForModification.this, android.R.layout.simple_dropdown_item_1line, data);
        atv_content.setAdapter(adapter);

         */

        // ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        // matv_content.setAdapter(adapter);
        // matv_content.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        /*
        atv_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();
                }
            }

        });   //

        atv_content.setError("Invalid input");

         */

        // 建立資料庫及資料表 - rfidformat , oldflist , idclist , rfidlist , ipaddress

        DBOpenHelper1 dbhelper = new DBOpenHelper1(this);
        assert dbhelper!= null ;
        SetDBHelper(dbhelper);                   // save database helper
        DBOpenHelper1 tempdbhelper = GetDBHelper1();
        SQLiteDatabase db = tempdbhelper.getReadableDatabase();
        tempdbhelper.ListAllTablesOfDB();        // Check all available tables have been existed !

        // ClearAllRecords("rfidlist") ;   // clear all records in rfidlist table
        // ClearAllRecords("oldflist") ;   // clear all records in oldflist table
        // ClearAllRecords("idclist")  ;   // clear all records in idclist  table

        ReadRecordsFromDB("rfidlist") ;     // read all records in rfidlist table
        ReadRecordsFromDB("idclist");       // read all records in idclist  table
        ReadRecordsFromDB("oldflist");      // read all records in oldflist table

        if (CheckDBRecords("rfidlist") == 0 ) {
            Log.d(TAG,"rfidlist 表格是空的");
            NoDataPromptDialog();
        }
        else {
            Log.d(TAG,"rfidlist 表格有資料");

        }

        if (CheckDBRecords("idclist") == 0 ) {
            Log.d(TAG, "idclist 表格是空的");
            NoDataPromptDialog();
        }
        else {
            Log.d(TAG,"idclist 表格有資料");
        }

        if (CheckDBRecords("oldflist") == 0 ) {
            Log.d(TAG,"oldflist 表格是空的");
            NoDataPromptDialog();
        }
        else {
            Log.d(TAG,"oldflist 表格有資料");
        }

        atv_content = (AutoCompleteTextView) findViewById(R.id.atv_content);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindCableDataForModification.this, android.R.layout.simple_dropdown_item_1line, GetidcnameForAutocomplete());
        assert adapter != null ;
        atv_content.setAdapter(adapter);

        atv_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();
                }
            }

        });   // 機房名稱


        // 機房名稱判斷
        atv_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG,"目前機房名稱字串是 : " + atv_content.getText().toString()) ;

                idcnameConfirmation(atv_content.getText().toString());   // save the idc name

                boolean yn = MatchXXXXName(atv_content.getText().toString(),0) ;  // check idc name

                if (yn == true ) {
                    idcnametxt.setText("資料相符");
                    idcnametxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                    idcimg.setImageResource(R.drawable.greenhookico);
                    idcnameConfirmation(atv_content.getText().toString());   // confirm the idc name

                }

            }

        });


        // 纜線名稱判斷
        atv_content1 = (AutoCompleteTextView) findViewById(R.id.atv_content1);

       //  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(FindCableDataForModification.this, android.R.layout.simple_dropdown_item_1line, GetcablenameForAutocomplete());
        atv_content1.setAdapter(adapter1);
        atv_content1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();
                }
            }

        });   // 纜線名稱


        atv_content1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,"目前輸入的字串是:" + atv_content1.getText().toString()) ;

            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"目前纜線名稱字串是 : " + atv_content1.getText().toString()) ;

                cablenameConfirmation(atv_content1.getText().toString());   // save the cable  name

                boolean yn = MatchXXXXName(atv_content1.getText().toString(),1) ;  // check cable name

                if (yn == true ) {

                    Log.d(TAG,"資料相同");

                    cablenametxt.setText("資料相符");
                    cablenametxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                    cableimg.setImageResource(R.drawable.greenhookico);
                    cablenameConfirmation(atv_content1.getText().toString());   // confirm the cable name

                }

            }


        });

        // 埠號
        atv_content2 = (AutoCompleteTextView) findViewById(R.id.atv_content2);

        //  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FindCableDataForModification.this, android.R.layout.simple_dropdown_item_1line, GetPortForAutocomplete());
        atv_content2.setAdapter(adapter2);
        atv_content2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();
                }
            }

        });   // 埠號


        atv_content2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG,"目前埠號字串是 : " + atv_content2.getText().toString()) ;

                portConfirmation(atv_content2.getText().toString());   // save the port no

                boolean yn = MatchXXXXName(atv_content2.getText().toString(),2) ;  // check port no

                if (yn == true ) {

                    Log.d(TAG,"資料相同");

                    porttxt.setText("資料相符");
                    porttxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                    portimg.setImageResource(R.drawable.greenhookico);

                    portConfirmation(atv_content2.getText().toString());   // confirm the port

                }

            }

        });

        atv_content3= (AutoCompleteTextView) findViewById(R.id.atv_content3);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(FindCableDataForModification.this, android.R.layout.simple_dropdown_item_1line, GetDeviceNameForAutocomplete());
        assert adapter3 != null ;
        atv_content3.setAdapter(adapter3);
        atv_content3.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();
                }
            }

        });   // 裝置名稱

        atv_content3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"目前裝置字串是 : " + atv_content3.getText().toString()) ;

                devicenameConfirmation(atv_content3.getText().toString());   // save the device name

                boolean yn = MatchXXXXName(atv_content3.getText().toString(),3) ;  // check device name

                if (yn == true ) {

                    Log.d(TAG,"資料相同");

                    devicenametxt.setText("資料相符");
                    devicenametxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                    deviceimg.setImageResource(R.drawable.greenhookico);
                    devicenameConfirmation(atv_content3.getText().toString());   // confirm the device name

                }

            }

        });

    }

    public void idcnameConfirmation (String idcname) {
        assert idcname != null ;
        this.idcnameconfirm = new String();
        this.idcnameconfirm = idcname ;

        Log.d(TAG,"機房名稱:" + this.idcnameconfirm) ;
    }

    public void cablenameConfirmation (String cablename) {
        assert cablename != null ;
        this.cablenameconfirm = new String();
        this.cablenameconfirm = cablename ;

        Log.d(TAG,"纜線名稱:" + this.cablenameconfirm) ;
    }


    public void portConfirmation (String port) {
        assert port != null ;
        this.portconfirm = new String();
        this.portconfirm = port ;

        Log.d(TAG,"埠號:" + this.portconfirm) ;
    }

    public void devicenameConfirmation (String devicename) {
        assert devicename != null ;
        this.devicenameconfirm = new String();
        this.devicenameconfirm = devicename ;

        Log.d(TAG,"裝置名稱:" + this.devicenameconfirm) ;
    }


    public String  GetdevicenameConfirmation () {

        // assert this.devicenameconfirm != null  ;  // make sure device name is available
        Log.d(TAG,"裝置名稱:" + this.devicenameconfirm) ;

        return this.devicenameconfirm ;

    }


    public String  GetidcnameConfirmation () {

         // assert this.idcnameconfirm != null  ;  // make sure device name is available
        Log.d(TAG,"機房名稱:" + this.idcnameconfirm) ;

        return this.idcnameconfirm ;

    }

    public String  GetcablenameConfirmation () {

        // assert this.cablenameconfirm != null  ;  // make sure cable name is available
        Log.d(TAG,"纜線名稱:" + this.cablenameconfirm) ;

        return this.cablenameconfirm ;

    }

    public String  GetportConfirmation () {

        // assert this.portconfirm != null  ;  // make sure port is available
        Log.d(TAG,"埠號:" + this.portconfirm) ;

        return this.portconfirm ;

    }

    public boolean MatchXXXXName(String name , int field ) {

        boolean yesno ;

        yesno = false ;

        String[] namelist ;


        if ( field == 0 ) {
            // 機房名稱檢查
            namelist = new String[GetidcNameLength()];   // idc name
            namelist = this.GetidcnameForAutocomplete() ;

            for (int i = 0 ; i < namelist.length ; i ++) {
                if (name.equals(namelist[i]) == true) {
                    yesno = true;
                    break;
                }
            }  // end of for loop
        }
        else if ( field == 1) {
            // 纜線名稱檢查
            namelist = new String[GetCableNameLength()];  // cable name
            namelist = this.GetcablenameForAutocomplete();

            for (int i = 0 ; i < namelist.length ; i ++) {
                if (name.equals(namelist[i]) == true) {
                    yesno = true;
                    break;
                }
            }  // end of for loop
        }      // end of cable name
        else if ( field == 2) {
            // 埠號檢查
            namelist = new String[GetPortNoLength()];  // port no
            namelist = this.GetPortForAutocomplete();

            for (int i = 0 ; i < namelist.length ; i ++) {
                if (name.equals(namelist[i]) == true) {
                    yesno = true;
                    break;
                }
            }  // end of for loop
        }      // end of port no

        else if ( field == 3) {

            namelist = new String[GetDeviceNameLength()];  // device name
            namelist = this.GetDeviceNameForAutocomplete();

            for (int i = 0 ; i < namelist.length ; i ++) {
                if (name.equals(namelist[i]) == true) {
                    yesno = true;
                    break;
                }
            }  // end of for loop
        }      // end of port no


        else
            Log.d(TAG,"錯誤的名稱") ;

        return yesno ;

    }  // end of MatchXXXXName , idc name and oldf name

    public void InitializationOfIDCRoomName() {

        Log.d(TAG,"初始化idc room name");

        this.idcListRoomNameFilterArrayList = new ArrayList<String>() ;  // 初始化 idc room name list

        assert this.idcListRoomNameFilterArrayList != null;  // check it is available for memory allocation


    }

    public void InitializationOfCableName() {

        Log.d(TAG,"初始化 Cable name");
        this.rfidListCableNameFilterArrayList = new ArrayList<String>() ;  // 初始化 rfidlist cable name list
        assert this.rfidListCableNameFilterArrayList != null;  // check it is available for memory allocation

    }

    public void InitializationOfPort() {

        Log.d(TAG,"初始化 Port ");
        this.rfidListPortFilterArrayList = new ArrayList<String>() ;  // 初始化 rfidlist port  list

        assert this.rfidListPortFilterArrayList != null;  // check it is available for memory allocation

    }

    public void InitializationOfDeviceName() {

        Log.d(TAG,"初始化 Device name");
        this.rfidListDeviceNameFilterArrayList = new ArrayList<String>() ;  // 初始化 rfidlist device name list
        assert this.rfidListDeviceNameFilterArrayList != null;  // check it is available for memory allocation

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

    public int CheckDBRecords(String table) {

        DBOpenHelper1 dbhelper = GetDBHelper1();


        if (dbhelper != null)
            Log.d(TAG, "Not null ");
        else
            Log.d(TAG, "Null");

        assert dbhelper != null;   // assert the database helper is available

        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.setLocale(Locale.CHINESE);//设置本地化

        Log.d(TAG, ">>>>>>>>>>>>>>> 讀取資料表: " + table.toString());

        Cursor cursor = db.rawQuery("select * from " + table.toString(), null);

        Log.d(TAG, ">>>>>>>>>>>>>>> 筆數: " + Integer.toString(cursor.getCount()));

        int amount = cursor.getCount() ;

        if (amount == 0) {
            /*
            DataBaseErrorDialog dataBaseErrorDialog = new DataBaseErrorDialog(this);
            dataBaseErrorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
            dataBaseErrorDialog.show();                // open the upload file dialog
             */
            Log.d(TAG, "資料表 :" + table.toString() + "中無任何資料!");

        }    // no any records in table -

        return amount ;

    }

    public void ClearAllRecords(String table_name ) {

        // 清除全部資料表 - rfidformat , oldflist , rfidlist, idclist

        Log.d(TAG,"清除資料表: "+table_name.toString()+" 中的全部記錄");

        DBOpenHelper1 dbHelper = new DBOpenHelper1(this);    //  create a database helper - dbhelper1
        SQLiteDatabase database = dbHelper.getWritableDatabase();   //  create a database
        database.execSQL("delete from " + table_name );             //  clear all records in table

        //  database.execSQL("delete from sqlite_sequence");
        //  please notice ! the below statement must be cleared !
        //  database.close();   // close database ! //

    }

    private  void Setidcname(String idcname ,int index ) {

        Log.d(TAG,"SetidcnameSetidcnameSetidcnameSetidcnameSetidcnameSetidcnameSetidcnameSetidcname");

        assert idcname != null ;
        this.idcListRoomNameFilterArrayList.add(index,idcname);  // 設置 idc room name list

        return;
    }

    private void SetCablename(String cablename , int index) {

        Log.d(TAG,"SetCablenameSetCablenameSetCablenameSetCablenameSetCablenameSetCablename");


        if (cablename.equals("") || cablename==null) {

            Log.d(TAG,">>>>>>>>>>>>>>>>>>" + "不存") ;
        }

        else {
            Log.d(TAG,"rfidlist ......." + cablename.toString());
            //this.idcListRoomNameFilterArrayList.add(index,cablename);  // 設置 idc room name list
            this.rfidListCableNameFilterArrayList.add(index, cablename);
        }

    }

    private void SetPort(String port , int index) {

        Log.d(TAG,"SetPortSetPortSetPortSetPortSetPortSetPortSetPortSetPort");


        if (port.equals("") || port==null) {

            Log.d(TAG,">>>>>>>>>>>>>>>>>>" + "不存") ;
        }

        else {
            Log.d(TAG,"rfidlist ......." + port.toString());
            //this.idcListRoomNameFilterArrayList.add(index,cablename);  // 設置 idc room name list
            this.rfidListPortFilterArrayList.add(index, port);
        }

    }
    private void SetDeviceName (String devicename , int index) {

        Log.d(TAG,"SetDeviceNameSetDeviceNameSetDeviceNameSetDeviceNameSetDeviceNameSetDeviceName");


        if (devicename.equals("") || devicename==null) {

            Log.d(TAG,">>>>>>>>>>>>>>>>>>" + "不存") ;
        }

        else {

            Log.d(TAG,"rfidlist ......." + devicename.toString());
            //this.idcListRoomNameFilterArrayList.add(index,cablename);  // 設置 device name list
            this.rfidListDeviceNameFilterArrayList.add(index, devicename);

        }

    }
    private int GetDeviceNameLength() {
        return this.rfidListDeviceNameFilterArrayList.size()  ;  // return device name length

    }

    private int GetPortNoLength() {
        return this.rfidListPortFilterArrayList.size()  ;  // return port no length

    }
    private int GetCableNameLength() {
        return this.rfidListCableNameFilterArrayList.size()  ;  // return cable  name length

    }
    private int GetidcNameLength() {
        return this.idcListRoomNameFilterArrayList.size()  ;  // return idc name length

    }


    private String GetidcnameFromidcroomlist(int index ) {

        assert this.idcListRoomNameFilterArrayList.get(index).toString() != null;

        return this.idcListRoomNameFilterArrayList.get(index).toString();

    }

    private String GetcablenameFromrfidlist(int index ) {

        assert this.rfidListCableNameFilterArrayList.get(index).toString() != null;

        return this.rfidListCableNameFilterArrayList.get(index).toString();

    }

    private String GetportFromrfidlist(int index ) {

        assert this.rfidListPortFilterArrayList.get(index).toString() != null;

        return this.rfidListPortFilterArrayList.get(index).toString();

    }

    private String GetdeviceFromrfidlist(int index ) {

        assert this.rfidListDeviceNameFilterArrayList.get(index).toString() != null;

        return this.rfidListDeviceNameFilterArrayList.get(index).toString();

    }

    private String[] GetidcnameForAutocomplete(){

        Log.d(TAG,"GetidcnameForAutocomplete");

        assert this.idcname!= null ;

        for (int i= 0 ; i < idcname.length ; i++) {
            Log.d(TAG,"機房名稱:" + this.idcname[i]) ;
        }

        return this.idcname ;
    }

    private String[] GetcablenameForAutocomplete(){

        Log.d(TAG,"GetcablenameForAutocomplete");

        assert this.cablename!= null ;

        for (int i= 0 ; i < cablename.length ; i++) {
            Log.d(TAG,"纜線名稱:" + this.cablename[i]) ;
        }

        return this.cablename ;
    }

    private String[] GetPortForAutocomplete(){

        Log.d(TAG,"GetPortForAutocomplete");

        assert this.portno!= null ;

        for (int i= 0 ; i < portno.length ; i++) {
            Log.d(TAG,"埠號:" + this.portno[i]) ;
        }

        return this.portno ;
    }

    private String[] GetDeviceNameForAutocomplete(){

        Log.d(TAG,"GetDeviceNameForAutocomplete");

        assert this.devicename!= null ;

        for (int i= 0 ; i < devicename.length ; i++) {
            Log.d(TAG,"裝置:" + this.devicename[i]) ;
        }

        return this.devicename ;
    }


    private void SetidcnameForAutocomplete() {
        int size = GetidcNameLength();

        if (size == 0 ) {
            Log.d(TAG,"無任何機房名稱!");
        }
        else {

            InitializationOfIDCRoomNameForAutocomplete(size); // 初始化機房名稱

            for (int i= 0 ; i < size ; i++) {

                this.idcname[i]= GetidcnameFromidcroomlist(i);
                Log.d(TAG,"機房名稱機房名稱機房名稱機房名稱機房名稱機房名稱機房名稱 "+ this.idcname[i].toString() );

            }

        }

    }

    private void SetcablenameForAutocomplete() {
        int size = GetCableNameLength();

        if (size == 0) {
            Log.d(TAG, "無任何纜線名稱!");
        } else {

            InitializationOfRFIDCableNameForAutocomplete(size); // 初始化纜線名稱

            for (int i = 0; i < size; i++) {

                this.cablename[i] = GetcablenameFromrfidlist(i);
                Log.d(TAG, "纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱纜線名稱 " + this.cablename[i].toString());
            }  // end of for

            // 處理重複的字串

            for (int i = 0; i < cablename.length; i++) {
                for (int j = 0; j < cablename.length; j++) {
                    if (i != j && cablename[i] == cablename[j]) {
                        cablename[j] = "";
                    }
                }
            }  // end of for

        }
    }  // end of SetcablenameForAutocomplete


    private void SetPortForAutocomplete() {
        int size = GetCableNameLength();

        if (size == 0) {
            Log.d(TAG, "無任何纜線名稱!");
        } else {

            InitializationOfRFIDPortForAutocomplete(size); // 初始埠號

            for (int i = 0; i < size; i++) {

                this.portno[i] = GetportFromrfidlist(i);
                Log.d(TAG, "埠號埠號埠號埠號埠號埠號埠號埠號埠號埠號埠號埠號 " + this.portno[i].toString());
            }  // end of for

            // 處理重複的字串

            for (int i = 0; i < portno.length; i++) {
                for (int j = 0; j < portno.length; j++) {
                    if (i != j && portno[i] == portno[j]) {
                        portno[j] = "";
                    }
                }
            }  // end of for

        }
    }  // end of SetcablenameForAutocomplete


    private void SetDeviceNameForAutocomplete() {
        int size = GetDeviceNameLength();

        if (size == 0) {
            Log.d(TAG, "無任何裝置名稱!");
        } else {

            InitializationOfRFIDDeviceNameForAutocomplete(size); // 初始裝置名稱

            for (int i = 0; i < size; i++) {

                this.devicename[i] = GetdeviceFromrfidlist(i);
                Log.d(TAG, "裝置名稱裝置名稱裝置名稱裝置名稱裝置名稱裝置名稱裝置名稱裝置名稱 " + this.devicename[i].toString());
            }  // end of for

            // 處理重複的字串

            for (int i = 0; i < devicename.length; i++) {
                for (int j = 0; j < devicename.length; j++) {
                    if (i != j && devicename[i] == devicename[j]) {
                        devicename[j] = "";
                    }
                }
            }  // end of for

        }
    }  // end of SetDeviceNameForAutocomplete


    private void InitializationOfIDCRoomNameForAutocomplete(int size){
        this.idcname = new String[size];

        assert this.idcname !=null ;

    }


    private void InitializationOfRFIDCableNameForAutocomplete(int size){
        this.cablename = new String[size];

        assert this.cablename !=null ;

    }

    private void InitializationOfRFIDPortForAutocomplete(int size){
        this.portno = new String[size];

        assert this.portno !=null ;

    }

    private void InitializationOfRFIDDeviceNameForAutocomplete(int size){
        this.devicename = new String[size];

        assert this.devicename !=null ;

    }


    public void ReadRecordsFromDB (String table )
    {

        Log.d(TAG,"讀取資料表:"+ table.toString()+ "中的資料記錄" + "\n");
        DBOpenHelper1 dbhelper = GetDBHelper1() ;


        if (dbhelper != null )
            Log.d(TAG,"Not null ");
        else
            Log.d(TAG,"Null");

        assert dbhelper != null ;   // assert the database helper is available

        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.setLocale(Locale.CHINESE);//设置本地化

        Log.d(TAG,">>>>>>>>>>>>>>> 讀取資料表: "+ table.toString());

        Cursor cursor     = db.rawQuery("select * from "+table.toString(),null);

        Log.d(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  筆數: " + Integer.toString(cursor.getCount())) ;

        if (cursor.getCount()==0) {
            /*
            DataBaseErrorDialog dataBaseErrorDialog = new DataBaseErrorDialog(this);
            dataBaseErrorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
            dataBaseErrorDialog.show();                // open the upload file dialog
             */
            Log.d(TAG,"資料表 :" +table.toString() + "中無任何資料!");

        }    // no any records in table -

        if (table.equals("rfidformat") == true ) {

            // 讀取 rfidformat表中資料

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");

            while (cursor.moveToNext()) {
                //
                String id = cursor.getString(cursor.getColumnIndexOrThrow("ids"));
                String display = cursor.getString(cursor.getColumnIndexOrThrow("display"));
                int json = cursor.getInt(cursor.getColumnIndexOrThrow("json"));
                int required = cursor.getInt(cursor.getColumnIndexOrThrow("required"));
                int editable = cursor.getInt(cursor.getColumnIndexOrThrow("editable"));
                int visible = cursor.getInt(cursor.getColumnIndexOrThrow("visible"));

                Log.d(TAG, "rfidformat- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d(TAG, "id :" + id.toString() + "\n");
                Log.d(TAG, "display :" + id.toString() + "\n");
                Log.d(TAG, "json :" + Integer.toString(json) + "\n");
                Log.d(TAG, "required :" + Integer.toString(required) + "\n");
                Log.d(TAG, "editable :" + Integer.toString(editable) + "\n");
                Log.d(TAG, "visible :" + Integer.toString(visible) + "\n");

            }   // end of while

        }
        else if (table.equals("oldflist") == true ) {

            // 讀取 oldflist 表中資料
            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");

            while (cursor.moveToNext()) {
                //
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("oldfId"));
                String oldfname = cursor.getString(cursor.getColumnIndexOrThrow("oldfName"));
                int idcId = cursor.getInt(cursor.getColumnIndexOrThrow("idcId"));
                int ports = cursor.getInt(cursor.getColumnIndexOrThrow("ports"));
                String tagId = cursor.getString(cursor.getColumnIndexOrThrow("tagId"));
                String connector = cursor.getString(cursor.getColumnIndexOrThrow("connector"));
                String floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
                String row = cursor.getString(cursor.getColumnIndexOrThrow("rows"));
                String cabinet = cursor.getString(cursor.getColumnIndexOrThrow("cabinet"));
                String box = cursor.getString(cursor.getColumnIndexOrThrow("box"));

                Log.d(TAG, "oldflist - >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d(TAG, "id :" + Integer.toString(id) + "\n");
                Log.d(TAG, "oldfname :" + oldfname.toString() + "\n");
                Log.d(TAG,"idcId :" + Integer.toString(idcId) + "\n");
                Log.d(TAG, "ports :" + Integer.toString(ports) + "\n");
                Log.d(TAG, "tagId : " + tagId.toString() + "\n" );
                Log.d(TAG, "connector :" + connector.toString() + "\n");
                Log.d(TAG, "floor :" + floor.toString() + "\n");
                Log.d(TAG, "row :" + row.toString() + "\n");
                Log.d(TAG, "cabinet :" + cabinet.toString() + "\n");
                Log.d(TAG, "box :" + box.toString() + "\n");

            }   // end of while

        }

        else if (table.equals("idclist") == true ) {

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");
            Log.d(TAG,"dilhlskkhsklghldshglkdhgldshlgdhlglkhglksdhlgkdlldslkghlk");

            InitializationOfIDCRoomName();  // 初始化idc name 的記憶體

            int count = 0 ;    // 初始的資料數

            while (cursor.moveToNext()) {
                //
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idcId"));
                String idcName = cursor.getString(cursor.getColumnIndexOrThrow("idcName"));

                Log.d(TAG, "idclist- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d(TAG, "id :" + Integer.toString(id) + "\n");
                Log.d(TAG, "idcName :" + idcName.toString() + "\n");

                if (idcName.equals("")) {
                     // do nothing !
                }
                else {
                    Setidcname(idcName, count);   // 存放 idc room name
                    count++;
                }

            }   // end of while

            Log.d(TAG,"長度<><><><><><><><><><><> " + GetidcNameLength()) ;

            SetidcnameForAutocomplete();
            GetidcnameForAutocomplete();  // check autocomplete

        }
        else if (table.equals("rfidlist") == true ) {

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");


            InitializationOfCableName();   // 初始化纜線名稱
            InitializationOfPort();        // 初始化埠名稱
            InitializationOfDeviceName();  // 初始化裝置名稱


            int count = 0  ;      // 初始化 idc name index
            int countport = 0 ;   // 初始化 port index
            int countdevice = 0 ;  // 初始化 device name index

            while (cursor.moveToNext()) {
                //
                int    rfidsId = cursor.getInt(cursor.getColumnIndexOrThrow("rfidsId"));
                String tagId = cursor.getString(cursor.getColumnIndexOrThrow("tagId"));
                int    oldfId = cursor.getInt(cursor.getColumnIndexOrThrow("oldfId"));
                String cableName = cursor.getString(cursor.getColumnIndexOrThrow("cableName"));
                String port = cursor.getString(cursor.getColumnIndexOrThrow("port"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String deviceName = cursor.getString(cursor.getColumnIndexOrThrow("deviceName"));
                String updateUser = cursor.getString(cursor.getColumnIndexOrThrow("updateUser"));
                String updateTime = cursor.getString(cursor.getColumnIndexOrThrow("updateTime"));
                String jsonData = cursor.getString(cursor.getColumnIndexOrThrow("jsonData"));

                Log.d(TAG, "rfidlist- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                Log.d(TAG, "rfidsId :" + Integer.toString(rfidsId) + "\n");
                Log.d(TAG, "tagId :"   + tagId.toString() + "\n");
                Log.d(TAG, "oldfId :"  + Integer.toString(oldfId)+"\n");
                Log.d(TAG, "cableName :"  + cableName.toString()+"\n");
                Log.d(TAG, "port :"  + port.toString()+"\n");
                Log.d(TAG, "status :" + status.toString() + "\n");
                Log.d(TAG, "deviceName :"   + deviceName.toString() + "\n");
                Log.d(TAG, "updateUser :"  + updateUser.toString()+"\n");
                Log.d(TAG, "updateTime :"  + updateTime.toString()+"\n");
                Log.d(TAG, "jsonData :"  + jsonData.toString()+"\n");


                if (cableName.equals("") || cableName == null ) {
                    // do nothing !
                }
                else
                    //  SetCablename(cableName, count);   // 存放 cable name
                {
                    Log.d(TAG,"計數:" + count );
                    SetCablename(cableName, count);
                    count ++  ;
                }

                if (port.equals("") || port == null) {

                }
                else {

                    Log.d(TAG,"port 數:"+ countport) ;
                    SetPort(port, countport);
                    countport ++  ;
                }

                if (deviceName.equals("")|| deviceName == null ) {

                }
                else {

                    Log.d(TAG,"device 名稱:" + countdevice) ;
                    SetDeviceName(deviceName,countdevice);
                    countdevice ++ ;

                }

               // count ++  ;

            }   // end of while

            Log.d(TAG,"SEESEESEESEESEESEESEESEESEESEESEESEESEESEESEE");
            Log.d(TAG,"長度 <><><><><><><><><><><> " + GetCableNameLength()) ;  // show cable name size
            SetcablenameForAutocomplete();   // 設定纜線名稱
            SetPortForAutocomplete();        // 埠號設定
            SetDeviceNameForAutocomplete();  // 裝置名稱設定

        }
        else {
            Log.d(TAG,"資料表不存在 !");
        }

        cursor.close();

    }  // end of show record from database

    public int CheckRecordsInTable(DBOpenHelper1 dbhelper , String table) {

        // 本方法是用來清空資料表中所有的記錄, 因為每次下載資料都要先將資料清空,以免重複資料被加入

        Log.d(TAG,"檢查表中的記錄");
        int amount = 0 ;

        SQLiteDatabase database = dbhelper.getReadableDatabase();

        Cursor c = database.rawQuery("select * from "+table, null);
        amount = c.getCount();

        Log.d(TAG,"表-"+table+"紀錄數量 :"+ amount);

        return amount ;   // 回傳資料數量

    }

    private void AddListener () {

        homeMainimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(v.getContext(),MainSqaurefun.class);
                startActivity(intent);
                // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)

            }
        });

        BackPreviousimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(v.getContext(),MainSqaurefun.class) ;
                startActivity(intent);
                 // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)

            }
        });

    /*
        atv_content.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(FindCableData.this, "Hello", Toast.LENGTH_SHORT).show();

            }
        });

     */
        // 開始搜尋
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idcname, cablename, port, devicename;

                // 取出確認要搜尋的欄位資料 - 機房名稱 , 光纜名稱 , 埠號 , 設備名稱
                ////////////////////////////////////////////////////

                idcname = GetidcnameConfirmation();
                cablename = GetcablenameConfirmation();
                port = GetportConfirmation();
                devicename = GetdevicenameConfirmation();

                /*
                Log.d(TAG,"?????????????????????????????????");
                Log.d(TAG,"idcname >>>>" + Integer.toString(idcname.length())) ;
                Log.d(TAG,"cablename >>>>" + Integer.toString(cablename.length())) ;
                Log.d(TAG,"port >>>>" + Integer.toString(port.length())) ;
                Log.d(TAG,"devicename >>>>" + Integer.toString(devicename.length())) ;
                 */


                if ((idcname == null || idcname.length() == 0) ||
                        (cablename == null || cablename.length() == 0) ||
                        (port == null || port.length() == 0) ||
                        (devicename == null || devicename.length() == 0)) {

                    Log.d(TAG, "有欄位為空!!!");

                    SearchFiledsErrorDialog(15);  // 顯示錯誤的對話框

                } else {

                    SharedPreferences pref = getSharedPreferences("backtoSearchCableDatarfidforModification", MODE_PRIVATE);
                    //讓pref處於編輯狀態
                    SharedPreferences.Editor editor = pref.edit();
                    //存放資料，put基本資料型態(key, value)
                    // 這裡是先設定為 null 字串 - 即初值

                    editor.putString("idcname", idcname);
                    editor.putString("cablename", cablename);
                    editor.putString("port", port);
                    editor.putString("devicename", devicename);
                    // 四個欄位的查詢


                    // 直接將修改的結果同步寫入檔案
                    editor.commit();
                    // 修改記憶體中的暫存資料，並以非同步式寫入檔案
                    editor.apply();

                    Log.d(TAG, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    Log.d(TAG, "idc name: " + idcname);
                    Log.d(TAG, "cable name:" + cablename);
                    Log.d(TAG, "port:" + port);
                    Log.d(TAG, "device name:" + devicename);

                    SharedPreferences sharedPref = getSharedPreferences("backtoSearchCableDatarfidforModification", MODE_PRIVATE);
                    String idcanmesharedPref = sharedPref.getString("idcname", "null");
                    String cablenamesharedPref = sharedPref.getString("cablename", "null");
                    String portsharedPref = sharedPref.getString("port", "null");
                    String devicenamesharedPref = sharedPref.getString("devicename", "null");

                    Log.d(TAG, "JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
                    Log.d(TAG, "idc name: " + idcanmesharedPref);
                    Log.d(TAG, "cable name:" + cablenamesharedPref);
                    Log.d(TAG, "port:" + portsharedPref);
                    Log.d(TAG, "device name:" + devicenamesharedPref);

                    // 將那些資料放入 bundle - 機房名稱 , 纜線名稱 , 埠號 , 裝置名稱
                    Bundle bundle = new Bundle();
                    bundle.putString("idcname", GetidcnameConfirmation());
                    bundle.putString("cablename", GetcablenameConfirmation());
                    bundle.putString("port", GetportConfirmation());
                    bundle.putString("devicename", GetdevicenameConfirmation());
                    bundle.putInt("closeflag", 1);   // close datapromptdialog flag


                    String query3tablesfromconditions = "SELECT " +
                            "idclist.idcName, " +
                            "oldflist.oldfName, " +
                            "rfidlist.cableName, " +
                            "rfidlist.jsonData, " +
                            "rfidlist.status, " +
                            "oldflist.floor, " +
                            "rfidlist.tagId, " +
                            "rfidlist.deviceName, " +
                            "oldflist.rows, " +
                            "oldflist.cabinet, " +
                            "oldflist.box, " +
                            "oldflist.ports, " +
                            "rfidlist.updateUser, " +
                            "rfidlist.updateTime " +

                            "FROM idclist " +
                            "JOIN oldflist " +
                            "ON idclist.idcId = oldflist.idcId " +
                            "JOIN rfidlist " +
                            "ON oldflist.oldfId = rfidlist.oldfId " +

                            "WHERE " +
                            "rfidlist.cableName = ? " +   // 纜線名稱
                            "OR oldflist.ports = ? "  +    // 埠號
                            "OR rfidlist.deviceName = ? " +    // 裝置名稱
                            "OR idclist.idcName = ?";        // 機房名稱


                    String[] selectionArgs = new String[]{
                            cablename ,   // 光纜名稱
                            port,         // 埠
                            devicename,   // 裝置名稱
                            idcname       // 機房名稱

                    };                    // 搜尋條件 (所有欄位)

                    DBOpenHelper1 dbhelper = new DBOpenHelper1(FindCableDataForModification.this);
                    assert dbhelper != null;  // assert database helper is available

                    SetDBHelper(dbhelper);                   // save database helper
                    DBOpenHelper1 tempdbhelper = GetDBHelper1();
                    SQLiteDatabase db = tempdbhelper.getReadableDatabase();

                    Cursor cursor = db.rawQuery(query3tablesfromconditions, selectionArgs);   // 查詢 合併的3個表格

                    Log.d(TAG, "找出來的資料數目:" + cursor.getCount());

                    int howmanyrecordshasbeenfound = cursor.getCount();
                    if (howmanyrecordshasbeenfound == 0) {

                        DataSearchingPromptDialog(bundle, v , false);  // 纜線搜尋提示對話框

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                // CloseDataSearchingPromptDialog(v) ;
                                // NoRecordsPromptDialog(v);   // 搜尋無資料提示對話框

                            }}, 2000);

                         // NoRecordsPromptDialog(v);   // 搜尋無資料提示對話框

                    } else {

                        Log.d(TAG, "FindCableDataForModification.java --------");

                        assert bundle != null;
                        assert v != null;

                        DataSearchingPromptDialog(bundle, v , true);  // 纜線搜尋提示對話框


                    }  // end of else
                }
            }
        });

    }   // end of  listeners  group


    public void NoRecordsPromptDialog( View view ){

        //過兩秒後要做的事情
        Log.d(TAG,"NoRecordsPromptDialog()");

        if (true) {

            dataSearchingPromptDialog = new ProgressDialog(FindCableDataForModification.this, R.style.dialog);
            dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
            dataSearchingPromptDialog.setMessage("查無纜線資料");
            dataSearchingPromptDialog.setCancelable(false);

            // dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dataSearchingPromptDialog.show();
            mdataSearchingPromptDialog = dataSearchingPromptDialog ;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
            dataSearchingPromptDialog.getWindow().setAttributes(lp);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                dataSearchingPromptDialog.dismiss();   // close the searching dialog 2 secs later
            }
        }, 2000);

        /*
        dataSearchingPromptDialog = new ProgressDialog(FindCableDataForModification.this, R.style.dialog);
        dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
        dataSearchingPromptDialog.setMessage("搜尋中...");
        dataSearchingPromptDialog.setCancelable(false);

        dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dataSearchingPromptDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
        dataSearchingPromptDialog.getWindow().setAttributes(lp);

         */

        // Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification.class);
        // intent.putExtras(bundle);
        // startActivity(intent);
        // finish();   // close current activity


    }


    // 搜尋資料提示對話框
    public void DataSearchingPromptDialog(Bundle bundle , View view , boolean which  ){

        assert bundle != null ; // assert the bundle is available

                //過兩秒後要做的事情
                Log.d(TAG,"DataSearchingPromptDialog()");

                if (true) {

                    dataSearchingPromptDialog = new ProgressDialog(FindCableDataForModification.this, R.style.dialog);
                    dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
                    dataSearchingPromptDialog.setMessage("纜線資料搜尋中");
                    dataSearchingPromptDialog.setCancelable(false);

                    dataSearchingPromptDialog.show();
                    mdataSearchingPromptDialog = dataSearchingPromptDialog ;

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
                    dataSearchingPromptDialog.getWindow().setAttributes(lp);

                }
                    if (which == true) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                //過兩秒後要做的事情
                                CableDataSearchPromptDialog();

                            }}, 2000);
                      //  CableDataSearchPromptDialog();

                    }

                    if (which == false) {   // 無資料

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                dataSearchingPromptDialog.dismiss();   // close the searching dialog
                                NoRecordsPromptDialog(view);   // 搜尋無資料提示對話框
                            }}, 2000);

                    }

                    Log.d("tag","close it ...");


                    if (which == true) {

                        // 傳出搜尋資料

                    Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification.class);
                    assert bundle !=null ;
                    // 傳資料過去

                    intent.putExtras(bundle);        // 將 bundle 包裹後傳出來
                    view.getContext().startActivity(intent);

                    }

                    // 使用 recyclerview 的方式 20240320
                    /*
                    Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification1.class);
                    assert bundle !=null ;
                    // 傳資料過去
                    intent.putExtras(bundle); // 將 bundle 包裹後傳出來
                    startActivity(intent);

                     */

                    // finish();   // close current activity

                    overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(
                           // dataSearchingPromptDialog.dismiss();

            /*
            }}, 3000);

             */
        /*
        Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification.class);
        assert bundle !=null ;
        // 傳資料過去
        intent.putExtras(bundle); // 將 bundle 包裹後傳出來
        startActivity(intent);
        // finish();   // close current activity
        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);
         */

        /*
        dataSearchingPromptDialog = new ProgressDialog(FindCableDataForModification.this, R.style.dialog);
        dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
        dataSearchingPromptDialog.setMessage("搜尋中...");
        dataSearchingPromptDialog.setCancelable(false);

        dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dataSearchingPromptDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
        dataSearchingPromptDialog.getWindow().setAttributes(lp);

         */

        // Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification.class);
        // intent.putExtras(bundle);
        // startActivity(intent);
        // finish();   // close current activity

                //過兩秒後要做的事情
                        /*
                        Intent intent = new Intent(v.getContext() , SearchCableDatarfidforModification.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();   // close current activity

                         */
        /*
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable(){

            @Override
            public void run() {

         */

                //過兩秒後要做的事情
                Log.d("tag","close it ...");
            /*
                Intent intent = new Intent(view.getContext() , SearchCableDatarfidforModification.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();   // close current activity

                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(
                // dataSearchingPromptDialog.dismiss();

             */
             /*
            }}, 2000);

              */

    }

    public void CloseDataSearchingPromptDialog(View view)
    {

        //過兩秒後要做的事情
        Log.d(TAG,"CloseDataSearchingPromptDialog()");

        if (false) {

            dataSearchingPromptDialog = new ProgressDialog(FindCableDataForModification.this, R.style.dialog);
            dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
            dataSearchingPromptDialog.setMessage("纜線資料搜尋中");
            dataSearchingPromptDialog.setCancelable(false);

            // dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dataSearchingPromptDialog.show();
            mdataSearchingPromptDialog = dataSearchingPromptDialog ;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
            dataSearchingPromptDialog.getWindow().setAttributes(lp);

        }

            dataSearchingPromptDialog.dismiss();   // close the searching dialog

    }

    private void SearchFiledsErrorDialog(int which )
    {

        // which 為分別 機房名稱 , 光纖名稱 , 埠號 , 設備名稱 皆空的檢查之用

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;

        TextView msgtxt ;

        // will create a view of our custom dialog layout
        //
        // 空欄位的對話框
        View alertCustomdialog = LayoutInflater.
                from(FindCableDataForModification.this).
                inflate(R.layout.cablemodificationerrordialog, null);  // 空的資料

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(FindCableDataForModification.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel   = (ImageView) alertCustomdialog.findViewById(R.id.exitcancel_button);  // 右上角取消按鈕
        // btn_no   = (Button) alertCustomdialog.findViewById(R.id.exitbtn_no) ;           // 不離開按鈕
        // btn_yes  = (Button) alertCustomdialog.findViewById(R.id.exitbtn_yes) ;          // 離開按鈕

        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);   //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        // logo.startAnimation(animFadeOut);   // execute fade out animation

        //finally show the dialog box in android all

        msgtxt = (TextView)  alertCustomdialog.findViewById(R.id.mesgtxt) ;    // 錯誤訊息的顯示

        switch (which) {

            case 0 :
                Log.d(TAG,"名稱皆為空");
                msgtxt.setText("全部名稱皆未輸入");
                break ;
            case 1:
                Log.d(TAG,"機房名稱為空");
                msgtxt.setText("機房名稱未輸入");
                break ;
            case 2:
                Log.d(TAG,"光纖名稱為空");
                msgtxt.setText("光纖名稱未輸入");
                break ;
            case 4 :
                Log.d(TAG,"名稱皆為空");
                msgtxt.setText("全部名稱皆未輸入");
                break ;
            case 3:
                Log.d(TAG,"機房名稱為空");
                msgtxt.setText("機房名稱未輸入");
                break ;
            case 5:
                Log.d(TAG,"光纖名稱為空");
                msgtxt.setText("光纖名稱未輸入");
                break ;
            case 6 :
                Log.d(TAG,"名稱皆為空");
                msgtxt.setText("全部名稱皆未輸入");
                break ;
            case 7:
                Log.d(TAG,"機房名稱為空");
                msgtxt.setText("機房名稱未輸入");
                break ;
            case 8:
                Log.d(TAG,"光纖名稱為空");
                msgtxt.setText("光纖名稱未輸入");
                break ;
            case 9 :
                Log.d(TAG,"名稱皆為空");
                msgtxt.setText("全部名稱皆未輸入");
                break ;
            case 10:
                Log.d(TAG,"機房名稱為空");
                msgtxt.setText("機房名稱未輸入");
                break ;
            case 11:
                Log.d(TAG,"光纖名稱為空");
                msgtxt.setText("光纖名稱未輸入");
                break ;
            case 12 :
                Log.d(TAG,"名稱皆為空");
                msgtxt.setText("全部名稱皆未輸入");
                break ;
            case 13:
                Log.d(TAG,"機房名稱為空");
                msgtxt.setText("機房名稱未輸入");
                break ;
            case 14:
                Log.d(TAG,"光纖名稱為空");
                msgtxt.setText("光纖名稱未輸入");
                break ;
            case 15:
                Log.d(TAG,"欄位不能為空");
                msgtxt.setText("有欄位未輸入");
                break ;
            default:
                Log.d(TAG,"錯誤 ! ");
                break ;

        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((AlertDialog) dialog).isShowing()) {

                    dialog.dismiss();  // close dialog
                    // 只是將 dialog 關閉 , do nothing  !
                    overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)

                }
            }
        });   // cancel button - onclicklistener


        /*
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
                CloseDBHelper(GetDBHelper1());   // close database !
                finish(); // close current activity
                OLDFDataFind.this.moveTaskToBack(true);  // 離開 the app

            }
        });   // 下載

         */

        dialog.show();   // 顯示 dialog

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setAttributes(layoutParams);
        //设置dialog进入的动画效果
        Window exitwindow = dialog.getWindow();
        exitwindow.setWindowAnimations(R.style.AnimCenter);    // 對話框離場效果

        // 2 seconds later , the dialog was closed

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情

                dialog.dismiss();  // close dialog

            }}, 2000);

    }

    private void CableDataSearchPromptDialog()
    {

        // 無資料庫對話框提示 !

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(FindCableDataForModification.this).
                inflate(R.layout.cabledataresearchdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(FindCableDataForModification.this);

        assert alert != null;

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);

        cancel = (ImageView) alertCustomdialog.findViewById(R.id.nodbcancel_button);
        final AlertDialog dialog = alert.create();

        // 下面是動畫的處理  ()

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);  //  這裡可以調整 dialog 顯示的位置 (目前是中間)
        window.setWindowAnimations(R.style.mystyle);  // 動畫

        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //finally show the dialog box in android all
        /*
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((AlertDialog) dialog).isShowing()) {

                    //  dialog.dismiss();  // close dialog
                    //  Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    //  logo.startAnimation(animFadeOut);   // execute fade out animation

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                            // logo.startAnimation(animFadeOut);   // execute fade out animation
                            // dialog.dismiss();  // close dialog
                        }
                    }, 1000);

                    Log.d(TAG, "no data ");

                }

            }
        });   // cancel button - onclicklistener

         */

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

                            Log.d(TAG, "no data");

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

        // 设置dialog进入的动画效果

        Window nodbwindow = dialog.getWindow();
        nodbwindow.setWindowAnimations(R.style.AnimBottom);    // 對話框 (中間摺疊)


    }

    @Override
    public void finish() {
        super.finish();

        CloseDBHelper(GetDBHelper1());  // close database !

        Log.d(TAG,"FindCableDataForModification 離開!");
        Intent i = new Intent(FindCableDataForModification.this, MainSqaurefun.class);
        startActivity(i);

        // mMainSquarefunActivity.moveTaskToBack(true);  // exit the app

        // overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);  // fade in/out effect  (淡出效果)
        // overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);  // fade in/out effect  (底出效果)
        // overridePendingTransition(R.anim.anim_zoom_in, R.anim.anim_zoom_out);   //

        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);  // fade in/out  的特效
    }

    @Override
    public void onBackPressed() {

        CloseDBHelper(GetDBHelper1());  // close database !
        super.onBackPressed(); //此行根据情况可以被comments
        overridePendingTransition(R.anim.anim_zoom_in, R.anim.anim_zoom_out);
    }

    private void NoDataPromptDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(FindCableDataForModification.this).
                inflate(R.layout.cabledatadownloaddialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(FindCableDataForModification.this);

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

                if (((AlertDialog) dialog).isShowing()) {
                    dialog.dismiss();  // close dialog

                    //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    //logo.startAnimation(animFadeOut);   // execute fade out animation

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            // Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                            // logo.startAnimation(animFadeOut);   // execute fade out animation
                            // do nothing  , you should click cancel button !

                        }
                    }, 200);

                    Log.d(TAG, "Ending Animation ");

                }

            }
        });   // cancel button - onclicklistener

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 2000;  // 2 seconds

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
                            // dialog.dismiss();  // close dialog

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    dialog.dismiss();  // close dialog
                                }
                            }, 2500);

                            Log.d(TAG, "Ending Animation ");

                        }
                    }
                }.start();
            }
        });

        dialog.show();   // 顯示 dialog

    }

}