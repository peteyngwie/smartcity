package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.os.Bundle;

public class accountMNG1 extends AppCompatActivity {

    // private GridviewAdapter gridAdapter;   //  for gridview adapter
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;

    private GridView gridView;

    private SqliteDatabase mDatabase;
    private ArrayList<Contacts> allContacts=new ArrayList<>();   // allocate all available contacts for use
    private ContactAdapter mAdapter;

    Toolbar toolbar ;

    private String accountnew ;
    private String pwdnew ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_mng1);

        prepareList();

        // prepared arraylist and passed it to the Adapter class
        // gridAdapter = new GridviewAdapter(this,listCountry, listFlag);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        // gridView.setAdapter(gridAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //  Toast.makeText(accountMNG1.this, gridAdapter.getItem(position), Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        Toast.makeText(accountMNG1.this, "查詢帳號", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(accountMNG1.this, "新增帳號", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(arg1.getContext() , createaccount.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(accountMNG1.this, "修改帳號", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(accountMNG1.this, "刪除帳號", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(accountMNG1.this, "錯誤的事件", Toast.LENGTH_SHORT).show();
                        break;

                }   // end of switch
            }
        });


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);  // initialize toolbar

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // when you click the back arrow < , current activity go back home activity
            }
        });


        mDatabase = new SqliteDatabase(this);
        allContacts = mDatabase.listContacts();

        if(allContacts.size() > 0){
           // contactView.setVisibility(View.VISIBLE);
            mAdapter = new ContactAdapter(this, allContacts);
            Toast.makeText(this, "目前已存在"+Integer.toString(allContacts.size())+"筆帳號!", Toast.LENGTH_SHORT).show();
           // contactView.setAdapter(mAdapter);

        }else {
            // contactView.setVisibility(View.GONE); //
            Toast.makeText(this, "目前無任何帳號!", Toast.LENGTH_LONG).show();
        }

    }

    public void prepareList() {

        listCountry = new ArrayList<String>();

        listCountry.add("查詢帳號");
        listCountry.add("新增帳號");
        listCountry.add("修改帳號");
        listCountry.add("刪除帳號");

        listFlag = new ArrayList<Integer>(); // for storing those images

        listFlag.add(R.drawable.queryaccico);
        listFlag.add(R.drawable.addaccico);
        listFlag.add(R.drawable.modaccico);
        listFlag.add(R.drawable.delaccico);

    }
}