package com.tra.loginscreen;

import static android.view.Gravity.CENTER;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;
// import com.blankj.utilcode.util.LogUtils;

//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;


import androidx.appcompat.app.AppCompatActivity;




public class DataExport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);

        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        int[] fixedColumnWidths      =      new int[]{11 ,30 ,30 ,32 ,38 ,38 ,38,38,38,38,38};
        int[] scrollableColumnWidths =      new int[]{    30 ,30 ,32 ,38 ,38 ,38,38,38,38,38};


        int fixedRowHeight = 80;
        int fixedHeaderHeight = 90;
        int size ;

        TableRow row = new TableRow(this);
        //header (fixed vertically)
        TableLayout header = (TableLayout) findViewById(R.id.table_header);

        row.setLayoutParams(wrapWrapTableRowParams);
        row.setGravity(Gravity.LEFT);
        row.setBackgroundColor(Color.YELLOW);

        row.addView(makeTableRowWithText("編號",             fixedColumnWidths[0], fixedHeaderHeight,0));
        row.addView(makeTableRowWithText("標籤序號",          fixedColumnWidths[1], fixedHeaderHeight,1));
        row.addView(makeTableRowWithText("路由編號",          fixedColumnWidths[2], fixedHeaderHeight,2));
        row.addView(makeTableRowWithText("機房名稱(From)",    fixedColumnWidths[3], fixedHeaderHeight,3));
        row.addView(makeTableRowWithText("機房樓層(From)",    fixedColumnWidths[4], fixedHeaderHeight,4));
        row.addView(makeTableRowWithText("機櫃編號(From)",    fixedColumnWidths[5], fixedHeaderHeight,5));
        row.addView(makeTableRowWithText("接續盒編號(From)",   fixedColumnWidths[6], fixedHeaderHeight,6));
        row.addView(makeTableRowWithText("連接埠編號(From)",   fixedColumnWidths[7], fixedHeaderHeight,7));
        row.addView(makeTableRowWithText("纜線編號(From)",     fixedColumnWidths[8], fixedHeaderHeight,8));
        row.addView(makeTableRowWithText("纜線名稱(From)",     fixedColumnWidths[8], fixedHeaderHeight,9));

        // row.addView(makeTableRowWithText("   ",     fixedColumnWidths[4], fixedHeaderHeight));


        header.addView(row);  // Add all data to first row (header)

        TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
        TableLayout scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);

        for (int i = 0; i < 100; i++) {


            TextView fixedView = makeTableRowWithText(Integer.toString(i+1), fixedColumnWidths[0], fixedRowHeight,0);
            fixedView.setBackgroundColor(Color.YELLOW);
            fixedView.setTextSize(24);
            fixedView.setTextColor(Color.BLACK);
            fixedColumn.addView(fixedView);
            fixedView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            row = new TableRow(this);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(CENTER);
            row.setBackgroundColor(Color.WHITE);

            row.addView(makeTableRowWithText("value 1", scrollableColumnWidths[0], fixedRowHeight , 1));
            row.addView(makeTableRowWithText("value 2", scrollableColumnWidths[1], fixedRowHeight,2));
            row.addView(makeTableRowWithText("value 3", scrollableColumnWidths[2], fixedRowHeight,3));
            row.addView(makeTableRowWithText("value 4", scrollableColumnWidths[3], fixedRowHeight,4));
            row.addView(makeTableRowWithText("value 5", scrollableColumnWidths[4], fixedRowHeight,5));
            row.addView(makeTableRowWithText("value 6", scrollableColumnWidths[5], fixedRowHeight,6));
            row.addView(makeTableRowWithText("value 7", scrollableColumnWidths[6], fixedRowHeight,7));
            row.addView(makeTableRowWithText("value 8", scrollableColumnWidths[7], fixedRowHeight,8));
            row.addView(makeTableRowWithText("value 9", scrollableColumnWidths[8], fixedRowHeight,9));
            row.addView(makeTableRowWithText("value 10", scrollableColumnWidths[9], fixedRowHeight,10));


            int x = row.getChildCount() ;
            Toast.makeText(this, "count : "+x, Toast.LENGTH_SHORT).show();

            scrollablePart.addView(row);
        }

        scrollablePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

        private TextView recyclableTextView;

        public TextView makeTableRowWithText(String text,
                                             int widthInPercentOfScreenWidth,
                                             int fixedHeightInPixels ,
                                             int index )
        {

            int screenWidth = getResources().getDisplayMetrics().widthPixels;

            recyclableTextView = new TextView(this);

            String str1 = "編號"          ;
            String str2 = "標籤序號"       ;
            String str3 = "路由編號"       ;
            String str4 = "機房名稱(From)"   ;
            String str5 = "機房樓層(From)"   ;
            String str6 = "機櫃編號(From)"   ;
            String str7 = "接續盒編號(From)" ;
            String str8 = "連接埠編號(From)"  ;
            String str9 = "纜線編號(From)"   ;
            String str10= "纜線名稱(From)"   ;



            if (    text.equals(str1)  ||
                    text.equals(str2)  ||
                    text.equals(str3)  ||
                    text.equals(str4)  ||
                    text.equals(str5)  ||
                    text.equals(str6)  ||
                    text.equals(str7)  ||
                    text.equals(str8)  ||
                    text.equals(str9)  ||
                    text.equals(str10) )
                recyclableTextView.setTextColor(Color.BLACK);
            else {
                switch (index) {
                    case 1 :
                        recyclableTextView.setTextColor(Color.BLUE);
                        break ;
                    case 2 :
                        recyclableTextView.setTextColor(Color.CYAN);
                        break ;
                    case 3 :
                        recyclableTextView.setTextColor(Color.BLACK);
                        break ;
                    case 4 :
                        recyclableTextView.setTextColor(Color.GREEN);
                    case 5 :
                        recyclableTextView.setTextColor(Color.rgb(100,23,245));
                        break ;
                    case 6 :
                        recyclableTextView.setTextColor(Color.GRAY);
                    case 7 :
                        recyclableTextView.setTextColor(Color.YELLOW);
                        break ;
                    case 8 :
                        recyclableTextView.setTextColor(Color.RED);
                        break ;
                    case 9 :
                        recyclableTextView.setTextColor(Color.MAGENTA);
                        break ;
                    default:
                        break;
                }
            }


            recyclableTextView.setText(text);
            recyclableTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            recyclableTextView.setTextSize(18);

            recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);

            recyclableTextView.setHeight(fixedHeightInPixels);
            return recyclableTextView;
        }



}