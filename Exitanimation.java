package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Exitanimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitanimation);
        Toast.makeText(this, "this is an exit animation", Toast.LENGTH_SHORT).show();
        finish();
    }
}