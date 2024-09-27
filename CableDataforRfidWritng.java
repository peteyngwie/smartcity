package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.RemoteException;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.seuic.uhf.EPC;          // EPC
import com.seuic.uhf.UHFService;   // EPC service

import org.apache.poi.ss.formula.functions.BaseNumberUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.seuic.scankey.IKeyEventCallback;  // 用來檢測鍵盤 callback 事件
import com.seuic.scankey.ScanKeyService;     // 掃描鍵盤服務

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import jxl.common.BaseUnit;


public class CableDataforRfidWritng extends AppCompatActivity {

    private ImageView PreviousPage;
    public Activity mCableDataModificationActivity;

    public List<EPC> mAllEpcs = new ArrayList<EPC>();

    public String[] country;

    private int len;

    private String searchtagid;

    // 下面都是欄位
    private EditText tagidedit;
    private TextView tagidtxt;
    private TextView statustxt;
    private TextView cablenametxt;
    private TextView fromtxt;
    private TextView totxt;
    private TextView floortxt;
    private TextView roomtxt;
    private TextView rowtxt;
    private TextView cabinettxt;
    private TextView boxtxt;
    private TextView porttxt;
    private TextView commenttxt;
    private TextView cablefirmtxt;
    private TextView equtxt;
    private TextView buildfirmtxt;
    private TextView transrecivertxt;
    private TextView transporttxt;
    private TextView updatertxt;
    private TextView updatetimetxt;

    private DBOpenHelper1 mDBHelper1;   // dbopenhelper1

    private MediaPlayer mPlayer = new MediaPlayer();

    private Button tagidwritingBtn;

    private String tagidforwriting ="";  // 初始化 tagid 為空的內容

    private UHFService mDevice;   // UHF device

    public static EPC epc;

    public static InventoryThread inventoryThread;

    private ScanKeyService mScanKeyService = ScanKeyService.getInstance();  // 鍵盤掃描服務

