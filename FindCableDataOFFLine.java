package com.tra.loginscreen;


import static android.widget.Toast.LENGTH_SHORT;
import static com.tra.loginscreen.DataManipulation2.TAG;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.graphics.Rect;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.os.Handler;
        import android.text.Editable;
        import android.text.TextWatcher;
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
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListPopupWindow;
        import android.widget.MultiAutoCompleteTextView;
        import android.widget.PopupWindow;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.UnsupportedEncodingException;
        import java.net.URLEncoder;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.HashSet;

public class FindCableDataOFFLine extends AppCompatActivity implements TextWatcher {

    ImageView homeMainimg ,             // 主選單畫面
            BackPreviousimg ,           // 回前頁
            IdcNameWarningimg  ,        // 機房名稱
            rfidCableNameWarningimg ,   // 光纜名稱
            rfidPortNoWarningimg   ,    // 埠號
            rfidDeviceNameWarningimg ;  // 裝置名稱

    Button SearchBtn ;   // 搜尋按鈕

    private DBOpenHelper1 dbOpenHelper1 ;  // database helper

    private ArrayList<idcData> idcList ;    // idc data list (機房名稱)
    private ArrayList<rfidData> rfidList ;  // rfid data list (光纜名稱 , 埠號 , 裝置名稱)

    private int idcListLen ;   // idc list's length
    private int rfidListLen ;  // rfid list's length


    private int idcroomnamelen ;   //  idc room name length
    private int portnolen ;        //  port no length
    private int cablenamelen ;     //  cable name length
    private int devicenamelen ;    //  device name length

    private int cablenameFilterLen ;  // cable name filter length

    private int AmountOfIDCList  ;    // amount of idc list records
    private int AmountOfOLDFList ;    // amount of oldf list records
    private int AmountOfRFIDList ;    // amount of rfidlist records


    // 如下資料是儲存預搜尋的資料欄位 - 機房名稱 , 光纖名稱 , 埠號 , 設備名稱

    private String idcRoomName ;     // 機房名稱
    private String FiberName   ;     // 光纖名稱
    private String PortNo      ;     // 埠號
    private String DeviceName  ;     // 設備名稱

    private TextView idcRoomNameTxt ;    // 機房名稱
    private TextView rfidCableNameTxt ;  // 光纜名稱

    private TextView rfidPortNoTxt ;     // 埠號
    private TextView rfidDeviceNameTxt ; // 裝置名稱


    public static Activity mFindCableDataActivity;   // this is a static activity

    public static ProgressDialog dataSearchingPromptDialog;

    /////  下拉式選單 ///////////////////////////////////////////////////////
    private AutoCompleteTextView atv_content  ;                // 機房名稱下拉式清單
    private AutoCompleteTextView      cablename_autocompletetxt  ;  // 光纜名稱下拉式選單
    private AutoCompleteTextView      portno_autocompletetext  ;    // 埠號下拉式選單
    private AutoCompleteTextView      devicename_autocompletetext ; // 設備名稱下拉式選單


    private MultiAutoCompleteTextView matv_content ;   // this is autocomplete list

    private String[] idcRoomListdata    ;           // 存放 idc name 之用 (機房名稱)
    private String[] rfidListCableNamedata ;        // 存放 rfid list cable name (光纜名稱)
    private String[] rfidListPortNo ;               // 存放 rfid list port no (埠號)
    private String[] rfidListDeviceName ;           // 存放 rfid list device name (裝置名稱)

    // 如下為 cable name , port no , device name 的過濾資料
    private ArrayList<String> rfidListCableNamedataFilterArrayList  = new ArrayList<String>();      // allocate array list for cable name filter
    private ArrayList<String> rfidListPortNoFilterArrayList         = new ArrayList<String>();      // allocate array list for port no   filter
    private ArrayList<String> rfidListDeviceNameFilterArrayList     = new ArrayList<String>();      // allocate array list for device name filter
    private ArrayList<String> idcListRoomNameFilterArrayList        = new ArrayList<String>() ;     // allocate array list for room name filter

    private String[] rfidListCableNamedataFilter ;  // 存放全部非空的字串 (光纜名稱) 過濾後
    private String[] rfidListPortNoFilter ;         // 存放全部非空的字串 (埠號) 過濾後
    private String[] rfidListDeviceNameFilter ;     // 存放全部非空的字串 (裝置名稱) 過濾後

    private String[] idcListRoomNameFilter ;        // 存放全部非空的字串 (機房名稱) 過濾後

    private String[] rfidListPortdata       ;       // 存放 rfid list port (埠號)
    private String[] rfidListDeviceNamedata ;       // 存放 rfid list device name (設備名稱)

    private SQLiteDatabase db ;

