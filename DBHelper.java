package com.tra.loginscreen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my.db";
    private static final int DB_VERSION = 1;
    private final static String _TableName = "Invoice_Item_Table"; //<-- table name

    public DBHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);   // constructor for DB Helper
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
    final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                       "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       "_IPADD VARCHAR(50)" + ");";

        db.execSQL(SQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);

        onCreate(db);  // after you delete database table , you must create table again
    }
}