    // 這裡是個callback function , 其有三個 keys 必須作用 , 分別為右側的 scan  , 左側的 UHF 及 板機
    // 2024 0410 - peter
    // 而必須處理資料顯示的部分
    // key的call back functions
    private IKeyEventCallback mCallback = new IKeyEventCallback.Stub() {
        @Override
        public void onKeyDown(int keyCode) throws RemoteException {
            // 鍵按下時 , 開始尋卡

            Log.d(TAG, "onKeyDown: keyCode=" + keyCode);

            if (keyCode == 248)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(mCableDataModificationActivity, "右側 scan", Toast.LENGTH_SHORT).show();

                    }
                });

            else if (keyCode == 249)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(mCableDataModificationActivity, "左側 UHF", Toast.LENGTH_SHORT).show();

                    }
                });

            else {

                // 板機

                /*
                mAllEpcs.clear();
                Log.d(TAG,"tag id list 清空!");
                Log.d(TAG,mAllEpcs.get(0).getId());
                Log.d(TAG,mAllEpcs.get(1).getId());
                Log.d(TAG,mAllEpcs.get(2).getId());
                Log.d(TAG,mAllEpcs.get(3).getId());
                Log.d(TAG,mAllEpcs.get(4).getId());
                Log.d(TAG,"mAllEpcs 長度:" + mAllEpcs.size());
                 */

                // 這裡啟用一個 thtread  來取得標籤 id
                // basically , we don't need to use thread !
                // inventoryThread = new InventoryThread() ;

                /*
                mAllEpcs = mDevice.getTagIDs();
                Log.d(TAG,"長度 ::: " + mAllEpcs.size()) ;
                Log.d(TAG,"內容 ::: " + mAllEpcs.get(0).getId());
                SearchTagid(mAllEpcs.get(0).getId()) ;   // save the tag id
                 */

                // int len = mAllEpcs.size() ;
                // settotaloftags(len);
                // inventoryThread.start();

                findtagid();  // 單次尋卡 - tag id

                // 更新 activity 的元件 , 顯示無搜尋到 tag 的畫面
                // NoTagDialog();

                if (tagidedit.getText().equals("")) {
                    Log.d(TAG,"標籤 id 為空");
                }
                else {

                    Log.d(TAG,"標籤 id 有效");
                }
            }

        }

        @Override
        public void onKeyUp(int keyCode) throws RemoteException {
            // 鍵鬆開後 , 尋卡結束
            // inventoryThread.interrupt(); // stop thread

            Log.d(TAG, "onKeyUp: keyCode=" + keyCode);

            if (keyCode == 250) {

                Log.d(TAG, "板機放開了!!!");

                if (epc.getId().isEmpty()) {

                    Log.d(TAG, "找標籤失敗");
                    // 這裡要一個對話框顯示一下訊息 !

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            NoTagPromptDialog(false);  // UI update 無標籤

                        }
                    });

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(mCableDataModificationActivity, "成功!", Toast.LENGTH_SHORT).show();
                            tagidedit.setText(" "+ epc.getId());
                            settagid(epc.getId());   // 將找出來的 tag id 存起來
                            tagidtxt.setTypeface(Typeface.DEFAULT_BOLD);  // bold type

                            mPlayer.start();   // play tagid ringtone

                            // tagidtxt.setText(tagidtxt.getText()  + epc.getId() );
                            NoTagPromptDialog(true);  // UI update 讀取標籤成功

                        }

                    });
                }
            } else {

                Log.d(TAG, "其他鍵");

            }
        }

    };   // 這是一個 callback 初始化 , 用來註冊 key down , key up


         // 資料必須顯示的欄位如下:
    private String tagid,
            status  ,
            cablename ,
            from ,
            to ,
            floor ,
            idcroom ,
            row ,
            cabinet ,
            box ,
            port ,
            comment ,
            cablefirm ,
            devicename ,
            buildingfirm,
            transmissionend ,
            transmissionport ,
            updateuser,
            updatetime ,
            idcname ;


    private String
            idcnamesearchback ,
            cablenamesearchback ,
            portsearchback ,
            devicenamesearchback ;

    private int backflag  ;

    public static  SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_data_rfidwriting);

        // 這個 layout 是給 rfid tag writing 用的

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        mPlayer = MediaPlayer.create(CableDataforRfidWritng.this, R.raw.tagidtone);        // 標籤 id 鈴聲

        mCableDataModificationActivity = this ;

        // 以下是顯示的欄位

        tagidedit = findViewById(R.id.tagidedit) ;   // 這裡用來存放 tag id

        tagidtxt = (TextView)findViewById(R.id.tagidtxt);
        statustxt =  (TextView) findViewById(R.id.statustxt) ;
        cablenametxt = (TextView) findViewById(R.id.cablenametxt);
        fromtxt = (TextView)findViewById(R.id.fromtxt) ;
        totxt = (TextView) findViewById(R.id.totxt) ;
        floortxt = (TextView) findViewById(R.id.floortxt);
        roomtxt = (TextView) findViewById(R.id.roomtxt);
        rowtxt = (TextView) findViewById(R.id.rowtxt);
        cabinettxt = (TextView) findViewById(R.id.cabinettxt);
        boxtxt = (TextView) findViewById(R.id.boxtxt) ;
        porttxt = (TextView) findViewById(R.id.porttxt);
        commenttxt = (TextView) findViewById(R.id.commenttxt);
        cablefirmtxt = (TextView) findViewById(R.id.cablefirmtxt);
        equtxt = (TextView) findViewById(R.id.equnametxt);
        buildfirmtxt = (TextView) findViewById(R.id.buildfirmtxt);
        transrecivertxt = (TextView) findViewById(R.id.transrecivertxt);
        transporttxt = (TextView) findViewById(R.id.transporttxt);
        updatertxt = (TextView) findViewById(R.id.updatertxt);
        updatetimetxt = (TextView) findViewById(R.id.updatetimetxt);

        Log.d(TAG,"標籤寫入開啟");

        PreviousPage = (ImageView)findViewById(R.id.previousimg);  // 回前一頁
        tagidwritingBtn = (Button)findViewById(R.id.writingtagidbtn) ;   // 標籤寫入

        Bundle bundle = this.getIntent().getExtras();

        // 將取得的 Bundle資料設定

        if (bundle != null) {

            ///////////////////////////// 取得前一頁傳送過來的資料, 並儲存那些資料 ! ///////////////////////////

            // Setidcnamesearchback(bundle.getString("idcnamesearchback"));
            // Setcablenamesearchback(bundle.getString("cablenamesearchback"));
            // Setportsearchback(bundle.getString("portsearchback"));
            // Setdevicenamesearchback(bundle.getString("devicenamesearchback"));
            // Setbackflag(bundle.getInt("back"));
            // Log.d(TAG,"idcnamesearchback " + Getidcnamesearchback());
            // Log.d(TAG,"cablenamesearchback " + Getcablenamesearchback());
            // Log.d(TAG,"portsearchback " + Getportsearchback());
            // Log.d(TAG,"devicenamesearchback " + Getdevicenamesearchback());
            // Log.d(TAG,"back " + Getbackflag());

            boolean flag = false ;    // 打開 uhf device
            // mDevice = UHFService.getInstance();

            flag = OpenUHFDevice();   // 開啟  UHF　service

            do
            {
                if (flag == true) {
                    Log.d(TAG, "UHF 已開啟");
                    break ;   // exit do ... while loop
                }
                else {
                    Log.d(TAG,"UHF 未開啟");
                    flag = mDevice.open() ;    // open UHF device !
                }

            } while( flag != false ); // 開啟 UHF 直到成功為止

            ///  鍵的註冊 -- 板機 , scan , UHF
            mScanKeyService.registerCallback(mCallback,"248,249,250");  // 註冊 callback function
            // scan , uhf , trigger keycode callback

            this.tagid = bundle.getString("tagid");
            this.status  = bundle.getString("status") ;
            // Log.d(TAG,"GGGGGGGGGGGGGGGGGGG" + this.status) ;
            this.cablename = bundle.getString("cablename");
            this.from = bundle.getString("from");
            this.to = bundle.getString("to");
            this.floor = bundle.getString("floor");
            this.idcroom = bundle.getString("idcroom");
            Log.d(TAG,"idc room name >>>>>" + this.idcroom);
            this.row = bundle.getString("row");
            this.cabinet = bundle.getString("cabinet");
            this.box = bundle.getString("box");
            this.port = bundle.getString("port");
            this.comment  = bundle.getString("comment");
            this.cablefirm  = bundle.getString("cablefirm") ;
            this.devicename = bundle.getString("equname");
            this.buildingfirm = bundle.getString("buildingfirm");
            this.transmissionend = bundle.getString("transreciever");
            this.transmissionport = bundle.getString("transport");
            this.updateuser = bundle.getString("updater");
            this.updatetime = bundle.getString("updatetime");

            // 將傳來的資料先存起來

            Setidcnamesearchback(this.idcroom);
            Setcablenamesearchback(this.cablename);
            Setportsearchback(this.port);
            Setdevicenamesearchback(this.devicename);

            Log.d(TAG,"NNNNNNNNNN");
            Log.d(TAG,"tagid :" + bundle.getString("tagid"));
            Log.d(TAG,"status :" + bundle.getString("status"));
            Log.d(TAG,"cablename :" + bundle.getString("cablename"));
            Log.d(TAG,"from :" + bundle.getString("from"));
            Log.d(TAG,"to :" + bundle.getString("to"));
            Log.d(TAG,"floor :" + bundle.getString("floor"));
            Log.d(TAG,"idcroom :" + bundle.getString("idcroom"));
            Log.d(TAG,"row :" + bundle.getString("row"));
            Log.d(TAG,"cabinet :" + bundle.getString("cabinet"));
            Log.d(TAG,"box :" + bundle.getString("box"));
            Log.d(TAG,"port :" + bundle.getString("port"));
            Log.d(TAG,"comment :" + bundle.getString("comment"));
            Log.d(TAG,"cablefirm :" + bundle.getString("cablefirm"));
            Log.d(TAG,"equname :" + bundle.getString("equname"));
            Log.d(TAG,"buildingfirm :" + bundle.getString("buildingfirm"));
            Log.d(TAG,"transreciever :" + bundle.getString("transreciever"));
            Log.d(TAG,"transport :" + bundle.getString("transport"));
            Log.d(TAG,"updater :" + bundle.getString("updater"));
            Log.d(TAG,"updatetime :" + bundle.getString("updatetime"));
            Log.d(TAG,"NNNNNNN");

            ////////////////////////////////////////////////////////////////////////////////////

            Log.d(TAG,"VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVv");

            // 先將找到資料存起來
            settagid(bundle.getString("tagid"));
            setstatus(bundle.getString("status"));
            setcablename(bundle.getString("cablename"));
            setfrom(bundle.getString("from"));
            setto(bundle.getString("to"));
            setfloor(bundle.getString("floor"));
            setidcroom(bundle.getString("idcroom"));
            setrow(bundle.getString("row"));
            setcabinet(bundle.getString("cabinet"));
            setbox(bundle.getString("box"));
            setport(bundle.getString("port"));
            setcomment(bundle.getString("comment"));
            setcablefirm(bundle.getString("cablefirm"));
            setequname(bundle.getString("equname"));
            setbuildingfirm(bundle.getString("buildingfirm"));
            settransreciever(bundle.getString("transreciever"));
            settransport(bundle.getString("transport"));
            setupdater(bundle.getString("updater"));
            setupdatetime(bundle.getString("updatetime"));
            Log.d(TAG,"99999999999999999999999999");

            ////////////////////////////////////////////////////////////
            // 顯示哪些資料到頁面  (19 欄位)

            tagidtxt.setText(tagidtxt.getText() + " "+ gettagid());   // tagid 內容

            statustxt.setText( statustxt.getText() + " " + getstatus().toString());
            cablenametxt.setText(cablenametxt.getText() + " "+getcablename());

            // from , to 必須由 rfidlist 的 json data 找出來才正確

            fromtxt.setText(fromtxt.getText() + " "+getfrom());
            totxt.setText(totxt.getText() + " "+getto());
            floortxt.setText(floortxt.getText() + " " + getfloor());
            roomtxt.setText(roomtxt.getText() + " " + getidcroom());
            rowtxt.setText(rowtxt.getText() + " "+getrow());
            cabinettxt.setText(cabinettxt.getText() + " "+ getcabinet());
            boxtxt.setText(boxtxt.getText() + " " + getbox());
            porttxt.setText(porttxt.getText() + " "+ getport());
            commenttxt.setText(commenttxt.getText()+ " " + getcomment());
            cablefirmtxt.setText(cablefirmtxt.getText() + " " + getcablefirm());
            equtxt.setText(equtxt.getText() + " " + getequname());
            buildfirmtxt.setText(buildfirmtxt.getText() +" " + getbuildingfirm());
            transrecivertxt.setText(transrecivertxt.getText() + " " + gettransreciever());
            transporttxt.setText(transporttxt.getText() +" " + gettransport());
            updatertxt.setText(updatertxt.getText() + " " + getupdater());
            updatetimetxt.setText(updatetimetxt.getText() + " " + getupdatetime());


        }
        else {

            Log.d(TAG,"資料有誤");
        }

        // 返回到

        PreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Log.d(TAG,"回前一頁 - 標籤寫入");

                //新增SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
                // 這裡要將資料存起來,然後再送出

                SharedPreferences pref = getSharedPreferences("backtobacktoNewSearchCableDatafortagwriting",v.getContext().MODE_PRIVATE);
                //讓pref處於編輯狀態
                SharedPreferences.Editor editor = pref.edit();

                //存放資料，put基本資料型態(key, value)，基本資料型態:boolean, float, int, long, String
                // 這裡要放入返回的搜尋欄位 - back 是用來記錄返回之用的
                // 取出當初搜尋出來的四個條件的欄位 - 機房名稱 , 纜線名稱 , 埠號 , 裝置名稱

                editor.putInt("back",1);
                editor.putString("idcname",Getidcnamesearchback());
                editor.putString("cablename",Getcablenamesearchback());
                editor.putString("port",Getportsearchback());
                editor.putString("devicename",Getdevicenamesearchback());

                // 直接將修改的結果同步寫入檔案
                editor.commit();
                // 修改記憶體中的暫存資料，並以非同步式寫入檔案
                editor.apply();

                int back = pref.getInt("back",0);
                String idcname = pref.getString("idcname",null) ;
                String cablename = pref.getString("cablename", null) ;
                String port = pref.getString("port" , null) ;
                String devicename = pref.getString("devicename",null);

                Log.d(TAG,"back ::" + back) ;  // 這裡必須是 1 (因為要返回)
                Log.d(TAG,"idcname ::" +idcname ) ;
                Log.d(TAG,"cablename ::" + cablename) ;  // 這裡必須是 1 (因為要返回)
                Log.d(TAG,"port ::" + port ) ;
                Log.d(TAG,"devicename ::" + devicename);

                // Intent intent = new Intent(v.getContext(),NewSearchCableDatafortagwriting.class);
                // 這一行是回到 , 前兩頁 ;行為上是不通的
                // Intent intent = new Intent(v.getContext(),SearchCableDatarfidforModification.class);
                // intent.putExtras(bundle) ;

                // v.getContext().startActivity(intent);

                // 這裡需要一個轉場動畫

                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);

            }
        });

        // 寫入鍵 - 用於寫入資料之用

        tagidwritingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"資料寫入....");

                Bundle bb = new Bundle();
                assert bb != null ;

                if (GetTagID().toString().length() == 0 )
                {
                    // 這裡必須有個對話框說明是空的 tag id
                    // 前提是避免 tag id 為空時的誤寫現象
                    EmptyTagPromptDialog();

                }
                else {

                    ///////////////  這裡要開 database   /////////////////

                    DBOpenHelper1 dbhelper = new DBOpenHelper1(v.getContext());
                    assert dbhelper != null;  // assert database helper is available

                    SetDBHelper(dbhelper);                   // save database helper
                    DBOpenHelper1 tempdbhelper = GetDBHelper1();
                    db = tempdbhelper.getReadableDatabase();

                    Toast.makeText(CableDataforRfidWritng.this, "資料庫開啟", Toast.LENGTH_SHORT).show();

                    if (dbhelper != null )
                        Log.d(TAG,"dbhelper is Not null ");
                    else
                        Log.d(TAG,"dbhelper is Null");

                    assert dbhelper != null ;   // assert the database helper is available

                    // 若資料庫非空, 則必須撈出同一筆資料以供查詢

                    String querywriting = "SELECT " +
                            "idclist.idcId, "+
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
                            "GROUP BY " +
                            "idclist.idcId, "+
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

                            "HAVING rfidlist.status = ? " +
                            "AND rfidlist.cableName = ? " +
                            "AND oldflist.floor = ? " +
                            "AND idclist.idcName = ? "+
                            "AND oldflist.rows = ? " +
                            "AND oldflist.cabinet = ? " +
                            "AND oldflist.box = ? " +
                            "AND oldflist.ports = ? " +
                            "AND rfidlist.deviceName = ? " +
                            "AND rfidlist.updateUser = ? " +
                            "OR rfidlist.updateTime = ? " ;


                    Log.d(TAG,"條件條件條件條件條件條件條件條件條件條件條件條件");

               String[] wcondination = {
                     getstatus(),
                     getcablename() ,
                     getfloor() ,
                     getidcroom() ,
                     getrow() ,
                     getcabinet() ,
                     getbox() ,
                     getport() ,
                     getequname() ,
                     getupdater() ,
                     getupdatetime()

             };  // query conditions

                    Cursor cursor = db.rawQuery(querywriting, wcondination );       // 查詢 合併的3個表格

                    Log.d(TAG,"查到多少筆: " + cursor.getCount()) ;
                    Log.d(TAG,"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

                    while (cursor.moveToNext()) {

                        Log.d(TAG,"-----------------------------------------------------");

                        String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                        if (status.equals("0"))
                            Log.d(TAG, "狀態: " + "未使用");
                        else
                            Log.d(TAG, "狀態: " + "已使用");

                        String cablename3 = cursor.getString(cursor.getColumnIndexOrThrow("cableName"));
                        Log.d(TAG, "光纜名稱: " + cablename3.toString());
                        String jsondata = cursor.getString(cursor.getColumnIndexOrThrow("jsonData"));

                        try {

                            JSONObject jsonObject = new JSONObject(jsondata);
                            String from_col1 = jsonObject.getString("column1");

                            String to_col2 = jsonObject.getString("column2");

                            Log.d(TAG, "from :" + from_col1);
                            Log.d(TAG, "to :" + to_col2);

                        } catch (JSONException e) {

                        }

                        floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
                        Log.d(TAG, "樓層: " + floor);
                        String idcname3 = cursor.getString(cursor.getColumnIndexOrThrow("idcName"));
                        Log.d(TAG, "機房名稱: " + idcname3);
                        String row3 = cursor.getString(cursor.getColumnIndexOrThrow("rows"));
                        Log.d(TAG, "排: " + row3);
                        String cabinet3 = cursor.getString(cursor.getColumnIndexOrThrow("cabinet"));
                        Log.d(TAG, "櫃: " + cabinet3);
                        String box3 = cursor.getString(cursor.getColumnIndexOrThrow("box"));
                        Log.d(TAG, "箱: " + box3.toString());
                        String port3 = cursor.getString(cursor.getColumnIndexOrThrow("ports"));
                        Log.d(TAG, "埠: " + port3);


                        try {

                            JSONObject jsonObject = new JSONObject(jsondata);
                            String comment3 = jsonObject.getString("column7");
                            Log.d(TAG, "備註: " + comment3);

                            String cablefirm3 = jsonObject.getString("column3");
                            Log.d(TAG, "纜線廠商: " + cablefirm3);

                        } catch (JSONException e) {

                        }

                        String devicename3 = cursor.getString(cursor.getColumnIndexOrThrow("deviceName"));
                        Log.d(TAG, "設備名稱: " + devicename3);

                        try {

                            JSONObject jsonObject = new JSONObject(jsondata);
                            String buildingfirm3 = jsonObject.getString("column4");
                            Log.d(TAG, "建置廠商: " + buildingfirm3);

                            String transmissionend3 = jsonObject.getString("column5");
                            Log.d(TAG, "傳輸接收端: " + transmissionend3.toString());
                            String deviceport3 = jsonObject.getString("column5");
                            Log.d(TAG, "傳輸設備Port: " + deviceport3);

                        } catch (JSONException e) {

                        }

                        String updateuser3 = cursor.getString(cursor.getColumnIndexOrThrow("updateUser"));
                        Log.d(TAG, "修改人: " + updateuser3);

                        String updatetime3 = cursor.getString(cursor.getColumnIndexOrThrow("updateTime"));
                        Log.d(TAG, "修改時間: " + updatetime3);

                    }   // end of while
                    Log.d(TAG,"-----------------------------------------------------");

                    updaterecord(gettagid());

                    /*

                    bb.putString("tagid", GetTagID());   // 取出真實的 tag id 非空的
                    bb.putString("status", getstatus());
                    bb.putString("cablename", getcablename());

                    bb.putString("from", getfrom());
                    bb.putString("to", getto());

                    bb.putString("floor", getfloor());
                    bb.putString("idcroom", getidcroom());
                    bb.putString("row", getrow());
                    bb.putString("cabinet", getcabinet());
                    bb.putString("box", getbox());
                    bb.putString("port", getport());
                    bb.putString("comment", getcomment());
                    bb.putString("cablefirm", getcablefirm());
                    bb.putString("equname", getequname());
                    bb.putString("buildingfirm", getbuildingfirm());
                    bb.putString("transreciever", gettransreciever());
                    bb.putString("transport", gettransport());
                    bb.putString("updater", getupdater());
                    bb.putString("updatetime", getupdatetime());

                    Intent i = new Intent(CableDataforRfidWritng.this , RfidTagidWritingConfirmation.class);
                    i.putExtras(bb) ;    // put the above boundle into intent and pass it to next activity
                    finish();  // close current activity

                    // v.getContext().startActivity(i);

                     */


                }   // bundle is available

                // mDevice.readTagLED();
                boolean b ;


                if (!GetTagID().isEmpty()) {

                    if ( mDevice.isopen() == true ) {
                        b = mDevice.readTagLED(GetTagID().getBytes(), "00000000".getBytes(), 1);
                        Log.d(TAG, "讀取讀取讀取讀取讀取讀取讀取讀取讀取讀取 : " + Boolean.toString(b));
                    }
                    else {
                        mDevice.isopen() ;
                    }
                }
                else
                    Log.d(TAG,"標籤為空 /////" ) ;

                Log.d(TAG,"纜線修改資料纜線修改資料纜線修改資料纜線修改資料纜線修改資料纜線修改資料");


                String querythreetables = "SELECT " +
                        "idclist.idcName, " +
                        "oldflist.oldfName, "  +
                        "rfidlist.cableName, " +
                        "rfidlist.jsonData, "  +
                        "rfidlist.status, "     +
                        "oldflist.floor, " +
                        "rfidlist.tagId, " +
                        "rfidlist.deviceName," +
                        "oldflist.rows, " +
                        "oldflist.cabinet," +
                        "oldflist.box, " +
                        "oldflist.ports," +
                        "rfidlist.updateUser, " +
                        "rfidlist.updateTime " +

                        "FROM idclist "+
                        "JOIN oldflist "+
                        "ON idclist.idcId = oldflist.idcId "+
                        "JOIN rfidlist " +
                        "ON oldflist.oldfId = rfidlist.oldfId";

                String devicename ="0000000000";


                /***********************************************
                 DBOpenHelper1 dbhelper = new DBOpenHelper1(v.getContext());
                 assert dbhelper != null;  // assert database helper is available

                 SetDBHelper(dbhelper);                   // save database helper
                 DBOpenHelper1 tempdbhelper = GetDBHelper1();
                 SQLiteDatabase db = tempdbhelper.getReadableDatabase();

                 if (dbhelper != null )
                 Log.d(TAG,"dbhelper is Not null ");
                 else
                 Log.d(TAG,"dbhelper is Null");
                 assert dbhelper != null ;   // assert the database helper is available
                 **********************************************************************/
                 // 以下無用 ! //////////////////////////////////////////////////////////

                 if (false) {
                     Cursor cursor = db.rawQuery(querythreetables, null);   // 查詢 合併的3個表格
                     // Cursor cursorupdate = db.rawQuery(updatethreetables , conditions) ;

                     Log.d(TAG, "筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數");
                     Log.d(TAG, "共有多少筆:" + cursor.getCount());

                     String query = "SELECT " +
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
                             "WHERE rfidlist.tagId = ? " +
                             "AND rfidlist.status = ? " +
                             "AND rfidlist.cableName = ? " +
                             "AND oldflist.floor = ? " +
                             "AND oldflist.rows = ? " +
                             "AND oldflist.cabinet = ? " +
                             "AND oldflist.box =  ? " +
                             "AND oldflist.ports = ? " +
                             "AND rfidlist.deviceName = ? " +
                             "AND rfidlist.updateUser = ? " +
                             "AND rfidlist.updateTime = ? " +
                             "AND idclist.idcName = ?";


                     String queryforupdate = "SELECT " +
                             "idclist.idcName " +
                             "FROM idclist " +
                             "WHERE idclist.idcName = ?";

                     String[] selectionArgs = new String[]{
                             gettagid(),
                             getstatus(),
                             getcablename(),
                             getfloor(),
                             getrow(),
                             getcabinet(),
                             getbox(),
                             getport(),
                             getequname(),
                             getupdater(),
                             getupdatetime(),
                             getidcroom()
                     };    // 搜尋條件 (所有欄位)

                     String[] selectionidc = new String[]{
                             getidcroom()
                     };    // 搜尋條件 (所有欄位)


                     String[] selectionidcforupdate = new String[]{
                             gettagid(),
                             getstatus(),
                             getcablename(),
                             getfloor(),
                             getrow(),
                             getcabinet(),
                             getbox(),
                             getport(),
                             getequname(),
                             getupdater(),
                             getupdatetime(),
                             getidcroom()

                     };    // 搜尋條件 (所有欄位)

                     // Cursor c = db.rawQuery(query, selectionArgs);

                     // Log.d(TAG, "查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數");
                     //  Log.d(TAG, "查到筆數 >>>> " + c.getCount());

                     // UPDATE COMPANY SET address = 'Texas', salary = 20000.00 WHERE 1=1 ;
                     String s = "llll";

                     String updatefileds = "UPDATE " + "idclist, " + "oldflist " +
                             "SET idclist.idcName = s " +
                             "FROM idclist " +
                             "JOIN oldflist " +
                             "ON idclist.idcId = oldflist.idcId " +
                             "JOIN rfidlist " +
                             "ON oldflist.oldfId = rfidlist.oldfId " +
                             "WHERE rfidlist.tagId = ? " +
                             "AND rfidlist.status = ? " +
                             "AND rfidlist.cableName = ? " +
                             "AND oldflist.floor = ? " +
                             "AND oldflist.rows = ? " +
                             "AND oldflist.cabinet = ? " +
                             "AND oldflist.box =  ? " +
                             "AND oldflist.ports = ? " +
                             "AND rfidlist.deviceName = ? " +
                             "AND rfidlist.updateUser = ? " +
                             "AND rfidlist.updateTime = ? " +
                             "AND idclist.idcName = ?";


                     // Cursor cc  = db.rawQuery(updatefileds, selectionidc) ;

                     // Log.d(TAG,"CCCCC" + cc.getCount()) ;

                     // 下面將查到的資料顯示出來 !
                     // 接著 再過濾 jsondata string
                     // 找出最終的資料

                     String tagid3,
                             status,
                             cablename3,
                             from_col1 = "",
                             to_col2 = "",
                             floor,
                             idcname3,
                             row3,
                             cabinet3,
                             box3,
                             port3,
                             comment7 = "",
                             cablefirm3 = "",
                             devicename3,
                             buildingfirm3 = "",
                             transmissionend3 = "",
                             deviceport3 = "",
                             updateuser3,
                             updatetime3;   // all fields

                     String jsondata;

                     while (cursor.moveToNext()) {

                         Log.d(TAG, "**********************************************");

                         tagid3 = cursor.getString(cursor.getColumnIndexOrThrow("tagId"));
                         Log.d(TAG, "TAG ID: " + tagid3.toString());

                         status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                         if (status.equals("0"))
                             Log.d(TAG, "狀態: " + "未使用");
                         else
                             Log.d(TAG, "狀態: " + "已使用");

                         cablename3 = cursor.getString(cursor.getColumnIndexOrThrow("cableName"));
                         Log.d(TAG, "光纜名稱: " + cablename3.toString());
                         floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
                         Log.d(TAG, "樓層: " + floor);
                         idcname3 = cursor.getString(cursor.getColumnIndexOrThrow("idcName"));
                         Log.d(TAG, "機房名稱: " + idcname3);
                         row3 = cursor.getString(cursor.getColumnIndexOrThrow("rows"));
                         Log.d(TAG, "排: " + row3);
                         cabinet3 = cursor.getString(cursor.getColumnIndexOrThrow("cabinet"));
                         Log.d(TAG, "櫃: " + cabinet3);
                         box3 = cursor.getString(cursor.getColumnIndexOrThrow("box"));
                         Log.d(TAG, "箱: " + box3.toString());
                         port3 = cursor.getString(cursor.getColumnIndexOrThrow("ports"));
                         Log.d(TAG, "埠: " + port3);
                         devicename3 = cursor.getString(cursor.getColumnIndexOrThrow("deviceName"));
                         Log.d(TAG, "設備名稱: " + devicename3);
                         updateuser3 = cursor.getString(cursor.getColumnIndexOrThrow("updateUser"));
                         Log.d(TAG, "修改人: " + updateuser3);
                         updatetime3 = cursor.getString(cursor.getColumnIndexOrThrow("updateTime"));
                         Log.d(TAG, "修改時間: " + updatetime3);
                         jsondata = cursor.getString(cursor.getColumnIndexOrThrow("jsonData"));
                         Log.d(TAG, "jsondata:" + jsondata.toString());

                         // extract those fields (column1 to column7) from jsondata
                         ///////////////

                         try {

                             JSONObject jsonObject = new JSONObject(jsondata);
                             from_col1 = jsonObject.getString("column1");
                             to_col2 = jsonObject.getString("column2");
                             comment7 = jsonObject.getString("column7");
                             cablefirm3 = jsonObject.getString("column3");
                             buildingfirm3 = jsonObject.getString("column4");
                             transmissionend3 = jsonObject.getString("column5");
                             deviceport3 = jsonObject.getString("column6");

                             Log.d(TAG, "from :" + from_col1);
                             Log.d(TAG, "to :" + to_col2);
                             Log.d(TAG, "comment :" + comment7);
                             Log.d(TAG, "cablefirm :" + cablefirm3);
                             Log.d(TAG, "buildingfirm :" + buildingfirm3);
                             Log.d(TAG, "transmissionend3 :" + transmissionend3);
                             Log.d(TAG, "deviceport3 :" + deviceport3);

                         } catch (JSONException e) {

                             Log.d(TAG, "錯誤:" + e.getMessage().toString());

                         }

                     }

                     // Cursor cu  = db.rawQuery(updatefileds, selectionidcforupdate) ;
                     // Log.d(TAG,"RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR" + cu.getCount()) ;
                 }
            }
        });

    }  // end of onCreate


        private void Setidcnamesearchback(String idcroomname) {
        Log.d(TAG,"Setidcnamesearchback >> " + idcroomname);
        this.idcnamesearchback = idcroomname ;

    }
    private void  updaterecord (String TAGID ) {


        String   updatewriting = "UPDATE " + "rfidlist " +
                "SET tagid = " + gettagid() + " " +

                "FROM idclist " +
                "JOIN oldflist " +
                "ON idclist.idcId = oldflist.idcId " +
                "JOIN rfidlist " +
                "ON oldflist.oldfId = rfidlist.oldfId " +


                "WHERE rfidlist.status = ? " +
                "AND rfidlist.cableName = ? " +
                "AND oldflist.floor = ? " +
                "AND idclist.idcName = ? "+
                "AND oldflist.rows = ? " +
                "AND oldflist.cabinet = ? " +
                "AND oldflist.box = ? " +
                "AND oldflist.ports = ? " +
                "AND rfidlist.deviceName = ? " +
                "AND rfidlist.updateUser = ? " +
                "OR rfidlist.updateTime = ? " ;

        String[] wcondination = {
                getstatus(),
                getcablename() ,
                getfloor() ,
                getidcroom() ,
                getrow() ,
                getcabinet() ,
                getbox() ,
                getport() ,
                getequname() ,
                getupdater() ,
                getupdatetime()

        };  // query conditions


        try {

            Cursor cursor = db.rawQuery(updatewriting, wcondination );       // 查詢 合併的3個表格

            // 若成功, 需要一個對話框通知寫入成功

            UpDateRecordSuccessfullyDialog();

        }
        catch (Exception e) {

            Log.d(TAG,"錯誤發生 >>>>" + e.getMessage().toString()) ;

        }


    }

    private void UpDateRecordSuccessfullyDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(CableDataforRfidWritng.this).
                inflate(R.layout.updaterecordsuccdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(CableDataforRfidWritng.this);

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
                CloseDBHelper(GetDBHelper1());   // close database !
                finish(); // close current activity
                CableDataforRfidWritng.this.moveTaskToBack(true);  // 離開 the app

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


    private String Getidcnamesearchback() {

        assert this.idcnamesearchback != null;

        return this.idcnamesearchback  ;
    }

    private void Setcablenamesearchback(String cablename) {
        assert cablename != null ;
        Log.d(TAG,"Setcablenamesearchback >> " + cablename);
        this.cablenamesearchback = cablename ;

    }
    private String Getcablenamesearchback() {

        assert this.cablenamesearchback!= null   ;

        return this.cablenamesearchback  ;
    }

    private void Setportsearchback(String port) {
        assert port!= null ;
        Log.d(TAG,"Setportsearchback >> " + port);
        this.portsearchback = port ;

    }
    private String Getportsearchback() {

        return this.portsearchback  ;
    }
    private void Setdevicenamesearchback(String devicename) {
        assert devicename !=null ;
        Log.d(TAG,"Setdevicenamesearchback >> " + devicename);
        this.devicenamesearchback = devicename ;
    }

    private String  Getdevicenamesearchback() {
        return this.devicenamesearchback ;
    }

    private void Setbackflag (int backflag) {
        assert backflag > -1 ;
        this.backflag = backflag ;
    }

    private int Getbackflag () {
        return this.backflag ;
    }

    //Performing action onItemSelected and onNothing selected

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(arg1.getContext(), country[position] , Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void settagid(String tagid) {
        assert tagid !=null ;
        Log.d(TAG,"tagid :" + tagid);
        this.tagid = tagid ;
    }

    private String gettagid() {
        assert this.tagid !=null ;
        Log.d(TAG,">> tagid :" + this.tagid);
        return this.tagid ;
    }

    private void  setstatus(String status) {
        assert status!=null ;
        Log.d(TAG,"MMMMMMMMMMMMMMMMMMMMMMMMMMM   status :"+ status);
        this.status = status;
    }
    private String getstatus() {
        assert status!=null ;
        Log.d(TAG,">> status :"+ this.status);
        return this.status ;
    }
    private void setcablename(String cablename) {
        assert cablename !=null ;
        Log.d(TAG,"cablename :" + cablename);
        this.cablename = cablename ;
    }

    private String getcablename() {
        assert this.cablename !=null ;
        Log.d(TAG,">> cablename :" + this.cablename);
        return this.cablename ;
    }
    private void setfrom(String from) {
        assert from!=null;
        Log.d(TAG,"from :" + from);
        this.from = from ;
    }

    private String getfrom() {
        assert this.from!=null;
        Log.d(TAG,">> from :" + this.from);
        return this.from ;
    }
    private void setto(String to) {
        assert to!=null ;
        Log.d(TAG,"to :" + to);
        this.to = to ;
    }

    private String getto() {
        assert this.to!=null ;
        Log.d(TAG,">> to :" + this.to);
        return this.to ;
    }
    private void setfloor(String floor ) {
        assert floor != null ;
        Log.d(TAG,"floor :" + floor);
        this.floor = floor ;

    }

    private String getfloor() {
        assert this.floor != null ;
        Log.d(TAG,">> floor :" + this.floor);
        return this.floor ;

    }
    private void setidcroom(String room) {
        assert room!=null ;
        Log.d(TAG,"room :" + room);
        this.idcroom = room ;

    }

    private String getidcroom() {
        assert this.idcroom!=null ;
        Log.d(TAG,">> room :" + this.idcroom);
        return this.idcroom ;

    }
    private void setrow(String row ) {
        assert row !=null ;
        Log.d(TAG,"row :" + row);
        this.row = row ;

    }

    private String getrow() {
        assert this.row !=null ;
        Log.d(TAG,">> row :" + this.row);
        return this.row ;

    }
    private void setcabinet(String cabinet) {
        assert cabinet != null ;
        Log.d(TAG,"cabinet :" + cabinet);

        this.cabinet = cabinet ;
    }

    private String getcabinet() {
        assert this.cabinet != null ;
        Log.d(TAG,">> cabinet :" + this.cabinet);

        return this.cabinet ;
    }

    private void setbox(String box) {
        assert box!= null ;
        Log.d(TAG,"box :" + box);

        this.box = box ;

    }

    private String getbox() {
        assert this.box!= null ;
        Log.d(TAG,">> box :" + this.box);

        return this.box ;

    }
    private void setport(String port ) {
        assert port!= null ;
        Log.d(TAG,"port :" + port);
        this.port= port ;
    }

    private String getport() {
        assert this.port!= null ;
        Log.d(TAG,">> port :" + this.port);
        return this.port ;
    }


    private void setcomment(String comment) {
        assert comment!=null ;
        Log.d(TAG,"comment :" + comment);
        this.comment = comment ;

    }

    private String getcomment() {
        assert this.comment!=null ;
        Log.d(TAG,">> comment :" + this.comment);
        return this.comment ;

    }
    private void setcablefirm(String cablefirm) {
        assert cablefirm != null ;
        Log.d(TAG,"cablefirm :" + cablefirm);
        this.cablefirm = cablefirm ;
    }

    private String getcablefirm() {
        assert this.cablefirm != null ;
        Log.d(TAG,">> cablefirm :" + this.cablefirm);
        return this.cablefirm ;
    }
    private void setequname(String equname) {
        assert equname !=null ;
        Log.d(TAG,"equname :" + equname);

        this.devicename = equname ;

    }

    private String getequname() {
        assert this.devicename !=null ;
        Log.d(TAG,">> equname :" + this.devicename);

        return this.devicename ;

    }

    private void setbuildingfirm(String buildingfirm) {
        assert buildingfirm!= null ;
        Log.d(TAG,"buildingfirm :" + buildingfirm);

        this.buildingfirm = buildingfirm  ;
    }

    private String  getbuildingfirm() {
        assert this.buildingfirm!= null ;
        Log.d(TAG,">> buildingfirm :" + this.buildingfirm);

        return this.buildingfirm ;
    }
    private void settransreciever (String transreciever) {
        assert transreciever!=null ;
        Log.d(TAG,"transreciever :" + transreciever);
        this.transmissionend = transreciever ;

    }

    private String gettransreciever () {
        assert this.transmissionend!=null ;
        Log.d(TAG,">> transreciever :" + this.transmissionend);
        return this.transmissionend  ;

    }
    private void settransport(String transport) {
        assert transport != null ;
        Log.d(TAG,"transport :" + transport);
        this.transmissionport = transport ;
    }

    private String gettransport() {
        assert this.transmissionport != null ;
        Log.d(TAG,">> transport :" + this.transmissionport);
        return this.transmissionport ;
    }
    private void setupdater (String updater) {
        assert updater!=null ;
        Log.d(TAG,"updater :" + this.updateuser);
        this.updateuser = updater ;

    }

    private String getupdater () {
        assert this.updateuser!=null ;
        Log.d(TAG,">> updater :" + this.updateuser);
        return this.updateuser ;

    }
    private void setupdatetime (String updatetime) {

        assert updatetime !=null ;
        Log.d(TAG,"updatetime :" + this.updatetime);

        this.updatetime = updatetime ;
    }

    private String getupdatetime () {

        assert this.updatetime !=null ;
        Log.d(TAG,">> updatetime :" + this.updatetime);
        return this.updatetime ;
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


    @Override
    public void onBackPressed() {
        //onBackPressed() 捕获后退键按钮back的信息
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
        finish();

        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out) ;
        // animation bottom in / out
    }

    private boolean OpenUHFDevice(){

        this.mDevice = UHFService.getInstance() ;
        this.mDevice.open();   // open UHF/RFID

        return this.mDevice.isopen() ;
    }
    private void CloseUHFDevice() {
        this.mDevice = UHFService.getInstance() ;
        this.mDevice.close();

    }

    private void NoTagPromptDialog(boolean which )
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        cancel = (ImageView)findViewById(R.id.notablecancel_button) ;

        View alertCustomdialog = LayoutInflater.
                from(CableDataforRfidWritng.this).
                inflate(R.layout.notagiddialog, null);  // 沒有  tag id 的對話框

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(CableDataforRfidWritng.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel = (ImageView) alertCustomdialog.findViewById(R.id.notablecancel_button);
        TextView msgtxt  = (TextView) alertCustomdialog.findViewById(R.id.tablemsgtxt) ;
        final AlertDialog dialog = alert.create();

        if (which == false ) {
            Log.d(TAG,"which :" + which) ;
            msgtxt.setText("查無標籤");
        }
        else {


            Log.d(TAG,"which :" + which) ;
            msgtxt.setText("標籤讀取成功");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){

                @Override
                public void run() {

                    //過兩秒後要做的事情 , 目前不要清除它
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // tagidedit.setText("");
                        }
                    });

                }}, 3000); // 3 secs later will do it !

        }

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
                            // dialog.dismiss();  // close dialog

                            //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            //logo.startAnimation(animFadeOut);   // execute fade out animation

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    dialog.dismiss();  // close dialog
                                }
                            }, 2000);

                            Log.d(TAG, "Ending Animation ");

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
        Window nodatawindow = dialog.getWindow();
        nodatawindow.setWindowAnimations(R.style.AnimBottom);    // 對話框 (中間摺疊)

    }

    private void EmptyTagPromptDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        //will create a view of our custom dialog layout

        cancel = (ImageView)findViewById(R.id.notablecancel_button) ;

        View alertCustomdialog = LayoutInflater.
                from(CableDataforRfidWritng.this).
                inflate(R.layout.emptytagiddialog, null);  // 沒有  tag id 的對話框

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(CableDataforRfidWritng.this);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);   // 設定dialog的 view

        cancel = (ImageView) alertCustomdialog.findViewById(R.id.notablecancel_button);
        TextView msgtxt  = (TextView) alertCustomdialog.findViewById(R.id.tablemsgtxt) ;
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
                            // dialog.dismiss();  // close dialog

                            //Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            //logo.startAnimation(animFadeOut);   // execute fade out animation

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    dialog.dismiss();  // close dialog
                                }
                            }, 2000);

                            Log.d(TAG, "Ending Animation ");

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
        Window nodatawindow = dialog.getWindow();
        nodatawindow.setWindowAnimations(R.style.AnimBottom);    // 對話框 (中間摺疊)

    }


    private void findtagid() {


        // mAllEpcs.clear();  // 首先 清除 tagid list 中的資料

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tagidedit.setText("");   // 每次都先清除一下
            }
        });


        if  ( mAllEpcs.size()  == 0 ) {

            Log.d(TAG,"資料已清空");
        }
        else {
            Log.d(TAG,"資料未清空");

        }
        if (mDevice.isopen() == true )
           mDevice.open() ;
        else ;   // if UHF has been open


        epc = new EPC();  // new a EPC object
        assert epc != null ;

        boolean result = mDevice.inventoryOnce(epc, 100);  //


        if ( result == true ) {
            Log.d(TAG, ">>>>>>>>>>>>> 標籤 id :" + epc.getId().toString());
            SaveTagID(epc.getId()) ;   // 存起 tagid

        }
        else
            Log.d(TAG,"無id ") ;


        if (len == 0) {
            // 必須有一個通知 UI 的動作 , 所以先存起來
            // settotaloftags(len);
            Log.d(TAG, "長度 ::: " + len);

        } else {

            // settotaloftags(len);
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(0).getId() );
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(1).getId() );
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(2).getId() );
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(3).getId() );
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(4).getId() );
            Log.d(TAG,">>>>>>> Tag id :" + mAllEpcs.get(5).getId() );

        }


    }



    @Override protected void onResume()
    {
        super.onResume();
        mScanKeyService.registerCallback(mCallback, null); //
    }

    @Override protected void onPause()
    {
        super.onPause();
        mScanKeyService.unregisterCallback(mCallback);  // 註銷 callback function
    }

        public class InventoryThread extends Thread {
            @Override
            public void run() {

                while (true) {

                    try {

                        sleep(100);
                        mAllEpcs = mDevice.getTagIDs();
                        Log.d(TAG, "長度 ::: " + mAllEpcs.size());
                        int len = mAllEpcs.size();

                        if (len == 0) {
                            // 必須有一個通知 UI 的動作 , 所以先存起來
                            settotaloftags(len);
                            Log.d(TAG, "長度 ::: " + len);

                        } else {

                            settotaloftags(len);
                            Log.d(TAG, "長度 ::: " + len);
                            Log.d(TAG, ">>>>>>>>>" + mAllEpcs.get(1).getId());
                            //Log.d(TAG, ">>>>>>>>>" + mAllEpcs.get(1).getId());
                            //Log.d(TAG, ">>>>>>>>>" + mAllEpcs.get(2).getId());
                            //Log.d(TAG, ">>>>>>>>>" + mAllEpcs.get(3).getId());
                            //Log.d(TAG, ">>>>>>>>>" + mAllEpcs.get(4).getId());

                            // SearchTagid(mAllEpcs.get(0).getId());  // 將找的 tagid 存起來
                            //Log.d(TAG,">>>>>>>>>"+ mAllEpcs.get(1).getId());
                            //Log.d(TAG,">>>>>>>>>"+ mAllEpcs.get(2).getId());
                            //Log.d(TAG,">>>>>>>>>"+ mAllEpcs.get(3).getId());
                            //Log.d(TAG,">>>>>>>>>"+ mAllEpcs.get(4).getId());
                            //Log.d(TAG,">>>>>>>>>"+ mAllEpcs.get(5).getId());
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }   // end of while

            }
        }
        // 搜尋到的 tag id

        private void SearchTagid(String tagid) {

        this.searchtagid = tagid ;

        }
        private String GetSearchTagid() {
           return this.searchtagid ;
        }

    private void settotaloftags(int len)
    {
        assert len >-1  ;

        this.len = len ;

    }
    private int gettotaloftags() {

        return this.len ;
    }

    private void NoTagDialog()
    {

        ImageView cancel;  // 右上角取消按鈕

        // Button btn_no , btn_yes ;

        //will create a view of our custom dialog layout

        View alertCustomdialog = LayoutInflater.
                from(CableDataforRfidWritng.this).
                inflate(R.layout.notagdialog, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(CableDataforRfidWritng.this);

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
        /*
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if you exit the app , you must close database !
                CloseDBHelper(GetDBHelper1());   // close database !
                finish(); // close current activity
                MainSqaurefun.this.moveTaskToBack(true);  // 離開 the app

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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情
                dialog.dismiss();

            }}, 2000);

    }

    private void SaveTagID(String tagid) {

        assert tagid != null ;

        this.tagidforwriting = tagid ;

    }

    private String GetTagID() {

       if ( this.tagidforwriting.equals("") == true )
           Log.d(TAG,"空的 tag id ");
       else
       Log.d(TAG,">> tag id :" + this.tagidforwriting) ;

        assert this.tagidforwriting != null ;

        return  this.tagidforwriting ;
    }

}