    // 下列是滑動時座標的初始直
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cable_data_offline);

        Toast.makeText(this, "纜線尋找 - 纜線資料", LENGTH_SHORT).show();

        homeMainimg     = (ImageView) findViewById(R.id.imghome);
        BackPreviousimg = (ImageView) findViewById(R.id.imgback);
        SearchBtn       = (Button)findViewById(R.id.btn_search);

        // 機房篩選  ////////////////////////////////////////////////////////
        idcRoomNameTxt    = (TextView)findViewById(R.id.idcnametxt) ;                   // idc room name (機房名稱) text message
        IdcNameWarningimg = (ImageView)findViewById(R.id.idcnamewarningImg) ;           // idc room name image
        // 光纜名稱篩選  ////////////////////////////////////////////////////
        rfidCableNameTxt = (TextView)findViewById(R.id.cablenametxt) ;                  // rfid cable name (光纜名稱)  text message
        rfidCableNameWarningimg = (ImageView)findViewById(R.id.cablenamewarningImg) ;   // rfid cable name image
        // 埠號篩選  //////////////////////////////////////////////////////
        rfidPortNoTxt = (TextView)findViewById(R.id.portnotxt) ;                        // rfid port no (埠號) text message
        rfidPortNoWarningimg  = (ImageView)findViewById(R.id.portnowarningImg) ;        // rfid port no (埠號) image

        rfidDeviceNameTxt = (TextView)findViewById(R.id.devicenametxt) ;                        // rfid device name (裝置名稱) text message
        rfidDeviceNameWarningimg  = (ImageView)findViewById(R.id.devicenamewarningImg ) ;       // rfid device name (裝置名稱) image

        mFindCableDataActivity = this ;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        SetDBHelper();   // 設置 dbopenhelper

        /*SQLiteDatabase*/ db = GetDBHelper().getReadableDatabase() ;  // open a database for read idclist table


        if (db != null )
            Log.d(TAG,"db 是無誤") ;
        else
            Log.d(TAG,"db 是有誤") ;

        // 讀取資料庫中表格 - idclist , rfidlist , they should use thread to do ! ( maybe next time will implement )
        //////////////////////////////////////////////////////////////////////
        ReadDataFromDB(db,"idclist");     // read data from database with table - idclist
        ReadDataFromDB(db,"rfidlist");    // read data from database with table - rfidlist
        // the above read database is okay ! 20240122

        AddListener();  // add listeners

        InitializationOfLength() ;  // initialziation of fields' length

        // overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)

        ///////  下拉式選單初始化  /////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////
        atv_content = (AutoCompleteTextView) findViewById(R.id.atv_content);                    // 初始化機房名稱選單
        cablename_autocompletetxt   = (AutoCompleteTextView)findViewById(R.id.cablename_txt) ;  // 初始化光纜名稱選單
        portno_autocompletetext     = (AutoCompleteTextView)findViewById(R.id.atv_content2) ;   // 初始化埠號選單
        devicename_autocompletetext = (AutoCompleteTextView)findViewById(R.id.atv_content3);    // 初始化設備名稱

        Log.d(TAG,"h");  // it's okay

        // 下面是為了將避免輸入鍵盤將畫面遮到影響輸入欄位, 所以在每個欄位輸入後關閉 !

        devicename_autocompletetext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);

            }

        });   // close device name field's keyboard


        Log.d(TAG,"x");  // it's okay

        portno_autocompletetext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);

            }

        });   // close port no field's keyboard

        Log.d(TAG,"t");  // it's okay

        cablename_autocompletetxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);

            }

        });   // close cable name field's keyboard

        Log.d(TAG,"r");  // it's okay


        atv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);  // 處理鍵盤遮蔽現象

            }

        });   // close dic Room name field's keyboard

        Log.d(TAG,"v");  // it's okay

        assert  atv_content                 != null &&
                cablename_autocompletetxt   != null &&
                portno_autocompletetext     != null &&
                devicename_autocompletetext != null  ;    // assert all dropdown lists are available !

        Log.d(TAG,"p");  // it's okay


        // 設定機房名稱下拉式選單項目 //////////////////////////////////

        if (true) {

            Log.d(TAG,"0");  // it's okay
            ArrayAdapter<String> idcnameadapter = new ArrayAdapter<String>(FindCableDataOFFLine.this, android.R.layout.simple_dropdown_item_1line, GetidcRoomListdataItem());
            Log.d(TAG,"1");  // it's okay

            atv_content.setThreshold(1);          // will start working from first character
            atv_content.setAdapter(idcnameadapter);
            Log.d(TAG,"2");  // it's okay
            atv_content.setTextColor(Color.BLUE);  // highlight red color text
        }

        Log.d(TAG,"l");  // it's okay


        if(true) {

            // 設定光纜名稱下拉式選單項目 //////////////////////////////////
            ArrayAdapter<String> cablenameadapter = new ArrayAdapter<String>(FindCableDataOFFLine.this, android.R.layout.simple_dropdown_item_1line, GetrfidCableNameListdataItem());
            Log.d(TAG,"光纜名稱1") ;
            cablename_autocompletetxt.setThreshold(1);              // will start working from first character
            cablename_autocompletetxt.setAdapter(cablenameadapter); // cable name adapter
            Log.d(TAG,"光纜名稱2") ;
            cablename_autocompletetxt.setTextColor(Color.BLUE);      // highlight red color text
            Log.d(TAG,"光纜名稱3") ;

        }
        Log.d(TAG, "m");  // it's okay

        if (true) {

            // 設定埠號下拉選單項目  /////////////////////////////////////
            ArrayAdapter<String> portnoadapter = new ArrayAdapter<String>(FindCableDataOFFLine.this, android.R.layout.simple_dropdown_item_1line, GetrfidPortNoListdataItem());
            portno_autocompletetext.setThreshold(1);                // will start working from first character
            portno_autocompletetext.setAdapter(portnoadapter);      // port no adapter
            portno_autocompletetext.setTextColor(Color.BLUE);        // highlight red color text
        }

        Log.d(TAG, "a");  // it's okay

        if (true) {

            // 設定裝置名稱下拉選單項目  /////////////////////////////////////
            ArrayAdapter<String> devicenameadapter = new ArrayAdapter<String>(FindCableDataOFFLine.this, android.R.layout.simple_dropdown_item_1line, GetrfidDeviceNameListdataItem());
            devicename_autocompletetext.setThreshold(1);                    // will start working from first character
            devicename_autocompletetext.setAdapter(devicenameadapter);      // device name adapter
            devicename_autocompletetext.setTextColor(Color.BLUE);            // highlight red color text

            Log.d(TAG, "b");  // it's okay
        }

        // ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, GetidcRoomListdataItem());
        // matv_content.setAdapter(adapter);
        // matv_content.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // 如下為機房名稱輸入的檢查機制 - disable !
        ////////////////////////////////////////////////////////////
        if (false) {
            atv_content.setValidator(new AutoCompleteTextView.Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    Log.d(TAG, "Checking if valid: " + text);
                    Arrays.sort(idcRoomListdata);

                    if (Arrays.binarySearch(idcRoomListdata, text.toString()) > 0) {
                        Log.d(TAG,"存在 : " + text.toString()) ;
                        Toast.makeText(FindCableDataOFFLine.this, "Correct", LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }

                @Override
                public CharSequence fixText(CharSequence invalidText) {
                    // If .isValid() returns false then the code comes here
                    // do whatever way you want to fix in the
                    // users input and  return it
                    atv_content.setTextColor(Color.RED);
                    return "機房名稱輸入錯誤,請重新輸入 !";

                }
            });  // end of setValidator (機房名稱)


            class FocusListener implements View.OnFocusChangeListener {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.getId() == R.id.atv_content && !hasFocus) {
                        ((AutoCompleteTextView)v).performValidation();
                    }
                }
            }

        }        // enable this function

        // 如下為纜線名稱輸入的檢查機制
        if (false) {

            cablename_autocompletetxt.setValidator(new AutoCompleteTextView.Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    Log.v("Test", "Checking if valid: " + text);
                    Arrays.sort(rfidListCableNamedata);
                    if (Arrays.binarySearch(rfidListCableNamedata, text.toString()) > 0) {
                        Toast.makeText(FindCableDataOFFLine.this, "Correct", LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }

                @Override
                public CharSequence fixText(CharSequence invalidText) {

                    // If .isValid() returns false then the code comes here
                    // do whatever way you want to fix in the
                    // users input and  return it

                    cablename_autocompletetxt.setTextColor(Color.RED);
                    return "纜線名稱輸入錯誤,請重新輸入 !";
                }
            });  // end of setValidator (纜線名稱)
        }        // disable this function


        // 如下為埠號輸入的檢查機制
        if (false) {
            portno_autocompletetext.setValidator(new AutoCompleteTextView.Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    Log.v("Test", "Checking if valid: " + text);
                    Arrays.sort(rfidListPortNoFilter);
                    if (Arrays.binarySearch(rfidListPortNoFilter, text.toString()) > 0) {
                        Toast.makeText(FindCableDataOFFLine.this, "Correct", LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }

                @Override
                public CharSequence fixText(CharSequence invalidText) {

                    // If .isValid() returns false then the code comes here
                    // do whatever way you want to fix in the
                    // users input and  return it

                    portno_autocompletetext.setTextColor(Color.RED);
                    return "埠號輸入錯誤,請重新輸入 !";

                }
            });  // end of setValidator (埠號)
        }        // disable this function

        if (false) {
            devicename_autocompletetext.setValidator(new AutoCompleteTextView.Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    Log.v("Test", "Checking if valid: " + text);
                    Arrays.sort(rfidListDeviceNameFilter);
                    if (Arrays.binarySearch(rfidListDeviceNameFilter, text.toString()) > 0) {
                        Toast.makeText(FindCableDataOFFLine.this, "Correct", LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }

                @Override
                public CharSequence fixText(CharSequence invalidText) {
                    // If .isValid() returns false then the code comes here
                    // do whatever way you want to fix in the
                    // users input and  return it

                    devicename_autocompletetext.setTextColor(Color.RED);
                    return "裝置名稱輸入錯誤,請重新輸入 !";

                }
            });  // end of setValidator (埠號)
        }        // disable this function


        ///////////////////////////////////////
        // 機房名稱下拉清單
        atv_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 獲取焦点顯示下拉清單
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();   // 若autotextcomplete 被點擊到 , 則下拉是選單出現
                    // Log.d(TAG,">>>>>>>>>>>>>>>>>" + autoCompleteTextView.getText().toString());
                }
                else {

                    String AutoCompleteTextString = autoCompleteTextView.getText().toString() ;   // 這一步重要!
                    Log.d(TAG, ">>>>>>>>>>>>>>>>>" + AutoCompleteTextString.toString());
                    boolean flag  = false   ;
                    boolean flag1 = false  ;

                    // 搜尋 idc list 中機房名稱

                    for ( int i = 0 ; i < GetAmountOfList("idclist") ; i ++)
                    {

                        String idcRoomName = GetidcRoomListdata(i);  // 取出機房名稱
                        assert idcRoomName != null ;
                        Log.d(TAG,"第"+ Integer.toString(i) + "個字串:" + idcRoomName.toString()) ;

                        if (AutoCompleteTextString.toString().equals(idcRoomName) == true ) {
                            // 比較機房名稱 - 若在其中,則將選中的機房名稱存起來 !
                            idcRoomNameTxt.setText("資料相符");
                            idcRoomNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                            IdcNameWarningimg.setImageResource(R.drawable.greenhookico);
                            SaveidcNameForSearch(autoCompleteTextView.getText().toString());     // 儲存 機房名稱

                            flag1= true  ;

                            break;  // 離開迴圈
                        }           // 若有在其中

                    }       // end of for loop

                    if (flag == false && flag1 != true ) {  // 沒在其中 (機房名稱)
                        idcRoomNameTxt.setText("無資料");
                        idcRoomNameTxt.setTextColor(Color.parseColor("#FF0101")); // red color
                        IdcNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                        atv_content.setError("");     // show error !
                        Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                    }

                }   // end of else
            }
        });   // end of 機房名稱拉清單


        // atv_content.setError("Error", null) ;
        // atv_content.setError("");
        /*"Invalid input"*/

        ///////////////////////////////////////
        // 光纜名稱下拉清單
        cablename_autocompletetxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // 获取焦点显示下拉
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();   // 若autotextcomplete 被點擊到 , 則下拉是選單出現
                    // Log.d(TAG,">>>>>>>>>>>>>>>>>" + autoCompleteTextView.getText().toString());
                }
                else {

                    String AutoCompleteTextString = autoCompleteTextView.getText().toString() ;   // 這一步重要!
                    Log.d(TAG, ">>>>>>>>>>>>>>>>>" + AutoCompleteTextString.toString());
                    boolean flag  = false   ;
                    boolean flag1 = false  ;

                    // 搜尋 rfid list 中光纜名稱

                    for ( int i = 0 ; i < GetAmountOfList("rfidlist") ; i ++)
                    {

                        String rfidCableName = GetrfidCableNameListdata(i);  // 取出光纜名稱
                        Log.d(TAG,"第"+ Integer.toString(i) + "個字串:" + rfidCableName.toString()) ;

                        if (AutoCompleteTextString.toString().equals(rfidCableName) == true ) {

                            // 比較光纜名稱 - 若在其中,則將選中的光纜名稱存起來 !
                            rfidCableNameTxt.setText("資料相符");
                            rfidCableNameTxt.setTextColor(Color.parseColor("#2E964B")); // green color
                            rfidCableNameWarningimg.setImageResource(R.drawable.greenhookico);

                            SaverfidCableNameForSearch(autoCompleteTextView.getText().toString());     // 儲存 光纜名稱
                            flag1= true  ;

                            break;   // 離開迴圈
                        }            // 若有在其中

                    }       // end of for loop

                    if (flag == false && flag1 != true ) {  // 沒在其中 (機房名稱)
                        rfidCableNameTxt.setText("無資料");
                        rfidCableNameTxt.setTextColor(Color.parseColor("#FF0101")); // red color
                        rfidCableNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                        cablename_autocompletetxt.setError("Invalid input");
                        Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                    }

                }   // end of else
            }

        });   // end of 光纜名稱下拉清單

        // cablename_autocompletetxt.setError("Invalid input");

        ///////////////////////////////////////
        // 埠號下拉清單
        portno_autocompletetext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // port no drop down list focus
                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {
                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();   // 若 autotextcomplete 被點擊到 , 則下拉是選單出現
                    // Log.d(TAG,">>>>>>>>>>>>>>>>>" + autoCompleteTextView.getText().toString());
                }
                else {

                    String AutoCompleteTextString = autoCompleteTextView.getText().toString() ;   // 這一步重要!
                    Log.d(TAG, ">>>>>>>>>>>>>>>>>" + AutoCompleteTextString.toString());

                    boolean flag  = false   ;
                    boolean flag1 = false  ;

                    // 搜尋 rfid list 中埠號

                    for ( int i = 0 ; i < GetAmountOfList("rfidlist") ; i ++)
                    {

                        String portno = GetrfidPortNoListdata(i);  // 取出埠號名稱
                        Log.d(TAG,"第"+ Integer.toString(i) + "個埠號:" + portno.toString()) ;

                        if (AutoCompleteTextString.toString().equals(portno) == true ) {

                            // 比較埠號名稱 - 若在其中,則將選中的埠號名稱存起來 !
                            rfidPortNoTxt.setText("資料相符");
                            rfidPortNoTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                            rfidPortNoWarningimg.setImageResource(R.drawable.greenhookico);

                            SavePortNoForSearch(autoCompleteTextView.getText().toString());     // 儲存 埠號
                            flag1 = true  ;

                            break;  // 離開迴圈
                        }           // 若有在其中

                    }       // end of for loop

                    if (flag == false && flag1 != true ) {  // 沒在其中 (埠號)

                        rfidPortNoTxt.setText("無資料");
                        rfidPortNoTxt.setTextColor(Color.parseColor("#FF0101")); // red color
                        rfidPortNoWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                        portno_autocompletetext.setError("Invalid input");
                        Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");

                    }

                }   // end of else
            }

        });   // end of 埠號下拉清單

        // portno_autocompletetext.setError("Invalid input");

        ///////////////////////////////////////
        // 裝置名稱下拉清單
        devicename_autocompletetext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // port no drop down list focus

                AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) v;

                if (hasFocus) {

                    // if you had been click this field , the list was dropped down .
                    autoCompleteTextView.showDropDown();   // 若 autotextcomplete 被點擊到 , 則下拉是選單出現
                    // Log.d(TAG,">>>>>>>>>>>>>>>>>" + autoCompleteTextView.getText().toString());
                }
                else {
                    // 取得所選的項目
                    String AutoCompleteTextString = autoCompleteTextView.getText().toString() ;   // 這一步重要!
                    Log.d(TAG, "裝置名稱 : /////// " + AutoCompleteTextString.toString());

                    boolean flag  = false   ;
                    boolean flag1 = false   ;


                    if (false) {
                        // For debugging !
                        for (int j = 0; j < rfidListDeviceNameFilter.length /*GetAmountOfList("rfidlist") */   ; j++)
                            Log.d(TAG, "第 " + Integer.toString(j) + "個裝置名稱 :" + rfidListDeviceNameFilter[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                        // 列出 rfid list 中裝置名稱
                    }   // end of debugging

                    for ( int i = 0 ; i < rfidListDeviceNameFilter.length ; i ++)
                    {

                        String devicename = rfidListDeviceNameFilter[i].toString() ; /*GetrfidDeviceNameListdata(i); */ // 取出裝置名稱
                        Log.d(TAG,"第"+ Integer.toString(i) + "個裝置名稱:" + devicename.toString()) ;

                        if (AutoCompleteTextString.toString().equals(devicename) == true ) {

                            // 比較裝置名稱 - 若在其中,則將選中的裝置名稱存起來 !

                            rfidDeviceNameTxt.setText("資料相符");
                            rfidDeviceNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                            rfidDeviceNameWarningimg.setImageResource(R.drawable.greenhookico);

                            SaveDeviceNameForSearch(autoCompleteTextView.getText().toString());     // 儲存 裝置名稱

                            flag1 = true  ;

                            break;  // 離開迴圈
                        }           // 若有在其中

                    }       // end of for loop

                    if (flag == false && flag1 != true ) {  // 沒在其中 (裝置名稱)

                        rfidDeviceNameTxt.setText("無資料");
                        rfidDeviceNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                        rfidDeviceNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                        devicename_autocompletetext.setError("Invalid input");
                        Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                    }
                    else {

                        SaveDeviceNameForSearch(autoCompleteTextView.getText().toString());     // 儲存 裝置名稱
                    }

                }   // end of else
            }

        });   // end of 裝置下拉清單

        // devicename_autocompletetext.setError("Invalid input");

        ////////////////////////////////////////////////////////////////////////////////////
        // real time watch listener
        // 裝置名稱及時監聽
        devicename_autocompletetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // do nothing !
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG,"onTextChanged 裝置名稱 :" +  devicename_autocompletetext.getText().toString());

                boolean flag  = false   ;
                boolean flag1 = false   ;

                if (false) {

                    // For debugging !
                    for (int j = 0; j < rfidListDeviceNameFilter.length /*GetAmountOfList("rfidlist") */   ; j++)

                        Log.d(TAG, "第 " + Integer.toString(j) + "個裝置名稱:" + rfidListDeviceNameFilter[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                    // 列出 rfid list 中裝置名稱
                }   // end of debugging

                for ( int i = 0 ; i < rfidListDeviceNameFilter.length ; i ++)
                {

                    String rfiddeviceName  = GetrfidDeviceNameListdata(i); // 取出裝置名稱
                    Log.d(TAG,"第"+ Integer.toString(i) + "個裝置名稱:" + rfiddeviceName.toString()) ;

                    if (devicename_autocompletetext.toString().equals(rfiddeviceName) == true ) {

                        // 比較裝置名稱 - 若在其中,則將選中的裝置名稱存起來 !

                        rfidDeviceNameTxt.setText("資料相符");
                        rfidDeviceNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                        rfidDeviceNameWarningimg.setImageResource(R.drawable.greenhookico);

                        SaveidcNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                        flag1 = true  ;

                        break;  // 離開迴圈
                    }           // 若有在其中
                }               // end of for loop

                if (flag == false && flag1 != true ) {  // 沒在其中 (裝置名稱)

                    rfidDeviceNameTxt.setText("無資料");
                    rfidDeviceNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                    rfidDeviceNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                    devicename_autocompletetext.setError("Invalid input");

                    Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                }
                else {

                    SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                }   // end of else

                SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());         // 機房名稱

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG,"設備名稱 :" +  devicename_autocompletetext.getText().toString());

                boolean flag  = false   ;
                boolean flag1 = false   ;

                if (false) {

                    // For debugging !
                    for (int j = 0; j < rfidListDeviceNameFilter.length /*GetAmountOfList("rfidlist") */   ; j++)

                        Log.d(TAG, "第 " + Integer.toString(j) + "個裝置名稱:" + rfidListDeviceNameFilter[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                    // 列出 rfid list 中機房名稱
                }   // end of debugging

                for ( int i = 0 ; i < rfidListDeviceNameFilter.length ; i ++)
                {

                    String rfiddeviceName  = GetrfidDeviceNameListdata(i); // 取出裝置名稱
                    Log.d(TAG,"第"+ Integer.toString(i) + "個裝置名稱:" + rfiddeviceName.toString()) ;

                    if (devicename_autocompletetext.toString().equals(rfiddeviceName) == true ) {

                        // 比較裝置名稱 - 若在其中,則將選中的裝置名稱存起來 !

                        rfidDeviceNameTxt.setText("資料相符");
                        rfidDeviceNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                        rfidDeviceNameWarningimg.setImageResource(R.drawable.greenhookico);

                        SaveidcNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                        flag1 = true  ;

                        break;  // 離開迴圈
                    }           // 若有在其中
                }               // end of for loop

                if (flag == false && flag1 != true ) {  // 沒在其中 (裝置名稱)

                    rfidDeviceNameTxt.setText("無資料");
                    rfidDeviceNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                    rfidDeviceNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                    devicename_autocompletetext.setError("");
                    Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                }
                else {

                    SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                }   // end of else

                SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());         // 機房名稱

            }

        });    // end of devicename_autocompletetext - 裝置名稱

        // 機房名稱及時監聽
        atv_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"機房名稱 :" +  atv_content.getText().toString());

                boolean flag  = false   ;
                boolean flag1 = false   ;

                if (false) {

                    // For debugging !
                    for (int j = 0; j < idcRoomListdata.length /*GetAmountOfList("rfidlist") */   ; j++)
                        Log.d(TAG, "第 " + Integer.toString(j) + "個機房名稱:" + idcRoomListdata[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                    // 列出 rfid list 中機房名稱
                }   // end of debugging

                for ( int i = 0 ; i < idcRoomListdata.length ; i ++)
                {

                    String idcroomName  = idcRoomListdata[i].toString() ; /*GetrfidDeviceNameListdata(i); */ // 取出機房名稱
                    Log.d(TAG,"第"+ Integer.toString(i) + "個機房名稱:" + idcroomName.toString()) ;

                    if (atv_content.toString().equals(idcroomName) == true ) {

                        // 比較埠號 - 若在其中,則將選中的埠號存起來 !

                        idcRoomNameTxt.setText("資料相符");
                        idcRoomNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                        IdcNameWarningimg.setImageResource(R.drawable.greenhookico);

                        SaveidcNameForSearch(atv_content.getText().toString());     // 儲存 機房

                        flag1 = true  ;

                        break;  // 離開迴圈
                    }           // 若有在其中
                }               // end of for loop

                if (flag == false && flag1 != true ) {  // 沒在其中 (纜線名稱)

                    rfidCableNameTxt.setText("無資料");
                    rfidCableNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                    rfidCableNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                    Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                }
                else {

                    SaveidcNameForSearch(atv_content.getText().toString());     // 儲存機房名稱

                }   // end of else

                SaveidcNameForSearch(atv_content.getText().toString());         // 機房名稱

            }
        });  // end of atv_content - 機房名稱

        // 埠號及時監聽
        portno_autocompletetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"埠號 :" +  portno_autocompletetext.getText().toString());

                boolean flag  = false   ;
                boolean flag1 = false   ;

                if (false) {

                    // For debugging !

                    for (int j = 0; j < rfidListPortNoFilter.length /*GetAmountOfList("rfidlist") */   ; j++)
                        Log.d(TAG, "第 " + Integer.toString(j) + "個埠號名稱 :" + rfidListPortNoFilter[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                    // 列出 rfid list 中埠號名稱
                }   // end of debugging

                for ( int i = 0 ; i < rfidListPortNoFilter.length ; i ++)
                {

                    String devicename = rfidListPortNoFilter[i].toString() ; /*GetrfidDeviceNameListdata(i); */ // 取出裝置名稱
                    Log.d(TAG,"第"+ Integer.toString(i) + "個埠號:" + devicename.toString()) ;

                    if (portno_autocompletetext.toString().equals(devicename) == true ) {

                        // 比較埠號 - 若在其中,則將選中的埠號存起來 !

                        rfidPortNoTxt.setText("資料相符");
                        rfidPortNoTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                        rfidPortNoWarningimg.setImageResource(R.drawable.greenhookico);

                        SavePortNoForSearch(portno_autocompletetext.getText().toString());     // 儲存 埠號名稱

                        flag1 = true  ;

                        break;  // 離開迴圈
                    }           // 若有在其中
                }               // end of for loop

                if (flag == false && flag1 != true ) {  // 沒在其中 (埠號)

                    rfidPortNoTxt.setText("無資料");
                    rfidPortNoTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                    rfidPortNoWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                    Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                }
                else {

                    SavePortNoForSearch(portno_autocompletetext.getText().toString());     // 儲存埠號
                }   // end of else

            }
        });  // end of portno_autocompletetext - 埠號

        cablename_autocompletetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG,"纜線名稱 :" +  cablename_autocompletetxt.getText().toString());

                boolean flag  = false   ;
                boolean flag1 = false   ;

                if (false) {

                    // For debugging !
                    for (int j = 0; j < rfidListCableNamedataFilter.length /*GetAmountOfList("rfidlist") */   ; j++)
                        Log.d(TAG, "第 " + Integer.toString(j) + "個纜線名稱:" + rfidListPortNoFilter[j].toString()/*GetrfidDeviceNameListdata(j)*/);
                    // 列出 rfid list 中埠號名稱
                }   // end of debugging

                for ( int i = 0 ; i < rfidListCableNamedataFilter.length ; i ++)
                {

                    String devicename = rfidListCableNamedataFilter[i].toString() ; /*GetrfidDeviceNameListdata(i); */ // 取出裝置名稱
                    Log.d(TAG,"第"+ Integer.toString(i) + "個纜線名稱:" + devicename.toString()) ;

                    if (cablename_autocompletetxt.toString().equals(devicename) == true ) {

                        // 比較纜線名稱 - 若在其中,則將選中的纜線名稱存起來 !

                        rfidCableNameTxt.setText("資料相符");
                        rfidCableNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                        rfidCableNameWarningimg.setImageResource(R.drawable.greenhookico);

                        SavePortNoForSearch(cablename_autocompletetxt.getText().toString());     // 儲存 纜線名稱

                        flag1 = true  ;

                        break;  // 離開迴圈
                    }           // 若有在其中
                }               // end of for loop

                if (flag == false && flag1 != true ) {  // 沒在其中 (纜線名稱)

                    rfidCableNameTxt.setText("無資料");
                    rfidCableNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                    rfidCableNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                    cablename_autocompletetxt.setError("");   // 錯誤
                    Log.d(TAG,"XXXXXXXXXXXXXX" + "無資料!");
                }
                else {

                    SaverfidCableNameForSearch(cablename_autocompletetxt.getText().toString());     // 儲存 纜線名稱

                }   // end of else

                // SaverfidCableNameForSearch(cablename_autocompletetxt.getText().toString());     // 纜線名稱

            }
        });     // end of cablename_autocompletetxt  - 纜線名稱

    }          // end of onCreate

    // The following codes are responsible for saving search fields - idc room name , port no , fiber name and device name
    // 如下為儲存搜尋欄位的資料
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void SaveFilterCableNameLength(int filtercablenamelen) {

        // 儲存過濾後的字串長度
        assert filtercablenamelen > -1 ;

        this.cablenameFilterLen = filtercablenamelen ;

        return ;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // 儲存 機房名稱, 光纜名稱 , 埠號 , 裝置名稱
    ////////////////////////////////////////////////////////////////////////////////////
    private void SaveidcNameForSearch(String idcroomname)
    {
        // 機房名稱
        assert idcroomname != null ;
        Log.d(TAG,">>>>>>>>>>>>>>>>>  機房名稱");
        this.idcRoomName = idcroomname ;
        SetidcNameLenForSearch(this.idcRoomName.length());   // 儲存 idc room name 的長度
        Log.d(TAG,"機房名稱 :" +  this.idcRoomName.toString());

    }

    private void SaverfidCableNameForSearch(String cablename)
    {
        // 光纜名稱
        assert cablename != null ;
        Log.d(TAG,">>>>>>>>>>>>>>>>>  光纜名稱");
        this.FiberName = cablename ;
        SetcableNameLenForSearch(this.FiberName.length());   // 儲存 cable name 的長度
        Log.d(TAG,"光纜名稱 :" +  this.FiberName.toString());
    }
    private void SavePortNoForSearch (String portno)
    {
        // 埠號
        assert portno !=null ;
        Log.d(TAG,">>>>>>>>>>>>>>>>>  埠號名稱");
        this.PortNo = portno ;
        SetportnoLenForSearch(this.PortNo.length());   // 儲存 port no 的長度
        Log.d(TAG,"埠號名稱 :" +  this.PortNo.toString());

    }

    private void SaveDeviceNameForSearch(String devicename)
    {
        // 設備名稱
        assert devicename != null ;
        Log.d(TAG,">>>>>>>>>>>>>>>>>  設備名稱");
        this.DeviceName = devicename ;
        SetdeviceNameLenForSearch(this.DeviceName.length());   // 儲存 device name 的長度
        Log.d(TAG,"設備名稱 :" +  this.DeviceName.toString());

        return  ;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 取回搜尋資料 - 機房名稱 , 光纜名稱 , 埠號  , 設備名稱
    /////////////////////////////////////////////////////

    private String GetidcNameForSearch () {

        assert this.idcRoomName != null ;
        Log.d(TAG,"GetidcNameForSearch()");
        Log.d(TAG,"機房名稱: " + this.idcRoomName ) ;

        // 存放 機房名稱的長度

        if (this.idcRoomName.isEmpty()) {
            SetidcNameLenForSearch(0);   // empty string
        }
        else
            SetidcNameLenForSearch(this.idcRoomName.length());   // set idc room length

        return this.idcRoomName ;   // 取回機房名稱

    }   // end of GetidcNameForSearch


    // 機房長度操作  ///////////////////////////////////////
    private void  SetidcNameLenForSearch(int len) {
        // 儲存 機房名稱長度
        assert len > -1 ;
        this.idcroomnamelen = len ;

    }
    private int GetidcNameLenForSearch() {
        // 取出 機房名稱長度

        return this.idcroomnamelen ;
    }

    private void  SetcableNameLenForSearch(int len) {
        //  儲存 纜線名稱長度
        assert len > -1 ;
        this.cablenamelen = len ;
    }

    private int GetcableNameLenForSearch() {

        // 取出 纜線名稱長度
        return this.cablenamelen ;
    }
    private void SetdeviceNameLenForSearch(int len) {

        //  儲存 裝置名稱長度
        assert len > -1 ;
        this.devicenamelen = len ;

    }
    private int GetdeviceNameLenForSearch() {
        //  取出 裝置名稱長度

        return this.devicenamelen ;
    }

    private void SetportnoLenForSearch( int len ) {

        //  儲存 port no 長度

        assert len > -1  ;
        this.portnolen = len ;
    }

    private void SetidcRoomNameLenForSearch(int len) {

        //  儲存 機房名稱長度
        assert len > -1 ;
        this.idcroomnamelen = len ;

    }

    private int GetidcRoomNameLenForSearch() {
        //  取出 機房名稱長度

        return this.idcroomnamelen ;
    }
    private int  GetportnoLenForSearch() {

        //  取出 port no 長度

        return this.portnolen  ;
    }

    private String GetidcRoomNameForSearch () {

        // assert this.idcRoomName != null ;

        Log.d(TAG,"GetidcRoomNameForSearch()");
        Log.d(TAG,"機房名稱: " + this.idcRoomName ) ;

        if (/*this.idcRoomName.isEmpty()*/ this.idcRoomName == null )
            SetidcRoomNameLenForSearch(0);
        else
            SetidcRoomNameLenForSearch(this.idcRoomName.length()) ;

        return this.idcRoomName ;       // 取回機房名稱

    }   // end of GetidcNameForSearch

    private String GetPortNoForSearch () {

        // assert this.PortNo != null ;
        Log.d(TAG,"GetPortNoForSearch()");
        Log.d(TAG,"埠號: " + this.PortNo ) ;

        if ( /*this.PortNo.isEmpty()*/ this.PortNo == null )
            SetportnoLenForSearch(0);
        else
            SetportnoLenForSearch(this.PortNo.length()) ;

        return this.PortNo ;       // 取回埠號

    }   // end of GetidcNameForSearch

    private String GetFiberNameForSearch () {

//        assert this.FiberName != null ;
        Log.d(TAG,"GetFiberNameForSearch()");
        Log.d(TAG,"光纜名稱: " + this.FiberName ) ;

        if (/*this.FiberName.isEmpty()*/ this.FiberName == null )
            SetportnoLenForSearch(0);
        else
            SetportnoLenForSearch(this.FiberName.length()) ;

        return this.FiberName ;    // 取回光纜名稱

    }   // end of GetFiberNameForSearch

    private String GetDeviceNameForSearch () {

        // assert this.DeviceName != null ;
        Log.d(TAG,"GetDeviceNameForSearch()");
        Log.d(TAG,"設備名稱: " + this.DeviceName ) ;

        if ( /* this.DeviceName.isEmpty() && */ this.DeviceName == null )
            SetdeviceNameLenForSearch(0);
        else
            SetdeviceNameLenForSearch(this.DeviceName.length());


        return this.DeviceName ;   // 取回設備名稱

    }   // end of GetidcNameForSearch


    //////////////////////////////////////////////////////////////////////////////////
    private void SetidcRoomListdata (int amount )
    {

        //  將 idc list 中的機房名稱全部取出來

        this.idcRoomListdata = new String[amount];
        assert this.idcRoomListdata != null;

        idcData idctempitem ;

        for ( int i = 0 ; i < amount ; i ++ ) {
            idctempitem = GetIdcItemFromIdcList(i);

            if (idctempitem.GetidcName().length() == 0) {
                Log.d(TAG, "空的機房名稱");
            } else {

                this.idcRoomListdata[i] = idctempitem.GetidcName();            // copy idc name to string list (機房名稱)
                idcListRoomNameFilterArrayList.add(this.idcRoomListdata[i]);   // add element to arraylist

            }
        }  // end of for loop


        HashSet<String> set = new HashSet<String>(idcListRoomNameFilterArrayList);     // hash set (omit 重覆的字串)
        ArrayList<String> idcListRoomNameFilterArrayList  = new ArrayList<String>(set);

        this.idcListRoomNameFilter = new String[idcListRoomNameFilterArrayList.size()] ;
        assert this.idcListRoomNameFilter!= null ;  //

        for ( int xx = 0 ; xx < idcListRoomNameFilter.length ; xx++ ) {

            idcListRoomNameFilter[xx] = idcListRoomNameFilterArrayList.get(xx).toString();
            Log.d(TAG," idcListRoomNameFilter["+xx+"]"+">>>>"+ idcListRoomNameFilter[xx].toString() );
        }

        // the following codes are responsible for debugging !
        /*
        for (int pp = 0 ; pp < idcListRoomNameFilterArrayList.size() ; pp++) {

            Log.d(TAG,"Room Name Room Name Room Name Room Name   " + idcListRoomNameFilter[pp].toString()) ;

        }

         */

    }

    private void SetrfidCableNameListdata(int amount)
    {
        // 將 rfid list 中的光纜名稱 (cable name)

        this.rfidListCableNamedata = new String[amount];
        assert this.rfidListCableNamedata != null;

        rfidData rfidtempitem ;
        Log.d(TAG,"rfid list 長度 : >>>>>>>>>>>> " + Integer.toString(amount));

        for ( int i = 0 ; i < amount ; i ++ ) {
            rfidtempitem = GetRfidItemFromRfidList(i);

            if ( rfidtempitem.GetcableName().length() == 0 ) {
                this.rfidListCableNamedata[i] = "KKKKKK";
            }
            else {
                this.rfidListCableNamedata[i] = rfidtempitem.GetcableName() ;  // copy cable name to string list (光纜名稱)
                rfidListCableNamedataFilterArrayList.add(this.rfidListCableNamedata[i]);   // add element to arraylist
            }

        }   // end of for loop

        // 過濾重覆的字串

        HashSet<String> set = new HashSet<String>(rfidListCableNamedataFilterArrayList);     // hash set (omit 重覆的字串)
        ArrayList<String> rfidListCableNamedataFilterArrayList  = new ArrayList<String>(set);

        this.rfidListCableNamedataFilter = new String[rfidListCableNamedataFilterArrayList.size()] ;
        assert this.rfidListCableNamedataFilter!= null ;  //

        for ( int xx = 0 ; xx < rfidListCableNamedataFilter.length ; xx++ ) {

            rfidListCableNamedataFilter[xx] = rfidListCableNamedataFilterArrayList.get(xx).toString();
        }

        // the following codes are responsible for debugging !
        for (int pp = 0 ; pp < rfidListCableNamedataFilterArrayList.size() ; pp++) {

            Log.d(TAG,"Cable Name Cable Name Cable Name Cable Name   " + rfidListCableNamedataFilter[pp].toString()) ;

        }

    }   // end of SetrfidCableNameListdata

    private void SetrfidPortNoListdata(int amount)
    {

        // 將 rfid list 中的埠號 (port no )

        this.rfidListPortdata = new String[amount];
        assert this.rfidListPortdata != null;

        rfidData rfidtempitem ;

        Log.d(TAG,"rfid list 長度 : >>>>>>>>>>>> " + Integer.toString(amount));

        for ( int i = 0 ; i < amount ; i ++ ) {

            rfidtempitem = GetRfidItemFromRfidList(i);

            this.rfidListPortdata[i] = rfidtempitem.Getport() ;                 // copy port no to string list (埠號)
            rfidListPortNoFilterArrayList.add(this.rfidListPortdata[i]);        // add element to arraylist

            if (this.rfidListPortdata[i].toString().equals("") == true ) {
                Log.d(TAG, "埠號 為空~~~~~~~~~~~~~~~~~~~~");
            }
            else
                Log.d(TAG,"~~~~~~~~~~~~~~~~~~~~~~~~~~埠號: " + this.rfidListPortdata[i].toString());

        }   // end of for loop

        // 過濾重覆的字串
        HashSet<String> set = new HashSet<String>(rfidListPortNoFilterArrayList);     // hash set (omit 重覆的字串 - port no )
        ArrayList<String> rfidListPortNoFilterArrayList  = new ArrayList<String>(set);

        this.rfidListPortNoFilter = new String[rfidListPortNoFilterArrayList.size()] ;
        assert this.rfidListPortNoFilter!= null ;  //  make sure it is available

        for ( int xx = 0 ; xx < rfidListPortNoFilter.length ; xx++ ) {

            rfidListPortNoFilter[xx] = rfidListPortNoFilterArrayList.get(xx).toString();
        }

        SortPortNo() ;   // 排一下

        // the following codes are responsible for debugging ! - port no
        for (int pp = 0 ; pp < rfidListPortNoFilterArrayList.size() ; pp++) {

            Log.d(TAG,"Port No Port No Port No Port No Port No  " + rfidListPortNoFilter[pp].toString()) ;

        }

    }   // end of SetrfidPortNoListdata


    private void SetrfidDeviceNameListdata(int amount)
    {

        // 將 rfid list 中的裝置名稱 (device name)

        this.rfidListDeviceNamedata = new String[amount];
        assert this.rfidListDeviceNamedata != null;

        rfidData rfidtempitem ;

        Log.d(TAG,"SetrfidDeviceNameListdata SetrfidDeviceNameListdata SetrfidDeviceNameListdata " + Integer.toString(amount));

        for ( int i = 0 ; i < amount ; i ++ ) {

            rfidtempitem = GetRfidItemFromRfidList(i);

            String devicename ;
            devicename = rfidtempitem.GetdeviceName();  // Getting device name

            if (devicename.equals("")== true) {
                continue;
            }
            else {
                this.rfidListDeviceNamedata[i] = rfidtempitem.GetdeviceName();                     // copy device name to string list (裝置名稱)
                rfidListDeviceNameFilterArrayList.add(this.rfidListDeviceNamedata[i]);              // add element to arraylist
            }
            if (this.rfidListDeviceNamedata[i].toString().equals("") == true ) {
                Log.d(TAG, "埠號 為空~~~~~~~~~~~~~~~~~~~~");
            }
            else
                Log.d(TAG,"~~~~~~~~~~~~~~~~~~~~~~~~~~ 裝置名稱: " + this.rfidListDeviceNamedata[i].toString());
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 0.1 sec

                    Log.d(TAG,"CCCCCCCCCCCCCCCCCCCCC") ;
                }
            }, 100);

             */

        }   // end of for loop

        // 過濾重覆的字串
        HashSet<String> set = new HashSet<String>(rfidListDeviceNameFilterArrayList);     // hash set (omit 重覆的字串 - port no )
        ArrayList<String> rfidListDeviceNameFilterArrayList  = new ArrayList<String>(set);

        this.rfidListDeviceNameFilter = new String[rfidListDeviceNameFilterArrayList.size()] ;
        assert this.rfidListDeviceNameFilter!= null ;  //  make sure it is available

        for ( int xx = 0 ; xx < this.rfidListDeviceNameFilter.length ; xx++ ) {

            Log.d(TAG,"過濾後的裝置名稱長度:" + Integer.toString(this.rfidListDeviceNameFilter.length));

            rfidListDeviceNameFilter[xx] = rfidListDeviceNameFilterArrayList.get(xx).toString();
            Log.d(TAG,"rfidListDeviceNameFilter :" + rfidListDeviceNameFilter[xx].toString());
        }

        // SortPortNo() ;   // 排一下

        // the following codes are responsible for debugging ! - device name
        for (int pp = 0 ; pp < rfidListDeviceNameFilterArrayList.size() ; pp++) {

            Log.d(TAG,"Device Name Device Name Device Name Device Name " + rfidListDeviceNameFilter[pp].toString()) ;

            // it is okay !
        }

    }   // end of SetrfidDeviceNameListdata

    private int LengthOfFilterrfidDeviceName() {
        return this.rfidListDeviceNameFilter.length ;
    }


    private int FilterCableNameStringArray()
    {
        // 過濾光纜名稱陣列

        String[] tempCableNameOfRfidList =  GetrfidCableNameListdataItem();  // 取得 rfid list 中光纜名稱string array

        assert tempCableNameOfRfidList != null ;  // 判斷 cable name string 非空

        int filtrCableNameLen = 0 ;               // initialize by zero

        for ( int j = 0 ; j < tempCableNameOfRfidList.length ; j ++ ) {

            if (tempCableNameOfRfidList[j].equals("KKKKKK") == true )
            {
                continue;  // ignore this count ! (that means it is an empty string )
            }
            else {

                filtrCableNameLen ++ ;   // count
            }
        }   // end of for loop

        return filtrCableNameLen ;

    }  // end of FilterCableNameStringArray()

    private int GetFilterCableNameLength() {

        assert this.cablenameFilterLen != 0 ;
        return this.cablenameFilterLen ;     // 回傳 filter cable name 陣列長度
    }

    private String[] GetFilterCableNameArray(){

        // 取得過濾cable name 字串 陣列
        assert this.rfidListCableNamedataFilter != null ;
        return this.rfidListCableNamedataFilter ;

    }

    private int  FilterAndSetCableNameArray(String cablenamearray[] , int n ) {

        String[] tempCableNameArray = GetFilterCableNameArray() ;

        assert tempCableNameArray != null ;
        Log.d(TAG,"長度 >>>>>>>>>>>>>>>>>" + Integer.toString(tempCableNameArray.length));


        if (n==0 || n==1){
            return n;
        }
        String[] tempA = new String[n];
        int j = 0;
        for (int i=0; i<n-1; i++){
            if (cablenamearray[i] != cablenamearray[i+1]){
                tempA[j++] = cablenamearray[i];
            }
        }
        tempA[j++] = cablenamearray[n-1];
        for (int i=0; i<j; i++){
            cablenamearray[i] = tempA[i];
        }
        return j;

    }


    private void CreateFilterCableNameArray(int cablenameLen) {

        // 這裡需要配置一個可用的記憶體空間

        this.rfidListCableNamedataFilter = new String[cablenameLen] ;  // allocate available memory space
        assert this.rfidListCableNamedataFilter != null ;

        Log.d(TAG,">>>>　建立光纜非空字串陣列 , Filter Cable Name 長度: " + Integer.toString(this.rfidListCableNamedataFilter.length));

        return ;
    }


    private void CreateFilterPortNoArray(int portnoLen) {

        // 這裡需要配置一個可用的記憶體空間

        this.rfidListPortNoFilter = new String[portnoLen] ;  // allocate available memory space
        assert this.rfidListPortNoFilter != null ;

        Log.d(TAG,">>>>　建立光纜非空字串陣列 , Filter Port No 長度: " + Integer.toString(this.rfidListPortNoFilter.length));

        return ;
    }

    private String GetidcRoomListdata(int index ) {

        return this.idcRoomListdata[index] ;            // 回傳 idcRoomListdata 第i個字串

    } // end of GetidcRoomListdata


    private String GetrfidCableNameListdata(int index ) {

        return this.rfidListCableNamedata[index] ;     // 回傳 rfidListCableNamedata 第i個字串

    } // end of GetrfidCableNameListdata

    private String GetrfidPortNoListdata(int index) {

        assert index > -1 ;
        assert this.rfidListPortdata != null ;

        return this.rfidListPortdata[index] ;         // 回傳 rfidListPortdata 第i 個字串
    }

    private String GetrfidDeviceNameListdata(int index) {

        assert index > -1 ;
        assert this.rfidListDeviceNamedata != null ;

        return this.rfidListDeviceNamedata[index] ;    // 回傳 rfidListDeviceNamedata 第i 個字串
    }

    //////////////////////////////////////////////
    public String[] GetidcRoomListdataItem() {


        // assert this.idcRoomListdata != null ;
        return this.idcRoomListdata ;
    }

    public String[] GetrfidCableNameListdataItem() {

        // 取得 rfid list 的 cable name 的陣列 (過濾後)
        assert this.rfidListCableNamedataFilter != null ;
        // assert this.rfidListCableNamedata != null ;
        return this.rfidListCableNamedataFilter ;
        // return this.rfidListCableNamedata  ;
    }


    public String[] GetrfidPortNoListdataItem() {

        // 取得 rfid list 的 port no 的陣列 (過濾後)
        // assert this.rfidListPortdata != null ;
        assert this.rfidListPortNoFilter != null ;
        // assert this.rfidListCableNamedata != null ;
        return this.rfidListPortNoFilter ;
        // return this.rfidListCableNamedata  ;
    }

    public String[] GetrfidDeviceNameListdataItem() {

        // 取得 rfid list 的 device name 的陣列 (過濾後)
        // assert this.rfidListPortdata != null ;
        assert this.rfidListDeviceNameFilter != null ;
        // assert this.rfidListCableNamedata != null ;
        return this.rfidListDeviceNameFilter ;
        // return this.rfidListCableNamedata  ;
    }



    public String[] SortPortNo () {

        // 字串排序 升冪
        int myNum = 0;
        ArrayList<String> portNoSort ;
        ArrayList<Integer> portNoInt ;
        portNoSort = new ArrayList<>(this.rfidListPortNoFilter.length) ;
        portNoInt = new ArrayList<>();

        for (int yy = 0 ; yy < this.rfidListPortNoFilter.length ; yy ++) {

            portNoSort.add(yy,this.rfidListPortNoFilter[yy].toString());   // add all elements of rfidListPortNoFilter
        }   // end of for loop

        // portNoSort.sort(Comparator.naturalOrder());   // 排序它們 , 但是這種方法排出來的不好 !

        ////////    轉換字串到整數型態
        for (int rr = 0 ; rr < this.rfidListPortNoFilter.length ; rr ++)
        {

            try {

                myNum = Integer.parseInt(rfidListPortNoFilter[rr].toString());
                portNoInt.add(myNum);  // add element of integer to arraylist

            } catch (NumberFormatException e) {

                Log.d(TAG,"Error : " + e.getMessage().toString()) ;

            }

        }     // end of for loop - 轉換string type to integer


        portNoInt.sort(Comparator.naturalOrder()) ; // 排一下


        for ( int tt = 0 ; tt < portNoInt.size() ; tt ++)
        {
            this.rfidListPortNoFilter[tt] = Integer.toString(portNoInt.get(tt)) ; // 轉回字串再存回去
        }

        return this.rfidListPortNoFilter ;    // 傳回字串

    }

    public int GetrfidNoDupCableNameLen() {

        int len = 0 ;

        String[] tempcablename = GetrfidCableNameListdataItem();

        assert tempcablename != null ;

        for ( int p = 0 ; p < tempcablename.length ; p++ ) {
            if (tempcablename[p].equals("KKKKKK") == true ) {}
            else { len ++ ; }

        }

        Log.d(TAG,"非空字串的總和: " + Integer.toString(len));

        return len ;
    }

    private void SetidcItemString(int index , String ss ) {

        assert index > -1 ;
        assert ss != null ;

        this.idcRoomListdata[index] = ss ;

    }

    private void ShowIdcName(int amount) {

        // 顯示 idc 中機房名稱

        for ( int i = 0 ; i < amount ; i++ )
            Log.d(TAG,"idc name $$$" + " " + this.idcRoomListdata[i] + "\n" );
    }

    private void ShowRfidCableName(int amount) {
        // 顯示 rfid list中光纜名稱
        Log.d(TAG,"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% amount :" + Integer.toString(amount)) ;
        for ( int i = 0 ; i < amount ; i++ )
            Log.d(TAG,"Rfid Cable name $$$" + " " + this.rfidListCableNamedata[i] + "\n" );

    }

    private void SetAmountOfList(int amount , String table )
    {
        // assert amount > 0  ;

        if (table.toString().equals("idclist") == true) {
            this.AmountOfIDCList  = amount ;  // save amount of idc list's records
        }
        else if (table.toString().equals("oldflist") == true) {
            this.AmountOfOLDFList = amount ; // save amount of oldf list's records
        }
        else if (table.toString().equals("rfidlist") == true) {
            this.AmountOfRFIDList = amount ; // save amount of rfid list's records
        }
        else
        {
            Log.d(TAG,"無任何資料!" + "錯誤的資料表:" +table.toString());
        }

    } // end of SetAmountOfList

    private int GetAmountOfList(String table) {

        if (table.toString().equals("idclist") == true )       {
            return this.AmountOfIDCList ;
        }
        else if (table.toString().equals("odflist") == true)   {
            return this.AmountOfOLDFList ;
        }
        else if (table.toString().equals("rfidlist") == true ) {
            return this.AmountOfRFIDList ;
        }
        else
            return -1 ;  // error
    }
    private void ReadDataFromDB(SQLiteDatabase db , String table ) {

        Log.d(TAG,"###########################################" + "\n");
        Log.d(TAG,"讀取資料表:"+ table.toString()+ "中的資料記錄" + "\n");

        Cursor cursor     = db.rawQuery("select * from "+ table.toString(),null);

        int amount = cursor.getCount() ;  // 取出表的記錄數量
        // SetAmountOfList(amount, table) ;  // 將 table 中的 record 數量存起來

        Log.d(TAG,">>>>>>>>>>>>>>  筆數: " + Integer.toString(cursor.getCount())) ;
        // int index = 0 ;    //

        if (cursor.getCount()==0) {

            // DataBaseErrorDialog dataBaseErrorDialog = new DataBaseErrorDialog(this);
            // dataBaseErrorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
            // dataBaseErrorDialog.show();                // open the upload file dialog

            Log.d(TAG,"資料表 :" +table.toString() + "中無任何資料!");
            NoRecordsInTableDialog(table);  // 無資料

        }    // no any records in table -
        else {

            SetAmountOfList(amount, table) ;  // 將 table 中的 record 數量存起來

        }

        if (table.equals("rfidformat") == true ) {

            // 讀取 rfidformat表中資料

            int rfidformatindex = 0 ;    //

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");

            while (cursor.moveToNext()) {
                ///////

                String id = cursor.getString(cursor.getColumnIndexOrThrow("ids"));
                String display = cursor.getString(cursor.getColumnIndexOrThrow("display"));
                int json = cursor.getInt(cursor.getColumnIndexOrThrow("json"));
                int required = cursor.getInt(cursor.getColumnIndexOrThrow("required"));
                int editable = cursor.getInt(cursor.getColumnIndexOrThrow("editable"));
                int visible = cursor.getInt(cursor.getColumnIndexOrThrow("visible"));

                Log.d(TAG, "rfidformat - 纜線資料格式 ");
                Log.d(TAG, "id :" + id.toString() + "\n");
                Log.d(TAG, "display :" + id.toString() + "\n");
                Log.d(TAG, "json :" + Integer.toString(json) + "\n");
                Log.d(TAG, "required :" + Integer.toString(required) + "\n");
                Log.d(TAG, "editable :" + Integer.toString(editable) + "\n");
                Log.d(TAG, "visible :" + Integer.toString(visible) + "\n");

                rfidformatindex ++ ;  // increase index


            }   // end of while

            // SetidcRoomListdata(amount); // 將 cable name 資料存在 string array 中
            // ShowIdcName(amount);        // debug idc name strings

        }
        else if (table.equals("oldflist") == true ) {

            int oldflistindex = 0 ;    //  oldf list index

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

                boolean DEBUG = false ;
                if (DEBUG) {
                    Log.d(TAG, "oldflist - oldflist 資料");
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
                }

                oldflistindex ++ ;
            }   // end of while

        }

        else if (table.equals("idclist") == true ) {

            int idclistindex = 0 ;    // idc list index

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");

            IDCArrayListInitialization(amount);  // 初始化 idclist array

            while (cursor.moveToNext()) {

                // 取出 cursor 中所有idc list 中的欄位
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idcId"));
                String idcName = cursor.getString(cursor.getColumnIndexOrThrow("idcName"));

                assert id > -1 ;
                assert idcName != null ;
                idcData idcItem = new idcData(id,idcName);
                assert idcItem != null ;

                SetIdcListArray(idcItem, idclistindex );   // 設定 idc item 到 idc list array

                idcData idcitem = GetIdcItemFromIdcList(idclistindex);
                assert idcitem != null ;
                int idOfIdcList = idcItem.Getid();
                String NameOfIdcList = idcItem.GetidcName() ;  // 取出 idc item 由 idc list array

                Log.d(TAG, "idclist ---------- idclist 資料");
                Log.d(TAG, "id :" +  Integer.toString(idOfIdcList) + "\n");
                Log.d(TAG, "idcName :" + idcName.toString() + "\n");  // 中文欄位資料

                idclistindex  ++ ; // 下一個 item

            }   // end of while

            SetidcRoomListdata(amount); // 將 idc name 資料存在 string array 中
            ShowIdcName(amount);        // debug idc name strings

            // cursor.close();   // close db cursor

        }
        else if (table.equals("rfidlist") == true ) {

            int rfidlistindex =  0 ;

            Log.d(TAG, "讀取資料表: " + table.toString() + "的欄位");

            RFIDArrayListInitialization(amount) ;  // 初始化 rfid list array

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

                assert rfidsId    > -1    ;
                assert tagId      != null ;
                assert rfidsId    > -1    ;
                assert tagId      != null ;
                assert oldfId     > -1    ;
                assert cableName  != null ;
                assert port       != null ;
                assert status     != null ;
                assert deviceName !=null  ;
                assert updateUser != null ;
                assert updateTime !=null  ;
                assert jsonData   !=null  ;

                rfidData rfidItem = new rfidData(rfidsId,
                        tagId,
                        oldfId,
                        cableName ,
                        port ,
                        status ,
                        deviceName ,
                        updateUser ,
                        updateTime ,
                        jsonData );

                assert rfidItem != null ;
                SetRfidListArray(rfidItem, rfidlistindex );                  // 設定 rfid item 到 rfid list array

                rfidData rfidData = GetRfidItemFromRfidList(rfidlistindex);  // 取出  rfid item 由 rfid list array

                int idOfRfidList = rfidData.GetrfidsId();                    // 取出 rfids id  由 rfid list array
                String tagIdOfRfidList = rfidData.GettagId() ;               // 取出 tag id    由 rfid list array
                int oldfIdOfRfidList = rfidData.GetoldfId()  ;               // 取出 oldf id   由 rfid list array
                String cableNameOfRfidList = rfidData.GetcableName() ;       // 取出 cable name 由 rfid list array
                String portOfRfidList = rfidData.Getport() ;                 // 取出 port 由 rfid list array
                String statusOfRfidList = rfidData.Getstatus() ;             // 取出 status 由 rfid list array
                String deviceNameOfRfidList = rfidData.GetdeviceName() ;     // 取出 device name 由 rfid list array
                String updateUserOfRfidList = rfidData.GetupdateUser() ;     // 取出 update user 由 rfid list array
                String updateTimeOfRfidList = rfidData.GetupdateTime() ;     // 取出 update time 由 rfid list array
                String jsonOfRfidList = rfidData.GetjsonData()  ;            // 取出 json data 由 rfid list array

                assert tagIdOfRfidList!= null && cableNameOfRfidList != null && portOfRfidList != null && statusOfRfidList != null
                        && deviceNameOfRfidList != null && updateUserOfRfidList != null && updateTimeOfRfidList !=null &&
                        jsonOfRfidList != null ;    // make sure above fields are avaibale

                Log.d(TAG, "rfidlist - rfidlist 資料");
                Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"+"@@@@@@@@@@@@@@@@@@@@@@@@@@2@@@@" );

                Log.d(TAG, "rfidsId :" + Integer.toString(idOfRfidList) + "\n");
                Log.d(TAG, "tagId :"   + tagIdOfRfidList.toString() + "\n");
                Log.d(TAG, "oldfId :"  + Integer.toString(oldfIdOfRfidList)+"\n");
                Log.d(TAG, "cableName :"  + cableNameOfRfidList.toString()+"\n");
                Log.d(TAG, "port :"  + portOfRfidList.toString()+"\n");
                Log.d(TAG, "status :" + statusOfRfidList.toString() + "\n");
                Log.d(TAG, "deviceName :"   + deviceNameOfRfidList.toString() + "\n");
                Log.d(TAG, "updateUser :"  + updateUserOfRfidList.toString()+"\n");
                Log.d(TAG, "updateTime :"  + updateTimeOfRfidList.toString()+"\n");
                Log.d(TAG, "jsonData :"  + jsonOfRfidList.toString()+"\n");

                rfidlistindex ++  ;     // 移到下一個 item

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過兩秒後要做的事情 , 為何要做此事 ? 為緩衝讀取資料完成的動作
                        Log.d("tag","wait for a while ...... ");

                    }}, 200);

            }   // end of while


            // The following  codes are responsible for delaying save data

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){

                @Override
                public void run() {

                    //過兩秒後要做的事情
                    Log.d("tag","wait for a while ...... ");

                }}, 1000);

            // 存放  rfid list 中 cable name , port no , device name 三者
            //////////////////////////////////////////////////////////////////

            SetrfidCableNameListdata(amount);            // 將  rfid cable name 資料存在 string array 中
            SetrfidPortNoListdata(amount);               // 將  rfid port no 資料存在 string array 中
            SetrfidDeviceNameListdata(amount);           // 將  rfid device name  資料存在 string array 中

            // Log.d(TAG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + Integer.toString(len)) ;
            // this is a check for filter cable name string array
            // SaveFilterCableNameLength(len);                // save filter cable name length
            // CreateFilterCableNameArray(len);               // construct the filter cable name string array
            // int filterLength = FilterAndSetCableNameArray(GetrfidCableNameListdataItem(),amount);
            // Log.d(TAG,"真正長度 :" + Integer.toString(filterLength) ) ;
            // GetrfidCableNameListdataItem();
            //  ShowRfidCableName(amount);                     // debug rfid cable name strings

        }
        else if (table.equals("android_metadata") == true) {

            Log.d(TAG, "資料表 :" + table.toString()) ;  // sqlite 預設資料表

        }
        else {
            Log.d(TAG,"資料表不存在 !");
        }

        // cursor.close();   // close cursor

    }

    private void NoRecordsInTableDialog(String table )
    {   // 無資料在表格中

        ImageView cancel;  // 右上角取消按鈕

        TextView tablemsg ;

        Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(FindCableDataOFFLine.this).
                inflate(R.layout.norecordsdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(FindCableDataOFFLine.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel   = (ImageView) alertCustomdialog.findViewById(R.id.exitcancel_button);  // 右上角取消按鈕
        btn_no   = (Button) alertCustomdialog.findViewById(R.id.exitbtn_no) ;           // 不離開按鈕
        tablemsg = (TextView) alertCustomdialog.findViewById(R.id.errmsgtxt) ;          // 錯誤訊息
        tablemsg.setText(table);                                                        // 顯示表格名稱
        // btn_yes  = (Button) alertCustomdialog.findViewById(R.id.exitbtn_yes) ;       // 離開按鈕

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

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();  // close current dialog

            }
        });    // 下載但不寫入資料庫  !


        /*
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if you exit the app , you must close database !
                CloseDBHelper(GetDBHelper1());   // close database !
                finish(); // close current activity
                FindCableDataOFFLine.this.moveTaskToBack(true);  // 離開 the app

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

    }
    private void ScanCableNameAndFilter(int len ) {

        this.rfidListCableNamedataFilter = new String[len] ;
        assert this.rfidListCableNamedataFilter !=null ;

        for (int x = 0 ; x < GetAmountOfList("rfdilist") ; x++ ) {
            Log.d(TAG,"VVVVVVVVVVVVVVVVVVVV" + Integer.toString(GetAmountOfList("rfdilist")));

        }

    }
    private static String encode(String s)
    {
        try
        {
            s = URLEncoder.encode(s, "utf-8");
            return s;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }


    public static String stringToUnicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++)
        {
            // 取出每一个字符
            char c = string.charAt(i);

            // 轉換為 unicode
            //"\\u只是代号，请根据具体所需添加相应的符号"

            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();  // unicode string
    }

    private void IDCArrayListInitialization(int amount)
    {
        // 初始化 idclist 的array list 空間
        Log.d(TAG,"idc list 初始化" + "長度:" + Integer.toString(amount));
        // assert amount > 0 ;
        this.idcList = new ArrayList<idcData>(amount);
        assert this.idcList != null ;

    }

    private void RFIDArrayListInitialization(int amount)
    {
        // 初始化 rfidlist 的 array list 空間

        Log.d(TAG,"rfid list 初始化" + "長度:" + Integer.toString(amount));

//        assert amount > 0 ;
        this.rfidList = new ArrayList<rfidData>(amount);
        assert this.rfidList != null ;

    }

    private void SetLenOfIDCList(int idcListLen ) {

        // 設置 idc list 的長度
        assert idcListLen > 0 ;
        this.idcListLen = idcListLen ;

        return ;
    }

    private void SetLenOfRFIDList(int rfidListLen ) {

        // 設置 rfid list 的長度
        assert rfidListLen > 0 ;
        this.rfidListLen = rfidListLen ;

        return ;
    }

    // 取出 idc list item

    private idcData GetIdcItemFromIdcList(int index ) {
        // 取出 idc list 中的項目
        assert this.idcList.get(index) != null ;
        return this.idcList.get(index) ;

    }

    // 取出 rfid list item

    private rfidData GetRfidItemFromRfidList (int index) {
        assert this.rfidList.get(index) != null ;
        return this.rfidList.get(index);

    }


    private void SetIdcListArray(idcData idcitem, int index ) {
        // 加入 idc list item

        assert index > -1 ;
        assert idcitem != null ;

        this.idcList.add(index , idcitem);   // add idc item to idc array list

        return ;
    }

    private void SetRfidListArray(rfidData rfiditem, int index )
    {

        // 加入 rfid list item

        assert index > -1 ;
        assert rfiditem != null ;

        this.rfidList.add(index , rfiditem);   // add rfid item to rfid array list

        return ;
    }

    public void SetDBHelper() {

        DBOpenHelper1 dbOpenHelper1 = new DBOpenHelper1(this);
        assert dbOpenHelper1 != null ;

        if (dbOpenHelper1!=null)
            Log.d(TAG,"Database helper");

        this.dbOpenHelper1 = dbOpenHelper1;   // setting private member dbOpenHelper1

    }
    public DBOpenHelper1 GetDBHelper() {

        assert this.dbOpenHelper1 != null ;
        return this.dbOpenHelper1 ;

    }


    // 初始化各搜尋欄位的長度
    public void InitializationOfLength() {

        Log.d(TAG,"InitializationOfLength()");
        this.idcroomnamelen = 0 ;
        this.cablenamelen = 0 ;
        this.portnolen = 0 ;
        this.devicenamelen = 0 ;
    }

    private void AddListener () {

        Log.d(TAG,"AddListener ()");

        homeMainimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); // close current activity
                db.close();

                Intent intent = new Intent(v.getContext(),OffLineMainSquare.class); // 離線 主功能畫面
                v.getContext().startActivity(intent);
                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)


            }
        });    // 回到主畫面 - home menu

        BackPreviousimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"回到主畫面");

                finish();
                db.close();
                Intent intent = new Intent(v.getContext(),FindCableWithOLDFOFFLine.class) ;  // 回到纜線尋找( oldf - offline ) 活動
                v.getContext().startActivity(intent);

            }
        });   // 回到前一畫面 - home menu

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
        // 搜尋按鈕 - search button ////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////
        SearchBtn.setOnClickListener(new View.OnClickListener() {

            FindCableDataFieldsCheckDialog findcablefieldscheckdialog ;
            @Override
            public void onClick(View v) {

                // Toast.makeText(FindCableData.this, "搜尋!", Toast.LENGTH_SHORT).show();
                /////////////  下面是將搜尋的欄位資料傳送到下一頁 ///////////////////////////
                //  idc name , cable name , port no , device name

                Log.d(TAG,"長度:長度:長度:長度:長度:長度");
                Log.d(TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

                int lenOfidcRoom = GetidcRoomNameLenForSearch();
                Log.d(TAG,"idc Room Name 長度 : " + Integer.toString(lenOfidcRoom));
                int lenOfcableNanme = GetcableNameLenForSearch() ;
                Log.d(TAG,"Cable Name 長度 : " + Integer.toString(lenOfcableNanme));
                int lenOfdeviceName = GetdeviceNameLenForSearch() ;
                Log.d(TAG,"Device Name 長度 : " + Integer.toString(lenOfdeviceName));
                int lenOfportNo = GetportnoLenForSearch() ;
                Log.d(TAG, "Port No 長度 :" + Integer.toString(lenOfportNo)) ;

                int[] Fields = new int[4] ;
                assert Fields!= null ;

                if (lenOfidcRoom != 0 )  // 機房名稱
                    Fields[0] = 1 ;
                else
                    Fields[0] = 0 ;
                if (lenOfcableNanme !=0 )  // 光纜名稱
                    Fields[1] = 1 ;
                else
                    Fields[1] = 0 ;
                if (lenOfportNo != 0 )     // 埠號
                    Fields[2] = 1 ;
                else
                    Fields[2] = 0 ;
                if (lenOfdeviceName !=0 )  // 裝置名稱
                    Fields[3] = 1 ;
                else
                    Fields[3] = 0 ;

                Log.d(TAG,"判斷 ::: "  + Integer.toString(Fields[0])  +
                        Integer.toString(Fields[1])   +
                        Integer.toString(Fields[2])  +
                        Integer.toString(Fields[3]) ) ;

                // 機房      纜線         port       裝置
                FindCablefields fieldsObj = new FindCablefields(Fields[0], Fields[1] , Fields[2], Fields[3] ) ;

                if (true) {

                    Log.d(TAG, "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");

                    String fields = Integer.toString(Fields[0]) +
                            Integer.toString(Fields[1]) +
                            Integer.toString(Fields[2]) +
                            Integer.toString(Fields[3]);

                    Log.d(TAG, "fields is " + fields);

                    if (!fields.equals("1111"))
                        SearchFieldsErrorDialog();     // 輸入欄位有誤

                    else {

                        DataSearchingPromptDialog(v.getContext());  // 搜尋中對話框 ....

                    /*
                    findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this, fields , fieldsObj);
                    findcablefieldscheckdialog.show();
                    findcablefieldscheckdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    */

                        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        //  Log.d(TAG, "idc room name: " + idcRoomName);
                        //  Log.d(TAG, "Cable name: " + CableName);
                        //  Log.d(TAG, "Port No: " + PortNo);
                        //  Log.d(TAG, "Device Name: " + DeviceName);

                        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                        Log.d(TAG, "裝置名稱 :" + devicename_autocompletetext.getText().toString());

                        boolean flag = false;
                        boolean flag1 = false;

                        if (false) {

                            // For debugging !
                            for (int j = 0; j < rfidListDeviceNameFilter.length /*GetAmountOfList("rfidlist") */   ; j++)

                                Log.d(TAG, "第 " + Integer.toString(j) + "個裝置名稱:" + /* rfidListDeviceNameFilter[j].toString() */
                                        GetrfidDeviceNameListdata(j));

                            // 列出 rfid list 中裝置名稱
                        }   // end of debugging

                        for (int i = 0; i < rfidListDeviceNameFilter.length; i++) {

                            String rfiddeviceName = GetrfidDeviceNameListdata(i); // 取出裝置名稱
                            Log.d(TAG, "XXXXXXXXXX　第" + Integer.toString(i) + "個裝置名稱:" + rfiddeviceName.toString());
                            Log.d(TAG, "devicename_autocompletetext : " + devicename_autocompletetext.getText().toString());


                            if (devicename_autocompletetext.getText().toString().equals(rfiddeviceName) == true) {

                                // 比較裝置名稱 - 若在其中,則將選中的裝置名稱存起

                                rfidDeviceNameTxt.setText("資料相符");
                                rfidDeviceNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                                rfidDeviceNameWarningimg.setImageResource(R.drawable.greenhookico);

                                SaveidcNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                                flag1 = true;

                                break;  // 離開迴圈
                            }           // 若有在其中
                        }   // end of for loop

                        if (flag == false && flag1 != true) {  // 沒在其中 (裝置名稱)

                            rfidDeviceNameTxt.setText("無資料");
                            rfidDeviceNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                            rfidDeviceNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                            devicename_autocompletetext.setError("");
                            Log.d(TAG, "XXXXXXXXXXXXXX>>>>>" + "無資料!");

                        } else {

                            SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                        }   // end of else

                        // SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());         // 機房名稱


                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                // Do something after 2s = 2000ms

                                boolean flag = false;
                                boolean flag1 = false;

                                if (false) {

                                    // For debugging !
                                    for (int j = 0; j < rfidListDeviceNameFilter.length /*GetAmountOfList("rfidlist") */   ; j++)

                                        Log.d(TAG, "第 " + Integer.toString(j) + "個裝置名稱:" + /* rfidListDeviceNameFilter[j].toString() */
                                                GetrfidDeviceNameListdata(j));

                                    // 列出 rfid list 中裝置名稱
                                }   // end of debugging

                                for (int i = 0; i < rfidListDeviceNameFilter.length; i++) {

                                    String rfiddeviceName = GetrfidDeviceNameListdata(i); // 取出裝置名稱
                                    Log.d(TAG, "XXXXXXXXXX　第" + Integer.toString(i) + "個裝置名稱:" + rfiddeviceName.toString());

                                    if (devicename_autocompletetext.toString().equals(rfiddeviceName) == true) {

                                        // 比較裝置名稱 - 若在其中,則將選中的裝置名稱存起來 !

                                        rfidDeviceNameTxt.setText("資料相符");
                                        rfidDeviceNameTxt.setTextColor(Color.parseColor("#2E964B"));  // green color
                                        rfidDeviceNameWarningimg.setImageResource(R.drawable.greenhookico);

                                        SaveidcNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                                        flag1 = true;

                                        Log.d(TAG, "flag1 >>>>>>>>" + Boolean.toString(flag1));

                                        break;  // 離開迴圈
                                    }           // 若有在其中
                                }               // end of for loop

                                if (flag == false && flag1 != true) {  // 沒在其中 (裝置名稱)

                                    rfidDeviceNameTxt.setText("無資料");
                                    rfidDeviceNameTxt.setTextColor(Color.parseColor("#FF0101"));    // red color
                                    rfidDeviceNameWarningimg.setImageResource(R.drawable.warraningico);       // 無資料
                                    devicename_autocompletetext.setError("");
                                    Log.d(TAG, "XXXXXXXXXXXXXX" + "無資料!");
                                } else {

                                    SaveDeviceNameForSearch(devicename_autocompletetext.getText().toString());     // 儲存裝置名稱

                                }
                                // end of else
                                // 這裡是顯示搜尋到纜線資料的頁面

                                Log.d(TAG, "\"\"\"\"\"\"\"");
                                GetDeviceNameForSearch();
                                GetFiberNameForSearch();
                                GetPortNoForSearch();
                                GetidcRoomNameForSearch();

                                // findcablefieldscheckdialog.dismiss();  // close the dialog and jump to next activity

                                Intent intent = new Intent();
                                intent.setClass(FindCableDataOFFLine.this, /*SearchCableDatarfid.class*/
                                        NewFindCableData.class
                                );

                                Bundle bundle = new Bundle();

                                // bundle data for search - 傳送前,必須檢查如下的欄位是否為空 :
                                /*  機房名稱 , 裝置名稱 , 纜線名稱 , 埠號   */

                                String devicename = GetDeviceNameForSearch();
                                String fibername  = GetFiberNameForSearch();
                                String portno     = GetPortNoForSearch();
                                String roomname   = GetidcRoomNameForSearch();

                                Log.d(TAG,"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                                Log.d(TAG,"機房名稱" + roomname) ;
                                Log.d(TAG,"光纜名稱" + fibername) ;
                                Log.d(TAG,"埠號" + portno) ;
                                Log.d(TAG,"裝置名稱" + devicename) ;

                                if (devicename != null && fibername != null && portno != null && roomname != null) {

                                    bundle.putString("devicename", GetDeviceNameForSearch().toString());
                                    bundle.putString("cablename", GetFiberNameForSearch().toString());
                                    bundle.putString("portno", GetPortNoForSearch().toString());
                                    bundle.putString("idcroom", GetidcRoomNameForSearch().toString());

                                    // dataSearchingPromptDialog.dismiss();
                                    intent.putExtras(bundle);
                                    v.getContext().startActivity(intent);

                                }

                            }
                        }, 1000);
                    }


                    // PopUp a Dialog that shows error message
                    //////////////////////////////////////////////
               /*
                int[] Fields = new int[4] ;

                if (lenOfidcRoom != 0 )
                    Fields[0] = 1 ;
                else
                    Fields[0] = 0 ;
                if (lenOfcableNanme !=0 )
                    Fields[1] = 1 ;
                else
                    Fields[1] = 0 ;
                if (lenOfdeviceName != 0 )
                    Fields[2] = 1 ;
                else
                    Fields[2] = 0 ;
                if (lenOfportNo !=0 )
                    Fields[3] = 1 ;
                else
                    Fields[3] = 0 ;
                                                               // 機房      纜線         port       裝置
                FindCablefields fieldsObj = new FindCablefields(Fields[0], Fields[1] , Fields[2], Fields[3] ) ;
                FindCableDataFieldsCheckDialog findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this, fieldsObj);
               */

               /*

                if (    lenOfidcRoom    == 0  &&
                        lenOfcableNanme != 0  &&
                        lenOfdeviceName != 0  &&
                        lenOfportNo     != 0 )
                {

                    // 機房名稱未輸入
                    FindCablefields fieldsObj = new FindCablefields(lenOfidcRoom, lenOfcableNanme , lenOfdeviceName, lenOfportNo) ;
                    FindCableDataFieldsCheckDialog findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this, "機房名稱未輸入!");
                    findcablefieldscheckdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    findcablefieldscheckdialog.show();
                    findcablefieldscheckdialog.setCancelable(true);  // close the dialog when user click outside of it !
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                }

                if (    lenOfidcRoom    != 0  &&
                        lenOfcableNanme == 0  &&
                        lenOfdeviceName != 0  &&
                        lenOfportNo     != 0 )
                 {

                    // 纜線名稱未輸入
                    FindCableDataFieldsCheckDialog findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this , "纜線名稱未輸入!");
                    findcablefieldscheckdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    findcablefieldscheckdialog.show();
                    findcablefieldscheckdialog.setCancelable(true);  // close the dialog when user click outside of it !
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                }

                if (    lenOfidcRoom    != 0  &&
                        lenOfcableNanme != 0  &&
                        lenOfdeviceName == 0  &&
                        lenOfportNo     != 0 )
                 {

                    // 裝置名稱未輸入

                    FindCableDataFieldsCheckDialog findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this , " 裝置名稱未輸入!");
                    findcablefieldscheckdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    findcablefieldscheckdialog.show();
                    findcablefieldscheckdialog.setCancelable(true);  // close the dialog when user click outside of it !
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                }
                if (    lenOfidcRoom    != 0  &&
                        lenOfcableNanme != 0  &&
                        lenOfdeviceName != 0  &&
                        lenOfportNo     == 0 )
                {
                    // Port no未輸入

                    FindCableDataFieldsCheckDialog findcablefieldscheckdialog = new FindCableDataFieldsCheckDialog(FindCableData.this , " 埠號未輸入!");
                    findcablefieldscheckdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 避免邊框空白 消除
                    findcablefieldscheckdialog.show();
                    findcablefieldscheckdialog.setCancelable(true);  // close the dialog when user click outside of it !
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 豎屏

                }

                Intent intent = new Intent(v.getContext() , SearchCableDatarfid.class);
                startActivity(intent);

                */

                }
            }
        });   // 搜尋按鈕


    }   // end of  listeners  group

    public void DataSearchingPromptDialog(Context context ){

        dataSearchingPromptDialog = new ProgressDialog(FindCableDataOFFLine.this, R.style.dialog);
        dataSearchingPromptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dataSearchingPromptDialog.setCanceledOnTouchOutside(false);
        dataSearchingPromptDialog.setMessage("搜尋中...");
        dataSearchingPromptDialog.setCancelable(false);

        dataSearchingPromptDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dataSearchingPromptDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dataSearchingPromptDialog.getWindow().getAttributes());
        dataSearchingPromptDialog.getWindow().setAttributes(lp);

    }

    private void SearchFieldsErrorDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(FindCableDataOFFLine.this).
                inflate(R.layout.errorfiledspromptdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(FindCableDataOFFLine.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel   = (ImageView) alertCustomdialog.findViewById(R.id.exitcancel_button);  // 右上角取消按鈕
        // btn_no   = (Button) alertCustomdialog.findViewById(R.id.exitbtn_no) ;           // 不離開按鈕
        btn_yes  = (Button) alertCustomdialog.findViewById(R.id.exitbtn_yes) ;          // 離開按鈕

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
         */

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if you exit the app , you must close database !
                // CloseDBHelper(GetDBHelper1());   // close database !
                // finish(); // close current activity
                // FindCableData.this.moveTaskToBack(true);  // 離開 the app

                dialog.dismiss();  // close the dialog

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
                Toast.makeText(FindCableDataOFFLine.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(FindCableDataOFFLine.this, "向下滑", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"下滑回到前一頁");
                finish();
                Intent intent = new Intent(this,MainSqaurefun.class);
                startActivity(intent);
            } else if(x1 - x2 > 50) {
                Toast.makeText(FindCableDataOFFLine.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Toast.makeText(FindCableDataOFFLine.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);   // 進場動畫(從下到上)
        db.close();   // close database

    }

    @Override
    public void onBackPressed(){
        //onBackPressed() 捕获后退键按钮back的信息
        Log.d(TAG,"onBackPressed") ;

        /*
        if(mBackPressed+TIME_EXIT < System.currentTimeMillis()){ //currentTimeMillis,返回毫秒级别的系统时间
         */
        super.onBackPressed();

        Log.d(TAG,"你在纜線資料中按了Back 鍵");
        finish();

        Intent intent = new Intent(FindCableDataOFFLine.this , FindCableWithOLDFOFFLine.class) ;
        startActivity(intent);

        // 這裡要詢問
        // AppExitQueryDialog(); // 詢問一下

        // CloseDBHelper(GetDBHelper1());  // close database !

        // 這裡要詢問是否要關閉 app
        if (false) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }

        return;
            /*
        }else{
            Toast.makeText(this,"再案一次退出app", Toast.LENGTH_SHORT
            ).show();
            mBackPressed=System.currentTimeMillis();
        }

             */
    }


}  // end of class  FindCableData