package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class createcable extends AppCompatActivity {

    // on below line we are creating variables.
    private ListView languageLV;
    private Button addBtn;
    private EditText f1,f2,f3;
    private ArrayList<cabledat> cableList;

    private cabledat obj ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcable);

        obj= new cabledat();

        languageLV = findViewById(R.id.idLVLanguages);
        addBtn = findViewById(R.id.idBtnAdd);

        f1 = findViewById(R.id.cablenameid);
        f2 = findViewById(R.id.tagid);
        f3 = findViewById(R.id.from);

        cableList = new ArrayList<cabledat>();

         obj.setf1("山側96c\n");
         obj.setf2("0045\n");
         obj.setf3("MPLS01-RX\n");
        // on below line we are adding items to our list
        cableList.add(obj);

        // on the below line we are initializing the adapter for our list view.
        ArrayAdapter<cabledat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cableList);

        // on below line we are setting adapter for our list view.
        languageLV.setAdapter(adapter);

        // on below line we are adding click listener for our button.
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting text from edit text
                cabledat obj1 ;

                obj1 = new cabledat() ;

                String cablename = f1.getText().toString();
                String tagid     = f2.getText().toString();
                String from      = f3.getText().toString();

                // on below line we are checking if item is not empty
                if (!cablename.isEmpty() && !tagid.isEmpty() && !from.isEmpty()) {

                    // on below line we are adding item to our list.
                    obj1.setf1(cablename);
                    obj1.setf2(tagid);
                    obj1.setf3(from);     // set dummy data

                    cableList.add(obj1);

                    // on below line we are notifying adapter
                    // that data in list is updated to
                    // update our list view.
                    adapter.notifyDataSetChanged();

                    // clear all input fields

                    f1.getText().clear();
                    f2.getText().clear();
                    f3.getText().clear();

                }
                else  {
                    Toast.makeText(createcable.this, "輸入欄位不能為空 !", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private class cabledat {
        String f1 , f2 ,f3 ;

        public cabledat () { }
        public void setf1 (String f1) { this.f1 = f1 ;  }
        public void setf2 (String f2) { this.f2 = f2 ;  }
        public void setf3 (String f3) { this.f3= f3  ;  }

        public String getf1() { return this.f1 ; }
        public String getf2() { return this.f2 ; }
        public String getf3() { return this.f3 ; }
    }
}