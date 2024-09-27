package com.tra.loginscreen;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    Context context;
    String fileName;
    List<String[]> rows = new ArrayList<>();

    public CSVReader(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;

    }

    public List<String[]> readCSV() throws IOException {

        InputStream is = context.getAssets().open(fileName);  // open the file

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {

            String[] row = line.split(csvSplitBy);   // extract data from lineby row and using common dot ,
            rows.add(row);
        }
        return rows;
    }
}