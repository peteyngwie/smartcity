package com.tra.loginscreen;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

public class DBOpenHelper1  extends SQLiteOpenHelper {

    private static final String name = "database.db";  // 資料庫名稱
    private static final int version = 1;             // 資料庫版本

    public DBOpenHelper1(Context context) {

        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "DBOpenHelper" + "資料表建立!");

        // this message that shows information about onCreate method was launched

        // 預設建立4個表 - rfid format , oldflist , rfidlist , idclist 表
        // RFID 格式表格

        db.execSQL("CREATE TABLE IF NOT EXISTS rfidformat (id integer primary key autoincrement , " +
                "ids TEXT, " +
                "display TEXT, " +
                "json INTEGER, " +
                "required INTEGER, " +
                "editable INTEGER, " +
                "visible INTEGER)");

        Log.d(TAG, "資料表 rfidformat 建立");

        // OLDF列表

        db.execSQL("CREATE TABLE IF NOT EXISTS oldflist (id integer primary key autoincrement , " +
                "oldfId INTEGER, " +
                "oldfName TEXT,  " +
                "idcId INTEGER, " +
                "ports INTEGER, " +
                "tagId TEXT, " +
                "connector TEXT, " +
                "floor TEXT, " +
                "rows TEXT, " +
                "cabinet TEXT, " +
                "box TEXT)");

        Log.d(TAG, "資料表 oldflist 建立");

        // RFID 列表

        db.execSQL("CREATE TABLE IF NOT EXISTS rfidlist   (id integer primary key autoincrement , " +
                "rfidsId INTEGER, " +
                "tagId TEXT, " +
                "oldfId INTEGER, " +
                "cableName TEXT, " +
                "port TEXT, " +
                "status TEXT, " +
                "deviceName TEXT, " +
                "updateUser TEXT, " +
                "updateTime TEXT, " +
                "jsonData TEXT )");


        Log.d(TAG, "資料表 rfidlist 建立");


        // IDC 列表

        db.execSQL("CREATE TABLE IF NOT EXISTS idclist    (id integer primary key autoincrement , " +
                "idcId  INTEGER ," +
                "idcName TEXT)");

        Log.d(TAG, "資料表 idclist 建立");


        // ip address 列表


        db.execSQL("CREATE TABLE IF NOT EXISTS ipaddress  (id integer primary key autoincrement , " +
                "address TEXT )");

        Log.d(TAG, "資料表 IP 位址建立");


    }   // end of create

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");

        // Execute SQL command for creating those tables
        //

        db.execSQL("DROP TABLE IF EXISTS rfidformat");  // rfid format
        db.execSQL("DROP TABLE IF EXISTS oldflist");    // oldf list
        db.execSQL("DROP TABLE IF EXISTS rfidlist");    // rfid list
        db.execSQL("DROP TABLE IF EXISTS idclist");     // idc  list
        db.execSQL("DROP TABLE IF EXISTS ipaddress");   // ipaddress

        onCreate(db);

    }

    public void checkTable(String table ) {
        // 檢查資料表是否存在, 不存在就新增
        // rfidformat , rfidlist , oldflist , idclist tables

        Log.d(TAG, "checkTable()");

        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + table.toString() + "'", null);


        if (cursor != null) {

            if (cursor.getCount() == 0) {
                Log.d(TAG, "資料表不存在,建立資料表 ... ");

                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + "rfidformat" + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ids TEXT UNIQUE," +
                        "display TEXT UNIQUE," +
                        "json INTEGER UNIQUE," +
                        "required INTEGER UNIQUE," +
                        "editable INTEGER UNIQUE," +
                        "visible INTEGER UNIQUE" +
                        ");");    // 建立 rfidformat table


            } else if (cursor.getCount() != 0) {

                Log.d(TAG, "資料表存在,並且要清空記錄!");
                Log.d(TAG, "列出全部的表格名稱");

                final ArrayList<String> dirArray = new ArrayList();
                SQLiteDatabase DB = getWritableDatabase();
                Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                int i = 0;

                while (c.moveToNext()) {

                    String s = c.getString(0);
                    if (s.equals("android_metadata")) {
                        //   System.out.println("Get Metadata");  default database meta data
                        continue;
                    } else {

                        dirArray.add(s);
                        Log.d(TAG, "表格名稱:" + dirArray.get(i).toString());
                    }

                    i++;
                    Log.d(TAG, "表格名稱:" + dirArray.get(i).toString());

                }    // end of while
            }
        }
    }

    public void ListAllTablesOfDB () {

        // This method which shows all available tables in database

        Log.d(TAG,"((((((((((((( 列出全部的表格 )))))))))))))))");

        SQLiteDatabase db = getWritableDatabase();   // open a database

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {

            // 列出所有的表
            while ( !c.isAfterLast() ) {

                Log.d(TAG,"表名 : "+c.getString(0));
                c.moveToNext();  // move to cursor

            }   // end of while

        }  // move cursor to first location

        c.close();
    }      // end of ListAllTablesOfDB


    public static boolean TableHaveData(SQLiteDatabase db,String tablename)
    {
        /*
        Cursor cursor;
        boolean a = false;
        cursor = db.rawQuery("select name from sqlite_master where type='table' ", null);

         */

        int amount=0;
        Cursor c = db.rawQuery("select * from "+ tablename, null);
        amount = c.getCount();

        if (amount == 0)
            return true ;
        else
            return false ;

        /*

        while(cursor.moveToNext())
        {
            // 找表
            String name = cursor.getString(0);

            if(name.equals(tablename))
            {
                a = true;
            }
            Log.i("System.out", name);
        }
        if(a)
        {
            cursor=db.query(tablename,null,null,null,null,null,null);
            // 是不是空表
            if(cursor.getCount()>0)
                return true;
            else
                return false;
        }
        else
               return false;


        int amount=0;
        Cursor c = db.rawQuery("select * from history_info", null);
        amount=c.getCount();

         */

    }

}