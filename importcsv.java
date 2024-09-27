package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tra.loginscreen.CSVReader ;
import org.apache.poi.hssf.util.HSSFColor;
import jxl.write.BoldStyle;

public class importcsv extends AppCompatActivity {

    TextView txt ;
    String FileName ;

    TextView importTxt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importcsv);

        txt = (TextView) findViewById(R.id.txt1);


        txt.setMovementMethod(ScrollingMovementMethod.getInstance()); // text contents could be scrolled

        List<String[]> rows = new ArrayList<>();  // all data to rows from data.csv

        FileName = "data.csv";
        CSVReader csvReader = new CSVReader(importcsv.this, FileName);

        try {
            rows = csvReader.readCSV();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rows.size(); i++) {

            txt.setTextSize(7);
            txt.setTextColor(Color.BLUE);  //  this is blue color

            txt.append(rows.get(i)[0] + "--" + rows.get(i)[1] + "--" + rows.get(i)[2] + "--" + rows.get(i)[3] + "--" + rows.get(i)[4] +"\n"  );
            // Log.i(TAG, String.format("row %s: %s, %s", i, rows.get(i)[0], rows.get(i)[2]));
        }  // end of for loop for getting data from

        showCSVImportDialog(FileName);

    }
    private void showCSVImportDialog(String fileName)   {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        importTxt = (TextView)viewGroup.findViewById(R.id.importtxt);


        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.importcsv, viewGroup, false);

        importTxt = (TextView)dialogView.findViewById(R.id.importtxt);
        importTxt.setText("檔案名稱: " +fileName);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btn = dialogView.findViewById(R.id.buttoncsvimport);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}