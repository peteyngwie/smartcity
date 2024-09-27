package com.tra.loginscreen;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//////////////////////////////////////////
// This is SQLite DataBase helper class
// It is responsible for storing 纜線資料
//
public class DBOpenHelper extends SQLiteOpenHelper {

    private final String DB_NAME = "CableDataDB.db";
    private String TABLE_NAMERFIDFormat   = "rfidformat";  // rfid format table
    private String TABLE_NAMEOLDFList     = "oldflist"  ;  // rfid format table
    private String TABLE_NAMERFIDList     = "rfidlist"  ;  // rfid format table
    private String TABLE_NAMEIDCList      = "idclist"   ;  // idc format table
    private String TABLE_NAMEIPList       = "iplist"    ;  // ip address table


    private final int DB_VERSION = 1;    // database version
    DBOpenHelper mDBHelper;
    private String TableName;   // table name

    public DBOpenHelper(@Nullable Context context   ,

                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version ,
                        String TableName

                        /* Context context */ )
    {
            // super(context, name, null, version);
               super(context, name, factory, version);
               this.TableName = TableName ;  // table name creation
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "DBOpenHelper onCreate ! ");

        // The following codes which construct 4 tables . They are rfid format , oldf List , idc List
        // and rfid List , respectively.
        // rfid format table : For each item which owns 6 attributes .

        String SQLTableRFIDFormat  = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERFIDFormat  + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ids TEXT ,"         +
                "display TEXT ,"     +
                "json INTEGER ,"     +
                "required INTEGER ," +
                "editable INTEGER ," +
                "visible INTEGER"    +
                ");";                     // create table rfidformat SQL command

        db.execSQL(SQLTableRFIDFormat);   // create it !  (rfid table)


        String SQLTableOLDFList = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMEOLDFList  + "( " +
        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "oldfId INTEGER,"  +
                "oldfName TEXT , " +
                "idcId INTEGER,"   +
                "ports INTEGER,"   +
                "tagId TEXT,"      +
                "connector TEXT,"  +
                "floor TEXT,"      +
                "rows TEXT,"       +      // Notice ! the field should use rows , not row .
                "cabinet TEXT,"    +
                "box TEXT "        +
                ");";                     // create table oldflist SQL command

        db.execSQL(SQLTableOLDFList);


        String SQLTableRFIDList = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERFIDList  + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rfidsId INTEGER, "  +
                "tagId TEXT, " +
                "oldfId INTEGER, "+
                "cableName TEXT, " +
                "port TEXT, " +
                "status TEXT, "+
                "deviceName TEXT, "+
                "updateUser TEXT, "+
                "updateTime TEXT, "+
                "jsonData TEXT "   +
                ");";                      // // create table rfidlist SQL command


        db.execSQL(SQLTableRFIDList);      // create it !  (rfid list table)




