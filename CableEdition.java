package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CableEdition extends AppCompatActivity {

    private RecyclerView recycler_view;
    // private EditListAdapter adapter;
    // private ArrayList<Cabledata> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_edition);

        // 準備資料，塞50個項目到ArrayList裡

        /*
        for(int i = 0; i < 150; i++) {
            Cabledata c = new Cabledata();
            c.setCableno("0ce2801190200078a4e7ba03");
            c.setRouteno("1");
            c.setFloor("3");
            c.setRoomname("中偉科技");
            mData.add(c);
        }

         */

        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_viewedit);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // 將資料交給adapter
        // adapter = new EditListAdapter (mData);
        // 設置adapter給recycler_view
        // recycler_view.setAdapter(adapter);
    }


    }
