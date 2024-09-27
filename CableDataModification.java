package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CableDataModification extends AppCompatActivity {

    private ImageView PreviousPage ;
    public Activity mCableDataModificationActivity ;

    public String[] country ;

    // 下面都是欄位

    private TextView tagidtxt ;
    private EditText statusedit ;
    private EditText cablenameedit ;
    private EditText fromedit ;
    private EditText toedit  ;
    private  TextView floortxt ;
    private TextView roomtxt ;
    private TextView rowtxt ;
    private TextView cabinettxt ;
    private TextView boxtxt ;
    private TextView porttxt ;
    private EditText commentedit ;
    private EditText cablefirmedit ;
    private EditText equedit ;
    private EditText buildfirmedit ;
    private EditText transreciveredit ;
    private EditText transportedit ;
    private EditText updateredit ;
    private EditText updatetimeedit ;

    private DBOpenHelper1 mDBHelper1 ;   // dbopenhelper1

    private Button modificationBtn ;

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


    private String idcnamesearchback,
                   cablenamesearchback ,
                   portsearchback ,
                   devicenamesearchback ;

    private int backflag  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_data_modification);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        mCableDataModificationActivity = this ;

        tagidtxt = (TextView)findViewById(R.id.tagidtxttitle);            // tagid 內容
        statusedit =  (EditText) findViewById(R.id.statusedit) ;

          cablenameedit  = (EditText) findViewById(R.id.cablenameedit);
          fromedit = (EditText)findViewById(R.id.fromedit) ;
          toedit = (EditText) findViewById(R.id.toedit) ;
          floortxt = (TextView) findViewById(R.id.floortxt);
          roomtxt = (TextView) findViewById(R.id.roomtxt);
          rowtxt = (TextView) findViewById(R.id.rowtxt);
          cabinettxt = (TextView) findViewById(R.id.cabinettxt);
          boxtxt = (TextView) findViewById(R.id.boxtxt) ;
          porttxt = (TextView) findViewById(R.id.porttxt);
          commentedit = (EditText) findViewById(R.id.commentedit);
          cablefirmedit = (EditText) findViewById(R.id.cablefirmedit);
          equedit = (EditText) findViewById(R.id.equedit);
          buildfirmedit = (EditText) findViewById(R.id.buildfirmedit);
          transreciveredit = (EditText) findViewById(R.id.transreciveredit);
          transportedit = (EditText) findViewById(R.id.transportedit);
          updateredit = (EditText) findViewById(R.id.updateredit);
          updatetimeedit = (EditText) findViewById(R.id.updatetimeedit);

           Log.d(TAG,"纜線修改開啟");

           PreviousPage = (ImageView)findViewById(R.id.previousimg);
           modificationBtn = (Button)findViewById(R.id.modifcationbtn) ;

          /*
           SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
           String yesno = sharedPref.getString("backtoSearchCableDatarfidforModification","no");

          if (yesno.equals("no"))
          {

              sharedPref = getPreferences(Context.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPref.edit();
              editor.putString("backtoSearchCableDatarfidforModification", "yes");
              editor.apply();

          }
          else {


          }

           */

        DBOpenHelper1 dbhelper = new DBOpenHelper1(this);
        assert dbhelper != null;  // assert database helper is available

        SetDBHelper(dbhelper);                   // save database helper
        DBOpenHelper1 tempdbhelper = GetDBHelper1();
        SQLiteDatabase db = tempdbhelper.getReadableDatabase();

        if (dbhelper != null )
            Log.d(TAG,"dbhelper is Not null ");
        else
            Log.d(TAG,"dbhelper is Null");

        assert dbhelper != null ;   // assert the database helper is available

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

            ////////////////////////////////////////////////////////////////////////////////////
            // 先將找到資料存起來
            //
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

            ////////////////////////////////////////////////////////////
            // 顯示哪些資料到頁面  ( 19 fileds )

            tagidtxt.setText("TAG ID: "+ gettagid());   // tagid 內容

            if (getstatus().equals("0"))
                statusedit.setText(" 未使用");
            else
                statusedit.setText(" 已使用");

            // 可修改欄位
            cablenameedit.setText(" "+getcablename());
            fromedit.setText(" "+getfrom());
            toedit.setText(" "+getto());

            floortxt.setText(floortxt.getText() + " " + getfloor());
            roomtxt.setText(roomtxt.getText() + " " + getidcroom());
            rowtxt.setText(rowtxt.getText() + " "+getrow());
            cabinettxt.setText(cabinettxt.getText() + " "+ getcabinet());
            boxtxt.setText(boxtxt.getText() + " " + getbox());
            porttxt.setText(porttxt.getText() + " "+ getport());

            // 可修改欄位
            commentedit.setText(commentedit.getText() + " " + getcomment());
            cablefirmedit.setText(cablefirmedit.getText() + " " + getcablefirm());
            equedit.setText(equedit.getText() + " " + getequname());
            buildfirmedit.setText(buildfirmedit.getText() +" " + getbuildingfirm());
            transreciveredit.setText(transreciveredit.getText() + " " + gettransreciever());
            transportedit.setText(transportedit.getText() +" " + gettransport());
            updateredit.setText(updateredit.getText() + " " + getupdater());
            updatetimeedit.setText(updatetimeedit.getText() + " " + getupdatetime());


        }
        else {

            Log.d(TAG,"資料有誤");
        }

        PreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Log.d(TAG,"回前一頁 - 纜線修改");


                //新增SharedPreferences，丟入的參數("檔案名稱",mode參數(存取權限))
                SharedPreferences pref = getSharedPreferences("backtoSearchCableDatarfidforModification",MODE_PRIVATE);
                //讓pref處於編輯狀態
                SharedPreferences.Editor editor = pref.edit();
                //存放資料，put基本資料型態(key, value)，基本資料型態:boolean, float, int, long, String
                editor.putInt("back",1);

                // 直接將修改的結果同步寫入檔案
                editor.commit();
                // 修改記憶體中的暫存資料，並以非同步式寫入檔案
                editor.apply();

                int back = pref.getInt("back",0);
                Log.d(TAG,"back :" + back) ;

                Intent intent = new Intent(v.getContext(),SearchCableDatarfidforModification.class);

                // 這一行是回到 , 前兩頁 ;行為上是不通的
                // Intent intent = new Intent(v.getContext(),SearchCableDatarfidforModification.class);
                // intent.putExtras(bundle) ;

                v.getContext().startActivity(intent);

                // 這裡需要一個轉場動畫

                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);

            }
        });

        modificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"修改資料");

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

                Cursor cursor   = db.rawQuery(querythreetables, null);   // 查詢 合併的3個表格

                Log.d(TAG,"筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數筆數") ;
                Log.d(TAG,"共有多少筆:" + cursor.getCount()) ;

                String query = "SELECT " +
                        "idclist.idcName, " +
                        "oldflist.oldfName, "  +
                        "rfidlist.cableName, " +
                        "rfidlist.jsonData, "  +
                        "rfidlist.status, "     +
                        "oldflist.floor, " +
                        "rfidlist.tagId, " +
                        "rfidlist.deviceName, " +
                        "oldflist.rows, " +
                        "oldflist.cabinet, " +
                        "oldflist.box, " +
                        "oldflist.ports, " +
                        "rfidlist.updateUser, " +
                        "rfidlist.updateTime " +

                        "FROM idclist "+
                        "JOIN oldflist "+
                        "ON idclist.idcId = oldflist.idcId "+
                        "JOIN rfidlist " +
                        "ON oldflist.oldfId = rfidlist.oldfId " +
                        "WHERE rfidlist.tagId = ? "   +
                        "AND rfidlist.status = ? "    +
                        "AND rfidlist.cableName = ? " +
                        "AND oldflist.floor = ? "     +
                        "AND oldflist.rows = ? "      +
                        "AND oldflist.cabinet = ? "   +
                        "AND oldflist.box =  ? "      +
                        "AND oldflist.ports = ? "     +
                        "AND rfidlist.deviceName = ? " +
                        "AND rfidlist.updateUser = ? " +
                        "AND rfidlist.updateTime = ? " +
                        "AND idclist.idcName = ?"  ;

                String[] selectionArgs = new String[]{
                        gettagid(),
                        getstatus(),
                        getcablename(),
                        getfloor(),
                        getrow(),
                        getcabinet(),
                        getbox() ,
                        getport(),
                        getequname(),
                        getupdater(),
                        getupdatetime(),
                        getidcroom()

                        } ;

                Cursor c  = db.rawQuery(query, selectionArgs) ;

                Log.d(TAG,"查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數查到筆數");
                Log.d(TAG,"查到筆數 >>>> " + c.getCount());

                // 下面將查到的資料顯示出來 !
                // 接著 再過濾 jsondata string
                // 找出最終的資料

                String  tagid3 ,
                        status ,
                        cablename3 ,
                        from_col1 ="",
                        to_col2 ="",
                        floor  ,
                        idcname3 ,
                        row3  ,
                        cabinet3 ,
                        box3 ,
                        port3,
                        comment7 ="" ,
                        cablefirm3 ="",
                        devicename3 ,
                        buildingfirm3 ="" ,
                        transmissionend3 ="" ,
                        deviceport3 ="" ,
                        updateuser3 ,
                        updatetime3 ;   // all fields

                String  jsondata  ;

                while(cursor.moveToNext()) {

                    Log.d(TAG,"**********************************************");

                    tagid3 = cursor.getString(cursor.getColumnIndexOrThrow("tagId"));
                    Log.d(TAG, "TAG ID: " + tagid3.toString());

                    status  = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                    if (status.equals("0"))
                        Log.d(TAG,"狀態: " + "未使用") ;
                    else
                        Log.d(TAG,"狀態: " + "已使用") ;

                    cablename3 = cursor.getString(cursor.getColumnIndexOrThrow("cableName"));
                    Log.d(TAG,"光纜名稱: " + cablename3.toString());
                    floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
                    Log.d(TAG,"樓層: " + floor) ;
                    idcname3 = cursor.getString(cursor.getColumnIndexOrThrow("idcName"));
                    Log.d(TAG,"機房名稱: " + idcname3) ;
                    row3 = cursor.getString(cursor.getColumnIndexOrThrow("rows"));
                    Log.d(TAG,"排: " + row3);
                    cabinet3 = cursor.getString(cursor.getColumnIndexOrThrow("cabinet"));
                    Log.d(TAG,"櫃: " +  cabinet3) ;
                    box3 = cursor.getString(cursor.getColumnIndexOrThrow("box"));
                    Log.d(TAG,"箱: "+ box3.toString());
                    port3 = cursor.getString(cursor.getColumnIndexOrThrow("ports")) ;
                    Log.d(TAG,"埠: " + port3);
                    devicename3 = cursor.getString(cursor.getColumnIndexOrThrow("deviceName"));
                    Log.d(TAG,"設備名稱: " +devicename3);
                    updateuser3 = cursor.getString(cursor.getColumnIndexOrThrow("updateUser"));
                    Log.d(TAG,"修改人: " + updateuser3);
                    updatetime3 = cursor.getString(cursor.getColumnIndexOrThrow("updateTime"));
                    Log.d(TAG,"修改時間: " + updatetime3);
                    jsondata = cursor.getString(cursor.getColumnIndexOrThrow("jsonData"));
                    Log.d(TAG,"jsondata:" + jsondata.toString());

                    // extract those fields (column1 to column7) from jsondata
                    ///////////////

                    try {

                        JSONObject jsonObject = new JSONObject(jsondata);
                        from_col1  = jsonObject.getString("column1");
                        to_col2    = jsonObject.getString("column2");
                        comment7   = jsonObject.getString("column7") ;
                        cablefirm3       = jsonObject.getString("column3") ;
                        buildingfirm3    = jsonObject.getString("column4") ;
                        transmissionend3 = jsonObject.getString("column5");
                        deviceport3      = jsonObject.getString("column6") ;

                        Log.d(TAG,"from :" + from_col1);
                        Log.d(TAG,"to :"   + to_col2) ;
                        Log.d(TAG,"comment :" + comment7) ;
                        Log.d(TAG,"cablefirm :" + cablefirm3 ) ;
                        Log.d(TAG,"buildingfirm :" + buildingfirm3 ) ;
                        Log.d(TAG,"transmissionend3 :" + transmissionend3) ;
                        Log.d(TAG,"deviceport3 :" + deviceport3) ;

                    } catch (JSONException e) {

                        Log.d(TAG,"錯誤:" + e.getMessage().toString()) ;

                    }

                }

            }
        });


        // 下面是spinner 的展開項目
        /*
        country= new String[3];
        for ( int i = 0 ; i <3 ; i++ ) {
            if (i == 0)
                country[i] = "未使用";
            else if (i == 1)
                country[i] = "已使用";
            else
                country[i] = "損毀";
        }

         */

        /* the following codes
        Spinner spin = (Spinner) findViewById(R.id.spinnerstatus);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "第 "+ Integer.toString(position+1) +" 個", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */

        //Creating the ArrayAdapter instance having the country list

        /* spiner 需要的spinner's apapter
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
         */

    }

    private void Setidcnamesearchback(String idcroomname) {
        this.idcnamesearchback = idcroomname ;

    }

    private String Getidcnamesearchback() {

        return this.idcnamesearchback  ;
    }

    private void Setcablenamesearchback(String cablename) {
        assert cablename != null ;
        this.cablenamesearchback = cablename ;

    }
    private String Getcablenamesearchback() {

        return this.cablenamesearchback  ;
    }

    private void Setportsearchback(String port) {
        assert port!= null ;
        this.portsearchback = port ;

    }
    private String Getportsearchback() {

        return this.portsearchback  ;
    }
    private void Setdevicenamesearchback(String devicename) {
        assert devicename !=null ;
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
        Log.d(TAG,"tagid :" + this.tagid);
        return this.tagid ;
    }

    private void  setstatus(String status) {
        assert status!=null ;
        Log.d(TAG,"status :"+ status);
         this.status = status;
    }
    private String getstatus() {
        assert status!=null ;
        Log.d(TAG,"status :"+ this.status);
        return this.status ;
    }
    private void setcablename(String cablename) {
        assert cablename !=null ;
        Log.d(TAG,"cablename :" + cablename);
        this.cablename = cablename ;
    }

    private String getcablename() {
        assert this.cablename !=null ;
        Log.d(TAG,"cablename :" + this.cablename);
        return this.cablename ;
    }
    private void setfrom(String from) {
        assert from!=null;
        Log.d(TAG,"from :" + from);
        this.from = from ;
    }

    private String getfrom() {
        assert this.from!=null;
        Log.d(TAG,"from :" + this.from);
        return this.from ;
    }
    private void setto(String to) {
        assert to!=null ;
        Log.d(TAG,"to :" + to);
        this.to = to ;
    }

    private String getto() {
        assert this.to!=null ;
        Log.d(TAG,"to :" + this.to);
        return this.to ;
    }
    private void setfloor(String floor ) {
        assert floor != null ;
        Log.d(TAG,"floor :" + floor);
        this.floor = floor ;

    }

    private String getfloor() {
        assert this.floor != null ;
        Log.d(TAG,"floor :" + this.floor);
        return this.floor ;

    }
    private void setidcroom(String room) {
        assert room!=null ;
        Log.d(TAG,"room :" + room);
        this.idcroom = room ;

    }

    private String getidcroom() {
        assert this.idcroom!=null ;
        Log.d(TAG,"room :" + this.idcroom);
        return this.idcroom ;

    }
    private void setrow(String row ) {
        assert row !=null ;
        Log.d(TAG,"row :" + row);
        this.row = row ;

    }

    private String getrow() {
        assert this.row !=null ;
        Log.d(TAG,"row :" + this.row);
        return this.row ;

    }
    private void setcabinet(String cabinet) {
        assert cabinet != null ;
        Log.d(TAG,"cabinet :" + cabinet);

        this.cabinet = cabinet ;
    }

    private String getcabinet() {
        assert this.cabinet != null ;
        Log.d(TAG,"cabinet :" + this.cabinet);

        return this.cabinet ;
    }

    private void setbox(String box) {
        assert box!= null ;
        Log.d(TAG,"box :" + box);

        this.box = box ;

    }

    private String getbox() {
        assert this.box!= null ;
        Log.d(TAG,"box :" + this.box);

        return this.box ;

    }
    private void setport(String port ) {
        assert port!= null ;
        Log.d(TAG,"port :" + port);
        this.port= port ;
    }

    private String getport() {
        assert this.port!= null ;
        Log.d(TAG,"port :" + this.port);
        return this.port ;
    }


    private void setcomment(String comment) {
        assert comment!=null ;
        Log.d(TAG,"comment :" + comment);
        this.comment = comment ;

    }

    private String getcomment() {
        assert this.comment!=null ;
        Log.d(TAG,"comment :" + this.comment);
        return this.comment ;

    }
    private void setcablefirm(String cablefirm) {
        assert cablefirm != null ;
        Log.d(TAG,"cablefirm :" + cablefirm);
        this.cablefirm = cablefirm ;
    }

    private String getcablefirm() {
        assert this.cablefirm != null ;
        Log.d(TAG,"cablefirm :" + this.cablefirm);
        return this.cablefirm ;
    }
    private void setequname(String equname) {
        assert equname !=null ;
        Log.d(TAG,"equname :" + equname);

        this.devicename = equname ;

    }

    private String getequname() {
        assert this.devicename !=null ;
        Log.d(TAG,"equname :" + this.devicename);

        return this.devicename ;

    }

    private void setbuildingfirm(String buildingfirm) {
        assert buildingfirm!= null ;
        Log.d(TAG,"buildingfirm :" + buildingfirm);

        this.buildingfirm = buildingfirm  ;
    }

    private String  getbuildingfirm() {
        assert this.buildingfirm!= null ;
        Log.d(TAG,"buildingfirm :" + this.buildingfirm);

        return this.buildingfirm ;
    }
    private void settransreciever (String transreciever) {
        assert transreciever!=null ;
        Log.d(TAG,"transreciever :" + transreciever);
        this.transmissionend = transreciever ;

    }

    private String gettransreciever () {
        assert this.transmissionend!=null ;
        Log.d(TAG,"transreciever :" + this.transmissionend);
        return this.transmissionend  ;

    }
    private void settransport(String transport) {
        assert transport != null ;
        Log.d(TAG,"transport :" + transport);
        this.transmissionport = transport ;
    }

    private String gettransport() {
        assert this.transmissionport != null ;
        Log.d(TAG,"transport :" + this.transmissionport);
        return this.transmissionport ;
    }
    private void setupdater (String updater) {
        assert updater!=null ;
        Log.d(TAG,"updater :" + this.updateuser);
        this.updateuser = updater ;

    }

    private String getupdater () {
        assert this.updateuser!=null ;
        Log.d(TAG,"updater :" + this.updateuser);
        return this.updateuser ;

    }
    private void setupdatetime (String updatetime) {

        assert updatetime !=null ;
        Log.d(TAG,"updatetime :" + this.updatetime);

        this.updatetime = updatetime ;
    }

    private String getupdatetime () {

        assert this.updatetime !=null ;
        Log.d(TAG,"updatetime :" + this.updatetime);
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
    }

}