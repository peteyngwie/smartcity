package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.ArrayList;

public class CableDeletion extends AppCompatActivity {
    private RecyclerView recycler_view;
    private DelListAdapter adapter;
    private ArrayList<String> mData = new ArrayList<>();

    private String content ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_deletion);

            // 準備資料，塞50個項目到ArrayList裡

            for (int i = 0; i < 150; i++) {
                if (i % 10 == 0)
                    mData.add("標籤編號 : A1B2000000000000000");
                else if (i % 3 == 0)
                    mData.add("標籤編號 : C1B0000200000000000");
                else if (i % 7 == 0)
                    mData.add("標籤編號 : 303960624384A000000");
                else if (i % 11 == 0)
                    mData.add("標籤編號 : 2038787487370004556");
                else if ( i % 17 ==0 )
                    mData.add("標籤編號 : D1C0002000000000000");
                else if ( i% 19 == 0 )
                    mData.add("標籤編號 : B2C0002000000000000");
                else if ( i% 23 == 0 )
                    mData.add("標籤編號 : E1D0001000000111000");
                else if ( i% 37 == 0 )
                    mData.add("標籤編號 : F3D0002A01000000000");
                else if ( i% 41 == 0 )
                    mData.add("標籤編號 : 22D0002000000110000");
                else
                    mData.add("標籤編號 : A240002000000012000");
            }

            // 連結元件
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            // 設置RecyclerView為列表型態
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            // 設置格線
            recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            // 將資料交給adapter
            adapter = new DelListAdapter(mData);
            // 設置adapter給recycler_view
            recycler_view.setAdapter(adapter);
        }
    }