        /*
        db.execSQL("CREATE TABLE IF NOT EXISTS rfidformat "   +
                   "(id integer  primary key autoincrement, " +
                   "ids TEXT ,"          +
                   "display TEXT ,"     +
                   "json INTEGER ,"     +
                   "required INTEGER ," +
                   "editable INTEGER ," +
                   "visible INTEGER)" );   // create rfid format table

        // oldf List table : For each item which owns 10 attributes
        db.execSQL("CREATE TABLE IF NOT EXISTS oldfList "    +
                   "(id integer primary key autoincrement, " +
                   "oldfId INTEGER,"+
                   "oldfName TEXT , " +
                   "idcId INTEGER,"+
                   "ports INTEGER," +
                   "tagId TEXT," +
                   "connector TEXT," +
                   "floor TEXT," +
                   "rows TEXT,"  +      // Notice ! the field should use rows , not row .
                   "cabinet TEXT," +
                   "box TEXT )");

        // idc List table : For each item which owns 2 attributes
        db.execSQL("CREATE TABLE IF NOT EXISTS idcList " +
                   "(id integer primary key autoincrement," +
                   "idcId  INTEGER ," +
                   "idcName TEXT)");

        // rfid List table : For each item which owns
        db.execSQL("CREATE TABLE IF NOT EXISTS rfidList " +
                   "(id integer primary key autoincrement," +
                   "rfidsId INTEGER, "  +
                   "tagId TEXT, " +
                   "oldfId INTEGER, "+
                   "cableName TEXT, " +
                   "port TEXT, " +
                   "status TEXT, "+
                   "deviceName TEXT, "+
                   "updateUser TEXT, "+
                   "updateTime TEXT, "+
                   "jsonData TEXT)");

         */

    }   // end of onCreate


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e(TAG, "onUpgrade ... ");

        // if table data was updated , this method was launched !


        db.execSQL("DROP TABLE IF EXISTS rfidformat");      // rfid cable format table
        db.execSQL("DROP TABLE IF EXISTS oldfList"  );      // oldf List table
        db.execSQL("DROP TABLE IF EXISTS idcList"   );      // idc List  table
        db.execSQL("DROP TABLE IF EXISTS rfidList"  );      // rfid List table
        /*
        final String SQL = "DROP TABLE " + TableName;
        db.execSQL(SQL);  // delete table
        */
       //  onCreate(db);   // drop above tables all

    }   // end of onUpgrade

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // if DB version was downgrade , the system call this method automatically .
        onUpgrade(database, oldVersion, newVersion);

    }   // end of onDowngrade method

    public String GetTableName(){

        return this.TableName ;
    }

    public void checkTable()
    {
        // 檢查資料表是否存在, 不存在就新增
        // rfidformat , rfidlist , oldflist , idclist tables

        Log.d(TAG,"checkTable()");

        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + GetTableName().toString() + "'", null);


        if (cursor != null) {

            if (cursor.getCount() == 0) {
                Log.d(TAG,"資料表不存在,建立資料表 ... ") ;

                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + "rfidformat" + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ids TEXT ," +
                        "display TEXT ," +
                        "json INTEGER ," +
                        "required INTEGER ," +
                        "editable INTEGER ," +
                        "visible INTEGER" +
                        ");");    // 建立 rfidformat table

                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + "oldflist" + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "oldfId INTEGER,"  +
                        "oldfName TEXT , " +
                        "idcId INTEGER,"   +
                        "ports INTEGER,"   +
                        "tagId TEXT,"      +
                        "connector TEXT,"  +
                        "floor TEXT,"      +
                        "rows TEXT,"       +      // Notice ! the field should use rows , not row .
                        "cabinet TEXT,"    +
                        "box TEXT "        +
                        ");");                    // 建立 oldflist table


                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + "rfidlist" + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "rfidsId INTEGER, "  +
                        "tagId TEXT, "       +
                        "oldfId INTEGER, "   +
                        "cableName TEXT, "   +
                        "port TEXT, "        +
                        "status TEXT, "      +
                        "deviceName TEXT, "  +
                        "updateUser TEXT, "  +
                        "updateTime TEXT, "  +
                        "jsonData TEXT "     +
                        ");");

                                               // 建立 rfidlist table

                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + "idclist" + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "idcId  INTEGER ," +
                                "idcName TEXT " +
                                 ");");



            }
            else if (cursor.getCount() != 0 ) {

                Log.d(TAG, "資料表存在,並且要清空記錄!");
                Log.d(TAG,"列出全部的表格名稱");

                final ArrayList<String> dirArray = new ArrayList();
                SQLiteDatabase DB = getWritableDatabase();
                Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                int i = 0 ;

                while(c.moveToNext())
                {

                    String s = c.getString(0);
                    if (s.equals("android_metadata")) {
                        //   System.out.println("Get Metadata");  default database meta data
                        continue;
                    } else {

                        dirArray.add(s);
                        Log.d(TAG,"表格名稱:"+dirArray.get(i).toString());
                    }


                    i++ ;
                    Log.d(TAG,"表格名稱:"+dirArray.get(i).toString());

                }    // end of while

                // Clear all records of table !
                SQLiteDatabase db = getWritableDatabase() ;
                    db.execSQL("delete from rfidformat");
                    db.execSQL("delete from oldflist");
                    db.execSQL("delete from rfidlist");
                    db.execSQL("delete from idclist");
            }

            cursor.close();
        }
    }

}
