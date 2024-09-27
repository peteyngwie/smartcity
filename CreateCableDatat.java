package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CreateCableDatat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckBox chk_select_all;
    private Button btn_delete_all;

    private ArrayList<Model> item_list = new ArrayList<>();
    private ModelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cable_datat);

        initControls();
    }
    private void initControls() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // chk_select_all = (CheckBox) findViewById(R.id.chk_select_all);
        // btn_delete_all = (Button) findViewById(R.id.btn_delete_all);



         for  ( int i = 0 ; i < 20 ; i ++ )
            // item_list.add(new Model("標籤編號:", "EPC:","台鐵海線",false));   // initialization of items

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ModelAdapter(item_list);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Click the item !!", Toast.LENGTH_SHORT).show();

            }
        });

        /*
        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {

                    for (Model model : item_list) {
                        model.setSelected(true);
                    }
                } else {

                    for (Model model : item_list) {
                        model.setSelected(false);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {
                    item_list.clear();
                    mAdapter.notifyDataSetChanged();
                    chk_select_all.setChecked(false);


                } else {
                    Snackbar.make(v, "Please click on select all check box, to delete all items.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

         */

    }
}