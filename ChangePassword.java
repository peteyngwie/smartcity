package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChangePassword extends AppCompatActivity {

    private ListView mList;
    //ListView数据源
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null ) {

            String username = bundle.getString("username");
            String token = bundle.getString("token");
            String password = bundle.getString("password");

            Toast.makeText(this, "password >>>>>>>>>>>" + password.toString(), Toast.LENGTH_SHORT).show();

            data  = new ArrayList<>();
            mList = (ListView)findViewById(R.id.mList);

            data.add(username);   // add the username

        }

        ChangePWDAdapter adapter = new ChangePWDAdapter(data);
        mList.setAdapter(adapter);
        //ListView item点击事件
        mList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChangePassword.this,"我是item点击事件 i = " + i + "l = " + l,Toast.LENGTH_SHORT).show();
            }
        });

    }
}