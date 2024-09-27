package com.tra.loginscreen;

import static android.app.PendingIntent.getActivity;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import com.tra.loginscreen.MyApplication ;  // this is a custom class for getting context of toast

import android.app.Application;
// import android.content.Context;
import android.content.Context;
import android.content.res.AssetManager;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelRead {
    String fileName;

    <context> ExcelRead(String fileName) {


        if (fileName.contains(".xls") == true) {

            Toast.makeText(MyApplication.getAppContext(), "" + "file name is correct", Toast.LENGTH_SHORT).show();
            this.fileName = fileName;
        } else {

            Toast.makeText(MyApplication.getAppContext(), "" + "Say hello", Toast.LENGTH_SHORT).show();
        }

    }


   // public int readExcelFileFromAssets_xls(ArrayList<HashMap<String, String>> CableDat) {
     /*
        FileOutputStream fos = null;
        int index = 1;
        int size = 0;
        */

       /*
        try {

            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = MyApplication.getAppContext().getAssets();


            //  open excel sheet
            myInput = assetManager.open("rfidtry.xls");
            // Create a POI File System object
            // POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            // HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
           //  HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.

           //  Iterator<Row> rowIter = mySheet.rowIterator();

            int rowno = 0; // setting the first row

           //  textshow.append("\n");
           /*
            while (rowIter.hasNext()) {

                HSSFRow myRow = (HSSFRow) rowIter.next();

                if (rowno != 0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno = 0;
                    String sno = "", date = "", det = "";

                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno == 0) {
                            sno = myCell.toString();
                        } else if (colno == 1) {
                            date = myCell.toString();
                        } else if (colno == 2) {
                            det = myCell.toString();
                        }
                        colno++;
                    }
                    //  textshow.append(sno + " -- " + date + "  -- " + det + "\n");
                }
                rowno++;
                size++ ;
            }
        } catch (Exception e) {
            Log.e(TAG, "error " + e.toString());
        }

        return size ;  // return number of items
        */




}





